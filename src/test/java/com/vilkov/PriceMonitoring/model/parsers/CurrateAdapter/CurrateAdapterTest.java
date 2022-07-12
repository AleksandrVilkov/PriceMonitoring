package com.vilkov.PriceMonitoring.model.parsers.CurrateAdapter;

import com.vilkov.PriceMonitoring.currateAdapter.CurrateAdapter;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Rate;
import org.junit.Test;

import java.util.Date;

public class CurrateAdapterTest {
    @Test
    public void getRateTest() {
        CurrateAdapter currateAdapter = new CurrateAdapter();
      Rate rate =  currateAdapter.getRate(Currency.USD, Currency.RUB, new Date());
      System.out.println();
    }
}
