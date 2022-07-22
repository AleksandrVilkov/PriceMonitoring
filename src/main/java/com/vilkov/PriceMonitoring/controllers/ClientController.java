package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.controllers.entity.EntityHelper;
import com.vilkov.PriceMonitoring.controllers.entity.MessageVO;
import com.vilkov.PriceMonitoring.model.ClientHelper;
import com.vilkov.PriceMonitoring.model.Status;
import com.vilkov.PriceMonitoring.model.entity.Client;
import com.vilkov.PriceMonitoring.model.entity.Message;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("api/client")
public class ClientController {
    @Autowired
    ClientHelper clientHelper;
    private final Logger logger = new Logger(Logger.getLoggerProperties().getProperty("controllersLogFolder"),
            Logger.getLoggerProperties().getProperty("controllersLogFileName"));
    @PostMapping("/create")
    public MessageVO createClient(@RequestParam("id") String id, @RequestParam("password") String clientPassword) {
        char[] password = clientPassword.toCharArray();
        Client client = new Client(id, password);
        Message message = clientHelper.createClient(client);
        if (message.getStatus().equals(Status.SUCCESS)) {
            logger.save("successful create client with ID " + id);
        } else {
            logger.save("ERROR creating client with ID " + id);
        }
        return EntityHelper.convertMessageToMessageVO(message);
    }
}
