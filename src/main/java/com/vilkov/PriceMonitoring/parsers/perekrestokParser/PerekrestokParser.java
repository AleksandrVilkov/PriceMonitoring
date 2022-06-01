package com.vilkov.PriceMonitoring.parsers.perekrestokParser;

import com.vilkov.PriceMonitoring.model.Product;
import com.vilkov.PriceMonitoring.parsers.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.logging.Logger;

public class PerekrestokParser implements Parser {


    public static Product getProduct(String url) {
        Logger logger = Logger.getLogger("PerekrestokParser");
        if (!url.contains("www.perekrestok.ru")) {
            logger.warning("url does not apply to www.perekrestok.ru");
            return null;
        }
        String price = null;
        String name = null;
        try {
            Document document = Jsoup.connect(url).get();
            price = document.select("div.price-new")
                    .first().childNode(0).toString()
                    .replace("\n", "")
                    .replace("&nbsp;", "");
            name = document.title().replace(" - купить с доставкой на дом в Перекрёстке", "");

        } catch (IOException e) {
            logger.warning(e.getMessage());
        }

        if (price == null || name == null) {
            logger.warning("There is a null value, it is impossible to create a product!");
            return null;
        }
        return new Product(name, price, "perekrestok");
    }
}

