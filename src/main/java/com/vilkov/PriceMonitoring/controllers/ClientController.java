package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.controllers.entity.EntityHelper;
import com.vilkov.PriceMonitoring.controllers.entity.MessageVO;
import com.vilkov.PriceMonitoring.model.logger.Logger;
import com.vilkov.PriceMonitoring.model.ClientHelper;
import com.vilkov.PriceMonitoring.model.entity.Client;
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
    //TODO покрыть логами
    @PostMapping("/create")
    public MessageVO createClient(@RequestParam("id") String id, @RequestParam("password") String clientPassword) {
        char[] password = clientPassword.toCharArray();
        Client client = new Client(id, password);
        return EntityHelper.convertMessageToMessageVO(clientHelper.createClient(client));
    }


}
