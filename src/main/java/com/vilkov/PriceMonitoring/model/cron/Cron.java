package com.vilkov.PriceMonitoring.model.cron;

import com.vilkov.PriceMonitoring.model.CurrencyRate.CurrencyRate;
import com.vilkov.PriceMonitoring.model.ProductHelper;
import com.vilkov.PriceMonitoring.model.dataStorage.DataStorage;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.ValuteContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.logging.Logger;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class Cron {
    Logger logger = Logger.getLogger("CRON");
    @Autowired
    DataStorage dataStorage;
    @Autowired
    ProductHelper productHelper;

    @Autowired
    CurrencyRate currencyRate;

    @Scheduled(fixedRate = Config.FREQUENCY_DEMON_WORK)
    @Async
    public void getNewProducts() {
        List<Client> clients = dataStorage.getAllClients();
        for (Client client : clients) {
            logger.info("Start cron-task: getNewProducts for client: " + client.getClientID());
            productHelper.getAndSaveCurrentPricesOfProducts(client);
            logger.info("Cron-task getNewProducts is completed for client: " + client.getClientID());
        }
    }

    @Scheduled(fixedRate = Config.FREQUENCY_DEMON_WORK)
    @Async
    public void getRates() {
        ValuteContainer valuteCntr = new ValuteContainer();
        valuteCntr.setValutes(currencyRate.getRate());
        dataStorage.createEntity(valuteCntr, new Client("admin", "admin".toCharArray()));
        System.out.println();
    }
}

