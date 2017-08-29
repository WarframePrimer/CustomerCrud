package com.warframe.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.warframe.model.Customer;
import com.warframe.model.PageBean;
import com.warframe.service.CustomerService;
import com.warframe.utils.CodeHandler;

public class CustomerServlet extends BaseServlet {
	
	
	private static final long serialVersionUID = 1L;
	private CustomerService customerService = new CustomerService();
	
	
	//��ҳ��ѯ
	public String findAll(HttpServletRequest request,HttpServletResponse response){
    	String value = request.getParameter("pageCode");
    	
    	String url = getUrl(request);
    	System.out.println(url);
    	if(value==null||value.trim().isEmpty()){
    		value = "1";
    	}
    	int pageCode = Integer.parseInt(value);
    	int totalRecords = customerService.count();
    	//System.out.println(totalRecords);
    	PageBean<Customer> pageBean = customerService.findAll(pageCode,totalRecords);
    	pageBean.setUrl(url);
    	//System.out.println(pageCode);
    	request.setAttribute("pageBean", pageBean);
    	return "f:/list.jsp";
    }
	
	
	//ɾ��
	public String delete(HttpServletRequest request,HttpServletResponse response){
		//��ȡ��Ҫɾ���Ŀͻ�id
		String cid = request.getParameter("cid");
		
		String msg = customerService.delete(cid);
		request.setAttribute("msg", msg);
		return "f:/msg.jsp";
	}
	
	//�ڱ༭ǰ���Ƚ��༭�ͻ�����Ϣ�����༭ҳ��
	public String preEdit(HttpServletRequest request,HttpServletResponse response){
		
		//response.setContentType("text/html;charset=utf8");
		String cid = request.getParameter("cid");
		Customer c = customerService.preEdit(cid);
		request.setAttribute("cstm", c);
		return "f:/edit.jsp";
	}
	
	
	//�༭�ͻ���Ϣ
	public String edit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//response.setContentType("text/html;charset=utf8");
		
		Customer c = new Customer();
		try {
			
			java.util.Map<String, String[]> map = request.getParameterMap();
			Map<String, String[]> props = CodeHandler.codeHandle(map);
			
			BeanUtils.populate(c, props);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		String message = customerService.edit(c);
		request.setAttribute("msg", message);
		
		return "f:/msg.jsp";
	}
	
	public String add(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Customer c = new Customer();
		try {
			
			java.util.Map<String, String[]> map = request.getParameterMap();
			Map<String, String[]> props = CodeHandler.codeHandle(map);
			
			BeanUtils.populate(c, props);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String message = customerService.add(c);
		request.setAttribute("msg", message);
		
		return "f:/msg.jsp";
	}
	
	public String query(HttpServletRequest request,HttpServletResponse response) throws IOException{
		/**
		 * �߼������ķ�ҳʵ��
		 */
		//�Ȼ�ȡ����ǰҳ��
		//System.out.println(getUrl(request));
		String url = getUrl(request);
		
		String value = request.getParameter("pageCode");
    	if(value==null||value.trim().isEmpty()){
    		value = "1";
    	}
    	int pageCode = Integer.parseInt(value);
    	
    	//��ȡ��Ҫ��������Ϣ��Ŀ����װ��bean
    	Customer c = new Customer();
		try {
			
			java.util.Map<String, String[]> map = request.getParameterMap();
			Map<String, String[]> props = CodeHandler.codeHandle(map);
			//������ȡ�����й���CustomerBean����Ϣ���б����ͨ��BeanUtils��populate������װ��һ��Customer������
			BeanUtils.populate(c, props);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		//�õ��߼�����֮��ļ�¼��
    	int totalRecords = customerService.countBySearch(c);
    	System.out.println(totalRecords);
    	//�õ������pageBen
    	PageBean<Customer> pageBean = customerService.findBySearch(pageCode,totalRecords,c);
    	
    	pageBean.setUrl(url);
    	request.setAttribute("pageBean", pageBean);
		return "f:/list.jsp";
	}
	
	public String getUrl(HttpServletRequest request){
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		
		
		String queryString = request.getQueryString();
		if(queryString.contains("&pageCode=")){
			int index = queryString.lastIndexOf("&pageCode=");
			queryString = queryString.substring(0, index+1);
		}
		
		return contextPath + servletPath +"?" + queryString;
	}
}
