package com.vilkov.PriceMonitoring.model.parsers.perekrestokParser;

import com.vilkov.PriceMonitoring.model.*;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.Money;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

public class PerekrestokParser implements Parser {


    public static Product getProduct(String url) {
        Logger logger = Logger.getLogger("PerekrestokParser");
        if (!url.contains("www.perekrestok.ru")) {
            logger.warning("url does not apply to www.perekrestok.ru");
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, "url does not apply to www.perekrestok.ru"));
        }
        Money price = new Money();
        String name;
        try {
            Document document = Jsoup.connect(url).get();
            double amount = Double.parseDouble(document.select("div.price-new")
                    .first().childNode(0).toString()
                    .replace("\n", "")
                    .replace("&nbsp;", "")
                    .replace("₽", "")
                    .replace(",", "."));

            price.setAmount(amount).setCurrency(Currency.RUB);
            name = document.title().replace(" - купить с доставкой на дом в Перекрёстке", "");

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, e.getMessage()));
        }

        if (price == null || name == null) {
            logger.warning("There is a null value, it is impossible to create a product!");
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, "There is a null value, it is impossible to create a product!"));
        }
        return new Product(name, price, "perekrestok", url,new Date(), new Message(Status.SUCCESS, "Success!"));
    }
}

