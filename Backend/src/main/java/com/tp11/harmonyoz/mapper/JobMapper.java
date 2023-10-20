package com.tp11.harmonyoz.mapper;

import com.tp11.harmonyoz.entity.JobData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface JobMapper {

    @Select("select distinct anzsco4_name as occupation from job_data order by anzsco4_name asc")
    List<String> getAllOccupations();

    @Select("select COLUMN_NAME as industry From INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = 'industry_job_counts' and COLUMN_NAME != 'SA4_CODE_2021' " +
            "order by COLUMN_NAME asc")
    List<String> getAllIndustries();

    @Select("SELECT SA2_NAME_2021 AS suburb, ROUND(normalized * ( SELECT count FROM job_norm WHERE anzsco4_name = #{occupation} " +
            "ORDER BY normalized_count DESC LIMIT 1 )) AS people FROM population_norm " +
            "WHERE SUBSTRING(SA2_CODE_2021, 1, 3) = ( SELECT sa4_code FROM job_norm WHERE anzsco4_name = #{occupation} " +
            "ORDER BY normalized_count DESC LIMIT 1) AND countries = #{country} ORDER BY normalized DESC LIMIT 5")
    List<JobData> getOccCountBySub(@Param("occupation") String occupation, @Param("country") String country);

    @Select("SELECT Year as year, SUM(Job_count) as job_count FROM occupation_data " +
            "WHERE anzsco4_name = #{occupation} " +
            "GROUP BY anzsco4_name, Year")
    List<JobData> getOccForecast(@Param("occupation") String occupation);

}
