package com.mercury.interview.dao;

import com.mercury.interview.bean.Interview;
import com.mercury.interview.bean.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface InterviewDao extends JpaRepository<Interview,Integer> {
    @Query(value = "select scheduler, count(scheduler)from Interview group by scheduler")
    public List<String> schedulerInterview();

    @Query("select scheduler, count(scheduler)from Interview group by scheduler having status= Pass ")
    public List<String> passedInterview();
}
