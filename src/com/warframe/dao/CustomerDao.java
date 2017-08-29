package com.warframe.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.warframe.model.Customer;
import com.warframe.model.PageBean;
import com.warframe.utils.CustomersHandler;
import com.warframe.utils.JdbcUtils;

public class CustomerDao {
	private Connection con = null;
	
	public PageBean<Customer> findAll(int pageCode,int totalRecords){
		PageBean<Customer> pageBean = new PageBean<>();
		pageBean.setPageCode(pageCode);
		pageBean.setTotalRecords(totalRecords);
		
		List<Customer> lists = new ArrayList<>();
		
		
		DbUtils.closeQuietly(con);
		con = JdbcUtils.getConnection();

		String sql = "select * from t_customer limit ?,?";
		QueryRunner qr = new QueryRunner();
		
		try {
			lists = qr.query(con, sql, new CustomersHandler(), (pageCode-1)*pageBean.getPageSize(),pageBean.getPageSize());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		pageBean.setLists(lists);
		String url = "method=findAll&pageCode=" + pageBean.getPageCode();
		
		//关闭连接需要在return语句之前
		DbUtils.closeQuietly(con);
		return pageBean;
	}
	
	public int count(){
		JdbcUtils.releaseConnection(con);
		con = JdbcUtils.getConnection();
		
		String sql = "select count(*) from t_customer";
		QueryRunner qr = new QueryRunner();
		
		try {
			Number count = (Number)qr.query(con, sql,new ScalarHandler());
			JdbcUtils.releaseConnection(con);
			
			return count.intValue();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		//DbUtils.closeQuietly(con);
		
	}
	
	
	//删除
	public int delete(String cid) {
		JdbcUtils.releaseConnection(con);
		con = JdbcUtils.getConnection();
		String sql = "delete from t_customer where cid = ?";
		int result = 0;
		QueryRunner qr = new QueryRunner();
		try {
			result = qr.update(con, sql, cid);
			DbUtils.closeQuietly(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public Customer getCustomerById(String cid) {
		JdbcUtils.releaseConnection(con);
		con = JdbcUtils.getConnection();
		String sql = "select * from t_customer where cid = ?";
		Customer c = null;
		QueryRunner qr = new QueryRunner();
		
		try {
			c = qr.query(con, sql, new CustomersHandler(), cid).get(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DbUtils.closeQuietly(con);
		
		return c;
	}

	public int edit(Customer c) {
		JdbcUtils.releaseConnection(con);
		con = JdbcUtils.getConnection();
		int result = 0;
		String sql = "update t_customer set cname = ?,gender = ?,"
				+ "birthday = ?,cellphone = ?,email = ?,description = ? "
				+ "where cid = ?";
		QueryRunner qr = new QueryRunner();
		try {
			result = qr.update(con, sql, c.getCname(),c.getGender(),c.getBirthday(),c.getCellphone(),
					c.getEmail(),c.getDescription(),c.getCid());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DbUtils.closeQuietly(con);
		return result;
	}

	public int add(Customer c) {
		JdbcUtils.releaseConnection(con);
		con = JdbcUtils.getConnection();
		int result = 0;
		String sql = "insert into t_customer(cname,gender,"
				+ "birthday,cellphone,email,description) values (?,?,?,?,?,?)";
		
		QueryRunner qr = new QueryRunner();
		try {
			result = qr.update(con, sql, c.getCname(),c.getGender(),c.getBirthday(),c.getCellphone(),c.getEmail(),
					c.getDescription());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbUtils.closeQuietly(con);
		return result;
		
	}

	
	/**
	 * 得到符合查询条件的记录数
	 * @param c
	 * @return
	 */
	public int countBySearch(Customer c) {
		
		//获取连接
		JdbcUtils.releaseConnection(con);
		con = JdbcUtils.getConnection();
		StringBuilder sql = new StringBuilder("select count(*) from t_customer where 1=1");
		
		
		List<String> params = new ArrayList<>();
		
		String cname = c.getCname();
		if(null != cname || !cname.trim().isEmpty()){
			sql.append(" and cname like ?");
			params.add("%" + cname + "%");
		}
		
		String gender = c.getGender();
		if(null!=gender||!gender.trim().isEmpty()){
			sql.append(" and gender like ?");
			params.add("%" + gender + "%");
		}
		
		String cellphone = c.getCellphone();
		if(null!=cellphone||!cellphone.trim().isEmpty()){
			sql.append(" and cellphone like ?");
			params.add("%" + cellphone + "%");
		}
		
		String email= c.getEmail();
		if(null!=email||!email.trim().isEmpty()){
			sql.append(" and email like ?");
			params.add("%" + email + "%");
		}
		
		QueryRunner qr = new QueryRunner();
		Number result = null;
		try {
			result = (Number)qr.query(con, sql.toString(), new ScalarHandler(), params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		DbUtils.closeQuietly(con);
		return result.intValue();
	}
	
	
	//将符合搜索条件的所有封装到pageBen中
	public PageBean<Customer> findBySearch(int pageCode, int totalRecords, Customer c) {
		PageBean<Customer> pageBean = new PageBean<>();
		pageBean.setPageCode(pageCode);
		pageBean.setTotalRecords(totalRecords);
		
		JdbcUtils.releaseConnection(con);
		con = JdbcUtils.getConnection();
		StringBuilder sql = new StringBuilder("select * from t_customer where 1=1");
		
		List params = new ArrayList<>();
		
		String cname = c.getCname();
		if(null != cname || !cname.trim().isEmpty()){
			sql.append(" and cname like ?");
			params.add("%" + cname + "%");
		}
		
		String gender = c.getGender();
		if(null!=gender||!gender.trim().isEmpty()){
			sql.append(" and gender like ?");
			params.add("%" + gender + "%");
		}
		
		String cellphone = c.getCellphone();
		if(null!=cellphone||!cellphone.trim().isEmpty()){
			sql.append(" and cellphone like ?");
			params.add("%" + cellphone + "%");
		}
		
		String email= c.getEmail();
		if(null!=email||!email.trim().isEmpty()){
			sql.append(" and email like ?");
			params.add("%" + email + "%");
		}
		
		//最后添加要查询的页数limit语句
		sql.append(" limit ?,?");
		params.add((pageCode-1)*pageBean.getPageSize());
		params.add(pageBean.getPageSize());
		
		QueryRunner qr = new QueryRunner();
		List<Customer> lists = null;
		try {
			lists = qr.query(con, sql.toString(), new CustomersHandler(), params.toArray());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pageBean.setLists(lists);
		DbUtils.closeQuietly(con);
		return pageBean;
	}
	
//	@Test
//	public void insert1000() throws SQLException{
//		//进行批处理插入1000条数据
//		JdbcUtils.releaseConnection(con);
//		con = JdbcUtils.getConnection();
//		System.out.println(con);
//		String sql = "insert into t_customer values (?,?,?,?,?,?,?)";
//		//con.setAutoCommit(false);
//		PreparedStatement pstmt = con.prepareStatement(sql);
//		
//		for(int i = 1;i<=1000;i++){
//			pstmt.setString(1, i+"1");
//			pstmt.setString(2, "客户_"+i);
//			pstmt.setString(3, i%2==0?"男":"女");
//			pstmt.setString(4, "1996-2-10");
//			pstmt.setString(5, "18796"+i);
//			pstmt.setString(6,"187960"+i+"@163.com");
//			pstmt.setString(7,"我是客户");
//			pstmt.addBatch();
//		}
//		
//		pstmt.executeBatch();
//		
//	}
	
}
