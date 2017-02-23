package com.dabai.factory;

import com.dabai.proxy.AdminDAOProxy;

public class AdminFactory {
	public static AdminDAOProxy getAdminInstance(){
		return new AdminDAOProxy();
	}
}
