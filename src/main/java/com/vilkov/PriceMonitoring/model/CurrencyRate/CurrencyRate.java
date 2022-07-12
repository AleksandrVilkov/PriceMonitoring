package com.vilkov.PriceMonitoring.model.CurrencyRate;
import com.vilkov.PriceMonitoring.model.entity.Valute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurrencyRate {
    @Autowired
    CurrencyRateInterface currencyRate;

    public List<Valute> getRate() {
        return currencyRate.getValutes();
    }
}
