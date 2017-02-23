package com.dabai.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dabai.daoImpl.pageService;
import com.dabai.factory.CategoryFactory;
import com.dabai.vo.category;
import com.dabai.vo.page;

/**
 * Servlet implementation class CategoryServlet
 */
@WebServlet("/CategoryServlet")
public class CategoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void getCategoryList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		List<category> cats =  null;page pages = null;
		String keyWord = request.getParameter("key") == null?"":request.getParameter("key");
		int pagenum = request.getParameter("pagenum")==null?1:Integer.parseInt(request.getParameter("pagenum"));
		ServletContext app = this.getServletContext();
		try{
			if(app.getAttribute("bookpage") == null){
				pages = new page(pagenum,CategoryFactory.getInstance().getCategorySize());
			} else {
				pages = (page) app.getAttribute("bookpage");
			}
			new pageService(pagenum,pages).initPage();
			cats = CategoryFactory.getInstance().getCategoryList(keyWord,pages.getStartindex(),false);
		}catch(SQLException e){
			e.printStackTrace();
		}
		request.setAttribute("cats",cats);
		app.setAttribute("categorypages",pages);
		request.getRequestDispatcher("/manager/listCategory.jsp").forward(request,response);
	}

	public void addCategory(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		category cat = (category)request.getAttribute("#category");
		if(request.getParameter("iscommond")!=null)
			cat.setIscommond(true);
		try {
			if(CategoryFactory.getInstance().addCategory(cat)){
				pw.println("<script language = javascript>alert(\"Ìí¼Ó³É¹¦\")</script>");
				response.sendRedirect("addCategory.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("addCategory")){
			this.addCategory(request,response);
		}else if(method.equals("getCategoryList")){
			this.getCategoryList(request,response);
		}
	}

}
