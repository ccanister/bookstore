package com.dabai.factory;

import com.dabai.dao.CategoryDao;
import com.dabai.proxy.CategoryDAOProxy;

public class CategoryFactory {
	public static CategoryDao getInstance(){
		return new CategoryDAOProxy();
	}
}
