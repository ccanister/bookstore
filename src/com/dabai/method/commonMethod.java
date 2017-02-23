package com.dabai.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.dabai.factory.dataBaseFactory;
/**
 * Ѱ��id
 * */

public class commonMethod {
	/**
	 * @param	table ����	className	����
	 * @return	id
	 * @throw	SQLException
	 * */
	public static <c> String findId(String table,String className) throws SQLException{
		className = "com.dabai.vo." + className;
		Method m = null;
		Class c = null;
		try {
			c = Class.forName(className);
			m = c.getMethod("getId");
		} catch (NoSuchMethodException | SecurityException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		String id = null;
		List<c> allId;
		String sql = "select id from " + table;
		QueryRunner runner = new QueryRunner(dataBaseFactory.getDataSource());
		allId = (List<c>) runner.query(sql, new BeanListHandler(c));
		if(allId.size()==0){
			id = "1";
		}else{
			int[] ids = new int[allId.size()];	
			int i =0;		//����id����
			Iterator<c> it = allId.iterator();
			while(it.hasNext()){
				try {
					ids[i] = Integer.parseInt((String) m.invoke(it.next()));
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
				}
				i++;
			}
			id = sortId(ids);
		}
		return id;
	}

	private static String sortId(int[] ids) {
		int max = 1000;
		int id = 0;
		if(ids.length!=0)
			id = ids[ids.length-1];
		int key = 0;
		Arrays.sort(ids);
		for(int i=0; i<ids.length; i++){
			if((key=Arrays.binarySearch(ids,ids[i]+1))>-1){
				continue;
			}else{
				id = ids[-(key+1)-1] + 1;
				break;
			}
			
		}
		return String.valueOf(id);
	}
	
	/**
	 * ��һ���û����ܶ������������id��
	 * ��ʹ��UUID��ȫ��Ψһ��ʶ��,��ָ��һ̨���������ɵ����֣�����֤����ͬһʱ���е����л�������Ψһ�ġ�
	 * */
	public static String makeUUID(){
		return UUID.randomUUID().toString();
		
	}

}
