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
 * ͨ��������ƻ�ȡ��������Ȼ����ø÷���
 * */
public class commonReflect {
	
	/**
	 * @params className �������õ���	methodName  ����		Object... params ����invoke�ĵڶ������������������Ĳ�����
	 * @return	��������ֵ
	 * @throw	ClassNotFoundException,IllegalAccessException, IllegalArgumentException, InvocationTargetException
	 * */
	public  static Object ReflectTest(String className,String methodName,Object... params) {// ����쳣�Ĵ���
											//ClassNotFoundException,IllegalAccessException, IllegalArgumentException, InvocationTargetException
		StringBuffer bs = new StringBuffer("com.dabai.proxy.");	//����
		bs.append(className);
		Class c = null;
		Object obj = null;		//���巵�ز���
		try{
			c = Class.forName(bs.toString());
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		Method[] methods = c.getMethods();	//�õ�������
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
	
	/**javaBean�������getXxx������setXX����
	 * 
	 * @param	obj	ʵ��������	
	 * @param	className	ʵ��������	
	 * @param	params	����setXX��������ֵ	
	 * @param 	keyword		setXX��XX�ؼ���
	 * @return	getXxx��������ֵ����
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
