package com.vilkov.PriceMonitoring.model.parsers;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class HTMLPageParser {
    private static final String GLOBUS = "www.globus.ru";
    private static final String PEREKRESTOK = "www.perekrestok.ru";
    private static final String CITILINK = "www.citilink.ru";

    public static Product searchProduct(String url) {
        final String cssQuery;
        final List<String> extraItemsName;
        final List<String> extraItemsPrice;
        if (url.contains(GLOBUS)) {
            cssQuery = "span.catalog-detail__item-price-actual-main";
            extraItemsName = new ArrayList<>();
            extraItemsName.add(" | Сеть гипермаркетов «Глобус»");
            return getProduct(url, cssQuery, extraItemsName, GLOBUS);
        } else if (url.contains(PEREKRESTOK)) {
            cssQuery = "div.price-new";
            extraItemsName = new ArrayList<>();
            extraItemsName.add(" - купить с доставкой на дом в Перекрёстке");
            extraItemsPrice = new ArrayList<>();
            extraItemsPrice.add("\n");
            extraItemsPrice.add("&nbsp;");
            extraItemsPrice.add("₽");
            extraItemsPrice.add(" ");
            extraItemsPrice.add(" ");
            return getProduct(url, cssQuery, extraItemsName, extraItemsPrice, PEREKRESTOK);

        } else if (url.contains(CITILINK)) {
            //id товара находится в конце url
            String[] urlChunk = url.replace("/", "").split("-");
            cssQuery = "#product-item-" + urlChunk[urlChunk.length - 1] + " > div.ProductCardLayout__product-description > div.ProductHeader.js--ProductHeader > div.ProductHeader__price-block > div.ProductHeader__price > div.ProductHeader__price-bonus > div > span > span.ProductHeader__price-default_current-price.js--ProductHeader__price-default_current-price";
            extraItemsName = new ArrayList<>();
            extraItemsName.add("- купить в Ситилинк | ");
            extraItemsName.add(urlChunk[urlChunk.length - 1]);
            extraItemsPrice = new ArrayList<>();
            extraItemsPrice.add("\n");
            extraItemsPrice.add(" ");
            return getProduct(url, cssQuery, extraItemsName, extraItemsPrice, CITILINK);
        } else {
            return null;
        }
    }


    private static Product getProduct(String url, String cssQuery, String shop) {
        return getProduct(url, cssQuery, new ArrayList<>(), new ArrayList<>(), shop);
    }

    private static Product getProduct(String url, String cssQuery, List<String> extraHeaderCharacters, String shop) {
        return getProduct(url, cssQuery, extraHeaderCharacters, new ArrayList<>(), shop);
    }

    private static Product getProduct(String url, String cssQuery, List<String> extraItemsName, List<String> extraItemsPrice, String shop) {
        Logger logger = Logger.getLogger("PageParser");
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
            logger.warning(e.getMessage());
            return new Product(null, null, shop, null, new Date(),
                    new Message(Status.ERROR, e.getMessage()));
        }

        if (price.getAmount() == 0) {
            logger.warning("There is a null value, it is impossible to create a product!");
            return new Product(null, null, shop, null, new Date(),
                    new Message(Status.ERROR, "There is a null value, it is impossible to create a product!"));
        }
        return new Product(name, price, shop, url, new Date(), new Message(Status.SUCCESS, "Success!"));
    }

}
