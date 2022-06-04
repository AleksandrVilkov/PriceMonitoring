package com.vilkov.PriceMonitoring.model.dataStorage;

import com.vilkov.PriceMonitoring.model.entity.Client;

import java.util.List;

public interface ClientDataStorageInterface {
    public List<Client> getAllClients();
}
