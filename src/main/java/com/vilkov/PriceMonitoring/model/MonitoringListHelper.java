package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.model.dataStorage.DataStorageInterface;
import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.MonitoringList;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

public class MonitoringListHelper {
    @Autowired
    private static DataStorageInterface dataBaseAdapter;
    Logger logger = Logger.getLogger("MonitorListHelper");

    public static boolean createMonitoringList(MonitoringList monitoringList, Client client) {
        if (dataBaseAdapter.readEntity(client, MonitoringList.class) == null) {
            return dataBaseAdapter.createEntity(monitoringList, client);
        } else {
            return false;
        }
    }

    public static List<BaseEntity> readMonitoringList(Client client) {
        return dataBaseAdapter.readEntity(client, MonitoringList.class);
    }

    public static boolean updateMonitoringList(MonitoringList monitoringList, Client client) {
        return dataBaseAdapter.updateEntity(monitoringList, client);
    }

    public static boolean deleteMonitoringList(Class cls, Client client) {
        return dataBaseAdapter.deleteEntity(client, cls);
    }
}
