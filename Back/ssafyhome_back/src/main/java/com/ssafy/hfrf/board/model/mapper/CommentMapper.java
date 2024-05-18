package com.ssafy.hfrf.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hfrf.board.model.CommentDto;

@Mapper
public interface CommentMapper {

	void writeComment(CommentDto commentDto) throws SQLException;

	List<CommentDto> listComment(int articleNo) throws SQLException;

	int getTotalCommentCount(int articleNo) throws SQLException;
	
	CommentDto getComment(int commentNo) throws Exception;

	void modifyComment(CommentDto commentDto) throws SQLException;

	void deleteComment(int commentNo) throws SQLException;
}
