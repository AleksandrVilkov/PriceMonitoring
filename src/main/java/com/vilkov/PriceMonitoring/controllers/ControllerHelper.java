package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

    public static boolean isProduct(List<BaseEntity> baseEntities) {
        boolean result = true;
        for (BaseEntity baseEntity : baseEntities) {
            if (!(baseEntity instanceof Product)) {
                result = false;
                return result;
            }
        }
        return result;
    }
}
