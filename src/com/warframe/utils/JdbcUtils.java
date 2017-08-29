package com.warframe.utils;

import java.sql.Connection;

import org.apache.commons.dbutils.DbUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * ���ݿ�����ʵ���࣬�������ݿ������
 * 
 * ͨ��c3p0���ӳ�
 * @author warframe
 *
 */
public class JdbcUtils {
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	//private static Connection con = null;
	
	
	//ͨ��c3p0���ӳص����õõ���dataSource���õ�Connection
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
