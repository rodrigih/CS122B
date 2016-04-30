
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.*;
/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get request parameters for userID and password
		String email = request.getParameter("username");
		String password = request.getParameter("password");
		String query = "Select * FROM customers WHERE " + "email = '" + email + "' AND password = '" + password + "'";
		
		String query2 = "Select id FROM customers WHERE " + "email = '" + email + "' AND password = '" + password + "'";
		int customerID = 0;

		Connection connection = Model.DBConnection.connectToDatabase();
		ResultSet result;
		try {
			Statement select = connection.createStatement();
			result = select.executeQuery(query);
			if (!result.first()) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/LoginPage.html");
				PrintWriter out = response.getWriter();
				out.println("<font color=blue>Invalid Login information!" + " Please try again!</font>");
				rd.include(request, response);
				return;
				//// return;
			}else{
				result = select.executeQuery(query2);
				customerID = result.getInt("id");
				connection.close();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		session.setAttribute("user", email);
		session.setAttribute("pw", password);
		session.setAttribute("customer", customerID);
		// setting session to expiry in 30 mins
//		session.setMaxInactiveInterval(60);
//		Cookie userName = new Cookie("user", email);
//		userName.setMaxAge(60);
//		response.addCookie(userName);
		response.sendRedirect("/index.jsp");
	}

}
