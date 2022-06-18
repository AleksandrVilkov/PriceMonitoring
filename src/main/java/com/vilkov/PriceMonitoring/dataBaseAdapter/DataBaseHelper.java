package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.*;
import org.bson.Document;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static com.vilkov.PriceMonitoring.model.entity.Currency.RUB;
import static com.vilkov.PriceMonitoring.model.entity.Currency.UNKNOWN;

public class DataBaseHelper {

    static Logger logger = Logger.getLogger("DataBaseHelper");


    public static Document toDoc(BaseEntity baseEntity) {
        if (baseEntity instanceof MonitoringList) {
            return new Document(Map.of(
                    "urls", ((MonitoringList) baseEntity).getUrls(),
                    "type", ((MonitoringList) baseEntity).getType()
            ));
        }

        if (baseEntity instanceof Client) {
            return new Document(Map.of(
                    "clientID", ((Client) baseEntity).getClientID(),
                    "type", ((Client) baseEntity).getType()
            ));
        }
        if (baseEntity instanceof Product) {
            return new Document(Map.of(
                    "url", ((Product) baseEntity).getUlr(),
                    "id", ((Product) baseEntity).getId(),
                    "name", ((Product) baseEntity).getName(),
                    "amountPrice", ((Product) baseEntity).getPrice().getAmount(),
                    "currencyPrice", ((Product) baseEntity).getPrice().getCurrency().name(),
                    "shop", ((Product) baseEntity).getShop(),
                    "date", ((Product) baseEntity).getDate(),
                    "type", ((Product) baseEntity).getType()
            ));
        }
        return null;
    }

    public static BaseEntity fromDoc(Document document) {
        if (document.get("type").equals(Product.class.toString())) {
            String url = (String) document.get("url");
            String name = (String) document.get("name");
            double amountPrice = (double) document.get("amountPrice");
            Currency currency;
            if (RUB.name().equals((String) document.get("currency"))) {
                currency = RUB;
            } else {
                currency = null;
            }
            String shop = (String) document.get("shop");

            Date date = (Date) document.get("date");
            Money price = new Money().setAmount(amountPrice).setCurrency(currency);
            return new Product(name, price, shop, url, date, new Message(Status.SUCCESS, "Derived from MongoDB"));
        }
        if (document.get("type").equals(MonitoringList.class.toString())) {
            var urls = document.get("urls");
            return new MonitoringList((List<String>) urls);
        }
        if (document.get("type").equals(Client.class.toString())) {
            String id = (String) document.get("clientID");
            return new Client(id, null);
        }

        if (document.get("type").equals(Product.class.toString())) {
            String url = (String) document.get("url");
            String name = (String) document.get("name");
            double amountPrice = (double) document.get("amountPrice");
            Currency currency = null;
            String currencyPrice = (String) document.get("currencyPrice");
            currency = currencyPrice.equals(RUB.name()) ? RUB : UNKNOWN;
            Money price = new Money(amountPrice, currency);
            String shop = (String) document.get("shop");
            Date date = (Date) document.get("date");
            Message message = new Message(Status.SUCCESS, "Successfully retrieved from DB");
            return new Product(name, price, shop, url, date, message);

        }
        return null;
    }
}
