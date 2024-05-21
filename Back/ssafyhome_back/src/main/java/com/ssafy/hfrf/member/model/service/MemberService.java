package com.ssafy.hfrf.member.model.service;

import com.ssafy.hfrf.member.model.MemberDto;

public interface MemberService {
	
	MemberDto loginMember(MemberDto memberDto) throws Exception;

	MemberDto getMember(String userId) throws Exception;

	void joinMember(MemberDto memberDto) throws Exception;
	
	int idCheck(String userId) throws Exception;
	
	int emailCheck(String emailId, String emailDomain) throws Exception;
	
	void updateMember(MemberDto memberDto) throws Exception;
	
	void deleteMember(String userId) throws Exception;

	void saveRefreshToken(String userId, String refreshToken) throws Exception;

	Object getRefreshToken(String userId) throws Exception;

	void deleRefreshToken(String userId) throws Exception;

}
