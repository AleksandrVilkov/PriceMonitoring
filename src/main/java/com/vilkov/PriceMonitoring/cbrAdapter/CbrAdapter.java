package com.vilkov.PriceMonitoring.cbrAdapter;

import com.vilkov.PriceMonitoring.HttpClient.HTTPClient;
import com.vilkov.PriceMonitoring.model.valute.CurrencyRateInterface;
import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.Valute;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CbrAdapter implements CurrencyRateInterface {

    private final Logger logger = new Logger(Logger.getLoggerProperties().getProperty("cbrAdapterLogFolder"),
            Logger.getLoggerProperties().getProperty("cbrAdapterLogFileName"));

    @Override
    public List<Valute> getValutes() {
        HTTPClient httpClient = new HTTPClient();
        String res = httpClient.sendGetRequest("https://www.cbr-xml-daily.ru/daily_json.js");
        JSONObject jsonObject = new JSONObject(res);
        String date = jsonObject.getString("Date");
        JSONObject valutesJSON = jsonObject.optJSONObject("Valute");
        JSONArray keys = valutesJSON.names();

        List<Valute> result = new ArrayList<>();
        for (int i = 0; i < valutesJSON.length(); i++) {
            JSONObject valuteJSON = valutesJSON.optJSONObject((String) keys.get(i));
            String charCode = valuteJSON.getString("CharCode");
            String value = valuteJSON.getBigDecimal("Value").toString();
            String previous = valuteJSON.getBigDecimal("Previous").toString();
            String numCode = valuteJSON.getString("NumCode");
            String nominal = Integer.toString(valuteJSON.getInt("Nominal"));
            String name = valuteJSON.getString("Name");
            Valute valute = new Valute();
            Message message = new Message(Status.SUCCESS, "ОК");
            valute.setCharCode(charCode)
                    .setValue(value)
                    .setPrevious(previous)
                    .setNumCode(numCode)
                    .setNominal(nominal)
                    .setName(name)
                    .setDate(date)
                    .setMessage(message);
            result.add(valute);
        }
        logger.save("* " + new Date() + ": Successfully received " + result.size() + " exchange rates from the CBR");
        return result;
    }


}
