package com.vilkov.PriceMonitoring.model.dataStorage;

import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DataStorageInterface {
    public boolean createEntity(BaseEntity baseEntity, Client client);

    public List<BaseEntity> readEntities(Client client, Class cls);

    public boolean updateEntity(BaseEntity baseEntity, Client client);

    public boolean deleteEntity(Client client, Class cls, String id);

    public List<Client> getAllClients();
}
