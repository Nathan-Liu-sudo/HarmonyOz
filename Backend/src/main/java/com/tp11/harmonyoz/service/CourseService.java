package com.tp11.harmonyoz.service;

import com.tp11.harmonyoz.entity.CourseData;
import com.tp11.harmonyoz.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseMapper courseMapper;

    @Autowired
    public CourseService(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public List<CourseData> getCourseInfo() {
        return courseMapper.getCourseInfo();
    }
}
