package com.ssafy.hfrf.house.model.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hfrf.house.model.DongCodeDto;
import com.ssafy.hfrf.house.model.mapper.DongMapper;

@Service
public class DongServiceImpl implements DongService {

	@Autowired
	DongMapper dongMapper;

	@Override
	public List<DongCodeDto> getSidoName() throws Exception {
		return dongMapper.getSidoName();
	}

	@Override
	public List<DongCodeDto> getGugunName(String sidoCode) throws Exception {
		return dongMapper.getGugunName(sidoCode);
	}

	@Override
	public List<DongCodeDto> getDongName(String dongCode) throws Exception {
		return dongMapper.getDongName(dongCode);
	}

	@Override
	public DongCodeDto getAddress(HashMap<String, Object> map) throws Exception {
		return dongMapper.getAddress(map);
	}
}