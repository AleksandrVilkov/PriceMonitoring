package com.vilkov.PriceMonitoring.model.dataStorage;

import com.vilkov.PriceMonitoring.model.entity.BaseEntity;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Product;

import java.util.List;

public interface DataStorageInterface {
    public boolean createEntity(BaseEntity baseEntity, Client client);

    public List<BaseEntity> readEntity(Client client, Class cls);

    public boolean updateEntity(BaseEntity baseEntity, Client client);

    public boolean deleteEntity(Client client, Class cls);
}
