package com.ssafy.hfrf.like.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "AptLike : 관심정보", description = "관심의 정보를 나타낸다.")
public class AptLikeDto {

	@Schema(description = "회원 아이디")
	private String userId;
	
	@Schema(description = "아파트 코드")
	private long aptCode; 

	@Schema(description = "돟 코드")
	private long dongCode;

}
