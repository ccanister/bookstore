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

		//被拦截的url和javabean的对应关系，key表示被拦截的url
	//value表示封装请求参数的javabean
	Map <String,String> actions = new HashMap<String,String>();
	private String formName;
	
	//从过滤器初始化参数读取
	//如果初始化参数不在该方法返回的list对象中，则该参数不是javabean
	//而是普通参数，可以覆盖该方法，并添加新的普通参数名称
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
		//读取所有过滤器初始化参数值
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
	
	//对字符串进行解码
	protected String decode(HttpServletRequest request,String s) throws UnsupportedEncodingException{
		String encoding = request.getCharacterEncoding();
		if(encoding == null)
			encoding = "utf-8";
		s = new String(s.getBytes(encoding),"utf-8");
		return s;
	}
	//如果javabean中属性数据类型无法处理，则调用该方法，其他的过滤器可以继承
	//formfilr类，并覆盖该方法以处理这些属性
	protected void doMethod(Object form,Object paramValue,Method method){}
	
	//返回指定请求参数值，该方法也可以在formfilter的子类被覆盖
	//以使用其他方法获得参数值
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
		//获得被拦截的web资源，该路径可看一个动作
		String className = actions.get(action);
		//封装请求参数的javabean对象实力保存在request域中的属性名称
		formName = this.getFilterParamNames().get(action);
		//获得与表单对应的javabean类名
		if(className!=null){
			try{
				Object form = Class.forName(className).newInstance();
				//创建javabean实例对象
				List<Method> setterMethods = new ArrayList<Method>();
				//保存javabean中所有setter方法的method对象
				Method[] methods = form.getClass().getMethods();
				//获得javabeana中所有方法的method对象
				for(Method method: methods){
					if(method.getName().startsWith("set")&&method.getParameterTypes().length==1){
						setterMethods.add(method);
					}
				}
				
				//为javabean中的属性寻找预期同名的请求参数
				for(Method method:setterMethods){
					Class paramType = method.getParameterTypes()[0];
					//获得setter方法的参数类型calss对象
					Object paramValue = this.getParamValue(myRequest,this.getProperty(method.getName()));
					//获得与setter方法对应的属性同名请求参数值
					//如果不存在同名的请求参数，则继续处理下一个setter方法
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
