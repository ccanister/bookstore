package com.dabai.factory;

import com.dabai.dao.BookDao;
import com.dabai.proxy.BookProxy;

public class BookFactory {
	public static BookDao getInstance(){
		return new BookProxy();
	}
}
