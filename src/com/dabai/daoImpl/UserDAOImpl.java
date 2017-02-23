package com.dabai.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dabai.config.valueConfig;
import com.dabai.dao.UserDao;
import com.dabai.factory.dataBaseFactory;
import com.dabai.method.commonReflect;
import com.dabai.vo.user;

public class UserDAOImpl implements UserDao {

	@Override
	public user findByName(String username) throws SQLException {
		user user = null;
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "Select * from user where username like ?";
		user = (user)runner.query(sql,username,new BeanHandler(user.class));
		
		return user;
	}

	@Override
	public boolean addUser(user use) throws SQLException {
		boolean flag = false;
		String sql = "insert into user (username,password,phone,email,address) values(?,?,?,?,?)";
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		//获取user类中属性并存入param数组中
		Object[] params = new Object[valueConfig.USER_NAME.length];
		for(int i=0;i<params.length;i++){
			params[i] = commonReflect.ExecuteMethod(use, "user", null, valueConfig.USER_NAME[i]);
		}
		if(runner.update(sql,params)>0)
			flag = true;
		
		return flag;
	}

	@Override
	public List<user> getAllUser() throws SQLException {
		String sql = "select * from user";
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		List<user> userlist = (List<user>) runner.query(sql,new BeanListHandler(user.class));
		
		return userlist;
	}
}
