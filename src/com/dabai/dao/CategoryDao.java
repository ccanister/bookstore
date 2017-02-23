package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;

import com.dabai.vo.category;

public interface CategoryDao {
	/**������𷽷�
	 * 
	 * @param	category ���
	 * @return	�������Ĵ�����
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean addCategory(category cat) throws SQLException;
	
	/**���ݹؼ��ֵõ��������
	 * 
	 * @param	keyWord		�ؼ���
	 * @param	pagenum		��ҳ����ʼ��
	 * @param	iscommond	�Ƿ����Ƽ��ķ���
	 * @return	�������
	 * @throws SQLException 
	 * @throw
	 * */
	public List<category> getCategoryList(String keyWord,int pagenum,boolean iscommond) throws SQLException;
	
	/**�õ��������
	 * 
	 * @param	
	 * @return	�������
	 * @throws SQLException 
	 * @throw
	 * */
	public int getCategorySize() throws SQLException;

	/**����id����
	 * 
	 * @param	id
	 * @return	categoryʵ��
	 * @throws SQLException 
	 * @throw
	 * */
	category findById(int id) throws SQLException;
}
