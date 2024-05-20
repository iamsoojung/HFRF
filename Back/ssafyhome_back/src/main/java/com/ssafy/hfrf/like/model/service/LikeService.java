package com.ssafy.hfrf.like.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.hfrf.like.model.AptLikeDto;

public interface LikeService {

	List<AptLikeDto> getLikeList(String userId) throws SQLException;
	
	void registerLike(AptLikeDto likeDto) throws SQLException;
	
	void deleteLike(AptLikeDto likeDto) throws SQLException;
}
