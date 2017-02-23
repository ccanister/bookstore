package com.dabai.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dabai.config.valueConfig;
import com.dabai.factory.BookFactory;
import com.dabai.factory.cartFactory;
import com.dabai.method.commonReflect;
import com.dabai.vo.book;
import com.dabai.vo.cartItem;
import com.dabai.vo.carts;
import com.dabai.vo.user;
import com.google.gson.Gson;

/**
 * Servlet implementation class BuyServlet
 */
@WebServlet(name="BuyServlet",urlPatterns="/client/BuyServlet")
public class BuyServlet extends HttpServlet {

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method!=null){
			if(method.equals("addCart")){
				this.addCart(request,response);
			} else if(method.equals("showCart")){
				this.showCart(request, response);
			} 
		}
		
	}
	
	/**
	 * 将商品加入购物车，如果已经存在，则直接加上数量
	 * */
	@SuppressWarnings("unchecked")
	private void addCart(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//验证user的存在
		PrintWriter pw =  response.getWriter();
		HttpSession ses = request.getSession();
		if(ses.getAttribute("user")==null){
			pw.println("<script language = javascript>alert(\"请先登录 \"),window.open('userlogin.jsp','_top')</script>");
		}
		carts cart ;
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
 		try {
			cart = this.getCart(request);	//取出该用户购物车信息，若数据库中不存在则创建相应数据
			if(this.findSameBook(cart.getCartItems(),bookId)){		//若该用户购物车已经存在该书本，则直接修改数量书本
				cartFactory.getInstance().modQuantity(quantity, bookId);
			} else {	//否则创建相应书本数据 
				cartItem cartitem = this.getCartItem(bookId,quantity,cart);
				cart.getCartItems().add(cartitem);
				cartFactory.getInstance().addCartItem(cartitem);
			}
			ses.setAttribute("carts",cart);
			pw.println("<script language=javascript>window.open('../index.jsp','_top')</script>");
         } catch (SQLException e) {
         		pw.println("<script language = javascript>alert(\"添加失败\")</script>");
		}
		
	}
	
	//将书本 信息传入cartitem
	private cartItem getCartItem(int bookId,int quantity,carts cart) throws SQLException {
		book bk = BookFactory.getInstance().findById(bookId);
		Object[] params = new Object[]{bookId,quantity,bk.getPrice()*quantity,cart.getId()};
		cartItem cartitem = new cartItem();
		for(int i=0; i<valueConfig.CARTITEM_VALUE.length; i++){
			commonReflect.ExecuteMethod(cartitem, "cartItem", params[i], valueConfig.CARTITEM_VALUE[i]);
		}
		return cartitem;
	}

	//在购物车里查看是够已经存在该书本
	private boolean findSameBook(Set<cartItem> cartitemSet,int bookId){
		boolean flag = false;
		Iterator<cartItem> cartitemit = cartitemSet.iterator();
		while(cartitemit.hasNext()){
			cartItem cartitem = cartitemit.next();
			if(cartitem.getBookId()==bookId){
				flag = true;
				break;
			}
		}
		
		return flag;
	}
	
	public carts getCart(HttpServletRequest request) throws SQLException{
		carts cart;
		HttpSession ses = request.getSession();
		if(ses.getAttribute("carts")==null){
			int userId = ((user)ses.getAttribute("user")).getId();
			int cartId = cartFactory.getInstance().getCartId(userId);
			cart = new carts();
			cart.setCartItems(new HashSet(cartFactory.getInstance().getAll(cartId)));
			cart.setId(cartId);
			ses.setAttribute("cartState", "1");
		}else{
			cart = (carts)ses.getAttribute("carts");
			ses.setAttribute("cartState", "1");
		}
		
		return cart;
	}
	
	/**
	 * 显示购物车信息
	 * */
	private void showCart(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession ses = request.getSession();
		PrintWriter pw = response.getWriter();
		user user = (user) ses.getAttribute("user");
		List<cartItem> cartitemlist=null;
		try {
			int cartId = cartFactory.getInstance().getCartId(user.getId());
			cartitemlist = cartFactory.getInstance().getAll(cartId);
			carts cart = new carts();
			cart.setCartItems(new HashSet(cartitemlist));cart.setId(cartId);
			Gson gson = new Gson();
			String cartitems = gson.toJson(cartitemlist);
			ses.setAttribute("cartitems",cartitems);
			ses.setAttribute("carts",cart);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(cartitemlist.size()==0){
			pw.println("<script language=javascript>alert('购物车为空！');window.open('../index.jsp');</script>");
		} else {
			pw.println("<script language=javascript>window.open('../client/cartList.jsp','_top');</script>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request,response);
	}
	
}


