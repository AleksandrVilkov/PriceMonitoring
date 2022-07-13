package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import com.vilkov.PriceMonitoring.model.parsers.Mvideo.MvideoParser;
import com.vilkov.PriceMonitoring.model.parsers.VideoShoper.VideoShoperParser;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ParserHelper {
    private static Logger logger = new Logger(Logger.getLoggerProperties().getProperty("modelLogFolder"),
            Logger.getLoggerProperties().getProperty("modelLogFileName"));

    public static Product searchProduct(String url) {
        Properties properties = getParserProperty();

        final String GLOBUS = properties.getProperty("GLOBUS");
        final String PEREKRESTOK = properties.getProperty("PEREKRESTOK");
        final String CITILINK = properties.getProperty("CITILINK");
        final String MVIDEO = properties.getProperty("MVIDEO");
        //final String STORE77 = properties.getProperty("STORE77");
        final String VIDEO_SHOPER = properties.getProperty("VIDEO_SHOPER");

        Product result = null;

        if (url.contains(GLOBUS)) {
            result = searchGlobusProduct(url, GLOBUS);
        }
        if (url.contains(PEREKRESTOK)) {
            result = searchPerekrestokProduct(url, PEREKRESTOK);
        }
        if (url.contains(CITILINK)) {
            result = searchCitilinkProduct(url, CITILINK);
        }
        if (url.contains(MVIDEO)) {
            result = searchMvideoProduct(url);
        }
        if (url.contains(VIDEO_SHOPER)) {
            result = searchVideoShoperProduct(url);
        }
        if (result == null) {
            result = new Product();
            Message message = new Message(Status.ERROR, "Invalid url. Probably this store is not supported");
            logger.save(new Date() + " " + message.toString());
            result.setMessage(message);
        }
        return result;
    }

    private static Properties getParserProperty() {
        Properties properties;
        try (FileInputStream fis = new FileInputStream("src/main/resources/parsers.properties")) {
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }

    private static Product searchMvideoProduct(String url) {
        MvideoParser mvideoParser = new MvideoParser();
        return mvideoParser.getProduct(url);
    }

    private static Product searchVideoShoperProduct(String url) {
        VideoShoperParser videoShoperParser = new VideoShoperParser();
        return videoShoperParser.getProduct(url);
    }

    private static Product searchGlobusProduct(String url, String shop) {
        final String cssQuery;
        final List<String> extraItemsName;
        cssQuery = "span.catalog-detail__item-price-actual-main";
        extraItemsName = new ArrayList<>();
        extraItemsName.add(" | Сеть гипермаркетов «Глобус»");
        return HTMLPageParser.getProduct(url, cssQuery, extraItemsName, shop);
    }

    private static Product searchPerekrestokProduct(String url, String shop) {
        final String cssQuery;
        final List<String> extraItemsName;
        final List<String> extraItemsPrice;
        cssQuery = "div.price-new";
        extraItemsName = new ArrayList<>();
        extraItemsName.add(" - купить с доставкой на дом в Перекрёстке");
        extraItemsPrice = new ArrayList<>();
        extraItemsPrice.add("\n");
        extraItemsPrice.add("&nbsp;");
        extraItemsPrice.add("₽");
        extraItemsPrice.add(" ");
        extraItemsPrice.add(" ");
        return HTMLPageParser.getProduct(url, cssQuery, extraItemsName, extraItemsPrice, shop);
    }

    private static Product searchCitilinkProduct(String url, String shop) {

        final List<String> extraItemsName = new ArrayList<>();
        final List<String> extraItemsPrice = new ArrayList<>();

        //id товара находится в конце url
        String[] urlChunk = url.replace("/", "").split("-");
        final String cssQuery = "#product-item-" + urlChunk[urlChunk.length - 1] + " > div.ProductCardLayout__product-description > div.ProductHeader.js--ProductHeader > div.ProductHeader__price-block > div.ProductHeader__price > div.ProductHeader__price-bonus > div > span > span.ProductHeader__price-default_current-price.js--ProductHeader__price-default_current-price";
        extraItemsName.add("- купить в Ситилинк | ");
        extraItemsName.add(urlChunk[urlChunk.length - 1]);
        extraItemsPrice.add("\n");
        extraItemsPrice.add(" ");
        return HTMLPageParser.getProduct(url, cssQuery, extraItemsName, extraItemsPrice, shop);
    }
}
