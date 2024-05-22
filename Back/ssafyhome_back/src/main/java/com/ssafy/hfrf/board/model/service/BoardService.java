package com.ssafy.hfrf.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.hfrf.board.model.BoardDto;
import com.ssafy.hfrf.board.model.BoardListDto;
import com.ssafy.hfrf.board.model.FileInfoDto;

public interface BoardService {

	void writeArticle(BoardDto boardDto) throws Exception;

	BoardListDto listArticle(Map<String, String> map) throws Exception;

//	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	
	BoardDto getArticle(int articleNo) throws Exception;

	void updateHit(int articleNo) throws Exception;

	void modifyArticle(BoardDto boardDto) throws Exception;

	void deleteArticle(int articleNo) throws Exception;

	List<FileInfoDto> fileInfoList(int articleNo) throws Exception;
}
