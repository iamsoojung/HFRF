package com.ssafy.hfrf.board.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "CommentDto : 댓글 정보", description = "게시글의 상세 정보를 나타낸다.")
public class CommentDto {
	
	@Schema(description = "댓글번호")
	private int commentNo;
	@Schema(description = "작성자 아이디")
	private String userId;
	@Schema(description = "글번호")
	private int articleNo;
	@Schema(description = "댓글내용")
	private String content;
	@Schema(description = "작성일")
	private String registerTime;
	
}
