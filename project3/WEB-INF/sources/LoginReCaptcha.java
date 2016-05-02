package com.khcart;

/* A servlet to display the contents of the MySQL movieDB database */

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginReCaptcha")
public class LoginReCaptcha extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginReCaptcha() {
		super();
		// TODO Auto-generated constructor stub
	}

	// Use http GET

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();

		PrintWriter out = response.getWriter();

		String email = request.getParameter("username");
		String password = request.getParameter("password");
		String query = "Select * FROM customers WHERE " + "email = '" + email + "' AND password = '" + password + "'";

		String query2 = "Select id FROM customers WHERE " + "email = '" + email + "' AND password = '" + password + "'";
		int customerID = 0;
		String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		// Verify CAPTCHA.
		boolean valid = VerifyUtils.verify(gRecaptchaResponse);
		Connection connection = DBConnection.connectToDatabase();
		ResultSet result;
		try {
			Statement select = connection.createStatement();
			result = select.executeQuery(query);
			if (!result.first() || !valid) {
				RequestDispatcher rd = request.getRequestDispatcher("/LoginPage.html");
				// PrintWriter out = response.getWriter();
				connection.close();
				out.println("<font color=blue>Invalid Login information or ReCaptcha Wrong!" + " Please try again!</font>");
				rd.forward(request, response);
				return;
				//// return;
			}
			// if (!valid) {
			// RequestDispatcher rd =
			// getServletContext().getRequestDispatcher("/LoginPage.html");
			// // errorString = "Captcha invalid!";
			// out.println("<HTML>" + "<HEAD><TITLE>" + "Login: Error" +
			// "</TITLE></HEAD>\n<BODY>"
			// + "<P>Recaptcha WRONG!!!! </P></BODY></HTML>");
			// rd.include(request, response);
			// return;
			// }
			else {
				result = select.executeQuery(query2);
				while (result.next())
					customerID = result.getInt("id");
				connection.close();
				session.setAttribute("user", email);
				session.setAttribute("pw", password);
				session.setAttribute("customer", customerID);
				response.sendRedirect("index.html");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		session.setAttribute("user", email);
//		session.setAttribute("pw", password);
//		session.setAttribute("customer", customerID);
		// setting session to expiry in 30 mins
		// session.setMaxInactiveInterval(60);
		// Cookie userName = new Cookie("user", email);
		// userName.setMaxAge(60);
		// response.addCookie(userName);

		// String gRecaptchaResponse =
		// request.getParameter("g-recaptcha-response");
		// System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
		// // Verify CAPTCHA.
		// boolean valid = VerifyUtils.verify(gRecaptchaResponse);

		out.close();
//		response.sendRedirect("index.html");

	}
}
