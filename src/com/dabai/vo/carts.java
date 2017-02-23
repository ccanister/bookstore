package com.dabai.vo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.dabai.factory.BookFactory;


public class carts {
	private int id;
	private Set<cartItem> cartItems;
	private double prices;
	private int userId;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserid(int userId) {
		this.userId = userId;
	}

	public carts(){
		cartItems = new HashSet<cartItem> ();
	}
	
	public Set<cartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Set<cartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getPrices() {
		double sum = 0;
		Iterator<cartItem> it = cartItems.iterator();
		while(it.hasNext()){
			cartItem cartitem = it.next();
			try {
				double price = BookFactory.getInstance().findById(cartitem.getBookId()).getPrice()*cartitem.getQuantity();
				sum += price;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return sum;
	}
	public void setPrices(double prices) {
		this.prices = prices;
	}
}
