import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpSession;

import Model.*;

public class AdvSearchServlet extends HttpServlet {
	// process
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdvSearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
        ArrayList<Model.Movie> movieList = new ArrayList<Model.Movie>();
		int count = 0;
		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String name = request.getParameter("name");
		String query = "Select * from movies WHERE ";
		if (!title.equals("")) {
			query += "title LIKE '%" + title + "%'";
			count++;
		}
		if (!year.equals("") && count != 0) {
			query += " AND year = " + year;
		} else if (!year.equals("") && count == 0) {
			query += "year = " + year;
			count++;
		}
		if (!director.equals("") && count != 0) {
			query += " AND director LIKE '%" + director + "%'";
		} else if (!director.equals("") && count == 0) {
			query += "director LIKE '%" + director + "%'";
			count++;
		}
		if (!name.equals("")) {
			if (count == 0) {
				query += "id IN " + "(SELECT movie_id FROM " + "stars_in_movies a natural JOIN stars b "
						+ "WHERE a.star_id = b.id AND ";
			} else {
				query += " AND id IN " + "(SELECT movie_id FROM " + "stars_in_movies a natural JOIN stars b "
						+ "WHERE a.star_id = b.id AND ";
			}
			String[] splitNames = name.split(" ");
			if (splitNames.length <= 1) {
				query += "(b.first_name LIKE \"%" + name + "%\" OR " + "b.last_name LIKE \"%" + name + "%\"))";
			} else if (splitNames.length == 2) {
				query += "(b.first_name LIKE \"%" + name + "%\" OR " + "b.first_name LIKE \"%" + splitNames[0]
						+ "%\" AND " + "b.last_name LIKE \"%" + splitNames[1] + "%\" OR " + "b.last_name LIKE \"%"
						+ name + "%\"))";
			} else {
				query += "(b.first_name LIKE \"%" + splitNames[0] + "%\" OR " + "b.first_name LIKE \"%" + splitNames[0]
						+ " " + splitNames[1] + "%\" OR " + "b.first_name LIKE \"%" + name + "%\" OR "
						+ "b.last_name LIKE \"%" + splitNames[2] + "%\" OR " + "b.last_name LIKE \"%" + splitNames[1]
						+ " " + splitNames[2] + "%\" OR " + "b.last_name LIKE \"%" + name + "%\"))";
			}
		}
		try {
		    Connection connection = Model.DBConnection.connectToDatabase();
		    ResultSet result;
	        Statement select = connection.createStatement();
			result = select.executeQuery(query);
	        while(result.next())
            {
	            int id = result.getInt("id");  
	            String _title = result.getString("title");
	            int _year = result.getInt("year");
	            String _director = result.getString("director");
	            String b_url = result.getString("banner_url");
	            String t_url = result.getString("trailer_url");
	            Model.Movie movie = new Model.Movie(id,_title,_year,_director,b_url,t_url);
	            movieList.add(movie);
	        }

            Model.MovieList movies = new Model.MovieList(movieList, title+year+director+name);
        
            session.setAttribute("movieList",movies);
            RequestDispatcher rd = request.getRequestDispatcher("/showMovies.jsp");
            rd.forward(request,response);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
