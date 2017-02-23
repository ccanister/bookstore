package com.dabai.proxy;

import java.sql.SQLException;

import com.dabai.dao.AdminDao;
import com.dabai.daoImpl.AdminDAOImpl;
import com.dabai.vo.administor;

public class AdminDAOProxy implements AdminDao{
	private AdminDao adao = null;
	public AdminDAOProxy(){
		adao = new AdminDAOImpl();
	}
	public boolean findLogin(administor admin){
		boolean flag = false;
		try {
			if(adao.findLogin(admin))
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
