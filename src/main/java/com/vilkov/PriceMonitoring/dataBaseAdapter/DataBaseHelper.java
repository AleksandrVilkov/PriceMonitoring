package com.vilkov.PriceMonitoring.dataBaseAdapter;

import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.*;
import org.bson.Document;

import java.util.ArrayList;
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
                    "type", (baseEntity).getType()
            ));
        }

        if (baseEntity instanceof Client) {
            StringBuilder password = new StringBuilder();
            for (int i = 0; i < ((Client) baseEntity).getPassword().length; i++) {
                password.append(((Client) baseEntity).getPassword()[i]);
            }
            return new Document(Map.of(
                    "clientID", ((Client) baseEntity).getClientID(),
                    "password", password.toString(),
                    "type", baseEntity.getType()
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
                    "type", (baseEntity).getType()
            ));
        }
        if (baseEntity instanceof ValuteContainer) {
            List<Document> valutes = new ArrayList<>();
            for (Valute valute : ((ValuteContainer) baseEntity).getValutes()) {
                Document document = new Document(Map.of(
                        "date", valute.getDate(),
                        "charCode", valute.getCharCode(),
                        "value", valute.getValue(),
                        "previous", valute.getPrevious(),
                        "numCode", valute.getNumCode(),
                        "nominal", valute.getNominal(),
                        "name", valute.getName()
                ));
                valutes.add(document);
            }


            return new Document(Map.of(
                    "type", (baseEntity).getType(),
                    "createDate", ((ValuteContainer) baseEntity).getDateOfCreation(),
                    "Valutes", valutes
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
            char[] password = document.get("password").toString().toCharArray();
            return new Client(id, password);
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
        if (document.get("type").equals(ValuteContainer.class.toString())) {
            //TODO реализовать

        }


        return null;
    }
}
