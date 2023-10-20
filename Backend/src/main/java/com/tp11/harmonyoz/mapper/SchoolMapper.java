package com.tp11.harmonyoz.mapper;

import com.tp11.harmonyoz.entity.SchoolData;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SchoolMapper {

    @Select("select a.School_Name as name, b.X, b.Y from school_info a left join school_geo b on a.School_No = b.School_No where a.School_Type like CONCAT('%', #{schoolType}, '%') and a.Education_Sector = 'Government' and (x BETWEEN 144.593742 AND 145.512528\n" +
            "AND y BETWEEN -38.433859 AND -37.511274)")
    List<SchoolData> getPubScGeo(@Param("schoolType") String schoolType);

    @Select("select a.School_Name as name, b.X, b.Y, c.Postal_Postcode as postcode, c.Full_Phone_No as phone from school_info a\n" +
            "    left join school_geo b on a.School_No = b.School_No\n" +
            "    left join school_contact c on a.School_No = c.School_No\n" +
            "where a.School_Type like CONCAT('%', #{schoolType}, '%') and (a.Education_Sector = 'Independent' or a.Education_Sector = 'Catholic')\n" +
            "and (x BETWEEN 144.593742 AND 145.512528 AND y BETWEEN -38.433859 AND -37.511274)")
    List<SchoolData> getPriScGeo(@Param("schoolType") String schoolType);

    @Select("SELECT si.School_No as no, si.School_Name as name, sg.x, sg.y, sc.Postal_Postcode as postcode, sc.Full_Phone_No as phone\n" +
            "            FROM school_geo sg\n" +
            "            JOIN school_info si ON sg.School_No = si.School_No\n" +
            "            JOIN school_contact sc ON sg.School_No = sc.School_No\n" +
            "            WHERE ST_Distance_Sphere(POINT(#{x}, #{y}), POINT(sg.x, sg.y)) <= 3000\n" +
            "            and School_Type like CONCAT('%', #{schoolType}, '%') and Education_Sector = #{eduSector}")
    List<SchoolData> getSchoolLoc(@Param("schoolType") String schoolType, @Param("eduSector") String eduSector,
                                  @Param("x") Double x, @Param("y") Double y);
}
