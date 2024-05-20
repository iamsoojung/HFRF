package com.ssafy.hfrf.like.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hfrf.like.model.AptLikeDto;

@Mapper
public interface LikeMapper {
	
	List<AptLikeDto> getLikeList(String userId) throws SQLException;
	
	void registerLike(AptLikeDto likeDto) throws SQLException;
	
	void deleteLike(AptLikeDto likeDto) throws SQLException;

}
