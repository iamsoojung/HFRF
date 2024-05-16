package com.ssafy.hfrf.house.model.service;

import java.util.HashMap;
import java.util.List;

import com.ssafy.hfrf.house.model.DongCodeDto;

public interface DongService {

	List<DongCodeDto> getSidoName() throws Exception;

	List<DongCodeDto> getGugunName(String sidoCode) throws Exception;

	List<DongCodeDto> getDongName(String dongCode) throws Exception;
	
	DongCodeDto getAddress(HashMap<String, Object> map) throws Exception;
}