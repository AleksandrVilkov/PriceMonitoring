package com.vilkov.PriceMonitoring.controllers;

import com.vilkov.PriceMonitoring.controllers.entity.RateVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Component
@RestController
@RequestMapping("api/rate")
public class RateController {

    @PostMapping("/getRate")
    public RateVO getRate(@RequestParam("currency") String currency,
                          @RequestParam("date") String date) {
        return null;
    }

    @PostMapping("/getRates")
    public List<RateVO> getRates(@RequestParam("currency") String currency,
                                 @RequestParam("startDate") String startDate,
                                 @RequestParam("endDate") String endDate) {
        return null;
    }

}
