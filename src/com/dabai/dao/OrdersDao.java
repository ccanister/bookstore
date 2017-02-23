package com.dabai.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.dabai.vo.OrderManager;
import com.dabai.vo.Orders;
import com.dabai.vo.orderItem;

/**
 * 与订单有关操作
 * */
public interface OrdersDao {
	
	/**增加订单法
	 * 
	 * @param	category 类别
	 * @return	增加订单的处理结果
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean addOrder(Orders orders) throws SQLException ;
	
	/**获得订单
	 * 
	 * @param	category 类别
	 * @return	得到订单
	 * @throws SQLException 
	 * @throw
	 * */
	public Set<OrderManager> getOrderSet() throws SQLException ;
	
	/**根据订单编号查找订单
	 * 
	 * @param	orderId 订单编号
	 * @return	订单实例
	 * @throws SQLException 
	 * @throw
	 * */
	public Orders getOrderById(String OrderId) throws SQLException;
	/**改变发货状态
	 * 
	 * @param	orderId 订单编号
	 * @return	修改操作标志
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean changeState(String OrderId) throws SQLException;
	
	/**根据订单总号查找包含的每个订单
	 * 
	 * @param	order_id 订单编号
	 * @return	订单list
	 * @throws SQLException 
	 * @throw
	 * */
	public List<orderItem> getorderitemList(String orderId) throws SQLException;

	/**增加订单法
	 * 
	 * @param	category 类别
	 * @return	增加订单的处理结果
	 * @throws SQLException 
	 * @throw
	 * */
	public boolean addOrderItem(orderItem orderitem) throws SQLException;
	
	/**根据用户id查找订单
	 * 
	 * @param	userID	用户编号
	 * @return	订单集合
	 * @throws SQLException 
	 * @throw
	 * */
	public List<String> getOrderByUserid(int userId) throws SQLException;
}
