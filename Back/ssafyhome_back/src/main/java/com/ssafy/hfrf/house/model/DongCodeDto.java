package com.ssafy.hfrf.house.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "DongCodeDto : 동별 코드 정보", description = "각 동의 코드 정보를 나타낸다.")
public class DongCodeDto {                                                                                                                                                                                                                                                                                                          

	@Schema(description = "동코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "2711013100")
	private String dongCode;
	@Schema(description = "시도이름", example = "대구광역시")
	private String sidoName;
	@Schema(description = "구군이름", example = "중구")
	private String gugunName;
	@Schema(description = "동이름", example = "태평로3가")
	private String dongName;
}
