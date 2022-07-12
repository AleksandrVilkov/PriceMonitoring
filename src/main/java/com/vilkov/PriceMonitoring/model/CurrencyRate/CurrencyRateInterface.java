package com.vilkov.PriceMonitoring.model.CurrencyRate;

import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Rate;

import java.util.Date;

public interface CurrencyRateInterface {
    public Rate getRate(Currency firstCurrency, Currency secondCurrency, Date date);
}
