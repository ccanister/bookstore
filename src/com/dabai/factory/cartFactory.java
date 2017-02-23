package com.dabai.factory;

import com.dabai.daoImpl.cartDAOImpl;
import com.dabai.proxy.cartProxy;

public class cartFactory {
	public static cartDAOImpl getInstance(){
		return new cartProxy();
	}
}
