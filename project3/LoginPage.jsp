<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>

	<form action="LoginReCaptcha" method="post">

		Email: <input type="email" name="username" required> <br>
		Password: <input type="password" name="password" required> <br>
     <div class="g-recaptcha"
             data-sitekey="6Ldkzx4TAAAAAN5vzjKVOxnXooAWOPrRARyItGwn"></div>

		<input type="submit" value="Login">
	</form>

</body>
</html>
