package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.Mvideo.MvideoParser;

import java.util.ArrayList;
import java.util.List;

public class ParserHelper {
    private static final String GLOBUS = "www.globus.ru";
    private static final String PEREKRESTOK = "www.perekrestok.ru";
    private static final String CITILINK = "www.citilink.ru";
    private static final String MVIDEO = "www.mvideo.ru";

    //TODO  вынести настройки в пропперти файл

    public static Product searchProduct(String url) {
        final String cssQuery;
        final List<String> extraItemsName;
        final List<String> extraItemsPrice;
        if (url.contains(GLOBUS)) {
            cssQuery = "span.catalog-detail__item-price-actual-main";
            extraItemsName = new ArrayList<>();
            extraItemsName.add(" | Сеть гипермаркетов «Глобус»");
            return HTMLPageParser.getProduct(url, cssQuery, extraItemsName, GLOBUS);
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
            return HTMLPageParser.getProduct(url, cssQuery, extraItemsName, extraItemsPrice, PEREKRESTOK);

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
            return HTMLPageParser.getProduct(url, cssQuery, extraItemsName, extraItemsPrice, CITILINK);
        } else if (url.contains(MVIDEO)) {
            return MvideoParser.getProduct(url);
        } else {
            return null;
        }
    }
}
