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
@Schema(title = "Apt : ", description = "아파트의 정보를 나타낸다.")
public class AptDto {

	@Schema(description = "코드", example = "sido조회: '', gugun조회: 11, dong조회: 11110")
	private String code;
	@Schema(description = "지역이름", example = "sido: 서울특별시, gugun: 종로구, dong: 청운동")
	private String name;

}
