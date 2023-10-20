package com.tp11.harmonyoz.controller;

import com.tp11.harmonyoz.entity.JobData;
import com.tp11.harmonyoz.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class JobController {

    private final JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/getAllOccupations")
    public List<String> getAllOccupations() {
        List<String> resultDTOList = jobService.getAllOccupations();
        return resultDTOList;
    }

    @GetMapping("/getAllIndustries")
    public List<String> getAllIndustries() {
        List<String> resultDTOList = jobService.getAllIndustries();
        return resultDTOList;
    }

    @GetMapping("/getOccCountBySub")
    public List<JobData> getOccCountBySub(@RequestParam String occupation, @RequestParam String country) {
        country += "_Tot";
        List<JobData> resultDTOList = jobService.getOccCountBySub(occupation, country);
        return resultDTOList;
    }

    @GetMapping("/getOccForecast")
    public List<JobData> getOccForecast(@RequestParam String occupation){
        List<JobData> resultDTOList = jobService.getOccForecast(occupation);
        return resultDTOList;
    }
}
