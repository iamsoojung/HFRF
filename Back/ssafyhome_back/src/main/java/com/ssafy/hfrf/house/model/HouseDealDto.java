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
@Schema(title = "HouseDealInfo : 매물정보", description = "매물의 정보를 나타낸다.")
public class HouseDealDto {

	@Schema(description = "매물 번호", example = "111101501000001")
	private long no;
	@Schema(description = "가격", example = "100,000")
	private String dealAmount;
	@Schema(description = "거래 년", example = "2015")
	private int dealYear;
	@Schema(description = "거래 월", example = "1")
	private int dealMonth;
	@Schema(description = "거래 일", example = "7")
	private int dealDay;
	@Schema(description = "면적", example = "151.81")
	private String area;
	@Schema(description = "층", example = "9")
	private String floor;
	@Schema(description = "해제여부", example = "")
	private String type;
	@Schema(description = "아파트 코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "11110000000001")
	private long aptCode;
}
