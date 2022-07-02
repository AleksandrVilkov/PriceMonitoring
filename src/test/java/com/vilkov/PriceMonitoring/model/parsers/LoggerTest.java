package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.logger.Logger;
import org.junit.Test;

public class LoggerTest {
    @Test
    public void createDirectoryTest() {
       Logger logger = new Logger("testing/77", "tests.txt");
       logger.save("Тестовая запись в лог");
        logger.save("Тестовая запись ошибки в лог");
        Logger logger2 = new Logger("/testing2", "tests.txt");
        logger2.save("Тестовая запись в лог2");
        logger2.save("Тестовая запись ошибки в лог2");
    }
}
