package com.dabai.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dabai.factory.dataBaseFactory;


class test{
	public static void main(String[] args){
		try {
			Connection conn = dataBaseFactory.getDataSource().getConnection();
			Statement state = 
					conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM book";
			ResultSet rs = state.executeQuery(sql);
			rs.last();
			System.out.println(rs.getRow());
			int current = 3;
//			rs.absolute(current);
			int i=0;
			while(i<current&&!rs.isBeforeFirst()){
				System.out.println(rs.getString("name"));
				i++;
				rs.next();
			}
			//System.out.println(rs.getString("id"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}