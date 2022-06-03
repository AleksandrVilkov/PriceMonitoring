package com.vilkov.PriceMonitoring.cron;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class CronConfig {

    @Scheduled(fixedRate = 3000)
    @Async
    public void getProducts() {
        System.out.println("выполнен cron");
    }

}
