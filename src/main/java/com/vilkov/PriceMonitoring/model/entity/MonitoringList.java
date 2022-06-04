package com.vilkov.PriceMonitoring.model.entity;

import java.util.ArrayList;
import java.util.List;

public class MonitoringList implements BaseEntity {
    private List<String> urls;
    private String type;


    public MonitoringList() {
        this.urls = new ArrayList<>();
        this.type = MonitoringList.class.toString();
    }

    public MonitoringList(List<String> urls) {
        this.urls = urls;
        this.type = MonitoringList.class.toString();
    }

    public String getType() {
        return type;
    }

    public List<String> getUrls() {
        return urls;
    }

    public MonitoringList setUrl(List<String> urls) {
        this.urls = urls;
        return null;
    }

    public boolean addUrl(String url) {
        if (!this.urls.contains(url)) {
            this.urls.add(url);
            return true;
        }
        return false;
    }

    public boolean deleteUrl(String url) {
        if (this.urls.contains(url)) {
            this.urls.remove(url);
            return true;
        }
        return false;

    }
}
