package com.ssafy.hfrf.house.model;

import java.util.List;

import com.ssafy.hfrf.board.model.FileInfoDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Schema(title = "HouseSell : 등록매물정보", description = "등록한 매물 정보를 나타낸다.")
public class HouseSell {

	@Schema(description = "매물의 고유 인덱스", example = "1")
	private int idx;

	@Schema(description = "판매자의 ID", example = "user123")
	private String id;

	@Schema(description = "매물의 제목", example = "서울역 센트럴 자이")
	private String title;

	@Schema(description = "매물 등록 날짜", example = "2023-05-23")
	private String date;

	@Schema(description = "매물의 유형 (예: 아파트, 오피스텔 등)", example = "아파트")
	private String type;

	@Schema(description = "매매 가격", example = "1000000000")
	private String price;

	@Schema(description = "관리비", example = "50000")
	private String admin_price;

	@Schema(description = "관리비 설명", example = "관리비는 전기, 수도, 청소비 포함")
	private String admin_desc;

	@Schema(description = "방 유형 (예: 원룸, 투룸 등)", example = "투룸")
	private String room_type;

	@Schema(description = "현재 층", example = "5")
	private int floor_now;

	@Schema(description = "전체 층수", example = "15")
	private int floor_tot;

	@Schema(description = "면적 (㎡)", example = "84.5")
	private double size;

	@Schema(description = "매물 설명", example = "남향으로 일조량이 좋고, 교통이 편리합니다.")
	private String content;

	@Schema(description = "매물 키워드", example = "역세권, 학군좋음")
	private String keyword;

	@Schema(description = "매물 주소", example = "서울특별시 중구")
	private String address;

	@Schema(description = "매물 상세 주소", example = "만리동2가")
	private String detail_address;

	@Schema(description = "위도", example = "37.5556")
	private String lat;

	@Schema(description = "경도", example = "126.9707")
	private String lng;
	
	@Schema(description = "업로드 파일정보")
	private List<FileInfoSellDto> fileInfos;
}
