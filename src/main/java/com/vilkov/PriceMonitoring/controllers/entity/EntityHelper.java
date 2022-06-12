package com.vilkov.PriceMonitoring.controllers.entity;

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
    public static MonitoringListVO convertProductToProductVO(MonitoringList monitoringList) {
        MonitoringListVO monitoringListVO = new MonitoringListVO();
        monitoringListVO.setUrls(monitoringList.getUrls());
        return monitoringListVO;
    }

}
