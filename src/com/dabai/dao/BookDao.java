package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;

import com.dabai.vo.book;

/**
 * 与书本操作有关业务逻辑
 * */
public interface BookDao {
	/**增加书本方法
	 * 
	 *@param	书本类
	 *@return	操作处理结果
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean addBook(book bk) throws SQLException;
	
	/**显示带有关键字书本
	 * 
	 *@param	keyword	   查询的关键字	“”代表不包含关键字
	 *@param	startindex	起始查询处  -1代表所有
	 *@param	categoryId	书本种类编号
	 *@return	查询到的所有书本集合
	 * @throws SQLException 
	 *@throw	
	 * */
	public List<book> getBookList(String keyWord,int startindex,int categoryId) throws SQLException;
	
	/**删除书本
	 * 
	 *@param	书本的id
	 *@return	查询的关键字
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean detBook(int id) throws SQLException;
	
	/**根据关键字查询书本
	 * 
	 *@param	书本的id
	 *@return	查询到的书本
	 * @throws SQLException 
	 *@throw	
	 * */
	public book findById(int id) throws SQLException;


}
