package com.ssafy.hfrf.house.model.service;

import java.util.List;

import com.ssafy.hfrf.house.model.HouseSell;
import com.ssafy.hfrf.house.model.PageBean;

public interface SellService {
	public List<HouseSell> list(PageBean bean);

	public HouseSell detail(String idx);

	public void add(HouseSell houseSell);

	public void update(HouseSell houseSell);

	public void remove(String idx);
}