package com.ssafy.hfrf.house.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.hfrf.house.model.HouseDealDto;
import com.ssafy.hfrf.house.model.HouseInfoDto;
import com.ssafy.hfrf.house.model.SidoGugunDto;
import com.ssafy.hfrf.house.model.mapper.HouseMapper;

@Service
public class HouseServiceImpl implements HouseService{
	
	private HouseMapper houseMapper;

	public HouseServiceImpl(HouseMapper houseMapper) {
		super();
		this.houseMapper = houseMapper;
	}

	@Override
	public List<SidoGugunDto> getSido() throws Exception {
		return houseMapper.getSido();
	}

	@Override
	public List<SidoGugunDto> getGugunInSido(String sido) throws SQLException {
		return houseMapper.getGugunInSido(sido);
	}

	@Override
	public List<HouseInfoDto> getDongInGugun(String gugun) throws SQLException {
		return houseMapper.getDongInGugun(gugun);
	}

	@Override
	public List<HouseInfoDto> getAptInDong(String dong) throws SQLException {
		return houseMapper.getDongInGugun(dong);
	}

	@Override
	public List<HouseInfoDto> getAptName(String aptName) throws SQLException {
		return houseMapper.getAptName(aptName);
	}

	@Override
	public List<HouseDealDto> getDealsByCode(String aptCode) throws SQLException {
		return houseMapper.getDealsByCode(aptCode);
	}

	@Override
	public SidoGugunDto getAddressName(String dongCode) throws SQLException {
		return houseMapper.getAddressName(dongCode);
	}

	@Override
	public HouseInfoDto selectHouse(String aptCode) throws SQLException {
		return houseMapper.selectHouse(aptCode);
	}
}
