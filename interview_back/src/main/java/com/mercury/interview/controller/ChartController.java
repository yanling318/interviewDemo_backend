package com.mercury.interview.controller;

import com.mercury.interview.bean.Scheduler;
import com.mercury.interview.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/charts")
public class ChartController {

    @Autowired
    private ChartService chartService;

    @GetMapping("/pie")
    public List<Scheduler> getPie(){
        System.out.println("line");
        System.out.println(chartService.getPie());
       return chartService.getPie();

    }
    @GetMapping("/bar")
    public List<Scheduler> getBar(){
       return chartService.getBar();
    }

    @GetMapping
    public List<?> getPiee(){
        System.out.println("pie");
       return chartService.getPiee();

    }


}
