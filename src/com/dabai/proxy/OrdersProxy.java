package com.dabai.proxy;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.dabai.dao.OrdersDao;
import com.dabai.daoImpl.OrdersDAOImpl;
import com.dabai.vo.OrderManager;
import com.dabai.vo.Orders;
import com.dabai.vo.orderItem;

public class OrdersProxy extends OrdersDAOImpl {
	OrdersDao odao;
	public OrdersProxy(){
		this.odao = new OrdersDAOImpl();
	}
	
	public boolean addOrder(Orders orders) {
		boolean flag = false;
		try {
			if(odao.addOrder(orders)){
				flag =true;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return flag;
	}
	public Set<OrderManager> getOrderSet( ){
		Set<OrderManager> ordermans = null;
		try {
			ordermans = this.odao.getOrderSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ordermans;
	}
	
	public Orders findOrderById(String Order_id){
		Orders order = null;
		try {
			order = odao.getOrderById(Order_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return order;
	}
	
	public boolean changeState(String Order_id){
		boolean flag = false;
		try {
			if(odao.changeState(Order_id)){
				flag = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public List<orderItem> getorderitemList(String order_id) {
		List<orderItem> orderitems = null;
		try {
			orderitems = odao.getorderitemList(order_id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderitems;
	}
	public List<String> findOrderByUserid(int userId) {
		List<String> orderarr = null;
		try {
			orderarr = this.odao.getOrderByUserid(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderarr;
	}
	public Orders getOrderById(String orderId) {
		Orders order = null;
		try {
			order = this.odao.getOrderById(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return order;
	}
}
