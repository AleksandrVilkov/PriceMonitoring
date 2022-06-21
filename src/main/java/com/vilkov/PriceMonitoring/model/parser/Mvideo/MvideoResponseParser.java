package com.vilkov.PriceMonitoring.model.parser.Mvideo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Currency;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.entity.Money;
import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parser.Parser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

public class MvideoResponseParser implements Parser {
    static Logger logger = Logger.getLogger("MvideoResponseParser");

    /**
     * TODO Решить, откуда брать имя товара
     * TODO Переделать под клиентскую ссылку
     */
    public static Product getResponse(String url) {
        Properties properties = new Properties();
        ResponseFromMvideo responseFromMvideo = null;

        try (FileInputStream fileInputStream = new FileInputStream("src/main/java/com/vilkov/PriceMonitoring/model/parser/Mvideo/requestProperty.properties")) {
            properties.load(fileInputStream);
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod(properties.getProperty("requestMethodGET"));
            httpURLConnection.setRequestProperty("user-agent", properties.getProperty("user-agent"));
            httpURLConnection.setRequestProperty("cookie", properties.getProperty("cookie"));
            logger.info("Sending 'GET' request to URL : " + url);
            logger.info("Response Code : " + httpURLConnection.getResponseCode());

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
            responseFromMvideo = new ObjectMapper().readValue(response.toString(), ResponseFromMvideo.class);
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }

        Product result;
        if (responseFromMvideo != null) {
            Money money = new Money();
            money.setCurrency(Currency.RUB);
            money.setAmount(Double.parseDouble(responseFromMvideo.getBody().materialPrices.get(0).getPrice().getBasePrice()));
            Message message = new Message(Status.SUCCESS, "Успешно!");
            result = new Product(responseFromMvideo.getBody().getMaterialPrices().get(0).getProductId(), money,
                    "www.mvideo.ru", url, new Date(), message);

        } else {
            result = new Product();
            result.setMessage(new Message(Status.ERROR, "Неудачная попытка. цена по URL " + url + " не получена"));
        }
        return result;
    }


}
//TODO вынести в отдельные классы в пакет entity
class ResponseFromMvideo {
    String success;
    List<String> messages;
    Body body;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}

class Body {
    List<String> availablePersonalPromos;
    PromoConfiguratorConfig promoConfiguratorConfig;
    List<MaterialPrices> materialPrices;

    public List<String> getAvailablePersonalPromos() {
        return availablePersonalPromos;
    }

    public void setAvailablePersonalPromos(List<String> availablePersonalPromos) {
        this.availablePersonalPromos = availablePersonalPromos;
    }

    public PromoConfiguratorConfig getPromoConfiguratorConfig() {
        return promoConfiguratorConfig;
    }

    public void setPromoConfiguratorConfig(PromoConfiguratorConfig promoConfiguratorConfig) {
        this.promoConfiguratorConfig = promoConfiguratorConfig;
    }

    public List<MaterialPrices> getMaterialPrices() {
        return materialPrices;
    }

    public void setMaterialPrices(List<MaterialPrices> materialPrices) {
        this.materialPrices = materialPrices;
    }
}

class MaterialPrices {
    String productId;
    Prices price;
    String bonusRubles;

    public String getBonusRubles() {
        return bonusRubles;
    }

    public void setBonusRubles(String bonusRubles) {
        this.bonusRubles = bonusRubles;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Prices getPrice() {
        return price;
    }

    public void setPrice(Prices price) {
        this.price = price;
    }
}

class Prices {
    String productId;
    String basePrice;
    String salePrice;
    String basePromoPrice;
    List<String> discounts;
    String applyManualDiscount;
    boolean applyPersonalPrice;
    boolean isFinalPrice;
    String supplier;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getBasePromoPrice() {
        return basePromoPrice;
    }

    public void setBasePromoPrice(String basePromoPrice) {
        this.basePromoPrice = basePromoPrice;
    }

    public List<String> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<String> discounts) {
        this.discounts = discounts;
    }

    public String getApplyManualDiscount() {
        return applyManualDiscount;
    }

    public void setApplyManualDiscount(String applyManualDiscount) {
        this.applyManualDiscount = applyManualDiscount;
    }

    public boolean isApplyPersonalPrice() {
        return applyPersonalPrice;
    }

    public void setApplyPersonalPrice(boolean applyPersonalPrice) {
        this.applyPersonalPrice = applyPersonalPrice;
    }

    public boolean getIsFinalPrice() {
        return isFinalPrice;
    }

    public void setIsFinalPrice(boolean finalPrice) {
        isFinalPrice = finalPrice;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
}

class PromoConfiguratorConfig {
}