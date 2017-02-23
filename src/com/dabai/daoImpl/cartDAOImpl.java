package com.dabai.daoImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dabai.dao.cartDao;
import com.dabai.factory.dataBaseFactory;
import com.dabai.vo.cartItem;
import com.dabai.vo.carts;
import com.dabai.vo.user;

public class cartDAOImpl implements cartDao{

	@Override
	public int addCart(carts cart) throws SQLException {
		int cartId = 0;
		boolean flag = true;
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		List<Object> params = new ArrayList<Object>();
		String sql;
		if(cart.getId()==0){
			sql = "insert into cart(userId) values(?)";
			params.add(cart.getUserId());
		} else {
			sql = "insert into cart(id,userId) values(?,?)";
			params.add(cart.getId());
			params.add(cart.getUserId());
		}
		if(runner.update(sql,params.toArray())>0)
			flag = true;
		if(flag){
			cartId = this.findCart(cart.getUserId()).getId();
		}
		
		return cartId;
	}

	@Override
	public boolean addCartItem(cartItem cartitem) throws SQLException {
		boolean flag = false;
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "insert into cartitem(bookId,quantity,prices,cartId) values(?,?,?,?)";
		Object[] params = {cartitem.getBookId(),cartitem.getQuantity(),cartitem.getPrices(),cartitem.getCartId()};
		if(runner.update(sql,params)>0)
			flag = true;
		return flag;
	}

	@Override
	public boolean findUser(int userId) throws SQLException {
		boolean flag = false;
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "select * from cart where userId like ?";
		if(runner.query(sql, userId, new BeanHandler(user.class))!=null)
			flag = true;
		return flag;
	}
	
	public carts findCart(int userId) throws SQLException {
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "select * from cart where userId like ?";
		carts cart = (carts) runner.query(sql, userId, new BeanHandler(carts.class));
		
		return cart;
	}
	
	@Override
	/**
	 * 
	 * */
	public int getCartId(int userId) throws SQLException {
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		int cartId = 0;
		if(!this.findUser(userId)) {
			String sql = "insert into cart(userId) values(?)";
			runner.update(sql,userId);
		}
		String sql = "select id from cart where userId like ?";
		Object[] arr = (Object[]) runner.query(sql,userId,new ArrayHandler());
		if(arr!=null)
			cartId = Integer.parseInt(arr[0].toString());	
		
		return cartId;
	}
	
	@Override
	public List<cartItem> getAll(int cartId) throws SQLException {
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "select * from cartitem where cartId like ?";
		List<cartItem> cartitemslist = (List<cartItem>) runner.query(sql, cartId, new BeanListHandler(cartItem.class));
		
		return cartitemslist;
	}
	
	@Override
	public cartItem getCartItem(int bookId) throws SQLException {
		String sql = "select * from cartitem where bookId like ?";
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		cartItem cartitem =  (cartItem) runner.query(sql,bookId,new BeanHandler(cartItem.class));
		
		return cartitem;
	}
	
	@Override
	public boolean modQuantity(int quantity,int bookId) throws SQLException{
		boolean flag = false;
		cartItem cartitem = this.getCartItem(bookId);
		double price = cartitem.getPrices() / cartitem.getQuantity();	//计算书本单价
		quantity = quantity + cartitem.getQuantity();	//重新统计书本数量
		double prices = price * quantity;	//重新计算书本总价
		String sql = "update cartitem set quantity=? ,prices=? where bookId like ?";
		Object[] params = {quantity,prices,bookId};
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		if(runner.update(sql,params)>0)
			flag = true;
		
		return flag;
	}
	
	@Override
	public boolean delCartItem(int bookId) throws SQLException{
		boolean flag = false;
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		String sql = "delete from cartitem where bookId like ?";
		if(runner.update(sql,bookId)>0)
			flag = true;
		
		return flag;
		
	}
}
