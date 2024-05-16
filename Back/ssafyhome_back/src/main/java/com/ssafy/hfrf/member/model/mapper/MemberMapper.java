package com.ssafy.hfrf.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hfrf.member.model.MemberDto;

@Mapper
public interface MemberMapper {
	
	MemberDto findByIdAndPassword(MemberDto memberDto) throws SQLException;

	MemberDto findById(String userId) throws SQLException;
	
	void insertMember(MemberDto memberDto) throws SQLException;

    int idCheck(String userId) throws SQLException;
	
	void updateMember(MemberDto memberDto) throws SQLException;
	
	void deleteMember(String userId) throws SQLException;

	void saveRefreshToken(Map<String, String> map) throws SQLException;

	Object getRefreshToken(String userid) throws SQLException;

	void deleteRefreshToken(Map<String, String> map) throws SQLException;

}
