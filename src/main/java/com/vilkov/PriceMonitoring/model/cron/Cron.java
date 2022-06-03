package com.vilkov.PriceMonitoring.model.cron;

import com.vilkov.PriceMonitoring.model.ProductHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class Cron {

    @Scheduled(fixedRate = Config.RECEIVE_TIME_CURRENT_PRICES)
    @Async
    public void getProducts() {
        ProductHelper.getAndSaveCurrentPricesOfProducts();
    }

}
