package com.ssafy.hfrf.house.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hfrf.house.model.HouseDealDto;
import com.ssafy.hfrf.house.model.HouseInfoDto;
import com.ssafy.hfrf.house.model.SidoGugunDto;
import com.ssafy.hfrf.house.model.service.HouseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/house")
@Tag(name = "아파트 컨트롤러", description = "아파트 매매 정보 관련 처리하는 클래스.")
@Slf4j
public class HouseController {
	
	private HouseService houseService;
	
	public HouseController(HouseService houseService) {
		super();
		this.houseService = houseService;
	}

	@Operation(summary = "시도 찾기", description = "시도 정보 가져오기")
	@GetMapping("/sido")
	public ResponseEntity<List<SidoGugunDto>> sido() throws Exception {
		log.debug("sido : {}", houseService.getSido());
		return new ResponseEntity<List<SidoGugunDto>>(houseService.getSido(), HttpStatus.OK);
	}

	@Operation(summary = "구군 찾기", description = "시도 코드로 구군 찾기")
	@GetMapping("/gugun")
	public ResponseEntity<List<SidoGugunDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		log.debug("sido : {}", sido);
		return new ResponseEntity<List<SidoGugunDto>>(houseService.getGugunInSido(sido), HttpStatus.OK);
	}

	@Operation(summary = "동 찾기", description = "구군 코드로 동 찾기")
	@GetMapping("/dong")
	public ResponseEntity<List<HouseInfoDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		log.debug("gugun : {}",gugun);
		return new ResponseEntity<List<HouseInfoDto>>(houseService.getDongInGugun(gugun), HttpStatus.OK);
	}

	@Operation(summary = "아파트 찾기(dongCode)", description = "dongCode로 아파트 찾기")
	@GetMapping("/apt")
	public ResponseEntity<List<HouseInfoDto>> apt(@RequestParam("dongCode") String dongCode) throws Exception {
		log.debug("dong : {}", dongCode);
		return new ResponseEntity<List<HouseInfoDto>>(houseService.getAptInDong(dongCode), HttpStatus.OK);
	}

	@Operation(summary = "아파트 찾기(aptName)", description = "aptName으로 아파트 찾기")
	@GetMapping("/aptName")
	public ResponseEntity<List<HouseInfoDto>> aptName(@RequestParam("aptName") String aptName) throws Exception {
		log.debug("aptName : {}", aptName);
		return new ResponseEntity<List<HouseInfoDto>>(houseService.getAptName(aptName), HttpStatus.OK);
	}

	@Operation(summary = "아파트 찾기(aptCode)", description = "aptCode로 아파트 찾기")
	@GetMapping("/aptCode")
	public ResponseEntity<?> getAptName(@RequestParam("aptCode") String aptCode) throws Exception {
		log.debug("aptCode : {} ", aptCode);
		return new ResponseEntity<String>(houseService.getAptNameByCode(aptCode), HttpStatus.OK);
	}

	@Operation(summary = "매매 정보 찾기(aptCode)", description = "aptCode로 매매 정보 찾기")
	@GetMapping("/aptDeal")
	public ResponseEntity<List<HouseDealDto>> aptDeals(@RequestParam("aptCode") String aptCode) throws Exception {
		log.debug("aptCode : {}", aptCode);
		return new ResponseEntity<List<HouseDealDto>>(houseService.getDealsByCode(aptCode), HttpStatus.OK);
	}

	@Operation(summary = "주소 찾기(dongcode)", description = "dongcode로 시도,구군,동 이름 찾기")
	@GetMapping("/address")
	public ResponseEntity<?> getAddressName(@RequestParam("dongcode")String dongcode) throws Exception{
		try {
			SidoGugunDto dto = houseService.getAddressName(dongcode);
			if(dto != null) {
				return new ResponseEntity<SidoGugunDto>(dto, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
