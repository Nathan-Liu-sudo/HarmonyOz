package com.tp11.harmonyoz.entity;

public class JobData {

    private String suburb;

    private Integer people;

    private String Occupation;

    private String industry;

    private Integer year;

    private Integer job_count;

    public String getSuburb() {
        return suburb;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getJob_count() {
        return job_count;
    }

    public void setJob_count(Integer job_count) {
        this.job_count = job_count;
    }
}
