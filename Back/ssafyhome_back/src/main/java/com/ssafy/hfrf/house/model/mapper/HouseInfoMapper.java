package com.ssafy.hfrf.house.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hfrf.house.model.HouseDealDto;
import com.ssafy.hfrf.house.model.HouseInfoDto;

@Mapper
public interface HouseInfoMapper {
	
    List<HouseInfoDto> selectHouseInfo(String aptName);
    
    List<HouseDealDto> selectHouseDeal(long aptCode);
}
