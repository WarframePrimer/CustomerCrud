package com.warframe.service;



import com.warframe.dao.CustomerDao;
import com.warframe.model.Customer;
import com.warframe.model.PageBean;



/**
 *
 * @author warframe
 *
 */
public class CustomerService {
	private CustomerDao customerDao = new CustomerDao();
	
	public PageBean<Customer> findAll(int pageCode,int totalRecords){
		return customerDao.findAll(pageCode,totalRecords);
	}
	
	
	public int count(){
		return customerDao.count();
	}


	public String delete(String cid) {
		
		int i = customerDao.delete(cid);
		if(i==1){
			return "��ϲ�㣬ɾ���ɹ���"; 
			
		}
		return "ɾ��ʧ�ܣ�";
	}


	public Customer preEdit(String cid) {
		Customer c = customerDao.getCustomerById(cid);
		return c;
	}


	public String edit(Customer c) {
		if(customerDao.edit(c)!=0){
			return "�޸ĳɹ�����";
		}
		
		return "�޸�ʧ�ܣ�";
	}


	public String add(Customer c) {
		if(customerDao.add(c)!=0){
			return "��ӳɹ���";
		}
		return "���ʧ��!";
		
	}


	public int countBySearch(Customer c) {
		int totalRecords = customerDao.countBySearch(c);
		return totalRecords;
	}

	/**
	 * 
	 * @param pageCode
	 * @param totalRecords
	 * @param c
	 * @return
	 */
	public PageBean<Customer> findBySearch(int pageCode, int totalRecords, Customer c) {
		PageBean<Customer> pageBean = customerDao.findBySearch(pageCode,totalRecords,c);
		return pageBean;
	}
	
}
