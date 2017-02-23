package com.dabai.vo;

public class cartItem {
	private int id;
	private int bookId;
	private int quantity;
	private int cartId;
	private double prices;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartId) {
		this.cartId = cartId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setPrices(double prices) {
		this.prices = prices;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrices() {
		return prices;
	}
}
