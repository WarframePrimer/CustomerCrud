package com.warframe.model;

import java.util.List;

public class PageBean<T> {
	private int pageCode;//��ǰҳ��
	private int pageSize =10;//ÿҳ��¼��
	private int totalRecords;//�ܼ�¼��
	private List<T> lists;//��ǰҳ��Ϣ
	private int totalPages = getTotalPages();//��ҳ��
	//����ַ��׺ȫ���ӵ�url�У���߸�����
	
	/**
	 * ��ѯ���� url="method=findAll"
	 * �߼���ѯ url="method=query"
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
