package com.tp11.harmonyoz.service;

import com.tp11.harmonyoz.entity.ImmigrantData;
import com.tp11.harmonyoz.mapper.ImmigrantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImmigrantService {
    private final ImmigrantMapper immigrantMapper;

    @Autowired
    public ImmigrantService(ImmigrantMapper immigrantMapper) {
        this.immigrantMapper = immigrantMapper;
    }

    public List<ImmigrantData> getTopFiveCountry() {
        return immigrantMapper.getTopFiveCountry();
    }
    public List<ImmigrantData> getCountries() {
        return immigrantMapper.getCountries();
    }
    public Integer findPopulationByCountry(String country) {
        return immigrantMapper.findPopulationByCountry(country);
    }
    public List<String> getMapCountries() {return immigrantMapper.getMapCountries();}
    public List<ImmigrantData> getMapDataByCountry(String country, String priceRange) {
        return immigrantMapper.getMapDataByCountry(country, priceRange);}
    public Integer getTotalPop() { return immigrantMapper.getTotalPop(); }
}
