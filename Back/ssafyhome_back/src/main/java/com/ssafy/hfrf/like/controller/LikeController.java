package com.ssafy.hfrf.like.controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hfrf.like.model.AptLikeDto;
import com.ssafy.hfrf.like.model.service.LikeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/aptLike")
@Tag(name = "찜 컨트롤러", description = "아파트 찜 처리하는 클래스.")
@Slf4j
public class LikeController {

	private LikeService likeService;
	
	public LikeController(LikeService likeService) {
		super();
		this.likeService = likeService;
	}

	@Operation(summary = "찜 리스트 가져오기", description = "해당 사용자가 찜한 아파트 리스트")
	@GetMapping("/list")
	public ResponseEntity<?> getLikeList(@RequestParam String userId) {
		try {
			log.debug("likeList : {}", likeService.getLikeList(userId));
			return new ResponseEntity<List<AptLikeDto>>(likeService.getLikeList(userId), HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "찜 등록하기", description = "해당 사용자가 찜 등록")
	@PostMapping("/like")
	public ResponseEntity<?> registerLike(@RequestBody AptLikeDto likeDto) {
		try {
			likeService.registerLike(likeDto);
			return ResponseEntity.ok().body(Collections.singletonMap("resultCode", "ok"));
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "찜 삭제하기", description = "해당 사용자가 찜 해제")
	@DeleteMapping("/dislike")
	public ResponseEntity<?> deleteLike(@RequestBody AptLikeDto likeDto) {
		try {
			likeService.deleteLike(likeDto);
			return ResponseEntity.ok().body(Collections.singletonMap("resultCode", "ok"));
		} catch (SQLException e) {
			return exceptionHandling(e);
		}
	}


	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
