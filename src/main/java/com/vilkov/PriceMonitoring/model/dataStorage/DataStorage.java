package com.vilkov.PriceMonitoring.model.dataStorage;

import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DataStorage {
    @Autowired
    private DataStorageInterface dataBaseAdapter;


    public boolean createEntity(BaseEntity baseEntity, Client client) {
        return dataBaseAdapter.createEntity(baseEntity, client);
    }

    public List<BaseEntity> readEntities(Client client, Class cls) {
        return dataBaseAdapter.readEntities(client, cls);
    }

    public boolean updateEntity(BaseEntity baseEntity, Client client) {
        return dataBaseAdapter.updateEntity(baseEntity, client);
    }

    public boolean deleteEntity(Client client, Class cls, String id) {
        return dataBaseAdapter.deleteEntity(client, cls, id);
    }

    public List<Client> getAllClients() {
        return dataBaseAdapter.getAllClients();
    }


}
