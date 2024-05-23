package com.ssafy.hfrf.house.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hfrf.house.model.HouseSell;
import com.ssafy.hfrf.house.model.PageBean;
import com.ssafy.hfrf.house.model.service.SellService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/house")
@Tag(name = "매물 판매 컨트롤러", description = "직접 매물을 등록하고 판매하는 것을 처리하는 클래스.")
@Slf4j
public class SellController {

	@Autowired
	private SellService service;
	
	private static final Logger logger = LoggerFactory.getLogger(SellController.class);
	private static final String SUCCESS = "success";
	
	@Operation(summary = "매물거래 글목록", description = "검색조건, 페이징에 맞게 리스트 가져오기")
	@GetMapping
	public ResponseEntity<List<HouseSell>> list(PageBean pagebean) {
		logger.info("매물거래 list - 호출", pagebean);
		pagebean.setInterval(10);
		return new ResponseEntity<List<HouseSell>>(service.list(pagebean), HttpStatus.OK);
	}

	@Operation(summary = "매물거래 글보기", description = "idx에 해당하는 글의 정보를 반환한다.")    
	@GetMapping("{idx}")
	public ResponseEntity<HouseSell> detail(@PathVariable String idx) {
		logger.info("매물거래 detail - 호출");
		return new ResponseEntity<HouseSell>(service.detail(idx), HttpStatus.OK);
	}

	@Operation(summary = "매물거래 글등록", description = "새로운 게시글 정보를 입력한다. 성공하면 success 리턴")
	@PostMapping
	public ResponseEntity<String> add(@RequestBody HouseSell houseSell) {
		logger.info("매물거래 add - 호출");
		service.add(houseSell);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	@Operation(summary = "매물거래 글정보 수정", description = "idx에 해당하는 게시글의 정보를 수정한다. 성공하면 'success' 문자열을 반환한다.")
	@PutMapping
	public ResponseEntity<String> update(@RequestBody HouseSell houseSell) {
		logger.info("매물거래 update- 호출");
		
		service.update(houseSell);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	@Operation(summary = "매물거래 글삭제", description = "idx에 해당하는 게시글의 정보를 삭제한다. 성공하면'success' 문자열을 반환한다.")
	@DeleteMapping("{idx}")
	public ResponseEntity<String> remove(@PathVariable String idx) {
		logger.info("매물거래  remove - 호출");
		service.remove(idx);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}