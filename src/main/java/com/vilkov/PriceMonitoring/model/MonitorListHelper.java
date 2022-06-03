package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorageInterface;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;

import java.util.List;
import java.util.logging.Logger;

public class MonitorListHelper {
    DataStorageInterface dataStorageInterface;
    Logger logger = Logger.getLogger("MonitorListHelper");

    public boolean createMonitoringList(MonitoringList monitoringList, Client client) {
        if (dataStorageInterface.readEntity(client, MonitoringList.class) == null) {
            return dataStorageInterface.createEntity(monitoringList, client);
        } else {
            return false;
        }
    }

    public List<BaseEntity> readMonitoringList(Client client) {
        return dataStorageInterface.readEntity(client,MonitoringList.class);
    }

    public boolean updateMonitoringList(MonitoringList monitoringList, Client client) {
        return false;
    }

    public boolean deleteMonitoringList(Class cls, Client client) {
        return dataStorageInterface.deleteEntity(client, cls);
    }
}
