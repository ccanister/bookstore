package com.dabai.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class formFilter
 */
public class formFilter implements Filter {

		//�����ص�url��javabean�Ķ�Ӧ��ϵ��key��ʾ�����ص�url
	//value��ʾ��װ���������javabean
	Map <String,String> actions = new HashMap<String,String>();
	private String formName;
	
	//�ӹ�������ʼ��������ȡ
	//�����ʼ���������ڸ÷������ص�list�����У���ò�������javabean
	//������ͨ���������Ը��Ǹ÷�����������µ���ͨ��������
	protected Map<String, String> getFilterParamNames(){
		Map<String,String> paramNames = new HashMap<String,String>();
		paramNames.put("/client/UserServlet","#user");
		paramNames.put("/manager/CategoryServlet","#category");
		paramNames.put("/manager/BookServlet","#book");
		paramNames.put("/admin/adminServlet","#admin");
		return paramNames;
		
	}
	/** 
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("form");
		Enumeration names = fConfig.getInitParameterNames();
		//��ȡ���й�������ʼ������ֵ
		while(names.hasMoreElements()){
			String name = names.nextElement().toString();
			actions.put(name,fConfig.getInitParameter(name));
		}
	}
	protected String getProperty(String setter){
		String property = setter.substring(3);
		property = property.substring(0,1).toUpperCase()+property.substring(1);
		
		return property;
	}
	
	//���ַ������н���
	protected String decode(HttpServletRequest request,String s) throws UnsupportedEncodingException{
		String encoding = request.getCharacterEncoding();
		if(encoding == null)
			encoding = "utf-8";
		s = new String(s.getBytes(encoding),"utf-8");
		return s;
	}
	//���javabean���������������޷���������ø÷����������Ĺ��������Լ̳�
	//formfilr�࣬�����Ǹ÷����Դ�����Щ����
	protected void doMethod(Object form,Object paramValue,Method method){}
	
	//����ָ���������ֵ���÷���Ҳ������formfilter�����౻����
	//��ʹ������������ò���ֵ
	protected Object getParamValue(HttpServletRequest request,String name){
		return request.getParameter(name);
	}
    /**
     * Default constructor. 
     */
    public formFilter() {
    	
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest myRequest = (HttpServletRequest)request;
		String action = myRequest.getServletPath();
		//��ñ����ص�web��Դ����·���ɿ�һ������
		String className = actions.get(action);
		//��װ���������javabean����ʵ��������request���е���������
		formName = this.getFilterParamNames().get(action);
		//��������Ӧ��javabean����
		if(className!=null){
			try{
				Object form = Class.forName(className).newInstance();
				//����javabeanʵ������
				List<Method> setterMethods = new ArrayList<Method>();
				//����javabean������setter������method����
				Method[] methods = form.getClass().getMethods();
				//���javabeana�����з�����method����
				for(Method method: methods){
					if(method.getName().startsWith("set")&&method.getParameterTypes().length==1){
						setterMethods.add(method);
					}
				}
				
				//Ϊjavabean�е�����Ѱ��Ԥ��ͬ�����������
				for(Method method:setterMethods){
					Class paramType = method.getParameterTypes()[0];
					//���setter�����Ĳ�������calss����
					Object paramValue = this.getParamValue(myRequest,this.getProperty(method.getName()));
					//�����setter������Ӧ������ͬ���������ֵ
					//���������ͬ������������������������һ��setter����
					if(paramValue==null)
						continue;
					if(paramType == String.class){
						String value = decode(myRequest,paramValue.toString());
						method.invoke(form,value);
					} else if(paramType == int.class || paramType == Integer.class){
						int value = Integer.parseInt(decode(myRequest,paramValue.toString()));
						method.invoke(form,value);
					} else if(paramType == float.class || paramType == Float.class){
						float value = Float.parseFloat(decode(myRequest,paramValue.toString()));
						method.invoke(form,value);
					} else if(paramType == double.class || paramType == Double.class){
						double value = Double.parseDouble(decode(myRequest,paramValue.toString()));
						method.invoke(form,value);
					}else if(paramType == boolean.class || paramType == Boolean.class){
						method.invoke(form,true);
					} else if(paramType == java.util.Date.class ){
						DateFormat df = DateFormat.getDateInstance();
						java.util.Date value = df.parse(paramValue.toString());
						method.invoke(form,value);
					} else{
						doMethod(form, paramValue, method);
					}
				}
				request.setAttribute(formName,form);
				// pass the request along the filter chain
				chain.doFilter(request, response);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	

}
