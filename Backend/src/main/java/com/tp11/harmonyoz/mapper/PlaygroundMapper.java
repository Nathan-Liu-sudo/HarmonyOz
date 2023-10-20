package com.tp11.harmonyoz.mapper;

import com.tp11.harmonyoz.entity.PlaygroundData;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PlaygroundMapper {

    @Select("SELECT name, SUBSTRING_INDEX(geo_point, ', ', 1) AS x, SUBSTRING_INDEX(geo_point, ', ', -1) AS y FROM playground")
    List<PlaygroundData> getPlayground();
}
