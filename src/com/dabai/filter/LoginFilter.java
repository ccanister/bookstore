package com.dabai.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/LoginFilter")
public class LoginFilter implements Filter {

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 * 先检验地址栏，在减压session对象
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession ses = req.getSession(false);
		String url = req.getRequestURI();
		boolean flag = true;
		response.setContentType("text/html;charset=utf-8");
		if(!(url.contains("head.jsp")||url.contains("left.jsp")||url.contains("right.jsp"))){
			if(ses==null||ses.getAttribute("admin")==null){
				response.getWriter().println("<script language=\"javascript\">alert('请先登录');if(window.opener==null)"
						+ "{window.top.location.href=\"../adminLogin.jsp\";}else{window.opener.top.location.href=\"../login.jsp\";"
						+ "window.close();}</script>");  
				System.out.println("hh");
			}
		}
		chain.doFilter(request,response);	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
