package com.dabai.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dabai.method.commonReflect;
import com.dabai.vo.administor;

/**
 * Servlet implementation class BussinessServlet
 */
@WebServlet("/adminServlet")
public class adminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String method = request.getParameter("method"); 
		if(method.equals("findLogin")){
			this.findLogin(request,response,method);
		}
		
	}

	public void findLogin(HttpServletRequest request, HttpServletResponse response,String method) throws IOException{
		PrintWriter pw = response.getWriter();
		administor admin = (administor) request.getAttribute("#admin");	//过滤器中获得admin对象
		try {
			if((boolean)(commonReflect.ReflectTest("AdminDAOProxy",method,admin))){
				HttpSession ses = request.getSession();
				ses.setAttribute("admin",admin);
				pw.print("<script language=javascript>window.open('admin_2.jsp','_top');</script>");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
