package com.ssafy.hfrf.house.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.hfrf.house.model.mapper.HouseMapper;

@Service
public class HouseServiceImpl {
	
	private HouseMapper houseMapper;
	
	@Autowired
	public HouseServiceImpl(HouseMapper houseMapper) {
		super();
		this.houseMapper = houseMapper;
	}
	
	
	

}
