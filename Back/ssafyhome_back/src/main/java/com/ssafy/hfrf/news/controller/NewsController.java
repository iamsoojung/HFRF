package com.ssafy.hfrf.news.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hfrf.news.model.service.NewsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/news")
@Tag(name = "뉴스 크롤링 컨트롤러", description = "주택 관련 뉴스를 크롤링 처리하는 클래스.")
@Slf4j
public class NewsController {
	
	private NewsService newsService;

	public NewsController(NewsService newsService) {
		super();
		this.newsService = newsService;
	}

	@Operation(summary = "주택 관련 뉴스 크롤링", description = "주택 관련 뉴스 크롤링 처리.")
	@GetMapping("/list")
	public ResponseEntity<?> newsList() {
		String result = newsService.newsList();
		
		if (result != null) {
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
}
