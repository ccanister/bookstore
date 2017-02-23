package com.dabai.dao;

import java.sql.SQLException;

import com.dabai.vo.administor;

public interface AdminDao {
	/**
	 * 用户登录验证
	 * @param
	 * @return 	验证的操作结果
	 * @throw
	 * */
	public boolean findLogin(administor admin) throws SQLException;
}
