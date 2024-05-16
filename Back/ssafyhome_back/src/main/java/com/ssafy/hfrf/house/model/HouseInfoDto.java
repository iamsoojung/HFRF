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
@Schema(title = "HouseInfo : 아파트정보", description = "아파트의 정보를 나타낸다.")
public class HouseInfoDto {

	@Schema(description = "아파트 코드", example = "11110000000001")
	private long aptCode;
	@Schema(description = "건축 년도", example = "2008")
	private int buildYear;
	@Schema(description = "길 이름", example = "사직로8길")
	private String roadName;
	@Schema(description = "길 본번", example = "00004")
	private String roadNameBonbun;
	@Schema(description = "길 부번", example = "00000")
	private String roadNameBubun;
	@Schema(description = "길 번호", example = "03")
	private String roadNameSeq;
	@Schema(description = "길 지하 코드", example = "0")
	private String roadNameBasementCode;
	@Schema(description = "길 코드", example = "4100135")
	private String roadNameCode;
	@Schema(description = "동", example = "사직동")
	private String dong;
	@Schema(description = "본번", example = "0009")
	private String bonbun;
	@Schema(description = "부번", example = "0000")
	private String bubun;
	@Schema(description = "시군구코드", example = "11110")
	private String sigunguCode;
	@Schema(description = "읍면동코드", example = "11500")
	private String eubmyundongCode;
	@Schema(description = "동코드", requiredMode = Schema.RequiredMode.REQUIRED, example = "1111011500")
	private String dongCode;
	@Schema(description = "땅코드", example = "1")
	private String landCode;
	@Schema(description = "아파트 이름", example = "광화문스페이스본(101동~105동)")
	private String apartmentName;
	@Schema(description = "지번", example = "9")
	private String jibun;
	@Schema(description = "경도", example = "126.968575235313")
	private String lng;
	@Schema(description = "위도", example = "37.5746540320628")
	private String lat;
}
