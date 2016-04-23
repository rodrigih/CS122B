package Model;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RemoveItem
 */
public class RemoveItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
        HttpSession session = request.getSession();
        Cart shoppingCart;
        shoppingCart = (Cart) session.getAttribute("cart");
//        if(request.getParameter("removePrice").equals("")){
//			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Cart.jsp");
//			PrintWriter out = response.getWriter();
//			out.println("<font color=blue>That is not a valid quantity!" + " Please try again!</font>");
//			rd.include(request, response);
//			return;
//        }
        String name = request.getParameter("removeName");
        int movieID = Integer.parseInt(request.getParameter("id"));
        Float price = Float.parseFloat(request.getParameter("removePrice"));
        int qty = Integer.parseInt(request.getParameter("removeQuantity"));
        CartItem item = new CartItem(movieID,name,price,qty);
        shoppingCart.removeItem(item);
        session.setAttribute("cart", shoppingCart);
        response.sendRedirect("Cart.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
