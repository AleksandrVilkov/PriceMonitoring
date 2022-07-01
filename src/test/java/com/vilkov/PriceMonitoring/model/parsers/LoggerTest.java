package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.Logger.Logger;
import com.vilkov.PriceMonitoring.Logger.MessageType;
import com.vilkov.PriceMonitoring.Logger.Place;
import org.junit.Test;

public class LoggerTest {
    @Test
    public void createDirectoryTest() {
       Logger logger = Logger.getInstance();
       logger.save(MessageType.MSG,"Тестовая запись в лог", Place.CONTROLLER);
        logger.save(MessageType.ERROR,"Тестовая запись ошибки в лог", Place.CONTROLLER);
    }
}
