package com.dabai.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dabai.config.valueConfig;
import com.dabai.factory.OrdersFactory;
import com.dabai.factory.cartFactory;
import com.dabai.method.commonMethod;
import com.dabai.method.commonReflect;
import com.dabai.vo.OrderManager;
import com.dabai.vo.Orders;
import com.dabai.vo.cartItem;
import com.dabai.vo.carts;
import com.dabai.vo.orderItem;
import com.dabai.vo.user;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet(name="OrderServlet",urlPatterns="/client/OrderServlet")
public class OrderServlet extends HttpServlet {
	private PrintWriter pw;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method!=null){
			if(method.equalsIgnoreCase("getOrdersManager")){
				this.getOrdersManager(request,response);
			}else if(method.equalsIgnoreCase("changeState")){
				this.changeState(request,response);
			}else if(method.equalsIgnoreCase("showCarts")){
				this.doPost(request, response);
			} else if(method.equalsIgnoreCase("getuserOrder")) {
				this.getuserOrder(request,response);
			} else if(method.equalsIgnoreCase("getOrderitem")) {
				this.getOrderitem(request,response);
			}
		}
		
	}

	private void getOrderitem(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession ses = request.getSession();
		String orderGson = request.getParameter("order");
		Gson gson = new Gson();
		Orders order = gson.fromJson(orderGson,Orders.class);
		List<orderItem> orderitemlist = order.getOrderItemlist();
		ses.setAttribute("orderitems", orderitemlist);
	}


	private void getuserOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<String> orderarr = this.getOrderId(request);	//获得该用户所有订单编号
		List<Orders> orderlist = this.setOrderAndOrderitem(orderarr);	//取得用户所有订单，并存入javabean中
		Gson gson = new Gson();
		String orderGson = gson.toJson(orderlist);
		request.setAttribute("orders",orderlist);
		request.setAttribute("orderGson",orderGson);
		request.getRequestDispatcher("getOrdersList.jsp").forward(request, response);
	}


	private List<Orders> setOrderAndOrderitem(List<String> orderarr) {
		List<Orders> orderlist = new ArrayList<Orders>();
		Iterator<String> orderit = orderarr.iterator();
		try {
			while(orderit.hasNext()){
				String orderId = orderit.next();
				Orders order = OrdersFactory.getInstance().getOrderById(orderId);
				order.setOrderItemlist(OrdersFactory.getInstance().getorderitemList(orderId));
				orderlist.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderlist;
	}


	private List<String> getOrderId(HttpServletRequest request) {
		List<String> orderarr = null;
		HttpSession ses = request.getSession();
		int userId = ((user)ses.getAttribute("user")).getId();
		try {
			orderarr = OrdersFactory.getInstance().getOrderByUserid(userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderarr;
	}


	private void changeState(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String order_id = request.getParameter("id");
		boolean flag = false;
		PrintWriter pw = response.getWriter();
		try {
			if(OrdersFactory.getInstance().changeState(order_id))
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(flag){
			pw.println("<script language=javascript>alert('已发货');</script>");
			this.getOrdersManager(request, response);
		}
	}

	private void getOrdersManager(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		Set<OrderManager> ordermans = null;
		try {
			ordermans = OrdersFactory.getInstance().getOrderSet();
		} catch (SQLException e) {
			e.printStackTrace(); 
		}
		Gson gson = new Gson();
		String state = request.getParameter("state")==null?"1":request.getParameter("state");
		request.setAttribute("state",state);
		if(state.equals("1")){
			Set<OrderManager> noordermans = this.noAlready(ordermans);
			String noordermanGson = gson.toJson(noordermans);
			request.setAttribute("noordermans", noordermans);
			request.setAttribute("noordermanGson",noordermanGson);
			request.getRequestDispatcher("../manager/deliverOrder1.jsp").forward(request, response);
		} else {
			Set<OrderManager> alreadyordermans = this.getAlready(ordermans);
			String alreadyordermansGson = gson.toJson(alreadyordermans);
			request.setAttribute("alreadyordermans", alreadyordermans);
			request.setAttribute("alreadyordermansGson",alreadyordermansGson);
			request.getRequestDispatcher("../manager/deliverOrder2.jsp").forward(request, response);
		}
		
	}
	
	private Set<OrderManager> noAlready(Set<OrderManager> ordermans) {
		Iterator<OrderManager> ordermanit = ordermans.iterator();
		Set<OrderManager> noOm = new HashSet<OrderManager>();
		while(ordermanit.hasNext()){
			OrderManager om = ordermanit.next();
			List<Orders> orderslist = om.getOrderslist();
			Iterator<Orders> ordersit = orderslist.iterator();
			while(ordersit.hasNext()){
				Orders order = ordersit.next();
				if(order.getState()==2){
					ordersit.remove();
				}
			}
			om.setOrderslist(orderslist);
			noOm.add(om);
		}
		
		
		return noOm;
	}

	private Set<OrderManager> getAlready(Set<OrderManager> ordermans) {
		Iterator<OrderManager> ordermanit = ordermans.iterator();
		Set<OrderManager> AlreadyOm = new HashSet<OrderManager>();
		while(ordermanit.hasNext()){
			OrderManager om = ordermanit.next();
			List<Orders> orderslist = om.getOrderslist();
			Iterator<Orders> ordersit = orderslist.iterator();
			while(ordersit.hasNext()){
				Orders order = ordersit.next();
				if(order.getState()==1){
					ordersit.remove();
				}
			}
			om.setOrderslist(orderslist);
			AlreadyOm.add(om);
		}
		
		
		return AlreadyOm;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if(method.equals("insertOrder")){
			if(request.getParameter("books")!=null)
				this.insertOrder(request,response);
		} else if(method.equals("getOrderitem")) {
			this.getOrderitem(request,response);
		}
		
	}

	/**
	 * 购物车的表单提交后，插入该项订单
	 * @throws ServletException 
	 * */
	private void insertOrder(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
		Orders orders = new Orders();
		//获取order各个属性
		Object[] params = this.getOredrParams(orders,request);
		for(int i=0;i<params.length;i++)
			commonReflect.ExecuteMethod(orders, "Orders", params[i], valueConfig.ORDERS_VALUE[i]);
		try {
			if(OrdersFactory.getInstance().addOrder(orders)){	//若插入总订单成功后，则清理该用户内购物车该书本信息
				this.insertOrderitem(orders,request,response);	
				this.delCart(orders,request,response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * 
	 * */
	private void delCart(Orders orders, HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException, ServletException {
		List<orderItem> orderitemlist = orders.getOrderItemlist();
		PrintWriter pw = response.getWriter();
		HttpSession ses = request.getSession();
		int userId = ((user)ses.getAttribute("user")).getId();
		int cartId = cartFactory.getInstance().getCartId(userId);
		Iterator<orderItem> orderitemit = orderitemlist.iterator();
		while(orderitemit.hasNext()){
			orderItem orderitem = orderitemit.next();
			int bookId = orderitem.getBookId();
			if(cartFactory.getInstance().getCartItem(bookId)!=null){
				cartItem cartitem = cartFactory.getInstance().getCartItem(bookId);
				int quantity = orderitem.getQuantity() - cartitem.getQuantity();
				if(quantity==0){
					cartFactory.getInstance().delCartItem(bookId);
				} else {
					cartFactory.getInstance().modQuantity(quantity, bookId);
				}
			}
		}
		if(cartFactory.getInstance().getAll(cartId).size()==0){
			ses.removeAttribute("carts");
			ses.removeAttribute("cartState");
			//ses.setAttribute("cartState","0");
		} else {
			this.setCartAttr(cartId,ses);
			ses.setAttribute("cartState","1");
		}
	}


	private void setCartAttr(int cartId,HttpSession ses) throws SQLException {
		carts cart = new carts();
		cart.setCartItems(new HashSet(cartFactory.getInstance().getAll(cartId)));
		cart.setId(cartId);
		List<cartItem> cartitemlist = cartFactory.getInstance().getAll(cartId);
		Gson gson = new Gson();
		String cartitems = gson.toJson(cartitemlist);
		ses.setAttribute("cartitems",cartitems);
		ses.setAttribute("carts",cart);
	}


	private Object[] getOredrParams(Orders orders,HttpServletRequest request) {
		HttpSession ses = request.getSession();
		user user =  (user)ses.getAttribute("user");
		int userId = user.getId();	
		String orderId = commonMethod.makeUUID();
		String ordertime =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());	//订单时间
		int state = 1;	//发货状态
		String books = request.getParameter("books");
		Gson gson = new Gson();  
		List<orderItem> orderitemlist = gson.fromJson(books, new TypeToken<List<orderItem>>() {  }.getType());  
		Iterator<orderItem> orderitemit = orderitemlist.iterator();
		double totalPrice=0;
		while(orderitemit.hasNext()){
			orderItem orderitem = orderitemit.next();
			if(orderitem==null){
				orderitemit.remove();
				continue;
			}
			totalPrice += orderitem.getPrices();
		}
		Object[] params = new Object[5];
		params[0]=orderId;params[1]=ordertime;params[2]=totalPrice;params[3]=state;params[4]=userId;
		orders.setOrderItemlist(orderitemlist);
		
		return params;
	}

	/**
	 * 插入每笔书本的订单 
	 * @params	orders	总订单
	 * **/
	private void insertOrderitem(Orders orders,HttpServletRequest request,HttpServletResponse response) throws SQLException {
		List<orderItem> orderitemlist = orders.getOrderItemlist();
		Iterator<orderItem> orderitemit = orderitemlist.iterator();
		while(orderitemit.hasNext()){
			orderItem orderitem = orderitemit.next();
			if(orderitem==null)
				orderitem = orderitemit.next();
			orderitem.setOrderId(orders.getId());
			OrdersFactory.getInstance().addOrderItem(orderitem);
		}
	}

}
