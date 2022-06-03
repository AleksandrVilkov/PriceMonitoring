package com.vilkov.PriceMonitoring.model;

import java.util.ArrayList;
import java.util.List;

public class MonitoringList {
    private List<String> urls;

    public MonitoringList() {
        urls = new ArrayList<>();
    }

    public List<String> getUrl() {
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
