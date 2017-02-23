package com.dabai.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet Filter implementation class EncodingFilter
 */

public class EncodingFilter implements Filter {
	private String charset;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
		this.charset = config.getInitParameter("charset");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 response.setContentType("text/html;charset="+charset);
		 HttpServletRequest req = (HttpServletRequest)request;
		 HttpServletResponse res = (HttpServletResponse)response;
		if(charset!=null){ 
			req.setCharacterEncoding(charset);
			res.setCharacterEncoding(charset);
		}
		chain.doFilter(request, response);
	}
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}
}
