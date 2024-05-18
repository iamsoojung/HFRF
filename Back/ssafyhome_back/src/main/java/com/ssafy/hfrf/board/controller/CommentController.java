package com.ssafy.hfrf.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.hfrf.board.model.CommentDto;
import com.ssafy.hfrf.board.model.service.CommentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
	
	@Operation(summary = "댓글 작성", description = "새로운 댓글을 입력한다.")
	@PostMapping("/write")
	public ResponseEntity<?> write(@RequestBody CommentDto commentDto) {
		try {
			commentService.writeComment(commentDto);
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("data", "success");
			return new ResponseEntity<Map<String,Object>>(resultMap, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "댓글 리스트 보기", description = "해당 게시글의 댓글들을 모아본다.")
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam("articleNo") int articleNo) {
		List<CommentDto> list;
		try {
			list = commentService.listComment(articleNo);
			if (list != null) {
				return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);
			} else {
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "수정 할 댓글 얻기", description = "글번호에 해당하는 댓글의 정보를 반환한다.")
	@GetMapping("/modify/{commentno}")
	public ResponseEntity<CommentDto> getModifyComment(
			@PathVariable("commentno") @Parameter(name = "commentno", description = "얻어올 댓글의 번호.", required = true) int commentno)
			throws Exception {
		log.info("getModifyArticle - 호출 : " + commentno);
		return new ResponseEntity<CommentDto>(commentService.getComment(commentno), HttpStatus.OK);
	}

	@Operation(summary = "댓글 수정", description = "댓글을 수정한다.")
	@PostMapping("/modify")
	public ResponseEntity<?> modify(@RequestBody CommentDto commentDto, @RequestParam Map<String, String> map) {
		try {
			commentService.modifyComment(commentDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}

	@Operation(summary = "댓글 삭제", description = "댓글을 삭제한다.")
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam("commentNo") int commentNo, @RequestParam Map<String, String> map) {
		try {
			commentService.deleteComment(commentNo);
			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("data", "success");
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} catch (Exception e) {
			return exceptionHandling(e);
		}
	}
	
	
	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

