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
//        ArrayList<Movie> movieList = new ArrayList<Movie>();
        ArrayList<Model.Movie> movieList = new ArrayList<Model.Movie>();

		String title = request.getParameter("title");
		String year = request.getParameter("year");
		String director = request.getParameter("director");
		String name = request.getParameter("name");

		String query = "Select * from movies "
				+ "WHERE title LIKE ? "
				+ "AND year LIKE ? "
				+ "AND director LIKE ?"
				+ " AND id IN "
				+ "(SELECT movie_id "
				+ "FROM stars_in_movies a natural JOIN stars b "
				+ "WHERE a.star_id = b.id AND ";

		try {
			Connection connection = Model.DBConnection.connectToDatabase();
			PreparedStatement selectMovie;

			String[] splitNames = name.split(" ");
			if (splitNames.length <= 1) {
				query += "(b.first_name LIKE ? OR b.last_name LIKE ?))";
				selectMovie = connection.prepareStatement(query);
				selectMovie.setString(1, "%" + title + "%");
				selectMovie.setString(2, "%" + year + "%");
				selectMovie.setString(3, "%" + director + "%");
				selectMovie.setString(4, "%" + name + "%");
				selectMovie.setString(5, "%" + name + "%");

			} else if (splitNames.length == 2) {
				query += "(b.first_name LIKE ? OR b.first_name LIKE ? AND b.last_name LIKE ? OR b.last_name LIKE ?))";
				selectMovie = connection.prepareStatement(query);
				selectMovie.setString(1, "%" + title + "%");
				selectMovie.setString(2, "%" + year + "%");
				selectMovie.setString(3, "%" + director + "%");
				selectMovie.setString(4, "%" + name + "%");
				selectMovie.setString(5, "%" + splitNames[0] + "%");
				selectMovie.setString(6, "%" + splitNames[1] + "%");
				selectMovie.setString(7, "%" + name + "%");

			} else {
				query += "(b.first_name LIKE ? OR b.first_name LIKE ?"
						+ " OR b.first_name LIKE ? OR b.last_name LIKE ? OR b.last_name LIKE ?"
						+ " OR b.last_name LIKE ?))";

				selectMovie = connection.prepareStatement(query);
				selectMovie.setString(1, "%" + title + "%");
				selectMovie.setString(2, "%" + year + "%");
				selectMovie.setString(3, "%" + director + "%");
				selectMovie.setString(4, "%" + splitNames[0] + "%");
				selectMovie.setString(5, "%" + splitNames[0] + splitNames[1] + "%");
				selectMovie.setString(6, "%" + name + "%");
				selectMovie.setString(7, "%" + splitNames[2] + "%");
				selectMovie.setString(8, "%" + splitNames[1] + splitNames[2] + "%");
				selectMovie.setString(9, "%" + name + "%");

			}

//			System.out.println(selectMovie.toString());

			ResultSet result;
			result = selectMovie.executeQuery();
			while (result.next()) {
				int id = result.getInt("id");
				String _title = result.getString("title");
				int _year = result.getInt("year");
				String _director = result.getString("director");
				String b_url = result.getString("banner_url");
				String t_url = result.getString("trailer_url");
	            Model.Movie movie = new Model.Movie(id,_title,_year,_director,b_url,t_url);
	            movieList.add(movie);
//				Movie movie = new Movie(id, _title, _year, _director, b_url, t_url);
//				movieList.add(movie);
			}
			connection.close();
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
