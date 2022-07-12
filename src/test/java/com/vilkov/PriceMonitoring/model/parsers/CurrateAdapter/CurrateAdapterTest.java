package com.vilkov.PriceMonitoring.model.parsers.CurrateAdapter;

import com.vilkov.PriceMonitoring.cbrAdapter.cbrAdapter;
import com.vilkov.PriceMonitoring.model.entity.Valute;
import org.junit.Test;

import java.util.List;

public class CurrateAdapterTest {
    @Test
    public void getRateTest() {
        cbrAdapter cbrAdapter = new cbrAdapter();
      List<Valute> valutes =  cbrAdapter.getValutes();
    }
}
