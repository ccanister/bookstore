package com.dabai.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dabai.config.valueConfig;
import com.dabai.dao.BookDao;
import com.dabai.factory.dataBaseFactory;
import com.dabai.method.commonReflect;
import com.dabai.vo.book;
import com.dabai.vo.page;

/**
 * 实现和book类有关的业务逻辑
 * */
public class BookDAOImpl implements BookDao{
	private QueryRunner runner = new QueryRunner();
	public BookDAOImpl(){
		runner = new QueryRunner(dataBaseFactory.getDataSource());                           
	}
	@Override
	public boolean addBook(book bk) throws SQLException {
		boolean flag = false;
		String sql = "insert into book (name,author,price,description,category_id,image) value(?,?,?,?,?,?)";
		List<Object> all = new ArrayList<Object>();
		for(int i=0;i<valueConfig.BOOK_VALUE.length-1;i++){
			all.add(commonReflect.ExecuteMethod(bk,"book",null,valueConfig.BOOK_VALUE[i]));
		}
		if(runner.update(sql,all.toArray())>0)
			flag = true;

		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<book> getBookList(String keyWord,int startindex,int categoryId) throws SQLException {
		List<book> bks = null;
		String sql = null;
		//初始化sql里面预编译参数
		List<Object> params = new ArrayList<Object>();
		if(categoryId>=0){
			sql = "SELECT * FROM book b inner join category c ON c.id=b.category_id where category_id like ?";
			params.add(categoryId);
		}
		else
			sql = "select * from book where 1=1";
		StringBuffer sb = new StringBuffer(sql);
		if(startindex<0){
				bks = params.size()==0?
						(List<book>) runner.query(sb.toString(),new BeanListHandler(book.class)):
						(List<book>) runner.query(sb.toString(),params.toArray(),new BeanListHandler(book.class))	;
		}else{
			sb.append(" limit ?,?");
			params.add(startindex);
			params.add(page.PAGECAP);
			bks = (List<book>) runner.query(sb.toString(),params.toArray(),new BeanListHandler(book.class));
		}
		
		return bks;
	}

	
	@Override
	public boolean detBook(int id) throws SQLException {
		boolean flag = false;
		String sql = "delete from book where id like ?";
		if(runner.update(sql,id)>0)
			flag = true;
		
		return flag;
	}
	
	@Override
	public book findById(int id) throws SQLException {
		String sql = "Select * from book where id like ?";
		
		return (book) runner.query(sql,id,new BeanHandler(book.class));
	}
	
}
