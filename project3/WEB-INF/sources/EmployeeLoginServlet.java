package com.khcart;

import Model.DBConnection;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmployeeLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public EmployeeLoginServlet() {
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
		String query = "SELECT * FROM employees WHERE " + 
            "email = '" + email + "' AND password = '" + password + "';";

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
				RequestDispatcher rd = request.getRequestDispatcher("_dashboard.html");
				connection.close();
				out.println("<font color=blue>Invalid Login information or ReCaptcha Wrong!" + " Please try again!</font>");
				rd.forward(request, response);
				return;
				//// return;
			}
			else {
				connection.close();
				response.sendRedirect("_dash_index.jsp");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		out.close();

	}
}
