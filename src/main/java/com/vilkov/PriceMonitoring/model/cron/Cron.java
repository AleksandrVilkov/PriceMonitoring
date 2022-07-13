package com.vilkov.PriceMonitoring.model.cron;

import com.vilkov.PriceMonitoring.model.CurrencyRate.CurrencyRate;
import com.vilkov.PriceMonitoring.model.ProductHelper;
import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.ValuteContainer;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class Cron {
    public static final int FREQUENCY_DEMON_WORK = 86_400_000;
    private final Logger logger = new Logger(Logger.getLoggerProperties().getProperty("modelLogFolder"),
            Logger.getLoggerProperties().getProperty("modelLogFileName"));
    @Autowired
    DataStorage dataStorage;
    @Autowired
    ProductHelper productHelper;
    @Autowired
    CurrencyRate currencyRate;

    @Scheduled(fixedRate = FREQUENCY_DEMON_WORK)
    @Async
    public void getNewProducts() {
        logger.save(new Date() + "Start cron-task: getNewProducts for clients");
        List<Client> clients = dataStorage.getAllClients();
        for (Client client : clients) {
            productHelper.getAndSaveCurrentPricesOfProducts(client);
        }
    }

    @Scheduled(fixedRate = FREQUENCY_DEMON_WORK)
    @Async
    public void getRates() {
        logger.save(new Date() + "Start cron-task: getRates");
        ValuteContainer valuteCntr = new ValuteContainer();
        valuteCntr.setValutes(currencyRate.getRate());
        dataStorage.createEntity(valuteCntr, new Client("admin", "admin".toCharArray()));
        System.out.println();
    }
}

