package com.vilkov.PriceMonitoring.model.dataStorage;

import com.vilkov.PriceMonitoring.model.MonitoringList;

public interface MonitoringListDataStorage {
    public boolean createMonitoringList(MonitoringList monitoringList);

    public MonitoringList readMonitoringList();

    public boolean updateMonitoringList(MonitoringList monitoringList);

    public boolean deleteMonitoringList();

}
