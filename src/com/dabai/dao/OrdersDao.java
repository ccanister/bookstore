package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.dabai.vo.OrderManager;
import com.dabai.vo.Orders;
import com.dabai.vo.orderItem;

/**
 * �붩���йز���
 * */
public interface OrdersDao {
	
	/**���Ӷ�����
	 * 
	 * @param	category ���
	 * @return	���Ӷ����Ĵ�����
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean addOrder(Orders orders) throws SQLException ;
	
	/**��ö���
	 * 
	 * @param	category ���
	 * @return	�õ�����
	 * @throws SQLException 
	 * @throw
	 * */
	public Set<OrderManager> getOrderSet() throws SQLException ;
	
	/**���ݶ�����Ų��Ҷ���
	 * 
	 * @param	orderId �������
	 * @return	����ʵ��
	 * @throws SQLException 
	 * @throw
	 * */
	public Orders getOrderById(String OrderId) throws SQLException;
	/**�ı䷢��״̬
	 * 
	 * @param	orderId �������
	 * @return	�޸Ĳ�����־
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean changeState(String OrderId) throws SQLException;
	
	/**���ݶ����ܺŲ��Ұ�����ÿ������
	 * 
	 * @param	order_id �������
	 * @return	����list
	 * @throws SQLException 
	 * @throw
	 * */
	public List<orderItem> getorderitemList(String orderId) throws SQLException;

	/**���Ӷ�����
	 * 
	 * @param	category ���
	 * @return	���Ӷ����Ĵ�����
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean addOrderItem(orderItem orderitem) throws SQLException;
	
	/**�����û�id���Ҷ���
	 * 
	 * @param	userID	�û����
	 * @return	��������
	 * @throws SQLException 
	 * @throw
	 * */
	public List<String> getOrderByUserid(int userId) throws SQLException;
}
