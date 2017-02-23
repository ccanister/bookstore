package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;

import com.dabai.vo.user;

/**
 * ���û�ע���¼�йص�ҵ���߼�
 * */
public interface UserDao {
	
	/**�����û������Ҹ��û�
	 * 
	 * @param	�û���
	 * @return	�û�ʵ��
	 * @throws SQLException 
	 * @throw
	 * */
	public user findByName(String username) throws SQLException ;
	
	/**����û�
	 * 
	 * @param	userʵ��
	 * @return	��ӳɹ���־
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean addUser(user use) throws SQLException ;

	/**���������û�
	 * 
	 * @return	��ӳɹ���־
	 * @throws SQLException 
	 * @throw
	 * */
	public List<user> getAllUser() throws SQLException;
}
