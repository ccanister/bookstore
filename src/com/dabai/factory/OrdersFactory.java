package com.dabai.factory;

import com.dabai.dao.OrdersDao;
import com.dabai.proxy.OrdersProxy;

public class OrdersFactory {
	public static OrdersDao getInstance(){
		return new OrdersProxy();
	}
}
