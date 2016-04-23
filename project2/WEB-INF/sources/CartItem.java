package com.khcart;

public class CartItem {
	private int movieID;
	private String name;
	private int quantity;
	private float price;
	
	public CartItem(int movieID, String name, float price, int quantity){
//		this.movieID = movieID;
		this.name = name;
		this.quantity = quantity;
		this.price = price;
	}
	
	public int getID(){
		return movieID;
	}
//	
	public String getName(){
		return name;
	}
	public int getQuantity(){
		return quantity;
	}
	
	public float getPrice(){
		return price;
	}
	
	public float getTotal(){
		return quantity *price;
	}
	
	public void setQuantity(int quantity){
		this.quantity = quantity;
	}
	public void incrQuantity(){
		this.quantity++;
	}
	
	
}
