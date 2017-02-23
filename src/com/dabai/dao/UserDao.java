package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;

import com.dabai.vo.user;

/**
 * 与用户注册登录有关的业务逻辑
 * */
public interface UserDao {
	
	/**根据用户名查找该用户
	 * 
	 * @param	用户名
	 * @return	用户实例
	 * @throws SQLException 
	 * @throw
	 * */
	public user findByName(String username) throws SQLException ;
	
	/**添加用户
	 * 
	 * @param	user实例
	 * @return	添加成功标志
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean addUser(user use) throws SQLException ;

	/**查找所有用户
	 * 
	 * @return	添加成功标志
	 * @throws SQLException 
	 * @throw
	 * */
	public List<user> getAllUser() throws SQLException;
}
