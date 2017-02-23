package com.dabai.daoImpl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.dabai.dao.AdminDao;
import com.dabai.factory.dataBaseFactory;
import com.dabai.vo.administor;

public class AdminDAOImpl implements AdminDao {

	@Override
	public boolean findLogin(administor admin) throws SQLException {
		boolean flag = false;
		QueryRunner run = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "select * from admin where adminName like ? and adminPass like ?";
		Object[] params = {admin.getName(),admin.getPassword()};
		administor ad = (administor) run.query(sql,params,
							new BeanHandler(administor.class));	//BeanHandler表示取出单行数据，且数据类型为javaBean
		if(ad!=null)
			flag = true;
		
		return flag;
	}
	
}
