package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.dataBaseAdapter.DataBaseAdapter;
import com.vilkov.PriceMonitoring.model.dataStorage.DataStorageInterface;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;

import java.util.List;
import java.util.logging.Logger;

public class MonitoringListHelper {
    private static final DataStorageInterface dataStorageInterface = new DataBaseAdapter();
    Logger logger = Logger.getLogger("MonitorListHelper");

    public static boolean createMonitoringList(MonitoringList monitoringList, Client client) {
        if (dataStorageInterface.readEntity(client, MonitoringList.class) == null) {
            return dataStorageInterface.createEntity(monitoringList, client);
        } else {
            return false;
        }
    }

    public static List<BaseEntity> readMonitoringList(Client client) {
        return dataStorageInterface.readEntity(client, MonitoringList.class);
    }

    public static boolean updateMonitoringList(MonitoringList monitoringList, Client client) {
        return dataStorageInterface.updateEntity(monitoringList, client);
    }

    public static boolean deleteMonitoringList(Class cls, Client client) {
        return dataStorageInterface.deleteEntity(client, cls);
    }
}
