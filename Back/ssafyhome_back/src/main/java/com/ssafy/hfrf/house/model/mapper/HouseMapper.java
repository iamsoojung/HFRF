package com.ssafy.hfrf.house.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hfrf.house.model.HouseDealDto;
import com.ssafy.hfrf.house.model.HouseInfoDto;
import com.ssafy.hfrf.house.model.SidoGugunDto;

@Mapper
public interface HouseMapper {
	
	List<SidoGugunDto> getSido() throws SQLException;
	
	List<SidoGugunDto> getGugunInSido(String sido) throws SQLException;

	List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException;

	List<HouseInfoDto> getAptInDong(String dongCode) throws SQLException;

	List<HouseInfoDto> getAptName(String aptName) throws SQLException;
	
	String getAptNameByCode(String aptCode) throws SQLException;

	List<HouseDealDto> getDealsByCode(String aptCode) throws SQLException;

	SidoGugunDto getAddressName(String dongCode) throws SQLException;

	HouseInfoDto selectHouse(String aptCode) throws SQLException;
}
