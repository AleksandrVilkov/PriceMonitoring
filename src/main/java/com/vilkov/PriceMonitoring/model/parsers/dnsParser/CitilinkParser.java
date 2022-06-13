package com.vilkov.PriceMonitoring.model.parsers.dnsParser;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.Money;
import com.vilkov.PriceMonitoring.model.entity.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

public class CitilinkParser {
    public static Product getProduct(String url) {

        Logger logger = Logger.getLogger("CitilinkParser");
        if (!url.contains("citilink.ru")) {
            logger.warning("url does not apply to citilink.ru");
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, "url does not apply to citilink.ru"));
        }

        Money price = new Money();
        String name;
        try {
            Document document = Jsoup.connect(url).get();
            String title = document.title();
            int bracket = title.lastIndexOf("|");
            String id = title.substring(bracket).replace("| ", "");
            name = document.title().replace("- купить в Ситилинк | ", "").replace(id, "");

            Element rubPriceElement = document.select("#product-item-" + id + " > div.ProductCardLayout__product-description > div.ProductHeader.js--ProductHeader > div.ProductHeader__price-block > div.ProductHeader__price > div.ProductHeader__price-bonus > div > span > span.ProductHeader__price-default_current-price.js--ProductHeader__price-default_current-price")
                    .first();
            assert rubPriceElement != null;
            TextNode rubTextNode = (TextNode) rubPriceElement.childNodes().get(0);
            String rub = rubTextNode.getWholeText().replace("\n", "").replace(" ", "");
            price.setAmount(Double.parseDouble(rub));
            price.setCurrency(Currency.RUB);


        } catch (IOException e) {
            logger.warning(e.getMessage());
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, e.getMessage()));
        }

        if (price.getAmount() == 0) {
            logger.warning("There is a null value, it is impossible to create a product!");
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, "There is a null value, it is impossible to create a product!"));
        }
        return new Product(name, price, "citilink", url, new Date(), new Message(Status.SUCCESS, "Success!"));
    }
}
