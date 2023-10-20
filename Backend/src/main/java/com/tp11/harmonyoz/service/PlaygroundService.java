package com.tp11.harmonyoz.service;

import com.tp11.harmonyoz.entity.PlaygroundData;
import com.tp11.harmonyoz.mapper.PlaygroundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaygroundService {

    private final PlaygroundMapper playgroundMapper;

    @Autowired
    public PlaygroundService(PlaygroundMapper playgroundMapper) {this.playgroundMapper = playgroundMapper;}

    public List<PlaygroundData> getPlayground(){
        return playgroundMapper.getPlayground();
    }
}
