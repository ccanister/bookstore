package com.dabai.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dabai.config.valueConfig;
import com.dabai.dao.CategoryDao;
import com.dabai.factory.dataBaseFactory;
import com.dabai.method.commonReflect;
import com.dabai.vo.book;
import com.dabai.vo.category;

public class CategoryDAOImpl  implements CategoryDao{
	
	@Override
	public boolean addCategory(category cat) throws SQLException {
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		boolean flag = false;
		List<Object> all = new ArrayList<Object>();
		String sql = "insert into category (name,description,iscommond) value(?,?,?)";
		for(int i=0; i<valueConfig.CATEGORY_VALUE.length-1; i++){
			all.add(commonReflect.ExecuteMethod(cat,"category",null,valueConfig.CATEGORY_VALUE[i]));
		}
		all.add(cat.isIscommond());
		if(runner.update(sql,all.toArray())>0)
			flag = true;
	
		return flag;
	}

	@Override
	public List<category> getCategoryList(String keyWord,int satrtindex,boolean iscommond) throws SQLException {
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		List<category> cats = null;
		StringBuffer sql = new StringBuffer("select * from category where 1=1");
		List<String> params = new ArrayList<String>();		//拼接在sql语句后面
		if(iscommond){
			sql.append(" and iscommond=true");
		}
		if(satrtindex<0){
			keyWord = keyWord==null?"":keyWord;
			sql.append(" and name like ?");
			params.add("%"+keyWord+"%");
			sql.append(" or description like ?");
			params.add("%"+keyWord+"%");
			cats = (List<category>) runner.query(sql.toString(),params.toArray(),new BeanListHandler(category.class));
		}else {
			sql.append(" limit ?,3");
			cats = (List<category>) runner.query(sql.toString(),satrtindex,new BeanListHandler(category.class));
		}
		return cats;
	}

	@Override
	public int getCategorySize() throws SQLException {
		String sql = "select count(*) from category";
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		Object[] arr = (Object[]) runner.query(sql,new ArrayHandler());
		long size = (long) arr[0];
		
		return (int)size;		//无法创建Integer类
	}

	@Override
	public category findById(int id) throws SQLException {
		String sql = "Select * from category where id like ?";
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		
		return (category) runner.query(sql,id,new BeanHandler(category.class));
	}
}
