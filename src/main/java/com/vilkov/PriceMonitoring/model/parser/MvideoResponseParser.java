package com.vilkov.PriceMonitoring.model.parser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class MvideoResponseParser implements Parser {
    static Logger logger = Logger.getLogger("MvideoResponseParser");

    /**
     * TODO не корректная кодировка ответа, подумать
     * TODO вынести константы параметров запроса в проперти, сделать парсер проперти файла
     * TODO Возвращать Product
     * */
    public static void getResponse(String url) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");

            con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36" +
                    " (KHTML, like Gecko) Chrome/102.0.0.0 Safari/537.36");
            con.setRequestProperty("same-origin", "same-origin");
            con.setRequestProperty("sec-fetch-mode", "cors");
            con.setRequestProperty("sec-fetch-dest", "empty");
            con.setRequestProperty("sec-ch-ua-platform", "Windows");
            con.setRequestProperty("sec-ch-ua-mobile", "?0");
            con.setRequestProperty("referer", "https://www.mvideo.ru/products/smartfon-xiaomi-redmi-9a-32gb-granite-gray-30051224");
            con.setRequestProperty("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            con.setRequestProperty("accept-encoding", ": gzip, deflate, br");
            con.setRequestProperty("cookie", "__lhash_=145209e3cba930e87ffc1c8fe1f57b44;" +
                    " CACHE_INDICATOR=false; COMPARISON_INDICATOR=false; HINTS_FIO_COOKIE_NAME=1; MAIN_PAGE_VARIATION_1=3; " +
                    "MVID_2_exp_in_1=2; MVID_AB_SERVICES_DESCRIPTION=var2; MVID_ADDRESS_COMMENT_AB_TEST=2; " +
                    "MVID_BLACK_FRIDAY_ENABLED=true; MVID_CALC_BONUS_RUBLES_PROFIT=true; " +
                    "MVID_CART_MULTI_DELETE=true; MVID_CATALOG_STATE=1; MVID_CITY_ID=CityCZ_34477; " +
                    "MVID_FILTER_CODES=true; MVID_FILTER_TOOLTIP=1; MVID_FLOCKTORY_ON=true; " +
                    "MVID_GEOLOCATION_NEEDED=true; MVID_GET_LOCATION_BY_DADATA=DaData; MVID_GIFT_KIT=true; " +
                    "MVID_GUEST_ID=20915095082; MVID_IS_NEW_BR_WIDGET=true; MVID_KLADR_ID=5004200000000; MVID_LAYOUT_TYPE=1; " +
                    "MVID_LP_SOLD_VARIANTS=2; MVID_NEW_ACCESSORY=true; MVID_NEW_DESKTOP_FILTERS=true; MVID_NEW_LK=true; " +
                    "MVID_NEW_LK_CHECK_CAPTCHA=true; MVID_NEW_LK_LOGIN=true; MVID_NEW_LK_MENU_BUTTON=true; " +
                    "MVID_NEW_LK_OTP_TIMER=true; MVID_NEW_MBONUS_BLOCK=true; MVID_NEW_SUGGESTIONS=true; " +
                    "MVID_PROMO_CATALOG_ON=true; MVID_REGION_ID=1; MVID_REGION_SHOP=S002; MVID_SERVICES=111; " +
                    "MVID_SERVICES_MINI_BLOCK=var2; MVID_TAXI_DELIVERY_INTERVALS_VIEW=new; MVID_TIMEZONE_OFFSET=3; " +
                    "MVID_WEBP_ENABLED=true; NEED_REQUIRE_APPLY_DISCOUNT=true; PICKUP_SEAMLESS_AB_TEST=2; " +
                    "PRESELECT_COURIER_DELIVERY_FOR_KBT=false; PROMOLISTING_WITHOUT_STOCK_AB_TEST=2; " +
                    "flacktory=no; searchType2=3; popmechanic_sbjs_migrations=popmechanic_1418474375998=1|||1471519752600=1|||1471519752605=1; " +
                    "SMSError=; authError=; _ym_uid=1655751311329722491; _ym_d=1655751311; _gid=GA1.2.683805220.1655751311; " +
                    "st_uid=cff1a6c81e88d568739abffda48b60bb; advcake_track_id=7866ecca-e56a-3404-0468-8a79e1d5603e; " +
                    "advcake_session_id=2a2b1bc6-9fcc-07e4-e07f-e9c73af7cddf; tmr_lvid=d8f46b9f5a8706d694bdc4c710d99485; " +
                    "tmr_lvidTS=1655751311105; gdeslon.ru.__arc_domain=gdeslon.ru; gdeslon.ru.user_id=a2243244-f09f-457c-a4c1-fb0441c5e1e4; " +
                    "_ym_isad=1; flocktory-uuid=56423496-a7e3-44ae-9557-96a080c2de82-5; uxs_uid=8b7f2b70-f0ca-11ec-8a52-81c4e5194c0c;" +
                    " afUserId=16573c4d-2011-45ce-8fa8-7972b0288cd1-p; AF_SYNC=1655751341921; BIGipServeratg-ps-prod_tcp80=1225055242.20480.0000; " +
                    "bIPs=1081167284; adrdel=1; adrcid=A-KuG0YOyeK3zkkL7MNyzow; __ttl__widget__ui=1655751346313-66577f64254b; MVID_ENVCLOUD=primary; " +
                    "mindboxDeviceUUID=ea2b1960-45ae-4bff-ba5f-155e747a14a4; directCrm-session={\"deviceGuid\":\"ea2b1960-45ae-4bff-ba5f-155e747a14a4\"}; " +
                    "_ga=GA1.1.958087176.1655751311; tmr_detect=0|1655752430077; tmr_reqNum=35; " +
                    "JSESSIONID=Kj7LvwHTTGzD3MM1QVyzWTWxZ8LQdBh6sBQG0yZVyMmnt2Dgp21t!-1510550346; ADRUM_BT=R:0|g:71c4b14f-4e20-478a-afa9-d8d3f1dfc52f286343; " +
                    "_ga_CFMZTSS5FM=GS1.1.1655751310.1.1.1655752690.0; _ga_BNX5WPP3YK=GS1.1.1655751310.1.1.1655752690.60");
            con.setRequestProperty("accept", "application/json");
            con.addRequestProperty("scheme", "https");


            int responseCode = con.getResponseCode();
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (Exception e) {

        }

    }


}
