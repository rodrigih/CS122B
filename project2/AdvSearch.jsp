<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<%@ include file="header.html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<article>
<div>
	<form action="AdvSearchServlet">
		Title:<br> <input type="text" name="title"> <br>
		Year:<br> <input type="text" name="year"> <br>
		Director:<br> <input type="text" name="director"> <br>
		Star:<br> <input type="text" name="name"> <br>
		<br> <input type="submit">
	</form>
	</div>
	</article>
</body>
</html>