package com.dabai.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.dabai.factory.UserFactory;
import com.dabai.vo.user;
import com.google.gson.Gson;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet(name="UserServlet",urlPatterns="/client/UserServlet")
public class UserServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equalsIgnoreCase("userLogin")){
			this.userLogin(request,response);
		}else if(method.equalsIgnoreCase("userRegister")){
			this.userRegister(request,response);
		}else if(method.equalsIgnoreCase("nologin")){
			this.nologin(request,response);
		}else if(method.equalsIgnoreCase("isExist")){
			this.isExist(request,response);
		}
		
	}

	private boolean isExist(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		boolean flag = false;
		String obj = "["+request.getParameter("data")+"]";
		JSONObject arr = JSONArray.fromObject(obj).getJSONObject(0);
		String username = (String) arr.get("username");
		try {
			if(UserFactory.getInstance().findByName(username) == null){
				System.out.println("no");
				response.getWriter().print("可以使用");
				flag = true;
			} else {
				System.out.println("yes");
				flag = false;
				response.getWriter().print("此用户名已被占用");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
		
	}

	private void nologin(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession ses = request.getSession();
		ses.invalidate();
		PrintWriter pw = response.getWriter();
		pw.println("<script language=javascript>alert('注销成功');window.open('../index.jsp','_top')</script>");
	}

	private void userRegister(HttpServletRequest request,HttpServletResponse response ) 
			throws IOException, ServletException {
		HttpSession ses = request.getSession();
		PrintWriter pw = response.getWriter();
		user user = (user)request.getAttribute("#user");
		try {
			if(UserFactory.getInstance().addUser(user)){
				ses.setAttribute("user", user);
				pw.println("<script language=javascript>alert('注册成功');</script>");	//Todoalert事件
				request.getRequestDispatcher("userlogin.jsp").forward(request, response);;
			} else{
				pw.println("<script language=javascript>alert('注册失败');</script>");
				request.getRequestDispatcher("register.jsp").forward(request, response);;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
   

	private void userLogin(HttpServletRequest request,HttpServletResponse response) throws IOException {
		HttpSession ses = request.getSession();
		String username = "%"+request.getParameter("Username")+"%";
		try {
			user use = UserFactory.getInstance().findByName(username);
			if(use!=null&&use.getPassword().equals(request.getParameter("Password"))){
				ses.setAttribute("user",use);
				response.sendRedirect("../index.jsp");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}
