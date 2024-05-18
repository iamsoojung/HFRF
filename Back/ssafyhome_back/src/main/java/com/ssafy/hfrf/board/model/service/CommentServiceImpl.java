package com.ssafy.hfrf.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.hfrf.board.model.CommentDto;
import com.ssafy.hfrf.board.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService{
	
	private CommentMapper commentMapper;
	
	@Autowired
	public CommentServiceImpl(CommentMapper commentMapper) {
		super();
		this.commentMapper = commentMapper;
	}

	@Override
	@Transactional
	public void writeComment(CommentDto commentDto) throws Exception {
		commentMapper.writeComment(commentDto);
	}

	@Override
	public List<CommentDto> listComment(int articleNo) throws Exception {
		return commentMapper.listComment(articleNo);
	}

	/*
	 * @Override public int getTotalCommentCount(Map<String, Object> map) throws
	 * Exception { return commentMapper.getTotalCommentCount(map); }
	 */

	@Override
	public CommentDto getComment(int commentNo) throws Exception {
		return commentMapper.getComment(commentNo);
	}

	@Override
	public void modifyComment(CommentDto commentDto) throws Exception {
		commentMapper.modifyComment(commentDto);
	}

	@Override
	public void deleteComment(int commentNo) throws Exception {
		commentMapper.deleteComment(commentNo);
	}
}
