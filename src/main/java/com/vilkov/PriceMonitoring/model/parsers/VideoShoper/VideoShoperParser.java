package com.vilkov.PriceMonitoring.model.parsers.VideoShoper;

import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.Parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Logger;

public class VideoShoperParser implements Parser {
    Logger logger = Logger.getLogger("VideoShoperParser");

    @Override
    public Product getProduct(String url) {
        StringBuilder response = null;
        try (FileInputStream fileInputStream = new FileInputStream("src/main/java/com/vilkov/PriceMonitoring/model/parsers/VideoShoper/requestProperty.properties")) {
            Properties properties = new Properties();
            properties.load(fileInputStream);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod(properties.getProperty("requestMethodGET"));
            httpURLConnection.setRequestProperty("user-agent", properties.getProperty("user-agent"));
            httpURLConnection.setRequestProperty("cookie", "cf_clearance=NxfymVEPB3u7HaJ3tpqA9MYkMmMRtn3d0Cve6ODNwO4-1657611803-0-150; PHPSESSID=985230bd078f7a0c3bbccc106488d4a9; cookieSite=1; BITRIX_SM_V_SALE_UID=121398780; _ga=GA1.2.662162147.1657611567; _gid=GA1.2.846734350.1657611567; _ym_uid=16576115673274341; _ym_d=1657611567; gdeslon.ru.__arc_domain=gdeslon.ru; gdeslon.ru.user_id=b590732c-e1b4-4dee-bc83-615e014e8e64; tmr_lvid=49a7828059222092b91e60bf2923e92f; tmr_lvidTS=1657611567487; searchbooster_v2_user_id=45OjhU4azJ0DCl3zpdVmU_qeIYQESz5ysXOLQJE_LgC|6.12.10.39; _ym_visorc=w; _ym_isad=2; BX_USER_ID=df403f05ee37f8f166877123a44db2fb; BITRIX_CONVERSION_CONTEXT_s1={\"ID\":7,\"EXPIRE\":1657659540,\"UNIQUE\":[\"conversion_visit_day\"]}; WhiteCallback_visitorId=10556992436; WhiteCallback_visit=18093103922; WhiteSaas_uniqueLead=no; _tt_enable_cookie=1; _ttp=c8faa653-301c-429a-acd0-d8eb88dbd004; mars=e2252c018e8b434ebf53f0096904b987; hunter_start={\"s\":1657611570519}; analytic_id=1657611570857; caltat_cookie=accept; dbl=dea7d2b627b94a11830be64b3bf1a487; fco2r34=dea7d2b627b94a11830be64b3bf1a487; WidgetChat_invitation_2972840=true; mp_8621d81b555825fcb2a9d4dbdb5b3105_mixpanel={\"distinct_id\": \"181f1594173188-0ae79ab9d8cf3-3f730051-1fa400-181f159417486d\",\"$device_id\": \"181f1594173188-0ae79ab9d8cf3-3f730051-1fa400-181f159417486d\",\"$initial_referrer\": \"https://video-shoper.ru/?__cf_chl_tk=PUm8zSFj6ARn.b3HM8deQbqJxQTRXR6hnune19j14TE-1657611801-0-gaNycGzNByU\",\"$initial_referring_domain\": \"video-shoper.ru\"}; cto_bundle=viETxV95TVI0RHFEJTJGOE5RMm5INkdrTWVqVlRBRGNxckV1MVF2MTU2c2NVMVdSWGIyJTJGVjllVVo0MnI3QmU1RjhwYjVuODNQaEV2MW1GbHolMkYxaWk4Y01ISVlDbUREQnZIcHo2c2tqTmFXZVZSajBRdFpjajNLYnMlMkJ6QUl5NlZrJTJGWUM1TmRHZE0zTWV1NFI1YXZ2YkUxUVBhbiUyQkElM0QlM0Q; MgidSensorNVis=1; MgidSensorHref=https://video-shoper.ru/shipment/honor-50-8-256gb-midnight-black-polnochnyy-chernyy.html; tmr_detect=0|1657612241201; WhiteCallback_timeAll=446; WhiteCallback_timePage=446; tmr_reqNum=22; WhiteCallback_openedPages=ERztA");
            httpURLConnection.setRequestProperty("sec-ch-ua-platform", properties.getProperty("sec-ch-ua-platform"));
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + httpURLConnection.getResponseCode());
            Thread.sleep(1500);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        System.out.println(response.toString());
        return null;
    }
}
