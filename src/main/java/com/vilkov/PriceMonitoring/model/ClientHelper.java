package com.vilkov.PriceMonitoring.model;

import com.vilkov.PriceMonitoring.dataBaseAdapter.DataBaseAdapter;
import com.vilkov.PriceMonitoring.model.dataStorage.ClientDataStorageInterface;
import com.vilkov.PriceMonitoring.model.entity.Client;

import java.util.List;

public class ClientHelper {

    private static final Client ADMIN_CLIENT = new Client("admin");
    private static final ClientDataStorageInterface dataStorageInterface = new DataBaseAdapter();

    public static List<Client> readClients() {
        return dataStorageInterface.getAllClients();
    }
}
