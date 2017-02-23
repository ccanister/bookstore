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
	 * ����Ʒ���빺�ﳵ������Ѿ����ڣ���ֱ�Ӽ�������
	 * */
	@SuppressWarnings("unchecked")
	private void addCart(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//��֤user�Ĵ���
		PrintWriter pw =  response.getWriter();
		HttpSession ses = request.getSession();
		if(ses.getAttribute("user")==null){
			pw.println("<script language = javascript>alert(\"���ȵ�¼ \"),window.open('userlogin.jsp','_top')</script>");
		}
		carts cart ;
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
 		try {
			cart = this.getCart(request);	//ȡ�����û����ﳵ��Ϣ�������ݿ��в������򴴽���Ӧ����
			if(this.findSameBook(cart.getCartItems(),bookId)){		//�����û����ﳵ�Ѿ����ڸ��鱾����ֱ���޸������鱾
				cartFactory.getInstance().modQuantity(quantity, bookId);
			} else {	//���򴴽���Ӧ�鱾���� 
				cartItem cartitem = this.getCartItem(bookId,quantity,cart);
				cart.getCartItems().add(cartitem);
				cartFactory.getInstance().addCartItem(cartitem);
			}
			ses.setAttribute("carts",cart);
			pw.println("<script language=javascript>window.open('../index.jsp','_top')</script>");
         } catch (SQLException e) {
         		pw.println("<script language = javascript>alert(\"���ʧ��\")</script>");
		}
		
	}
	
	//���鱾 ��Ϣ����cartitem
	private cartItem getCartItem(int bookId,int quantity,carts cart) throws SQLException {
		book bk = BookFactory.getInstance().findById(bookId);
		Object[] params = new Object[]{bookId,quantity,bk.getPrice()*quantity,cart.getId()};
		cartItem cartitem = new cartItem();
		for(int i=0; i<valueConfig.CARTITEM_VALUE.length; i++){
			commonReflect.ExecuteMethod(cartitem, "cartItem", params[i], valueConfig.CARTITEM_VALUE[i]);
		}
		return cartitem;
	}

	//�ڹ��ﳵ��鿴�ǹ��Ѿ����ڸ��鱾
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
	 * ��ʾ���ﳵ��Ϣ
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
			pw.println("<script language=javascript>alert('���ﳵΪ�գ�');window.open('../index.jsp');</script>");
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


