package com.warframe.utils;

import java.sql.Connection;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库连接实体类，负责数据库的连接
 * 
 * 通过c3p0连接池
 * @author warframe
 *
 */
public class JdbcUtils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	//private static Connection con = null;
	
	
	//通过c3p0连接池的配置得到的dataSource来得到Connection
	public static Connection getConnection(){
		try {
			return dataSource.getConnection();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public static void releaseConnection(Connection con){
		DbUtils.closeQuietly(con);
	}
		
}
