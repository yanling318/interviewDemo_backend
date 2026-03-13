package com.mercury.interview.service;

import com.mercury.interview.bean.Interview;
import com.mercury.interview.bean.Scheduler;
import com.mercury.interview.dao.InterviewDao;
import com.sun.org.apache.xpath.internal.objects.XNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChartService {

    @Autowired
    private InterviewDao interviewDao;

    public List<Scheduler> getPie() {
        HashMap<String, Integer> map = new HashMap<>();
        List<Scheduler> pie = new ArrayList<>();
        interviewDao.findAll().forEach(i -> {
            String scheduler = i.getScheduler();
            if (map.get(scheduler) == null) {
                map.put(scheduler, 1);
            } else {
                map.put(scheduler, map.get(scheduler) + 1);
            }
        });
        //System.out.println(map);
        for (String key : map.keySet()) {
            pie.add(new Scheduler(key, map.get(key)));
        }

        return pie;
    }

    public List<Scheduler> getBar() {
        HashMap<String, Integer> map = new HashMap<>();
        List<Scheduler> bar = new ArrayList<>();
        interviewDao.findAll().forEach(i -> {
            String scheduler = i.getScheduler();
            if (map.get(scheduler) == null &&i.getStatus().equals("Pass")) {
                map.put(scheduler, 1);
            } else {
                //System.out.println(i.getStatus());
                if (i.getStatus().equals("Pass")) {
                    map.put(scheduler, map.get(scheduler) + 1);
                }
            }
        });

        HashMap<String, Integer> newMap = new HashMap<>();
        interviewDao.findAll().forEach(i -> {
            String scheduler = i.getScheduler();
            if (newMap.get(scheduler) == null) {
                newMap.put(scheduler, 1);
            } else {
                newMap.put(scheduler, newMap.get(scheduler) + 1);
            }
        });
        //System.out.println(map);
        HashMap<String,Double> map1 = new HashMap<>();

        for (String key : map.keySet()) {
            for (String newKey : map.keySet()) {
                if(key == newKey){
                    //System.out.println(map.get(key));
                   map1.put(key,(double) Math.round(map.get(key)/ (double)newMap.get(newKey)*100)/100);
                   // double r = (double)Math.round(xxx*100)/100;
                }
            }
        }
        //System.out.println(map1);
        //System.out.println(newMap);

        for(String key: map1.keySet()){
            bar.add(new Scheduler(key,map1.get(key)));
        }

        return bar;
    }

    public List<?> getPiee() {
        System.out.println("query");
        System.out.println("!!!!" + interviewDao.schedulerInterview());
        interviewDao.schedulerInterview().forEach(i->{
            System.out.println(i);
        });
        return interviewDao.schedulerInterview();
    }

}
