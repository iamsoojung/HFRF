package com.ssafy.hfrf.house.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hfrf.house.model.DongCodeDto;
import com.ssafy.hfrf.house.model.service.DongService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/house")
@Tag(name = "동 컨트롤러", description = "주소, 시도/구군/동 정보를 가져오는 클래스.")
@Slf4j
public class DongController {

	private DongService dongService;
	
	public DongController(DongService dongService) {
		super();
		this.dongService = dongService;
	}

	@Operation(summary = "주소 가져오기", description = "동 코드에 해당하는 주소를 가져온다.")
	@GetMapping("/addr")
	public ResponseEntity<?> getAddress(@RequestParam("dongCode") String dongCode) {
		log.debug("getAddress() : ", dongCode);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dongcode", dongCode);
		try {
			DongCodeDto dongCodeDto = dongService.getAddress(map);

			if(dongCodeDto!=null) {
				return new ResponseEntity<DongCodeDto>(dongCodeDto, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "시도 가져오기", description = "시도 정보를 가져온다.")
	@GetMapping("/sido")
	private ResponseEntity<?> getSidoName(){
//		log.debug("getSidoName() ");
		try {
			List<DongCodeDto> list = dongService.getSidoName();

			System.out.println(list.toString());
			if(list != null && !list.isEmpty()) {
				return new ResponseEntity<List<DongCodeDto>>(list, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "구군 가져오기", description = "구군 정보를 가져온다.")
	@GetMapping("/gugun")
	private ResponseEntity<?> getGugunName(@RequestParam("sido") String sido) {
		log.debug("getGugunName() : {}", sido);

		String sidoCode = sido;
		try {
			List<DongCodeDto> list = dongService.getGugunName(sidoCode);
			
			if(list != null && !list.isEmpty()) {
				return new ResponseEntity<List<DongCodeDto>>(list, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "동 가져오기", description = "동 정보를 가져온다.")
	@GetMapping("/dong")
	private ResponseEntity<?> getDongName(@RequestParam("gugun") String gugun) {
		log.debug("getDongName() : {}", gugun);

		String dongCode = gugun;
		try {
			List<DongCodeDto> list = dongService.getDongName(dongCode);

			if(list != null && !list.isEmpty()) {
				return new ResponseEntity<List<DongCodeDto>>(list, HttpStatus.OK);
			}else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}