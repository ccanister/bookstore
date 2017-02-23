package com.dabai.proxy;

import java.sql.SQLException;
import java.util.List;

import com.dabai.dao.BookDao;
import com.dabai.daoImpl.BookDAOImpl;
import com.dabai.vo.book;

/**
 * book¥˙¿Ì¿‡
 * */
public class BookProxy extends BookDAOImpl {
	private BookDao bdao;
	public BookProxy(){
		bdao = new BookDAOImpl();
	}
	
	public boolean addBook(book bk){
		boolean flag = false;
		try {
			if(bdao.addBook(bk))
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public List<book> getBookList(String keyWord,int pagenum,int categoryId) {
		List<book> bks = null;
		try {
			bks = bdao.getBookList(keyWord,pagenum,categoryId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return bks;
	}
	
	public boolean detBook(int id) {
		boolean flag = false;
		try {
			if(this.bdao.detBook(id))
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	public book findById(int id){
		book bk = null;
		try {
			bk = this.bdao.findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return bk;
	}
	
}
