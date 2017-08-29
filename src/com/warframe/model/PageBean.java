package com.warframe.model;

import java.util.List;

public class PageBean<T> {
	private int pageCode;//当前页数
	private int pageSize =10;//每页记录数
	private int totalRecords;//总记录数
	private List<T> lists;//当前页信息
	private int totalPages = getTotalPages();//总页数
	//将地址后缀全部加到url中，提高复用性
	
	/**
	 * 查询所有 url="method=findAll"
	 * 高级查询 url="method=query"
	 */
	private String url;
	
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public int getTotalPages(){
		int totalPages = totalRecords/pageSize;
		
		return  totalRecords%pageSize==0?totalPages:totalPages+1;
		
	}
	
	
	
	public List<T> getLists() {
		return lists;
	}



	public void setLists(List<T> lists) {
		this.lists = lists;
	}



	public int getPageCode() {
		return pageCode;
	}
	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	
	
}
