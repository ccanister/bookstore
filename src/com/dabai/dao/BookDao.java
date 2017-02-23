package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;

import com.dabai.vo.book;

/**
 * ���鱾�����й�ҵ���߼�
 * */
public interface BookDao {
	/**�����鱾����
	 * 
	 *@param	�鱾��
	 *@return	����������
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean addBook(book bk) throws SQLException;
	
	/**��ʾ���йؼ����鱾
	 * 
	 *@param	keyword	   ��ѯ�Ĺؼ���	�������������ؼ���
	 *@param	startindex	��ʼ��ѯ��  -1��������
	 *@param	categoryId	�鱾������
	 *@return	��ѯ���������鱾����
	 * @throws SQLException 
	 *@throw	
	 * */
	public List<book> getBookList(String keyWord,int startindex,int categoryId) throws SQLException;
	
	/**ɾ���鱾
	 * 
	 *@param	�鱾��id
	 *@return	��ѯ�Ĺؼ���
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean detBook(int id) throws SQLException;
	
	/**���ݹؼ��ֲ�ѯ�鱾
	 * 
	 *@param	�鱾��id
	 *@return	��ѯ�����鱾
	 * @throws SQLException 
	 *@throw	
	 * */
	public book findById(int id) throws SQLException;


}
