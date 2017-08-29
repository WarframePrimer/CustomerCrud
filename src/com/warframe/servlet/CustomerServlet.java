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
	
	
	//分页查询
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
	
	
	//删除
	public String delete(HttpServletRequest request,HttpServletResponse response){
		//获取到要删除的客户id
		String cid = request.getParameter("cid");
		
		String msg = customerService.delete(cid);
		request.setAttribute("msg", msg);
		return "f:/msg.jsp";
	}
	
	//在编辑前首先将编辑客户旧信息传到编辑页面
	public String preEdit(HttpServletRequest request,HttpServletResponse response){
		
		//response.setContentType("text/html;charset=utf8");
		String cid = request.getParameter("cid");
		Customer c = customerService.preEdit(cid);
		request.setAttribute("cstm", c);
		return "f:/edit.jsp";
	}
	
	
	//编辑客户信息
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
		 * 高级搜索的分页实现
		 */
		//先获取到当前页码
		//System.out.println(getUrl(request));
		String url = getUrl(request);
		
		String value = request.getParameter("pageCode");
    	if(value==null||value.trim().isEmpty()){
    		value = "1";
    	}
    	int pageCode = Integer.parseInt(value);
    	
    	//获取到要检索的信息条目，封装成bean
    	Customer c = new Customer();
		try {
			
			java.util.Map<String, String[]> map = request.getParameterMap();
			Map<String, String[]> props = CodeHandler.codeHandle(map);
			//将表单获取到的有关于CustomerBean的信息进行编码后通过BeanUtils的populate方法封装到一个Customer对象中
			BeanUtils.populate(c, props);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		//得到高级搜索之后的记录数
    	int totalRecords = customerService.countBySearch(c);
    	System.out.println(totalRecords);
    	//得到具体的pageBen
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
