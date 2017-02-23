package com.dabai.vo;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
	private user user;
	private List<book> bks;
	private List<Orders> orderslist;
	public OrderManager(){
		this.bks = new ArrayList<book>();
	}
	

	public List<Orders> getOrderslist() {
		return orderslist;
	}


	public void setOrderslist(List<Orders> orderslist) {
		this.orderslist = orderslist;
	}


	public user getUser() {
		return user;
	}

	public void setUser(user user) {
		this.user = user;
	}

	public List<book> getBks() {
		return bks;
	}

	public void setBks(List<book> bks) {
		this.bks = bks;
	}


}
