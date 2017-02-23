package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;

import com.dabai.vo.cartItem;
import com.dabai.vo.carts;

public interface cartDao {
	/**���뵽���ﳵ�ܱ�
	 * 
	 *@param	cart��
	 *@return	����������
	 * @throws SQLException 
	 *@throw	
	 * */
	public int addCart(carts cart) throws SQLException;
	
	/**���뵽���ﳵ����
	 * 
	 *@param	cartItem��
	 *@return	����������
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean addCartItem(cartItem cartitem) throws SQLException;
	
	/**�����û�id�����Ƿ���û��й��ﳵ��¼
	 * 
	 *@param	�û�id
	 *@return	����������
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean findUser(int id) throws SQLException;
	
	/**�����û�id������Ӧ���ﳵ��Ϣ
	 * 
	 *@param	�û�id
	 *@return	����������
	 * @throws SQLException 
	 *@throw	
	 * */
	public carts findCart(int userId) throws SQLException;

	/**�����û�id������Ӧ���ﳵ�ܱ�id
	 * ��id�������򴴽�������ȡ��
	 * 
	 *@param	�û�id
	 *@return	���ﳵid
	 * @throws SQLException 
	 *@throw	
	 * */
	public int getCartId(int userId) throws SQLException;

	/**���ݹ��ﳵ�ı�Ų��ҹ��ﳵ������Ʒ
	 * 
	 *@param	���ﳵ���
	 *@return	������Ʒ
	 * @throws SQLException 
	 *@throw	
	 * */
	public List<cartItem> getAll(int cartId) throws SQLException;

	/**�鱾�Ѵ������ݿ��У���ֱ���޸�����
	 * 
	 *@param	qunatity	�鱾����
	 *@param	bookId	�鱾���
	 *@return	�ж�����Ƿ�ɹ�
	 * @throws SQLException 
	 *@throw	
	 * */
	public boolean modQuantity(int quantity, int bookId) throws SQLException;

	/**�����鱾���ɾ�����ﳵ��Ϣ
	 * 
	 *@param	bookId	�鱾���
	 *@return	�ж�ɾ���Ƿ�ɹ�
	 * @throws SQLException 
	 * */
	public boolean delCartItem(int bookId) throws SQLException;

	/**�����鱾��Ż�ȡ�ù��ﳵ��Ϣ
	 * 
	 *@param	bookId	�鱾���
	 *@return	���ﳵ�鱾��Ϣ
	 * @throws SQLException 
	 * */
	public cartItem getCartItem(int bookId) throws SQLException;
}
