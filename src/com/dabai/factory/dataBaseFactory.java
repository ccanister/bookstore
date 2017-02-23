package com.dabai.factory;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 制造数据连接池的工厂
 * */
public class dataBaseFactory {
	private static Connection conn;			
	private static DataSource ds ;		//source类，用来初始化QuereyRunner
	static {
		ds = new ComboPooledDataSource();
	}
	
	public static Connection getConnection(){
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	public static DataSource getDataSource(){
		return ds;
	}
	
}
