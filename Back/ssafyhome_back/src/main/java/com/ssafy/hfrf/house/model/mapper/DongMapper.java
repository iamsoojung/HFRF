package com.ssafy.hfrf.house.model.mapper;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hfrf.house.model.DongCodeDto;

@Mapper
public interface DongMapper {
	
	List<DongCodeDto> getSidoName() throws SQLException;
	
	List<DongCodeDto> getGugunName(String sidoCode) throws SQLException;

	List<DongCodeDto> getDongName(String dongCode) throws SQLException;

	DongCodeDto getAddress(HashMap<String, Object> map) throws SQLException;
}