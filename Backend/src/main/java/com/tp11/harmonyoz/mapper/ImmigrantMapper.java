package com.tp11.harmonyoz.mapper;

import com.tp11.harmonyoz.entity.ImmigrantData;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ImmigrantMapper {

    @Select("SELECT Country_of_birth as country, Population_2021 as population FROM population_born_oversea" +
            " ORDER BY Population_2021 DESC LIMIT 5")
    List<ImmigrantData> getTopFiveCountry();

    @Select("select Country_of_birth as country from population_born_oversea where Population_2021 != 0 order by Country_of_birth asc")
    List<ImmigrantData> getCountries();

    @Select("select Population_2021 as number FROM population_born_oversea where Country_of_birth = #{country}")
    Integer findPopulationByCountry(@Param("country") String country);

    @Select("SELECT LEFT(COLUMN_NAME, LENGTH(COLUMN_NAME) - 4) AS country FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'sa2_population_origin' " +
            "and COLUMN_NAME not like '%_5_14%' and COLUMN_NAME not like '%SA2%' and COLUMN_NAME not like '%Australia%' order by LEFT(COLUMN_NAME, LENGTH(COLUMN_NAME) - 4) asc")
    List<String> getMapCountries();

    // only Vic data, no 0 data
    @Select("select a.SA2_NAME_2021 as subName, a.SA2_CODE_2021 as subCode, a.${country} as population,\n" +
            "hcbrp.Case_Count as caseCount\n" +
            "from sa2_population_origin a\n" +
            "    left join sal_code_names b on a.SA2_CODE_2021 = b.SAL_CODE_2021\n" +
            "    left join house_count_by_rent_price hcbrp on a.SA2_CODE_2021 = hcbrp.SA2_CODE_2021\n" +
            "    where a.${country} != 0 and hcbrp.Rent_Price_Range = #{priceRange} order by a.${country} desc")
    List<ImmigrantData> getMapDataByCountry(@Param("country") String country, @Param("priceRange") String priceRange);

    // test
    @Select("select sum(Population_2021) as total from population_born_oversea")
    Integer getTotalPop();

}
