package com.dabai.config;

import java.util.HashMap;
import java.util.Map;

public class valueConfig {
	public final static String[] CATEGORY_VALUE = {"Name","Description","Iscommond"};
	public final static String[] BOOK_VALUE = {"Name","Author","Price","Description","Category_id","Image","Token"};
	public final static String[] USER_NAME = {"Username","Password","Phone","Email","Address"};
	public final static String[] USER_CHECK = {"^(\\w{5,8})","\\w+","^((13[0-9])|(15[^4])|(18[0.2.3,5-9])|(17[0-8]!147)\\d(8)$)",
					"(([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]?@([a-z0-9A-Z]+\\.)+[a-zA-Z]{2,})","^[a-zA-Z]+","^\\w+"};
	public final static Map<String,String> USER_VALUE = new HashMap<String,String>();
	static {
		for(int i=0; i<USER_NAME.length; i++){
			USER_VALUE.put(USER_NAME[i],USER_CHECK[i]);
		}
	}
	public static Map<String, String> getUserValue() {
		return USER_VALUE;
	}
	public final static String[] CARTITEM_VALUE = {"BookId","Quantity","Prices","CartId"};
	public final static String[] ORDERITEM_VALUE = {"Quantity","Prices","OrderId","BookId"};
	public final static String[] ORDERS_VALUE = {"Id","Ordertime","Price","State","UserId"};
	public final static String[] ORDERMANAGER_VALUE = {"User","Bks","Orders"};
}
