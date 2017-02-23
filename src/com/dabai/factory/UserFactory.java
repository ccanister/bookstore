package com.dabai.factory;

import com.dabai.dao.UserDao;
import com.dabai.proxy.UserProxy;

public class UserFactory {
	public static UserDao getInstance(){
		return new UserProxy();
	}
}
