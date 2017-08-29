package com.warframe.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//�õ����󷽷�����
		String methodName = request.getParameter("method");
		
		if(methodName==null||methodName.trim().isEmpty()){
			throw new RuntimeException("��������Ϊ�գ���");
		}
		
		Class<? extends BaseServlet> c = this.getClass();
		Method method = null;
		
		try {
			method = c.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
		} catch (Exception e) {
			throw new RuntimeException("������ķ�����"+methodName+"�����ڣ���");
		}
		
				
					
					
		try {
			String result = (String)method.invoke(this, request,response);
			if(result==null||result.trim().isEmpty()){
				throw new RuntimeException("û��ָ��Ҫִ��ʱ�ض�����ת������");
			}
			
			if(result.contains(":")){
				int index = result.indexOf(":");
				String s = result.substring(0, index);//Ҫ���еĶ���
				String path = result.substring(index+1);//ָ������Դ·��
				
				if(s.equalsIgnoreCase("r")){
					response.sendRedirect(request.getContextPath()+path);
				}else if(s.equalsIgnoreCase("f")){
					request.getRequestDispatcher(path).forward(request, response);
				}
			}else{
				request.getRequestDispatcher(result).forward(request, response);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
					

				
				
	}
	
}
