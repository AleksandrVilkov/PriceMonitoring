package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.MonitoringListDataStorage;

import java.util.logging.Logger;

public class MonitorListHelper {
    MonitoringListDataStorage monitoringListDataStorage;
    Logger logger = Logger.getLogger("MonitorListHelper");

    public boolean createMonitoringList(MonitoringList monitoringList) {
        if (monitoringListDataStorage.readMonitoringList() == null) {
            return monitoringListDataStorage.createMonitoringList(monitoringList);
        } else {
            return false;
        }
    }

    public MonitoringList readMonitoringList() {
        return monitoringListDataStorage.readMonitoringList();
    }

    public boolean updateMonitoringList(MonitoringList monitoringList) {
        if (monitoringListDataStorage.readMonitoringList() == null) {
            return false;
        } else if (monitoringListDataStorage.readMonitoringList() != null && monitoringListDataStorage.deleteMonitoringList()) {
            return monitoringListDataStorage.createMonitoringList(monitoringList);
        } else {
            return false;
        }
    }

    public boolean deleteMonitoringList() {
        return monitoringListDataStorage.deleteMonitoringList();
    }
}
