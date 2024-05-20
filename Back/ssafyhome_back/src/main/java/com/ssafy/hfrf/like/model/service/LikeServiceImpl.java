package com.ssafy.hfrf.like.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.hfrf.like.model.AptLikeDto;
import com.ssafy.hfrf.like.model.mapper.LikeMapper;

@Service
public class LikeServiceImpl implements LikeService {
	
	private LikeMapper likeMapper;

	public LikeServiceImpl(LikeMapper likeMapper) {
		super();
		this.likeMapper = likeMapper;
	}

	@Override
	public List<AptLikeDto> getLikeList(String userId) throws SQLException {
		return likeMapper.getLikeList(userId);
	}

	@Override
	public void registerLike(AptLikeDto likeDto) throws SQLException {
		likeMapper.registerLike(likeDto);
	}

	@Override
	public void deleteLike(AptLikeDto likeDto) throws SQLException {
		likeMapper.deleteLike(likeDto);
	}
}
