package com.vilkov.PriceMonitoring.controllers.entity;

import com.vilkov.PriceMonitoring.model.entity.FixedPrice;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;
import com.vilkov.PriceMonitoring.model.entity.Product;

public class EntityHelper {
    public static ProductVO convertProductToProductVO(Product product) {
        ProductVO productVO = new ProductVO();
        productVO.setId(product.getId());
        productVO.setName(product.getName());
        String price = product.getPrice().getAmount() + " " + product.getPrice().getCurrency().name();
        productVO.setPrice(price);
        productVO.setShop(product.getShop());
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
