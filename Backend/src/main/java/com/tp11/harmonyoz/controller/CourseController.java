package com.tp11.harmonyoz.controller;

import com.tp11.harmonyoz.entity.CourseData;
import com.tp11.harmonyoz.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/getCourseInfo")
    public List<CourseData> getCourseInfo(){
        List<CourseData> courseData = courseService.getCourseInfo();
        return courseData;
    }
}
