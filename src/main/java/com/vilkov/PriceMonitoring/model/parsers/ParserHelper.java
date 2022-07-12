package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.Mvideo.MvideoParser;

import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class ParserHelper {
    static Logger logger = Logger.getLogger("MvideoResponseParser");
    private static final String GLOBUS = "www.globus.ru";
    private static final String PEREKRESTOK = "www.perekrestok.ru";
    private static final String CITILINK = "www.citilink.ru";
    private static final String MVIDEO = "www.mvideo.ru";
    private static final String STORE77 = "https://store77.net/";
    private static final String VIDEO_SHOPER = "https://video-shoper.ru";
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
        } else if (url.contains(STORE77)){
//            cssQuery = "price_title_product";
//            return HTMLPageParser.getProduct(url,cssQuery,STORE77);
        } else if (url.contains(VIDEO_SHOPER)) {
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36");
                httpURLConnection.setRequestProperty("cookie", "cf_clearance=6MFIKukJojryBIc1WZfOXDPRiHYfNvX82TfPLKWSp4Y-1657609250-0-150; PHPSESSID=8d372c74dab014448b2ad8f87e422c8c; cookieSite=1; BITRIX_SM_V_SALE_UID=121397806; _ga=GA1.2.707955759.1657609250; _gid=GA1.2.786370808.1657609250; tmr_lvid=55401fa9ac32e92fb5cd37eba92e9de9; tmr_lvidTS=1657609250750; BX_USER_ID=12012f28389990355f250f5a4210074a; BITRIX_CONVERSION_CONTEXT_s1={\"ID\":1,\"EXPIRE\":1657659540,\"UNIQUE\":[\"conversion_visit_day\"]}; searchbooster_v2_user_id=TU1FnIGNIbB_2l_6tDz_a_Zg8QcQG6ow6ZaAGVLX5uq|6.12.10.0; _ym_uid=1657609251448440893; _ym_d=1657609251; _tt_enable_cookie=1; _ttp=9369b2f5-c9d4-49e4-a24a-b596721d7a87; _ym_visorc=w; _ym_isad=2; mars=1d0bbce564ec4f4ab429856659ffb32d; WhiteCallback_visitorId=10556696186; WhiteCallback_visit=18092616059; WhiteSaas_uniqueLead=no; caltat_cookie=accept; dbl=44d8a85d02884945829d4463573221fb; gdeslon.ru.__arc_domain=gdeslon.ru; gdeslon.ru.user_id=7a80dab9-380b-4634-9352-661f3a1be99c; hunter_start={\"s\":1657609255998}; MgidSensorNVis=1; MgidSensorHref=https://video-shoper.ru/shipment/huawei-p50-pocket-12-512gb-premium-edition-premium-gold.html; analytic_id=1657609297459173; WidgetChat_invitation_2972840=true; mp_8621d81b555825fcb2a9d4dbdb5b3105_mixpanel={\"distinct_id\": \"181f135e82d5f8-04e088fd667f0d-26021a51-144000-181f135e82e7ab\",\"$device_id\": \"181f135e82d5f8-04e088fd667f0d-26021a51-144000-181f135e82e7ab\",\"$initial_referrer\": \"https://video-shoper.ru/?__cf_chl_tk=I1CPPYFyIEc7j3uMvenIqYFX06.JfBDM1B25yH2u0Q4-1657609247-0-gaNycGzNBz0\",\"$initial_referring_domain\": \"video-shoper.ru\"}; cto_bundle=4QCoD19qME1vNlclMkYlMkJMZCUyQmZ2Q0JDT3piNlRJajljcWhiTEVFTzk3QUdMc0hZSGc0S0RGdkZjeTRpOElIcG03ZFlBSWtpQkFON2ZOZjIyelg5MnJienZodXpVa1RRZVVXUFZMdXFBVzV6WDNWU1UyNW4wc2ZpJTJCOXRnZEllSmoyY1Z6b0FWc2JsVVU0MCUyQlRaJTJGQzliVGlCbHJVd2clM0QlM0Q; tmr_detect=0|1657610036799; WhiteCallback_timeAll=1184; WhiteCallback_timePage=1184; tmr_reqNum=40; WhiteCallback_openedPages=itjHd");
                httpURLConnection.setRequestProperty("sec-ch-ua-platform", "Windows");
                logger.info("Sending 'GET' request to URL : " + url);
                logger.info("Response Code : " + httpURLConnection.getResponseCode());
                Thread.sleep(1500);
            } catch (Exception e) {

            }
            cssQuery = "datalayer_prise";
            return HTMLPageParser.getProduct(url,cssQuery,VIDEO_SHOPER);
        }

            else {
            return null;
        }
        return null;
    }
}
