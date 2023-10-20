package com.tp11.harmonyoz.service;

import com.tp11.harmonyoz.entity.JobData;
import com.tp11.harmonyoz.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private final JobMapper jobMapper;

    @Autowired
    public JobService(JobMapper jobMapper) {
        this.jobMapper = jobMapper;
    }

    public List<String> getAllOccupations() {
        return jobMapper.getAllOccupations();
    }

    public List<String> getAllIndustries() {
        return jobMapper.getAllIndustries();
    }

    public List<JobData> getOccCountBySub(String occupation, String country) {
        return jobMapper.getOccCountBySub(occupation, country);
    }

    public List<JobData> getOccForecast(String occupation) {
        return jobMapper.getOccForecast(occupation);
    }

}