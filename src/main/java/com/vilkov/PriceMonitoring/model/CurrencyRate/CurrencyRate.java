package com.vilkov.PriceMonitoring.model.CurrencyRate;

import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Rate;

import java.util.Date;

public class CurrencyRate {
    CurrencyRateInterface currencyRate;

    public Rate getRate(Currency firstCurrency, Currency secondCurrency, Date date) {
        return currencyRate.getRate(firstCurrency, secondCurrency,date);
    }

    //TODO получать лист ставок в заданные даты
}
