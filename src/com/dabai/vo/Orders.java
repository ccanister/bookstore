package com.dabai.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Orders {
	private String id;
	private double price;
	private int state;
	private int userId;
	private String ordertime;
	private List<orderItem> orderItemlist;
	public Orders(){
		orderItemlist = new ArrayList<orderItem>();
	}
	public List<orderItem> getOrderItemlist() {
		return orderItemlist;
	}

	public void setOrderItemlist(List<orderItem> orderItemlist) {
		this.orderItemlist = orderItemlist;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
}
