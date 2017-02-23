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
import javax.servlet.http.HttpSession;

import com.dabai.daoImpl.pageService;
import com.dabai.factory.BookFactory;
import com.dabai.vo.book;
import com.dabai.vo.page;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet(name="IndexServlet",urlPatterns="/client/IndexServlet")
public class IndexServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method!=null){
			if(method.equalsIgnoreCase("getBookList"))
				this.getBookList(request,response);
		}
	}
	/**
	 * 取得该页码下的书本信息
	 * */
	private void getBookList(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses = request.getSession();
		int categoryId = this.getCategoryId(request);
		int pagenum = this.getPageNum(request);
		page bookpage = null;List<book> bks = null;
		try {
			bookpage = this.setBookPage(ses,pagenum,categoryId);
 			bks = BookFactory.getInstance().getBookList("",bookpage.getStartindex(),categoryId);
			if(bks.size()==0){
				System.out.println("dd");
				PrintWriter pw = response.getWriter();
				pw.print("暂时无书本");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("bks",bks.toArray());
		ses.setAttribute("bookpage",bookpage);
		ses.setAttribute("categoryId", categoryId);
		request.getRequestDispatcher("/client/getBookList.jsp").forward(request,response);
	}

	private int getCategoryId(HttpServletRequest request) {
		HttpSession ses = request.getSession();
		int categoryId;
		if(request.getParameter("categoryId")==null){
			categoryId = ses.getAttribute("categoryId")==null?-1:
				(Integer)(ses.getAttribute("categoryId"));
		}else{
			categoryId = Integer.parseInt(request.getParameter("categoryId"));
		}
		
		return categoryId;
	}
	//在servletcontext下保存页码有关信息
	private page setBookPage(HttpSession ses,int pagenum,int categoryId) throws SQLException {
		page pages = null;
		if(ses.getAttribute("bookpage") == null || categoryId != (Integer)ses.getAttribute("categoryId")){
			pages = new page(1,BookFactory.getInstance().getBookList("",-1,categoryId).size());
		} else {
			pages = (page) ses.getAttribute("bookpage");
		}
		new pageService(pagenum,pages).initPage();
		
		return pages;
	}
	//获得页码，若无页码参数则默认为1
	private int getPageNum(HttpServletRequest request) {
		ServletContext app = this.getServletContext();
		int pagenum = 1;
		if(request.getParameter("pagenum")==null){
			if(app.getAttribute("bookpage")!=null)
				pagenum = ((page)(app.getAttribute("bookpage"))).getPagenum();
		} else {
			pagenum = Integer.parseInt(request.getParameter("pagenum"));
		}
		
		return pagenum;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
