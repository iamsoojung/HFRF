package com.ssafy.hfrf.house.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.hfrf.house.model.FileInfoSellDto;
import com.ssafy.hfrf.house.model.HouseSell;
import com.ssafy.hfrf.house.model.PageBean;
import com.ssafy.hfrf.house.model.mapper.SellMapper;
import com.ssafy.hfrf.util.PageUtility;

@Service
public class SellServiceImpl implements SellService {
	@Autowired
	private SellMapper sellMapper;

	@Override
	public List<HouseSell> list(PageBean bean) {
		int cnt = sellMapper.count(bean);
		
		try {
			PageUtility page = new PageUtility(bean.getInterval(), cnt, bean.getPageNo(), "");
			bean.setPageLink(page.getPageBar());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return sellMapper.list(bean); 
	}

	@Override
	public HouseSell detail(String idx) {
		return sellMapper.detail(idx);
	}

	@Override
	@Transactional
	public void add(HouseSell houseSell) throws Exception {
		sellMapper.add(houseSell);
		List<FileInfoSellDto> fileInfoSells = houseSell.getFileInfos();
		if (fileInfoSells != null && !fileInfoSells.isEmpty()) {
			sellMapper.registerFile(houseSell);
		}
	}

	@Override
	@Transactional
	public void update(HouseSell houseSell) {
		sellMapper.update(houseSell);
	}

	@Override
	@Transactional
	public void remove(String idx) {
		sellMapper.remove(idx);
	}
}