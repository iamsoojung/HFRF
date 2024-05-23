package com.ssafy.hfrf.house.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.hfrf.house.model.HouseSell;
import com.ssafy.hfrf.house.model.PageBean;

@Mapper
public interface SellMapper {
	public List<HouseSell> 	list(PageBean bean);
	public HouseSell 		detail(String idx);
	public void 			add(HouseSell houseSell);
	public void 			update(HouseSell houseSell);
	public void 			remove(String idx);
	public int 				count(PageBean bean);
}