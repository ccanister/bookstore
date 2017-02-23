package com.dabai.proxy;

import java.sql.SQLException;
import java.util.List;

import com.dabai.dao.UserDao;
import com.dabai.daoImpl.UserDAOImpl;
import com.dabai.vo.user;

public class UserProxy extends UserDAOImpl{
	UserDao udao;
	public UserProxy(){
		udao = new UserDAOImpl();
	}
	public user findByName(String userName){
		user user = null;
		try {
			user = udao.findByName(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
		
	}
	
	public boolean addUser(user use) {
		boolean flag = false;
		try {
			flag = udao.addUser(use);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public List<user> getAllUser() {
		List<user> userlist = null;
		try {
			userlist = this.udao.getAllUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return userlist;
	}
}
