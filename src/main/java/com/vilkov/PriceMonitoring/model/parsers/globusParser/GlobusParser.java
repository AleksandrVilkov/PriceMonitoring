package com.vilkov.PriceMonitoring.model.parsers.globusParser;

import com.vilkov.PriceMonitoring.model.*;
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

public class GlobusParser implements Parser {
    public static Product getProduct(String url) {

        Logger logger = Logger.getLogger("GlobusParser");
        if (!url.contains("www.globus.ru")) {
            logger.warning("url does not apply to www.globus.ru");
            return new Product(null, null, null, null, new Date(),
                    new Message(Status.ERROR, "url does not apply to www.globus.ru"));
        }

        Money price = new Money();
        String name;
        try {
            Document document = Jsoup.connect(url).get();

            Element rubPriceElement = document.select("span.catalog-detail__item-price-actual-main")
                    .first();
            assert rubPriceElement != null;
            TextNode rubTextNode = (TextNode) rubPriceElement.childNodes().get(0);
            String rub = rubTextNode.getWholeText();

            Element penniesPriceElement = document.select("span.catalog-detail__item-price-actual-sub").first();
            TextNode penniesTextNode = (TextNode) penniesPriceElement.childNodes().get(0);
            String pennies = penniesTextNode.getWholeText();
            price.setAmount(Double.parseDouble(rub + "." + pennies)).setCurrency(Currency.RUB);

            name = document.title().replace(" | Сеть гипермаркетов «Глобус»", "");

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
        return new Product(name, price, "globus", url, new Date(), new Message(Status.SUCCESS, "Success!"));
    }
}
