package com.ssafy.hfrf.house.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.hfrf.house.model.HouseDealDto;
import com.ssafy.hfrf.house.model.HouseInfoDto;

public interface HouseInfoService {
	
    List<HouseInfoDto> selectHouseInfo(String aptName) throws SQLException;
    
    List<HouseDealDto> selectHouseDeal(long aptCode) throws SQLException;
    
    
}
