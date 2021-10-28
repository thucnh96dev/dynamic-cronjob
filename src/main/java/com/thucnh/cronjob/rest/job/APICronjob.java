package com.thucnh.cronjob.rest.job;

import com.thucnh.cronjob.domain.JobInfo;
import com.thucnh.cronjob.payload.job.JobFindFillter;
import com.thucnh.cronjob.payload.job.JobIto;
import com.thucnh.cronjob.payload.job.ScheduleJob;
import com.thucnh.cronjob.rest.AbstractAPI;
import com.thucnh.cronjob.service.JobService;
import com.thucnh.cronjob.service.quartz.QuartzJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : thucnh
 * @mailto : thucnh96.dev@gmail.com
 * @created :27/10/2021 - 11:26 AM
 */
@RequestMapping(APICronjob._APINAME)
@RestController
public class APICronjob extends AbstractAPI {

    public static final   String _APINAME = "/api/quartz";

    @Autowired
    private JobService jobService;

    @Autowired
    private QuartzJobService quartzJobService;

    @GetMapping("")
    public ResponseEntity<Object> getJob(Model model) throws Exception{
        Map<String, Object> data = new HashMap<>();
        List<ScheduleJob> entities = quartzJobService.getRunningJobs();
        model.addAttribute("entities",entities);
        return toResponse(data);
    }

    @PostMapping("/find")
    public ResponseEntity<Object> findJob(@RequestBody JobFindFillter fillter) throws Exception{
        Map<String, Object> data = new HashMap<>();
        Page<JobInfo> page = jobService.findAllJobs(fillter);
        //List<ScheduleJob> entities = quartzJobService.getRunningJobs();
        data.put("entities",page.getContent());
        data.put("totalElement",page.getTotalElements());
        return toResponse(data);
    }


    @PostMapping("")
    public ResponseEntity<Object> addJob(@RequestBody JobIto jobIto) throws Exception{
        Map<String, Object> data = quartzJobService.addJob(jobIto.getClassName(),jobIto.getCronExpression());
        return toResponse(data);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateJob(@PathVariable(name = "id") long id) throws Exception{
        Map<String, Object> data = new HashMap<>();
        return toResponse(data);
    }

    @PostMapping("/pause")
    public ResponseEntity<Object> pauseJob(@PathVariable(name = "id") long id) throws Exception{
        Map<String, Object> data = new HashMap<>();
        return toResponse(data);
    }

    @PostMapping("/resume")
    public ResponseEntity<Object> resumeJob(@PathVariable(name = "id") long id) throws Exception{
        Map<String, Object> data = new HashMap<>();
        return toResponse(data);
    }
}
