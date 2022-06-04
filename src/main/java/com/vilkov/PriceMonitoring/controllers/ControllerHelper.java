package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.dataBaseAdapter.DataBaseAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

public class ControllerHelper {
    private static final Logger logger = Logger.getLogger(ControllerHelper.class.toString());
    public static Date getDateFromString(String stringDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        try {
            return dateFormat.parse(stringDate);
        } catch (ParseException e) {
            logger.warning(e.getMessage());
            return null;
        }
    }
}
