package com.dabai.dao;

import java.sql.SQLException;

import com.dabai.vo.administor;

public interface AdminDao {
	/**
	 * �û���¼��֤
	 * @param
	 * @return 	��֤�Ĳ������
	 * @throw
	 * */
	public boolean findLogin(administor admin) throws SQLException;
}
