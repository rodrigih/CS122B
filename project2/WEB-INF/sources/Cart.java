package com.khcart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspWriter;

public class Cart {
	// List<Movie> cart;
	java.text.DecimalFormat currency = new java.text.DecimalFormat("$ #,###,##0.00");
	List<CartItem> cart;
	int size = 0;
	float totalDue;

	public Cart() {
		cart = new ArrayList<CartItem>();
	}
	
	public void clear(){
		cart = new ArrayList<CartItem>();
		size = 0;
	}
	public List<CartItem> removeItem(CartItem item) {
		for(CartItem items : cart){
			if (item.getName().equals(items.getName())) {
				if(item.getQuantity() == items.getQuantity()){
					cart.remove(items);
					size--;
					return cart;
				}
				else{
					items.setQuantity(items.getQuantity()-item.getQuantity());
					return cart;
				}	
			}
		}
		return cart;
	}

	public List<CartItem> addToCart(CartItem item) {
		for (CartItem items : cart) {
			if (item.getName().equals(items.getName())) {
				items.incrQuantity();
				return cart;
			}
		}
		cart.add(item);
		size++;
		return cart;

	}

	public List<CartItem> returnCart() {
		return cart;
	}
	
	public float getTotal(){
		float total = 0;
		for(CartItem item : cart){
			total+=item.getTotal();
		}
		return total;
	}
	
	public int getSize(){
		return size;
	}

	public void checkoutDisplay(JspWriter out) throws IOException{
		out.println("<h2>Your Cart:</h2>");
		out.println("<table border=1>");
		// out.println("<tr><th>ID</th><th>Name</th><th>Price</th><th>Quantity</th><th>Total</th></tr>");
		out.println("<th>Movie Title</th><th>Movie ID</th><th>Price</th><th>Quantity</th><th>Total</th>");
		for (int i = 0; i < cart.size(); i++) {
//			out.println("<form action =\"RemoveItem\" method=\"GET\">");
			// CartItem item = (CartItem)cart.get(i);
			out.println("<tr><td>" + cart.get(i).getName() + "</td>" +
			"<td>"+cart.get(i).getID()+"</td>"+
			 "<td>"+currency.format(cart.get(i).getPrice())+"</td>"+
			 "<td>"+cart.get(i).getQuantity()+"</td>"+
			 "<td>"+currency.format(cart.get(i).getTotal())+"</td></tr>");

		}

		out.println("<tr><td colspan = 3>Total</td>" + "<td align=right>" + currency.format(getTotal())
				+ "</td></tr>");
		out.println("</table>");

	}
	public void display(JspWriter out) throws IOException {
		//
		// start the table and output the header row
		//
		out.println("<h3>Cart contents</h3>");
		out.println("<table border=1>");
		// out.println("<tr><th>ID</th><th>Name</th><th>Price</th><th>Quantity</th><th>Total</th></tr>");
		out.println("<th>Movie Title</th><th>Movie ID</th><th>Price</th><th>Quantity</th><th>Total</th>");

		//
		// output one item at a time from the cart, one item to a row table
		//
		for (int i = 0; i < cart.size(); i++) {
			out.println("<form action =\"RemoveItem\" method=\"GET\">");
			// CartItem item = (CartItem)cart.get(i);
			out.println("<tr><td>" + cart.get(i).getName() + "</td>" +
					"<td>"+cart.get(i).getID()+"</td>"+

			"<td>"+currency.format(cart.get(i).getPrice())+"</td>"+
			 "<td>"+cart.get(i).getQuantity()+"</td>"+
			 "<td>"+currency.format(cart.get(i).getTotal())+"</td>"+
			 

					"<td><input type=\"hidden\" name=\"removeName\" value=\""+cart.get(i).getName()+"\">"+
					"<input type=\"hidden\" name=\"id\" value=\""+cart.get(i).getID()+"\">"+
					"<input type=\"hidden\" name=\"removePrice\" value=\""+currency.format(cart.get(i).getPrice())+"\">"+
					"<input type=\"number\" name=\"removeQuantity\"value=1 "
					+ "min=\"1\" max=\""+cart.get(i).getQuantity()+"\"required>"+
					"<input type=\"submit\" value=\"Remove Item\" ></td></tr></form>");
//					"<td align=right>" + currency.format(cart.get(i).getPrice()) + "</td>" + "<td align=right>"
//					+ cart.get(i).getQuantity() + "</td>" + "<td align=right>" + currency.format(cart.get(i).getTotal())
//					+ "</td>" + "<td align=center><A href='removeItemFromCart.jsp?id=" + cart.get(i).getName()
//					+ "'>remove</A></TD></tr>");
			// total += item.price*item.quantity;
		}

		out.println("<tr><td colspan = 4>Total</td>" + "<td align=right>" + currency.format(getTotal())
				+ "</td></tr>");
		out.println("</table>");
//		out.println("<a href='checkOut.jsp'><input type='Button' value='Register'/></A><br/>");
		out.println("<form action=\"Checkout.jsp\" method=\"POST\">"
				+ "<input type=\"submit\" value=\"Checkout\"/> </form>");
//		<form action="Checkout.jsp" method="POST">
//		<input type="submit" value="Checkout"/>
//		</form>
	}
}
