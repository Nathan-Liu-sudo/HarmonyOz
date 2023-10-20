package com.tp11.harmonyoz.service;

import com.tp11.harmonyoz.entity.SchoolData;
import com.tp11.harmonyoz.mapper.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService {
    private final SchoolMapper schoolMapper;

    @Autowired
    public SchoolService(SchoolMapper schoolMapper) {
        this.schoolMapper = schoolMapper;
    }

    public List<SchoolData> getPubScGeo(String schoolType) {
        return schoolMapper.getPubScGeo(schoolType);
    }

    public List<SchoolData> getPriScGeo(String schoolType) {
        return schoolMapper.getPriScGeo(schoolType);
    }

//    public List<SchoolData> getSchoolLoc(String schoolType, String eduSector, String suburb) {
//        return schoolMapper.getSchoolLoc(schoolType, eduSector, suburb);
//    }

    public List<SchoolData> getSchoolLoc(String schoolType, String eduSector, Double x, Double y) {
        return schoolMapper.getSchoolLoc(schoolType, eduSector, x, y);
    }
}
