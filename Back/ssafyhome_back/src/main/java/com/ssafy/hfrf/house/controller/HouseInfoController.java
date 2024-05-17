package com.ssafy.hfrf.house.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hfrf.house.model.HouseDealDto;
import com.ssafy.hfrf.house.model.HouseInfoDto;
import com.ssafy.hfrf.house.model.service.HouseInfoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/house")
@Tag(name = "아파트 정보 컨트롤러", description = "아파트 정보를 가져오는 클래스.")
@Slf4j
public class HouseInfoController {

	private HouseInfoService houseInfoService;

	public HouseInfoController(HouseInfoService houseInfoService) {
		super();
		this.houseInfoService = houseInfoService;
	}

	@Operation(summary = "아파트 검색하기", description = "검색한 아파트 정보를 가져온다.")
	@GetMapping("/deal")
	public ResponseEntity<Map<String, Object>> selectHouseInfo(@RequestParam("aptName") String aptName) {
		log.info("selectHouseInfo(), aptName : {}", aptName);
		Map<String, Object> resultMap = new HashMap<>();

		try {
			List<HouseInfoDto> houseInfoDtoList = houseInfoService.selectHouseInfo(aptName);
			log.debug("아파트 검색 : {}", houseInfoDtoList);
			resultMap.put("message", "success");
			resultMap.put("houseInfoList", houseInfoDtoList);
			log.info("houseInfoLIst : {}", houseInfoDtoList);
			return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			log.error("아파트 검색 : {}", e.getMessage());
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "아파트 상세 검색하기", description = "검색한 아파트 정보(거래내역)를 가져온다.")
	@GetMapping("/deal/{aptCode}")
	public ResponseEntity<Map<String, Object>> selectHouseDeal(@PathVariable("aptCode") long aptCode) {
		log.info("selectHouseDeal(), aptCode: {}", aptCode);
		Map<String, Object> resultMap = new HashMap<>();

		try {
			List<HouseDealDto> houseDealList = houseInfoService.selectHouseDeal(aptCode);
			log.debug("아파트 상세검색(거래내역) : {}", houseDealList);
			resultMap.put("message", "success");
			resultMap.put("houseDealList", houseDealList);
			return new ResponseEntity<>(resultMap, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			log.error("아파트 상세 검색 실패 : {}", e.getMessage());
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<Map<String, Object>> exceptionHandling(Exception e) {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("message", e.getMessage());
		e.printStackTrace();
		return new ResponseEntity<>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
