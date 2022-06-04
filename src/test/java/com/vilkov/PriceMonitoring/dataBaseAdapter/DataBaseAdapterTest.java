package com.vilkov.PriceMonitoring.dataBaseAdapter;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class DataBaseAdapterTest {
    @Test
    public void getAllDataBasesTest() {
        DataBaseAdapter dataBaseAdapter = DataBaseAdapter.getInstance();
        Assert.assertEquals(dataBaseAdapter.getAllClients().isEmpty(), false);
    }
}
