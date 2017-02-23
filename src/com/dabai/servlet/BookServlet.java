package com.dabai.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.dabai.daoImpl.pageService;
import com.dabai.factory.BookFactory;
import com.dabai.factory.CategoryFactory;
import com.dabai.vo.book;
import com.dabai.vo.category;
import com.dabai.vo.page;
import com.dabai.vo.token;

/**
 * Servlet implementation class BookServlet
 */

public class BookServlet extends HttpServlet {

	public static int count = 0;
	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("getBookList")){
			this.getBookList(request,response);
		} else if(method.equals("detBook")){
				this.detBook(request,response);
		} else if(method.equals("modBook")){
				this.modBook(request,response);
		} else if(method.equalsIgnoreCase("getBookById")){
			this.getBookById(request,response);
		}
	}

	public void getBookById(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		book bk;
		try {
			bk = BookFactory.getInstance().findById(bookId);
			request.setAttribute("book", bk);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("../client/bookDescription.jsp").forward(request, response);
	}

	public void detBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		boolean flag = false;
		int id = Integer.parseInt(request.getParameter("bookId"));		
		try {
			if(BookFactory.getInstance().detBook(id))
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.getBookList(request, response);
		return;
	}

	public void modBook(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		book bk = null;
		int id = Integer.parseInt(request.getParameter("bookId"));
		try {
			if(this.ExatcId(id))
				bk = BookFactory.getInstance().findById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("bk",bk);
		request.getRequestDispatcher("addBook.jsp").forward(request, response);
	}

	public List<book> getBookList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<book> bks = null;page pages = null;
		int pagenum = request.getParameter("pagenum")==null?1:Integer.parseInt(request.getParameter("pagenum"));
		String keyWord = request.getParameter("keyWord")==null?"":request.getParameter("keyWord");
		ServletContext app = this.getServletContext();
		try { 
			if(app.getAttribute("bookpage") == null){
				pages = new page(pagenum,BookFactory.getInstance().getBookList(keyWord, -1,-1).size());
			} else {
				pages = (page) app.getAttribute("bookpage");
			}
			new pageService(pagenum,pages).initPage();
			bks = BookFactory.getInstance().getBookList(keyWord,pages.getStartindex(),-1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("bks",bks.toArray());
		app.setAttribute("bookpages",pages);
		request.getRequestDispatcher("/manager/listBook.jsp").forward(request,response);
		
		return bks;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.addBook(request,response);
	}
	
	public void getCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			List<category> cats = CategoryFactory.getInstance().getCategoryList("", -1,false);
			request.setAttribute("categories",cats);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}


	public void addBook(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		book bk = (book) request.getAttribute("#book");
		token tk = token.getInstance();
		if(tk.isValid(bk.getToken(), request)){
			if(bk!=null){
				String imageName = this.addImage(bk.getFile().getFileStream(),bk.getFile().getFilename());
				bk.setImage(imageName);
				try {   
					if(BookFactory.getInstance().addBook(bk))
						response.getWriter().println("<script language=javascript>alert('添加成功')</script>");	//添加成功显示
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			this.getCategory(request, response);
			response.sendRedirect("../manager/addBook.jsp");
		} else{
			tk.savedToken(request);
		}
	}

	/**
	 * 将图片保存到相应路径
	 * @throws FileUploadException 
	 * */
	public String addImage(InputStream in,String ext) throws ServletException, IOException {
		String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + new Random().nextInt(1000) + "."+ext;
		String savepath = "e:/P project/image";
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(savepath + "\\" + fileName));
		BufferedInputStream bis = new BufferedInputStream(in);
		int len = 0;   
		byte buffer[] = new byte[1024];
		while((len = bis.read(buffer)) > 0){
			bos.write(buffer, 0, len);
		}
		//bos.close();
		//bis.close();
		return fileName;
	}


	/**检查id是否存在
	 * 
	 * @param	书本id
	 * @return	书本id是否存在结果
	 * @throw	SQLException
	 * */
	public boolean ExatcId(int id) throws SQLException {
		boolean flag = false;
		List<book> bks = BookFactory.getInstance().getBookList("",-1,-1);
		Iterator<book> it = bks.iterator();
		while(it.hasNext()){
			if(it.next().getId()==id){
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
//	@SuppressWarnings("unused")
//	public void syo(Object o) {
//		if(o.getClass().toString().equals("Map")){
//			Map map = (Map)(o);
//			Iterator it = map.entrySet().iterator();
//			while(it.hasNext()){
//				Map.Entry<String, String> entry = (Entry<String, String>) it.next();
//				System.out.println(entry.getValue());
//			}
//		}else if(o.getClass().toString().equals("List")){
//			List l = (List)(o);
//			Iterator it = l.iterator();
//			while(it.hasNext()){
//				System.out.println(it.next());
//			}
//		}else{
//			System.out.println(o);
//		}
//	}
}
