package com.dabai.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.dabai.config.valueConfig;
import com.dabai.vo.category;
import com.dabai.factory.*;

/**
 * 通过反射机制获取到方法名然后调用该方法
 * */
public class commonReflect {
	
	/**
	 * @params className 方法调用的类	methodName  方法		Object... params 传入invoke的第二个参数，即方法名的参数、
	 * @return	方法返回值
	 * @throw	ClassNotFoundException,IllegalAccessException, IllegalArgumentException, InvocationTargetException
	 * */
	public  static Object ReflectTest(String className,String methodName,Object... params) {// 多个异常的处理
											//ClassNotFoundException,IllegalAccessException, IllegalArgumentException, InvocationTargetException
		StringBuffer bs = new StringBuffer("com.dabai.proxy.");	//包名
		bs.append(className);
		Class c = null;
		Object obj = null;		//定义返回参数
		try{
			c = Class.forName(bs.toString());
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		Method[] methods = c.getMethods();	//得到方法名
		for(Method m:methods){
			if(m.toString().contains(methodName)){
				try {
					obj = (m.invoke(c.newInstance(),params));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return obj;
	}
	
	/**javaBean里面调用getXxx方法，setXX方法
	 * 
	 * @param	obj	实例化的类	
	 * @param	className	实例化类名	
	 * @param	params	传递setXX方法参数值	
	 * @param 	keyword		setXX的XX关键字
	 * @return	getXxx方法返回值集合
	 * @throw
	 * */
	public static Object ExecuteMethod(Object obj,String className,Object params,String keyWord){
		StringBuffer sb = new StringBuffer("com.dabai.vo.");
		sb.append(className);
		Class c = null;
		try {
			c = Class.forName(sb.toString());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Method[] methods = c.getMethods();
		Object all = null;
		for(Method m:methods){
			if(m.toString().contains("get"+keyWord)&&params==null){
				try {
					all = m.invoke(obj,null);
				} catch (IllegalArgumentException
					| InvocationTargetException  | IllegalAccessException e) {
						e.printStackTrace();
				}
			}else if(m.toString().contains("set"+keyWord)&&params!=null){
				try {
					m.invoke(obj,params);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return all;
	}
	
}
