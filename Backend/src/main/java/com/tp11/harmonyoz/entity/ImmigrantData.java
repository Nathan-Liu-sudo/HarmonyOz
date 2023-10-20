package com.tp11.harmonyoz.entity;

public class ImmigrantData {

    private String country;

    private String subName;

    private Integer subCode;

    private Integer population;

    private Integer caseCount;

    public ImmigrantData() {
    }

    public ImmigrantData(String country, Integer population) {
        this.country = country;
        this.population = population;
    }

    public ImmigrantData(String subName, Integer subCode, Integer population) {
        this.subName = subName;
        this.subCode = subCode;
        this.population = population;
    }

    public Integer getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(Integer caseCount) {
        this.caseCount = caseCount;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getSubCode() {
        return subCode;
    }

    public void setSubCode(Integer subCode) {
        this.subCode = subCode;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }
}
