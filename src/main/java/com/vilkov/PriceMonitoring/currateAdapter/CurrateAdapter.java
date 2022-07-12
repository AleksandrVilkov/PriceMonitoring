package com.vilkov.PriceMonitoring.currateAdapter;

import com.vilkov.PriceMonitoring.HttpClient.HTTPClient;
import com.vilkov.PriceMonitoring.model.CurrencyRate.CurrencyRateInterface;
import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.Rate;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CurrateAdapter implements CurrencyRateInterface {
    private final Logger logger = new Logger("CurrateAdapter", "CurrateAdapter");

    //TODO Добавить проверку на подходящую валюту. Запрос доступных валют https://currate.ru/api/?get=currency_list&key=YOUR-API-KEY:
    //TODO Добавить отправку даты. Пример запроса: //&date=2018-02-12T15:00:00
    @Override
    public Rate getRate(Currency firstCurrency, Currency secondCurrency, Date date) {
        Date dateFormat;
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-d").parse(date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(new File("src/main/java/com/vilkov/PriceMonitoring/currateAdapter/prop.properties"))) {
            properties.load(fileInputStream);
        } catch (Exception e) {
            logger.save(e.getMessage());
        }
        HTTPClient httpClient = new HTTPClient();
        String res = httpClient.sendGetRequest("https://currate.ru/api/?get=rates&pairs="
                + firstCurrency.name() + secondCurrency.name() + "&key=" + properties.getProperty("token"));
        return getRateFromJSON(res, firstCurrency, secondCurrency);
    }

    private Rate getRateFromJSON(String json, Currency firstCurrency, Currency secondCurrency) {
        JSONObject jsonObject = new JSONObject(json);
        int status = jsonObject.getInt("status");
        JSONObject jsonData = jsonObject.optJSONObject("data");
        String rate = (String) jsonData.opt(firstCurrency.name() + secondCurrency.name());
        if (rate == null) {
            rate = (String) jsonData.opt(secondCurrency.name() + firstCurrency.name());
        }
        Message message = new Message();
        if (status == 200 && rate != null) {
            message.setMessage("exchange rate successfully received");
            message.setStatus(Status.SUCCESS);
        } else {
            message.setMessage("Error getting exchange rate");
            message.setStatus(Status.ERROR);
            return new Rate(firstCurrency, secondCurrency, null, new Date(), message);
        }
        return new Rate(firstCurrency, secondCurrency, rate, new Date(), message);
    }
}
