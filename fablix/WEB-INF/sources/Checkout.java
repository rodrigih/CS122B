
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Checkout() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();
		String email = (String) session.getAttribute("user");
		String password = (String) session.getAttribute("pw");
		Cart shoppingCart = (Cart) session.getAttribute("cart");

		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String ccNum = request.getParameter("creditcard");
		String expirationDate = request.getParameter("expiration");
		expirationDate = expirationDate.replace('-', '/');
		String query = "SELECT * from creditcards WHERE " + "id = '" + ccNum + "' AND first_name = '" + firstName
				+ "' AND last_name = '" + lastName + "' AND expiration = '" + expirationDate + "'";
		String query2 = "Select * FROM customers where cc_id = '" + ccNum + "'";
		Connection connection = DBConnection.connectToDatabase();
		ResultSet result;
		try {
			Statement select = connection.createStatement();
			result = select.executeQuery(query);
			if (!result.first()) {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Checkout.jsp");
				PrintWriter out = response.getWriter();
				out.println("<font color=blue>Invalid credit card information!" + " Please try again!</font>");
				rd.include(request, response);
				return;
				//// return;
			} else {
				connection.close();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try (PrintWriter out = response.getWriter()) {

			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>result</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>CONFIRMATION PAGE </h1>");
			out.println("USER = " + email);

			out.println("Name = " + firstName + lastName + "   ");
			out.println("Card# = " + ccNum + "   ");
			out.println("Exp Date = " + expirationDate);
			out.println("QUERY = " + query);
			out.println("QUERY2 = " + query2);
			out.println("</body>");
			out.println("</html>");
		}

		int customerID = (int) session.getAttribute("customer");
		Date dNow = new Date();
		String date = new SimpleDateFormat("yyyy/MM/dd").format(dNow);

		try {
			PreparedStatement insertQuery = connection
					.prepareStatement("INSERT" + "into sales(customer_id, movie_id, sale_date)" + "VALUES(?,?,?)");
			for (int i = 0; i < shoppingCart.cart.size(); i++) {
				int movieID = shoppingCart.cart.get(i).getID();
				insertQuery.setInt(1, customerID);
				insertQuery.setInt(2, movieID);
				insertQuery.setString(3, date);
				insertQuery.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		shoppingCart.cart.clear();
		shoppingCart.clear();


	}

}
