package com.vilkov.PriceMonitoring.model.parsers.mVideoParser;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.Money;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

public class MVideoParser implements Parser {
    public static Product getProduct(String url) {

        Logger logger = Logger.getLogger("Start MVideoParser");
        if (!url.contains("www.mvideo.ru")) {
            logger.warning("url doesn't apply to www.mvideo.ru");
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, "url doesn't apply to www.mvideo.ru"));
        }

        Money priceMVideoProduct = new Money();
        String nameTmp;
        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Chrome")
                    .cookie("beget", "begetok")
                    .get();
            Element rubPriceElement = document.select("span.price__main-value")
                    .first();
            assert rubPriceElement != null;
            TextNode rubTextNode = (TextNode) rubPriceElement.childNodes().get(0);
            String rub = rubTextNode.getWholeText();

            Element penniesPriceElement = document.select("span.price__main-value").first();
            TextNode penniesTextNode = (TextNode) penniesPriceElement.childNodes().get(0);
            String pennies = penniesTextNode.getWholeText();
            priceMVideoProduct.setAmount(Double.parseDouble(rub + "." + pennies)).setCurrency(Currency.RUB);

            nameTmp = document.title().replace(" | Магазин бытовой техники \"Глобус\"", "");

        } catch (IOException e) {
            logger.warning(e.getMessage());
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, e.getMessage()));
        }

        if (priceMVideoProduct.getAmount() == 0) {
            logger.warning("There is a null value, it is impossible to create a product!");
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, "There is a null value, it is impossible to create a product!"));
        }
        return new Product(nameTmp, priceMVideoProduct, "MVideo", url, new Date(), new Message(Status.SUCCESS, "Success!"));
    }
}
