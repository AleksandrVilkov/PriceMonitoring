package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.Money;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HTMLPageParser {

    private static final Logger logger = new Logger(Logger.getLoggerProperties().getProperty("modelLogFolder"),
            Logger.getLoggerProperties().getProperty("modelLogFileName"));

    public static Product getProduct(String url, String cssQuery, String shop) {
        return getProduct(url, cssQuery, new ArrayList<>(), new ArrayList<>(), shop);
    }

    public static Product getProduct(String url, String cssQuery, List<String> extraHeaderCharacters, String shop) {
        return getProduct(url, cssQuery, extraHeaderCharacters, new ArrayList<>(), shop);
    }

    public static Product getProduct(String url, String cssQuery, List<String> extraItemsName, List<String> extraItemsPrice, String shop) {
        Money price = new Money();
        String name;
        try {
            Document document = Jsoup.connect(url).get();
            Element rubPriceElement = document.select(cssQuery)
                    .first();
            assert rubPriceElement != null;
            TextNode rubTextNode = (TextNode) rubPriceElement.childNodes().get(0);
            String rub = rubTextNode.getWholeText();
            for (String extra : extraItemsPrice) {
                rub = rub.replace(extra, "");
            }
            if (rub.contains(",")) {
                String[] part = rub.split(",");
                String pens = "0." + part[1];
                price.setAmount(Double.parseDouble(part[0]) + Double.parseDouble(pens));
                price.setCurrency(Currency.RUB);
            } else {
                price.setAmount(Double.parseDouble(rub)).setCurrency(Currency.RUB);
            }
            name = document.title();
            for (String extra : extraItemsName) {
                name = name.replace(extra, "");
            }
        } catch (IOException e) {
            logger.save(new Date() + "Exception in HTMLPageParser: " + e.getMessage());
            return new Product(null, null, shop, null, new Date(),
                    new Message(Status.ERROR, e.getMessage()));
        }

        if (price.getAmount() == 0) {
            logger.save(new Date()
                    + " ERROR in amount price from "
                    + url + ". There is a null value, it is impossible to create a product!");
            return new Product(null, null, shop, null, new Date(),
                    new Message(Status.ERROR, "There is a null value, it is impossible to create a product!"));
        }
        return new Product(name, price, shop, url, new Date(), new Message(Status.SUCCESS, "Success!"));
    }

}
