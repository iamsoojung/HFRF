//package com.ssafy.hfrf.house.controller;
//
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.ssafy.hfrf.house.model.service.HouseService;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.extern.slf4j.Slf4j;
//
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
//@RestController
//@RequestMapping("/house")
//@Tag(name = "아파트정보 컨트롤러", description = "게시판에 글을 등록, 수정, 삭제, 목록, 상세보기등 전반적인 처리를 하는 클래스.")
//@Slf4j
//public class HouseController {
//	
//	private HouseService houseService;
//	
//	public HouseController(HouseService houseService) {
//		super();
//		this.houseService = houseService;
//	}
//	
//	@GetMapping("/sido")
//	@Operation(summary = "시도 코드 조회", description = "시도 코드 가져오기")
//	public ResponseEntity<List<Apt>> getSidoCode() throws Exception {
//		List<Apt> aptList = aptService.getSidoCode();
//		return new ResponseEntity<>(aptList, aptList ==  null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
//	}
//	
//	@GetMapping("/gugun/{sidoCode}")
//	@Operation(summary = "구군 코드 조회", description = "구군 코드 가져오기")
//	public ResponseEntity<List<Apt>> getGugunCode(@PathVariable("sidoCode") String sidoCode) throws Exception {
//		List<Apt> aptList = aptService.getGugunCode(sidoCode);
//		return new ResponseEntity<>(aptList, aptList ==  null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
//	}
//	
//	@GetMapping("/dong/{gugunCode}")
//	@Operation(summary = "지역 코드 조회", description = "동 코드 가져오기")
//	public ResponseEntity<List<Apt>> getDongCode(@PathVariable("gugunCode") String gugunCode) throws Exception {
//		List<Apt> aptList = aptService.getDongCode(gugunCode);
//		return new ResponseEntity<>(aptList, aptList ==  null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
//	}
//
//	// dongCode 1111011500
//	@GetMapping("/searchInfo/{code}")
//	@Operation(summary = "아파트 조회", description = "동 코드에 해당하는 아파트 가져오기")
//	public ResponseEntity<List<AptInfo>> doSearchInfo(@PathVariable("code") String code) throws Exception {
//		System.out.println("in doSearchInfo");
//		List<AptInfo> aptInfoList = aptService.getInfoList(code);
//		return new ResponseEntity<>(aptInfoList, aptInfoList ==  null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
//	}
//
//	// aptCode 11110000000001
//	@GetMapping("/searchDeal/{code}")
//	@Operation(summary = "아파트 매물 조회", description = "아파트 코드에 해당하는 매물 가져오기")
//	public ResponseEntity<List<AptDeal>> doSearchDeal(@PathVariable("code") String code)throws Exception {
//		List<AptDeal> aptDealList = aptService.getDealList(code);
//		return new ResponseEntity<>(aptDealList, aptDealList ==  null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
//	}
//
//	@GetMapping("/favorite/{userId}")
//	@Operation(summary = "관심목록 조회", description = "해당 유저의 관심목록 가져오기")
//	public ResponseEntity<List<AptInfo>> showFavorite(@PathVariable("userId") String userId) throws Exception {
//		List<AptInfo> aptInfoList = aptService.getFavorite(userId);
//		return new ResponseEntity<>(aptInfoList, HttpStatus.OK);
//	}
//	
//	@PostMapping("/favorite/{userId}/{code}")
//	@Operation(summary = "관심목록 추가", description = "해당 유저의 관심목록에 추가하기")
//	public ResponseEntity<?> doFavorite(@PathVariable("userId") String userId, @PathVariable("code") String code) throws Exception {
//		int result = aptService.addFavorite(code, userId);
//		return new ResponseEntity<>(result, result !=  1 ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
//	}
//
//	@DeleteMapping("/favorite/{userId}/{code}")
//	@Operation(summary = "관심목록 제거", description = "해당 유저의 관심목록에서 제거하기")
//	public ResponseEntity<?> doRmFavorite(@PathVariable("userId") String userId, @PathVariable("code") String code) throws Exception {
//		int result = aptService.rmFavorite(code, userId);
//		return new ResponseEntity<>(result, result !=  1 ? HttpStatus.BAD_REQUEST : HttpStatus.OK);
//	}
//	
//	
//
//}
