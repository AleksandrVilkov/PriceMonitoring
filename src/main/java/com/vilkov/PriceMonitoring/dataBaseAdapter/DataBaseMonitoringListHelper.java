package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.vilkov.PriceMonitoring.model.MonitoringList;
import org.bson.Document;

import java.util.*;

public class DataBaseMonitoringListHelper {
    public static Document toDoc(MonitoringList monitoringList) {
        List<String> urls = monitoringList.getUrl();
        Map <String, String> result = new HashMap<>();
        for (int i = 0; i<urls.size(); i++) {
            result.put(String.valueOf(i),urls.get(i));
        }
        return new Document(Collections.unmodifiableMap(result));
    }

    public static MonitoringList fromDoc(Document document) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i<document.size();i++) {
            result.add(String.valueOf(document.get(Integer.toString(i))));
        }
        return new MonitoringList().setUrl(result);
    }
}
