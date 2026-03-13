package com.mercury.interview.service;

import com.mercury.interview.bean.Interview;
import com.mercury.interview.dao.InterviewDao;
import com.mercury.interview.http.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    @Autowired
    private InterviewDao interviewDao;

    public List<Interview> getAll() {
        return interviewDao.findAll();
    }

    public Response save(Interview interview) {
        System.out.println("!!!!!"+interview);
        try {
            interviewDao.save(interview);
            return new Response(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false);
        }
    }

    public Response edit(Interview interview) {
        Interview i = interviewDao.findById(interview.getId()).get();
        if (i != null) {
            i.setStatus(interview.getStatus());
            interviewDao.save(i);
            return new Response(true);
        } else {
            return new Response(false);
        }
    }



}
