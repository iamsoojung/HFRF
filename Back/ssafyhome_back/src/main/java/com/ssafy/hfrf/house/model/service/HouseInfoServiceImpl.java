package com.ssafy.hfrf.house.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hfrf.house.model.HouseDealDto;
import com.ssafy.hfrf.house.model.HouseInfoDto;
import com.ssafy.hfrf.house.model.mapper.HouseInfoMapper;

@Service
public class HouseInfoServiceImpl implements HouseInfoService{

	@Autowired
	HouseInfoMapper houseInfoMapper;

	@Override
	public List<HouseInfoDto> selectHouseInfo(String aptName) throws SQLException {
		return houseInfoMapper.selectHouseInfo(aptName);
	}

	@Override
	public List<HouseDealDto> selectHouseDeal(long aptCode) throws SQLException {
		return houseInfoMapper.selectHouseDeal(aptCode);
	}
	
}
