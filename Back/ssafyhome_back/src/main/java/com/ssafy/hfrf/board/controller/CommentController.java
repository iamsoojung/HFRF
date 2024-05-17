package com.ssafy.hfrf.board.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hfrf.board.model.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/comment")
@Tag(name = "댓글 컨트롤러", description = "게시글에 댓글을 등록, 수정, 삭제, 목록, 갯수 가져오기등 전반적인 처리를 하는 클래스.")
@Slf4j
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}
//	
//	@Operation(summary = "댓글작성", description = "새로운 댓글을 입력한다.")
//	@PostMapping("/write")
//	public ResponseEntity<?> write(@RequestBody CommentDto commentDto) {
//		
//	}
	
	

}

