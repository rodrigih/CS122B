package Model;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Search {


	public static ArrayList<Movie> createList(String keywords) {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		Set<Movie> movieSet = new HashSet<Movie>();
	    Connection connection = DBConnection.connectToDatabase();
        
        long startTime = System.nanoTime();  

		try {
			for (Movie movie : getMoviesByStars(keywords,connection)) {
				movieSet.add(movie);
			}
			for (Movie movie : getMoviesByGenre(keywords,connection)) {
				movieSet.add(movie);
			}
			for (Movie movie : getMoviesByKey(keywords,connection)) {
				movieSet.add(movie);
			}
			for (Movie movie : movieSet) {
				movies.add(movie);
			}

            long endTime = System.nanoTime();  
            long elapsedTime = endTime - startTime; 

            System.out.println("Elapsed Time = " + elapsedTime);

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movies;
	}

	private static ArrayList<Movie> getMoviesByStars(String keyword,Connection connection) throws SQLException {
		String[] splitKeyword = keyword.split(" ");
		ResultSet result;
		ArrayList<Movie> movieList = new ArrayList<Movie>();

		String queryStatement = "SELECT * FROM movies WHERE id IN " + "(SELECT movie_id FROM "
				+ "stars_in_movies a natural JOIN stars b " + "WHERE a.star_id = b.id AND ";
		PreparedStatement selectMovie;

		if (splitKeyword.length <= 1) {
			queryStatement += "(b.first_name LIKE ? OR " + "b.last_name LIKE ?))";
			selectMovie = connection.prepareStatement(queryStatement);
			selectMovie.setString(1, "%" + keyword + "%");
			selectMovie.setString(2, "%" + keyword + "%");
			result = selectMovie.executeQuery();
		} else if (splitKeyword.length == 2) {
			queryStatement += "(b.first_name LIKE ? OR " + "b.first_name LIKE ? AND " + "b.last_name LIKE ? OR "
					+ "b.last_name LIKE ?))";
			selectMovie = connection.prepareStatement(queryStatement);
			selectMovie.setString(1, "%" + keyword + "%");
			selectMovie.setString(2, "%" + splitKeyword[0] + "%");
			selectMovie.setString(3, "%" + splitKeyword[1] + "%");
			selectMovie.setString(4, "%" + keyword + "%");
			result = selectMovie.executeQuery();
		} else {
			queryStatement += "(b.first_name LIKE ? OR " + "b.first_name LIKE ? OR " + "b.first_name LIKE ? OR "
					+ "b.last_name LIKE ? OR " + "b.last_name LIKE ? OR " + "b.last_name LIKE ?))";
			selectMovie = connection.prepareStatement(queryStatement);
			selectMovie.setString(1, "%" + splitKeyword[0] + "%");
			selectMovie.setString(2, "%" + splitKeyword[0] + splitKeyword[1] + "%");
			selectMovie.setString(3, "%" + keyword + "%");
			selectMovie.setString(4, "%" + splitKeyword[2] + "%");
			selectMovie.setString(5, "%" + splitKeyword[1] + splitKeyword[2] + "%");
			selectMovie.setString(6, "%" + keyword + "%");
			result = selectMovie.executeQuery();
		}
		while (result.next()) {
			int id = result.getInt("id");
			String title = result.getString("title");
			int year = result.getInt("year");
			String director = result.getString("director");
			String b_url = result.getString("banner_url");
			String t_url = result.getString("trailer_url");
			Movie movie = new Movie(id, title, year, director, b_url, t_url);
			movieList.add(movie);
		}
		return movieList;
	}

	private static ArrayList<Movie> getMoviesByGenre(String keyword,Connection connection) throws SQLException {
		ResultSet result;
		ArrayList<Movie> movieList = new ArrayList<Movie>();

		String query = "SELECT * FROM movies WHERE id IN "
				+ "(SELECT movie_id FROM genres_in_movies a NATURAL JOIN genres b"
				+ " WHERE a.genre_id = b.id AND (b.name LIKE ? ))";

		PreparedStatement selectMovie;
		selectMovie = connection.prepareStatement(query);
		selectMovie.setString(1, "%" + keyword + "%");
		result = selectMovie.executeQuery();

		while (result.next()) {
			int id = result.getInt("id");
			String title = result.getString("title");
			int year = result.getInt("year");
			String director = result.getString("director");
			String b_url = result.getString("banner_url");
			String t_url = result.getString("trailer_url");
			Movie movie = new Movie(id, title, year, director, b_url, t_url);
			movieList.add(movie);
		}
		return movieList;
	}

	private static ArrayList<Movie> getMoviesByKey(String keyword,Connection connection) throws SQLException {
		ResultSet result;
		ArrayList<Movie> movieList = new ArrayList<Movie>();

		String query = "SELECT * FROM movies WHERE " + "title LIKE ? OR " + "director LIKE ?;";

		PreparedStatement selectMovie;
		selectMovie = connection.prepareStatement(query);
		selectMovie.setString(1, "%" + keyword + "%");
		selectMovie.setString(2, "%" + keyword + "%");
		result = selectMovie.executeQuery();

		while (result.next()) {
			int id = result.getInt("id");
			String title = result.getString("title");
			int year = result.getInt("year");
			String director = result.getString("director");
			String b_url = result.getString("banner_url");
			String t_url = result.getString("trailer_url");
			Movie movie = new Movie(id, title, year, director, b_url, t_url);
			movieList.add(movie);
		}
		return movieList;
	}

}
