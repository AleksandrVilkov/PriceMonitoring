package com.vilkov.PriceMonitoring.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class ControllerHelperTest {
    @Test
    public void getDateFromStringTest() {
        String stringDate = "01.05.2021";
        Date date = ControllerHelper.getDateFromString(stringDate);
        Assert.assertEquals(date.getDate(), 01);
        Assert.assertEquals(date.getMonth(), 4);

        stringDate = "21.06.2022";
        date = ControllerHelper.getDateFromString(stringDate);
        Assert.assertEquals(date.getDate(), 21);
        Assert.assertEquals(date.getMonth(), 5);
    }

    @Test
    public void isCorrectUrlTest() {
        Assert.assertEquals(ControllerHelper.isCorrectUrl("https://www.globus.ru/products/93301499_ST/"), true);
        Assert.assertEquals(ControllerHelper.isCorrectUrl("https:////ww.globus.ru/products/93301499_ST/"), false);
        Assert.assertEquals(ControllerHelper.isCorrectUrl("https/://www.globus.ru/products/93301499_ST/"), false);
    }
}
