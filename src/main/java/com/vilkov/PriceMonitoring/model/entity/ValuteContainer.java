package com.vilkov.PriceMonitoring.model.entity;

import java.util.Date;
import java.util.List;

public class ValuteContainer implements BaseEntity {

    private String type;
    private List<Valute> valutes;
    private Date dateOfCreation;

    public ValuteContainer() {
        this.dateOfCreation = new Date();
        this.type = ValuteContainer.class.toString();
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public List<Valute> getValutes() {
        return valutes;
    }

    public void setValutes(List<Valute> valutes) {
        this.valutes = valutes;
    }

    @Override
    public String getType() {
        return type;
    }
}
