package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;

import com.dabai.vo.category;

public interface CategoryDao {
	/**增加类别方法
	 * 
	 * @param	category 类别
	 * @return	增加类别的处理结果
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean addCategory(category cat) throws SQLException;
	
	/**根据关键字得到所有类别
	 * 
	 * @param	keyWord		关键字
	 * @param	pagenum		分页的起始处
	 * @param	iscommond	是否是推荐的分类
	 * @return	所有类别
	 * @throws SQLException 
	 * @throw
	 * */
	public List<category> getCategoryList(String keyWord,int pagenum,boolean iscommond) throws SQLException;
	
	/**得到类别数量
	 * 
	 * @param	
	 * @return	类别数量
	 * @throws SQLException 
	 * @throw
	 * */
	public int getCategorySize() throws SQLException;

	/**根据id分类
	 * 
	 * @param	id
	 * @return	category实例
	 * @throws SQLException 
	 * @throw
	 * */
	category findById(int id) throws SQLException;
}
