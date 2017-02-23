package com.dabai.proxy;

import java.sql.SQLException;
import java.util.List;

import com.dabai.dao.cartDao;
import com.dabai.daoImpl.cartDAOImpl;
import com.dabai.vo.cartItem;
import com.dabai.vo.carts;

public class cartProxy extends cartDAOImpl{
	private cartDao cdao;
	public cartProxy(){
		this.cdao = new cartDAOImpl();
	}
	
	public int addCart(carts cart)  {
		
		int cartId = 0;
		try {
			cartId = cdao.addCart(cart);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cartId;
	}
	
	public boolean addCartItem(cartItem cartitem){
		
		boolean flag = false;
		try {
			if(cdao.addCartItem(cartitem))
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public boolean findUser(int id){
		
		boolean flag = false;
		try {
			if(cdao.findUser(id))
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	public int getCartId(int userId) {
		
		int cartId = 0;
		try {
			cartId = cdao.getCartId(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cartId;
	}
	
	public List<cartItem> getAll(int cartId) {
		List<cartItem> cartitemlist = null;
		try {
			cartitemlist=  cdao.getAll(cartId);
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		return cartitemlist;
	}
	
	public boolean modQuantity(int quantity, int bookId) {
		boolean flag = false;
		try {
			if(cdao.modQuantity(quantity, bookId))
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public boolean delCartItem(int bookId) {
		boolean flag = false;
		try {
			if(cdao.delCartItem(bookId))
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	public cartItem getCartItem(int bookId) {
		cartItem cartitem = null;
		try {
			cartitem = this.cdao.getCartItem(bookId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return cartitem;
	}
}
