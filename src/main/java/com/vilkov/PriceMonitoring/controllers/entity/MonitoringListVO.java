package com.vilkov.PriceMonitoring.controllers.entity;

import java.util.List;

public class MonitoringListVO {
    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    private List<String> urls;
}
