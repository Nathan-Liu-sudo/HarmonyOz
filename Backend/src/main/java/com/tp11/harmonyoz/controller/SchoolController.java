package com.tp11.harmonyoz.controller;

import com.tp11.harmonyoz.entity.PlaygroundData;
import com.tp11.harmonyoz.entity.SchoolData;
import com.tp11.harmonyoz.service.PlaygroundService;
import com.tp11.harmonyoz.service.SchoolService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*")
public class SchoolController {

    private final SchoolService schoolService;

    private final PlaygroundService playgroundService;

    @Autowired
    public SchoolController(SchoolService schoolService, PlaygroundService playgroundService) {
        this.schoolService = schoolService;
        this.playgroundService = playgroundService;
    }

    @GetMapping("/getScGeo")
    public List<SchoolData> getScGeo(@RequestParam String schoolType, @RequestParam String educationSector) {
        List<SchoolData> resultDTOList = new ArrayList<>();
        if (Objects.equals(educationSector, "Public")) {
            resultDTOList = schoolService.getPubScGeo(schoolType);
        } else {
            resultDTOList = schoolService.getPriScGeo(schoolType);
        }
        return resultDTOList;
    }

    @GetMapping("/getSchoolLoc")
    public List<SchoolData> getSchoolLoc(@RequestParam String schoolType, @RequestParam String educationSector,
                                         @RequestParam Double x, @RequestParam Double y) {
        List<SchoolData> resultDTOList = new ArrayList<>();
        resultDTOList = schoolService.getSchoolLoc(schoolType, educationSector, x, y);
        return resultDTOList;
    }

    @GetMapping("/getPlayground")
    public List<PlaygroundData> getPlayground(){
        List<PlaygroundData> resultDTOList = new ArrayList<>();
        resultDTOList = playgroundService.getPlayground();
        return resultDTOList;
    }
}

