package com.vilkov.PriceMonitoring.model.cron;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Logger;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class Cron {

    Logger logger = Logger.getLogger("CRON");

    @Scheduled(fixedRate = Config.RECEIVE_TIME_CURRENT_PRICES)
    @Async
    public void getNewProducts() {

        //TODO реализовать
//        logger.info("Start cron-task: getNewProducts");
//        ProductHelper.getAndSaveCurrentPricesOfProducts(new Client("t"));
//        logger.info("Cron-task getNewProducts is completed");
//    }

    }
}
