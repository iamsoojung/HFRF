package com.ssafy.hfrf.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hfrf.board.model.CommentDto;

public interface CommentService {

	void writeComment(CommentDto commentDto) throws Exception;

	List<CommentDto> listComment(int articleNo) throws Exception;

	int getTotalCommentCount(int articleNo) throws Exception;
	
	CommentDto getComment(int commentNo) throws Exception;

	void modifyComment(CommentDto commentDto) throws Exception;

	void deleteComment(int commentNo) throws Exception;
}
