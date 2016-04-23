<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.khcart.*"%>
<%-- <%@ include file ="header.html" %>
 --%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Checkout!</title>
</head>
<body>
	<%
		HttpSession cartSession = request.getSession(false);
		if (cartSession == null) {
	%>
	
	<center><h1>CART IS EMPTY session not here</h1></center>
	<%
		return;
		} else {
			Cart shoppingCart;
			shoppingCart = (Cart) cartSession.getAttribute("cart");
			if (shoppingCart == null) {
				%>
				<h1>CART IS EMPTY There is nothing here</h1>
				<%
				return;
			}if(shoppingCart.getSize() == 0){
				%>
				<h1>CART IS EMPTY</h1>
				<%
				return;
			}
			shoppingCart.display(out);
		}
	%>

</body>
</html>