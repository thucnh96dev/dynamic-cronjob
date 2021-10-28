package com.thucnh.cronjob.controller;

import com.thucnh.cronjob.domain.JobInfo;
import com.thucnh.cronjob.service.JobService;
import org.quartz.impl.SchedulerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :28/10/2021 - 8:36 AM
 */
@Controller
@RequestMapping(value = "/job")
public class JobController {

    @Autowired
    private JobService jobService;


    @GetMapping
    public String jobPage(Model model){
        List<JobInfo> jobs = jobService.getAll();
        model.addAttribute("jobs",jobs);
        return "index";

    }
}
