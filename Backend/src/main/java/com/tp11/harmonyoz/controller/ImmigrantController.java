package com.tp11.harmonyoz.controller;

import com.tp11.harmonyoz.entity.ImmigrantData;
import com.tp11.harmonyoz.service.ImmigrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ImmigrantController {

    private final ImmigrantService immigrantService;

    @Autowired
    public ImmigrantController(ImmigrantService immigrantService) {
        this.immigrantService = immigrantService;
    }

    @GetMapping("/")
    public String helloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/getTopFiveCountry")
    public List<ImmigrantData> getTopFiveCountry(){
        List<ImmigrantData> topFiveImmigrantData = immigrantService.getTopFiveCountry();
        return topFiveImmigrantData;
    }

    @GetMapping("/getCountries")
    public List<String> getCountries(){
        List<ImmigrantData> immigrantDataList = immigrantService.getCountries();
        List<String> countries = new ArrayList<>();

        for (ImmigrantData data : immigrantDataList) {
            countries.add(data.getCountry());
        }

        return countries;
    }

    @GetMapping("/findPopulationByCountry")
    public Integer findPopulationByCountry(@RequestParam String country) {
        if (country != null && !country.isEmpty()) {
            Integer population = immigrantService.findPopulationByCountry(country);
            return population;
        } else {
            return 0;
        }
    }

    @GetMapping("/getMapCountries")
    public List<String> getMapCountries(){
        List<String> mapCountries = immigrantService.getMapCountries();
        System.out.println(mapCountries);
        return mapCountries;
    }

    @GetMapping("/getMapDataByCountry")
    public List<ImmigrantData> getMapDataByCountry(@RequestParam String country, @RequestParam String priceRange){
        List<ImmigrantData> immigrantDataList = immigrantService.getMapDataByCountry(country, priceRange);
        return immigrantDataList;
    }

    @GetMapping("/getTotalPop")
    public Integer getTotalPop() {
        Integer total = immigrantService.getTotalPop();
        if ( total != null) {
            return total;
        }
        return 0;
    }

}

