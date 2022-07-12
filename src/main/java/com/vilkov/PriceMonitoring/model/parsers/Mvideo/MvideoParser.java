package com.vilkov.PriceMonitoring.model.parsers.Mvideo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.Money;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.Parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

public class MvideoParser implements Parser {
    static Properties properties = new Properties();
    static Logger logger = Logger.getLogger("MvideoResponseParser");

    public Product getProduct(String url) {
        int priceAmount = getPrice(url);
        Money money = new Money(Double.parseDouble(String.valueOf(priceAmount)), Currency.RUB);
        String productName = getProductName(url);
        Message message;
        if (priceAmount != 0 && productName != null) {
            message = new Message(Status.SUCCESS, "Успешно!");
        } else {
            message = new Message(Status.ERROR, "Ошибка получения!");
        }

        return new Product(getProductName(url),
                money,
                "www.mvideo.ru",
                url,
                new Date(),
                message
        );
    }


    private int getPrice(String url) {
        StringBuilder response;
        try (FileInputStream fileInputStream = new FileInputStream("src/main/java/com/vilkov/PriceMonitoring/model/parsers/Mvideo/requestProperty.properties")) {
            properties.load(fileInputStream);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(getRequestUrlForPrice(url)).openConnection();
            httpURLConnection.setRequestMethod(properties.getProperty("requestMethodGET"));
            httpURLConnection.setRequestProperty("user-agent", properties.getProperty("user-agent"));
            httpURLConnection.setRequestProperty("cookie", properties.getProperty("cookie"));
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + httpURLConnection.getResponseCode());
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
            HashMap<String, Object> objectHashMap = new ObjectMapper().readValue(response.toString(), HashMap.class);
            HashMap<String, Object> body = (HashMap<String, Object>) objectHashMap.get("body");
            List<Map<String, Object>> materialPrices = (List<Map<String, Object>>) body.get("materialPrices");
            Map<String, Object> price = (Map<String, Object>) materialPrices.get(0).get("price");
            return (int) price.get("basePrice");
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return 0;
    }


    private String getProductName(String url) {
        StringBuilder response;
        HashMap<String, Object> objectMapper;
        try (FileInputStream fileInputStream = new FileInputStream("src/main/java/com/vilkov/PriceMonitoring/model/parser/Mvideo/requestProperty.properties")) {
            properties.load(fileInputStream);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(getRequestUrlForProductName(url))
                    .openConnection();
            httpURLConnection.setRequestMethod(properties.getProperty("requestMethodGET"));
            httpURLConnection.setRequestProperty("user-agent", properties.getProperty("user-agent"));
            httpURLConnection.setRequestProperty("cookie", properties.getProperty("cookie"));
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + httpURLConnection.getResponseCode());
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            response = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
            objectMapper = new ObjectMapper().readValue(response.toString(), HashMap.class);
            HashMap<String, String> body = (HashMap<String, String>) objectMapper.get("body");
            return body.get("name");
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return null;
    }

    private String getRequestUrlForPrice(String url) {
        return "https://www.mvideo.ru/bff/products/prices?productIds=" + getProductIDfromUrl(url);
    }

    private String getRequestUrlForProductName(String url) {
        return "https://www.mvideo.ru/bff/product-details?productId=" + getProductIDfromUrl(url);
    }

    private String getProductIDfromUrl(String url) {
        //ID товара - последний элемент
        String[] productId = url.split("-");
        return productId[productId.length - 1];
    }
}
