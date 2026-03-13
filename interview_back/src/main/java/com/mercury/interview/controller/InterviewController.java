package com.mercury.interview.controller;

import com.mercury.interview.bean.Interview;
import com.mercury.interview.http.Response;
import com.mercury.interview.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping
    public List<Interview> getAll(){
        return interviewService.getAll();
    }
    @PostMapping
    public Response save(@RequestBody Interview interview){
        System.out.println("######"+interview);
        return  interviewService.save(interview);
    }
    @PostMapping("/edit")
    public Response edit(@RequestBody Interview interview){
        return interviewService.edit(interview);
    }
}
