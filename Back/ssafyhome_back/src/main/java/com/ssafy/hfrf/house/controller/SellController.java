package com.ssafy.hfrf.house.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.hfrf.board.model.BoardDto;
import com.ssafy.hfrf.board.model.FileInfoDto;
import com.ssafy.hfrf.house.model.FileInfoSellDto;
import com.ssafy.hfrf.house.model.HouseSell;
import com.ssafy.hfrf.house.model.PageBean;
import com.ssafy.hfrf.house.model.service.SellService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/house")
@Tag(name = "매물 판매 컨트롤러", description = "직접 매물을 등록하고 판매하는 것을 처리하는 클래스.")
@Slf4j
public class SellController {

	private SellService sellService;

	public SellController(SellService sellService) {
		super();
		this.sellService = sellService;
	}	
	
    @Value("${file.path}")
    private String uploadPath;
    
    @Value("${file.path.upload-images}")
    private String uploadImagePath;
    
    @Value("${file.path.upload-files}")
    private String uploadFilePath;

	@Operation(summary = "매물거래 글작성", description = "새로운 게시글 정보를 입력한다. 성공하면 success 리턴")
	@PostMapping
	public ResponseEntity<?> add(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "price") String price,
			@RequestParam(value = "admin_price") String admin_price,
			@RequestParam(value = "admin_desc") String admin_desc,
			@RequestParam(value = "room_type") String room_type,
			@RequestParam(value = "floor_now") int floor_now,
			@RequestParam(value = "floor_tot") int floor_tot,
			@RequestParam(value = "size") double size,
			@RequestParam(value = "content") String content,
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "address") String address,
			@RequestParam(value = "detail_address") String detail_address,
			@RequestParam(value = "lat") String lat,
			@RequestParam(value = "lng") String lng,
            @RequestParam(value = "fileInfos", required = false) MultipartFile[] files) {
		
		log.info("매물거래 add - 호출");
		try {
			HouseSell houseSell = new HouseSell();
			houseSell.setId(id);
			houseSell.setTitle(title);
			houseSell.setType(type);
			houseSell.setPrice(price);
			houseSell.setAdminPrice(admin_price);
			houseSell.setAdminDesc(admin_desc);
			houseSell.setRoomType(room_type);
			houseSell.setFloorNow(floor_now);
			houseSell.setFloorTot(floor_tot);
			houseSell.setSize(size);
			houseSell.setContent(content);
			houseSell.setKeyword(keyword);
			houseSell.setAddress(address);
			houseSell.setDetailAddress(detail_address);
			houseSell.setLat(lat);
			houseSell.setLng(lng);
            
            // FileUpload 관련 설정.
            if (files != null && files.length > 0) {
                List<FileInfoSellDto> fileInfoSellList = new ArrayList<>();
                for (MultipartFile file : files) {
                	FileInfoSellDto fileInfoSell = new FileInfoSellDto();
                    String originalFileName = file.getOriginalFilename();
                    if (originalFileName != null && !originalFileName.isEmpty()) {
                        String today = new SimpleDateFormat("yyMMdd").format(new Date());
                        String saveFolder = uploadPath + File.separator + today;
                        File folder = new File(saveFolder);
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                        String saveFileName = UUID.randomUUID().toString()
                                + originalFileName.substring(originalFileName.lastIndexOf('.'));
                        file.transferTo(new File(folder, saveFileName));

                        fileInfoSell.setOriginalFile(originalFileName);
                        fileInfoSell.setSaveFolder(today);
                        fileInfoSell.setSaveFile(saveFileName);
                    }
                    fileInfoSellList.add(fileInfoSell);
                }
                houseSell.setFileInfos(fileInfoSellList);
            }
            sellService.add(houseSell);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
	}
	
	@Operation(summary = "매물거래 글목록", description = "검색조건, 페이징에 맞게 리스트 가져오기")
	@GetMapping
	public ResponseEntity<List<HouseSell>> list(PageBean pagebean) {
		log.info("매물거래 list - 호출", pagebean);
		pagebean.setInterval(10);
		return new ResponseEntity<List<HouseSell>>(sellService.list(pagebean), HttpStatus.OK);
	}

	@Operation(summary = "매물거래 글보기", description = "idx에 해당하는 글의 정보를 반환한다.")    
	@GetMapping("{idx}")
	public ResponseEntity<HouseSell> detail(@PathVariable String idx) {
		log.info("매물거래 detail - 호출");
		return new ResponseEntity<HouseSell>(sellService.detail(idx), HttpStatus.OK);
	}
	
//	@Operation(summary = "수정 할 글 얻기", description = "글번호에 해당하는 게시글의 정보를 반환한다.")
//	@GetMapping("/modify/{articleno}")
//	public ResponseEntity<BoardDto> getModifyArticle(
//			@PathVariable("articleno") @Parameter(name = "articleno", description = "얻어올 글의 글번호.", required = true) int articleno)
//			throws Exception {
//		log.info("getModifyArticle - 호출 : " + articleno);
//		return new ResponseEntity<BoardDto>(sellService.getArticle(articleno), HttpStatus.OK);
//	}

	@Operation(summary = "매물거래 글수정", description = "idx에 해당하는 게시글의 정보를 수정한다. 성공하면 'success' 문자열을 반환한다.")
	@PutMapping
	public ResponseEntity<String> update(@RequestBody HouseSell houseSell) {
		log.info("매물거래 update- 호출");
		
		sellService.update(houseSell);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	@Operation(summary = "매물거래 글삭제", description = "idx에 해당하는 게시글의 정보를 삭제한다. 성공하면'success' 문자열을 반환한다.")
	@DeleteMapping("{idx}")
	public ResponseEntity<String> remove(@PathVariable String idx) {
		log.info("매물거래  remove - 호출");
		sellService.remove(idx);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}

	private ResponseEntity<String> exceptionHandling(Exception e) {
		e.printStackTrace();
		return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}