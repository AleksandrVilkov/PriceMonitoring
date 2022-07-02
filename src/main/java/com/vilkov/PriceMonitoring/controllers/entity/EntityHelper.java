package com.vilkov.PriceMonitoring.controllers.entity;

import com.vilkov.PriceMonitoring.model.entity.FixedPrice;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;
import com.vilkov.PriceMonitoring.model.entity.Product;

public class EntityHelper {
    public static ProductVO convertProductToProductVO(Product product) {
        ProductVO productVO = new ProductVO();
        String name = product.getName() != null ? product.getName() : "Неизвестно";
        productVO.setName(name);

        double amount;
        String currency;
        if (product.getPrice() != null) {
            amount = product.getPrice().getAmount() != 0 ? product.getPrice().getAmount() : 0;
            currency = product.getPrice().getCurrency() != null ? product.getPrice().getCurrency().name() : "";
        } else {
            amount = 0;
            currency = "";
        }
        String price = amount + " " + currency;
        productVO.setPrice(price);
        productVO.setShop(product.getShop() != null ? product.getShop() : "Неизвестен");
        productVO.setDate(product.getDate().toString());
        return productVO;
    }

    public static MonitoringListVO convertMonitoringListToMonitoringListVO(MonitoringList monitoringList) {
        MonitoringListVO monitoringListVO = new MonitoringListVO();
        monitoringListVO.setUrls(monitoringList.getUrls());
        return monitoringListVO;
    }

    public static FixedPriceVO convertFixedPriceToFixedPriceVO(FixedPrice fixedPrice) {
        FixedPriceVO fixedPriceVO = new FixedPriceVO();
        String price;
        if (fixedPrice.getPrice().getCurrency() == null) {
            price = fixedPrice.getPrice().getAmount() + " RUB";
        } else {
            price = fixedPrice.getPrice().getAmount() + " " + fixedPrice.getPrice().getCurrency().name();
        }

        fixedPriceVO.setPrice(price);
        fixedPriceVO.setDate(fixedPrice.getDate().toString());
        fixedPriceVO.setShop(fixedPrice.getShop());
        return fixedPriceVO;
    }

    public static MessageVO convertMessageToMessageVO(Message message) {
        MessageVO messageVO = new MessageVO();
        messageVO.setStatus(message.getStatus().name());
        messageVO.setMessageText(message.getMessage());
        return messageVO;
    }
}
