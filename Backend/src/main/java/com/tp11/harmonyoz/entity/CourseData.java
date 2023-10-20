package com.tp11.harmonyoz.entity;

public class CourseData {

    private String courseCode;

    private String courseName;

    private String courseLevel;

    private Integer courseDuration;

    private Integer courseFee;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public void setCourseLevel(String courseLevel) {
        this.courseLevel = courseLevel;
    }

    public Integer getCourseDuration() {
        return courseDuration;
    }

    public void setCourseDuration(Integer courseDuration) {
        this.courseDuration = courseDuration;
    }

    public Integer getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(Integer courseFee) {
        this.courseFee = courseFee;
    }
}
