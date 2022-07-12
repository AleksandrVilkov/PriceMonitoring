package com.vilkov.PriceMonitoring.model.parsers.VideoShoper;

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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

public class VideoShoperParser implements Parser {
    private final Logger LOGGER = Logger.getLogger("VideoShoperParser");
    private final String SHOP = "https://video-shoper.ru";

    @Override
    public Product getProduct(String url) {
        //Если приходит ошибка 503 = нужно посмотреть cookie и user-agent
        StringBuilder response = null;
        try (FileInputStream fileInputStream = new FileInputStream("src/main/java/com/vilkov/PriceMonitoring/model/parsers/VideoShoper/requestProperty.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestProperty("user-agent", properties.getProperty("user-agent"));
            httpURLConnection.setRequestProperty("cookie", "PHPSESSID=985230bd078f7a0c3bbccc106488d4a9; cookieSite=1; BITRIX_SM_V_SALE_UID=121398780; _ga=GA1.2.662162147.1657611567; _gid=GA1.2.846734350.1657611567; _ym_uid=16576115673274341; _ym_d=1657611567; gdeslon.ru.__arc_domain=gdeslon.ru; gdeslon.ru.user_id=b590732c-e1b4-4dee-bc83-615e014e8e64; tmr_lvid=49a7828059222092b91e60bf2923e92f; tmr_lvidTS=1657611567487; searchbooster_v2_user_id=45OjhU4azJ0DCl3zpdVmU_qeIYQESz5ysXOLQJE_LgC|6.12.10.39; _ym_visorc=w; _ym_isad=2; BX_USER_ID=df403f05ee37f8f166877123a44db2fb; BITRIX_CONVERSION_CONTEXT_s1={\"ID\":7,\"EXPIRE\":1657659540,\"UNIQUE\":[\"conversion_visit_day\"]}; WhiteCallback_visit=18093103922; _tt_enable_cookie=1; _ttp=c8faa653-301c-429a-acd0-d8eb88dbd004; mars=e2252c018e8b434ebf53f0096904b987; hunter_start={\"s\":1657611570519}; analytic_id=1657611570857; caltat_cookie=accept; dbl=dea7d2b627b94a11830be64b3bf1a487; fco2r34=dea7d2b627b94a11830be64b3bf1a487; WidgetChat_invitation_2972840=true; _fbp=fb.1.1657613461198.1519420356; WhiteCallback_visitorId=10556992436; WhiteCallback_updateMainPage=ERztA; MgidSensorNVis=3; MgidSensorHref=https://video-shoper.ru/shipment/pylesos-dyson-v15-detect-absolute.html; mp_8621d81b555825fcb2a9d4dbdb5b3105_mixpanel={\"distinct_id\": \"181f1594173188-0ae79ab9d8cf3-3f730051-1fa400-181f159417486d\",\"$device_id\": \"181f1594173188-0ae79ab9d8cf3-3f730051-1fa400-181f159417486d\",\"$initial_referrer\": \"https://video-shoper.ru/?__cf_chl_tk=PUm8zSFj6ARn.b3HM8deQbqJxQTRXR6hnune19j14TE-1657611801-0-gaNycGzNByU\",\"$initial_referring_domain\": \"video-shoper.ru\"}; cto_bundle=iqBffF95TVI0RHFEJTJGOE5RMm5INkdrTWVqVlNTc0JqaDcwbDJtJTJGSDhaY0JPV2ZieXJnMW00MnRUbUJiSXZRSmhPaDFuam1PcUZSVDRrQUJpcmJsbDBCOUN2dVZCcTBOS3FXVUJ5U3NycnlBSXlRVzMwVVA0cUdibHk2MmdaODgyYTNFY2RzTFhvRW1XOE1sUE11WjEzb2l5WW13JTNEJTNE; tmr_detect=0|1657614858349; WhiteCallback_timeAll=3272; WhiteCallback_timePage=3268; tmr_reqNum=73; WhiteCallback_openedPages=ERztA.dlcjT; cf_chl_2=7a7ab8ab99be0c8; cf_chl_prog=x16; cf_clearance=cjah6mXHtWgDmxt4GwlWbGq2ceCKkvJg49o6nqrHCC4-1657615453-0-150");
            httpURLConnection.setRequestProperty("sec-ch-ua-platform", properties.getProperty("sec-ch-ua-platform"));
            LOGGER.info("Sending 'GET' request to URL : " + url);
            int responseCode = httpURLConnection.getResponseCode();
            LOGGER.info("Response Code : " + responseCode);

            if (responseCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                response = new StringBuilder();
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append(inputLine);
                }
                bufferedReader.close();
                Document document = Jsoup.parse(String.valueOf(response));
                Element nameElement = document.select("body > div.wrapper_page > div.wrapper_main > div.middle_column > div.shead > h1").first();
                TextNode nameTextNode = (TextNode) nameElement.childNodes().get(0);
                String name = nameTextNode.getWholeText().replace("Купить ", "");

                Element priceElement = document.select("body > div.wrapper_page > div.wrapper_main > div.middle_column > div.detail_f_tabs > div.tab_best > div:nth-child(1) > div.price_detail_kits")
                        .first();
                TextNode priceTextNode = (TextNode) priceElement.childNodes().get(0);
                String price = priceTextNode.getWholeText().replaceAll("[^0-9]", "");

                Money money = new Money();
                money.setAmount(Double.parseDouble(price)).setCurrency(Currency.RUB);
                Message message = new Message(Status.SUCCESS, "");
                return new Product(name, money, SHOP, url, new Date(), message);
            } else {
                Message message = new Message(Status.ERROR, "Cannot get Product from VideoShoper.ru. Err code: " + responseCode);
                return new Product(null, null, SHOP, url, new Date(), message);
            }

        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
            Message message = new Message(Status.ERROR, e.getMessage());
            return new Product(null, null, SHOP, url, new Date(), message);
        }
    }
}
