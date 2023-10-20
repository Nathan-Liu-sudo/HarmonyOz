package com.tp11.harmonyoz.mapper;

import com.tp11.harmonyoz.entity.CourseData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("SELECT DISTINCT CRICOS_Course_Code as courseCode, Course_Name as courseName, Course_Level as courseLevel, " +
            "`Duration_(Weeks)` as courseDuration, Tuition_Fee as courseFee from vic_courses")
    List<CourseData> getCourseInfo();
}
