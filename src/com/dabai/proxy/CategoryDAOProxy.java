package com.dabai.proxy;

import java.sql.SQLException;
import java.util.List;

import com.dabai.dao.CategoryDao;
import com.dabai.daoImpl.CategoryDAOImpl;
import com.dabai.vo.category;

public class CategoryDAOProxy extends CategoryDAOImpl{ 
	private CategoryDao cdao;
	public CategoryDAOProxy(){
		cdao = new CategoryDAOImpl();
	}
	
	public boolean addCategory(category cat){
		boolean flag = false;
		try {
			if(cdao.addCategory(cat)){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return flag;
	}
	
	public List<category> getCategoryList( String keyWord,int pagenum,boolean iscommond){
		List<category> cats = null;
		try {
			cats = cdao.getCategoryList(keyWord,pagenum,iscommond);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cats;
	}
	
	public int getCategorySize(){
		int size = 0;
		try {
			size = cdao.getCategorySize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return size;
	}
	
	public category findById(int id){
		category cat = null;
		try {
			cat = cdao.findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cat;
	}
}
