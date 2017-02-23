package com.dabai.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dabai.config.valueConfig;
import com.dabai.dao.OrdersDao;
import com.dabai.factory.UserFactory;
import com.dabai.factory.dataBaseFactory;
import com.dabai.method.commonReflect;
import com.dabai.vo.OrderManager;
import com.dabai.vo.Orders;
import com.dabai.vo.book;
import com.dabai.vo.orderItem;
import com.dabai.vo.user;

public class OrdersDAOImpl implements OrdersDao{

	@Override
	public boolean addOrder(Orders orders) throws SQLException {
		boolean flag = false;
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "insert into orders (id,ordertime,price,state,userId) value(?,?,?,?,?)";
		Object[] params = new Object[5];
		for(int i=0; i<params.length; i++){
			params[i] = commonReflect.ExecuteMethod(orders, "Orders", null, valueConfig.ORDERS_VALUE[i]);
		}
		if(runner.update(sql,params)>0){
			flag = true;
		}
		
		return flag;
	}

	@Override
	public boolean addOrderItem(orderItem orderitem) throws SQLException {
		boolean flag = false;
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "insert into orderitem (quantity,prices,orderId,bookId) value(?,?,?,?)";
		Object[] params = new Object[valueConfig.ORDERITEM_VALUE.length];
		for(int i=0; i<params.length; i++){
			params[i] = commonReflect.ExecuteMethod(orderitem, "orderItem", null, valueConfig.ORDERITEM_VALUE[i]);
		}
		if(runner.update(sql,params)>0){
			flag = true;
		}
		
		return flag;
	}
//	@Override
//	public Set<OrderManager> getOrderList() throws SQLException {
//		String sql = "select * from orders where 1=1 order by ordertime";
//		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
//		List<Orders> orderslist = (List<Orders>) runner.query(sql, new BeanListHandler(Orders.class));
//		Iterator<Orders> orderit = orderslist.iterator();
//		Object[] params = new Object[valueConfig.ORDERMANAGER_VALUE.length];
//		Set<OrderManager> ordermans = new HashSet<OrderManager>();
//		while(orderit.hasNext()){
//			OrderManager orderman = new OrderManager();	//循环一次，代表有新的总订单生成
//			Orders orders = orderit.next();
//			/**
//			 * 为orders添加orderitem
//			 * */
//			String order_id = orders.getId();
//			List<orderItem> orderitemlist = this.getorderitemList(order_id);
//			Iterator<orderItem> orderitemsit = orderitemlist.iterator();
//			while(orderitemsit.hasNext()){
//				for(int i=0; i<orderitemlist.size();i++){
//					orders.getOrderitems().put(String.valueOf(i),orderitemsit.next());
//				}
//			}
//			/**
//			 * 添加user类
//			 * */
//			int userId = orders.getuserId();
//			String user_sql = "select * from user where id like ?";
//			user user = (user)runner.query(user_sql,userId,new BeanHandler(user.class));
//			/**
//			 * 添加nook类
//			 * */
//			Map<String,orderItem> orderitemmap = orders.getOrderitems();
//			List<book> bks = orderman.getBks();
//			for(Map.Entry<String, orderItem> entry:orderitemmap.entrySet()){
//				String book_id = entry.getValue().getBook_id();
//				String book_sql = "select * from book where id like ?";
//				bks.add((book)runner.query(book_sql,book_id,new BeanHandler(book.class)));
//			}
//			params[0] = user;params[1] = bks;params[2] = orders;
//			for(int i=0;i<params.length; i++){
//				commonReflect.ExecuteMethod(orderman, "OrderManager", params[i], valueConfig.ORDERMANAGER_VALUE[i]);
//			}
//			ordermans.add(orderman);
//		}
//		return ordermans;
//	}

	@Override
	public Orders getOrderById(String orderId) throws SQLException {
		String sql = "select * from orders where id like ?";
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		Orders order = (Orders) runner.query(sql,orderId,new BeanHandler(Orders.class));
		return order;
	}

	@Override
	public boolean changeState(String Order_id) throws SQLException {
		boolean flag = false;
		String sql = "update orders set state=2 where id like ?";
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		if(runner.update(sql,Order_id)>0)
			flag = true;
		return flag;
	}

	public List<orderItem> getorderitemList(String orderId) throws SQLException{
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String order_sql = "select * from orderitem where orderId like ?";
		List<orderItem> orderitemlist = (List<orderItem>) runner.query(order_sql,orderId,new BeanListHandler(orderItem.class));
		return orderitemlist;
	}

	@Override
	public Set<OrderManager> getOrderSet() throws SQLException {
		List<user> userlist = UserFactory.getInstance().getAllUser();
		Iterator<user> userit = userlist.iterator();
		Set<OrderManager> ordermanSet = new HashSet<OrderManager>();
		while(userit.hasNext()){
			OrderManager om = new OrderManager();
			user user = userit.next();
			om.setUser(user);
			int userId = user.getId();
			List<String> orderarr = this.getOrderByUserid(userId);
			if(orderarr.size()!=0){
				Iterator<String> orderIdit = orderarr.iterator();
				List<Orders> orderslist = new ArrayList<Orders>();
				while(orderIdit.hasNext()){
					String orderId = orderIdit.next();
					Orders order = this.getOrderById(orderId);
					List<orderItem> orderitemlist = this.getorderitemList(orderId);
					order.setOrderItemlist(orderitemlist);
					orderslist.add(order);
				}
				om.setOrderslist(orderslist);
				ordermanSet.add(om);
			}
		}
		
		return ordermanSet;
	}

	@Override
	public List<String> getOrderByUserid(int userId) throws SQLException {
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "select id from orders where userId like ?";
		List<Object []> temparr =  (List<Object[]>) runner.query(sql,userId,new ArrayListHandler());
		Iterator<Object[] > orderit = temparr.iterator();
		List<String> orderarr = new ArrayList<String>();
		while(orderit.hasNext()){
			Object[] obj = orderit.next();
			String tempstr = "";
			for(int i=0;i<obj.length;i++)
				tempstr += obj[i];
			
			orderarr.add(tempstr);
		}
		
		return orderarr;
	}
}
