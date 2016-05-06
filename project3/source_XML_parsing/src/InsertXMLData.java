
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.io.File;
import java.sql.*;

public class InsertXMLData {

	public static Connection initConnection() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/moviedb_project3_grading";
			conn = DriverManager.getConnection(url, "classta", "classta");
            /*
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/moviedb";
			conn = DriverManager.getConnection(url, "testuser", "testpass");
            */
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

	//
	// This code/function is from J development to check if string is a number
	public static boolean isNumber(String string) {
		if (string == null || string.isEmpty()) {
			return false;
		}
		int i = 0;
		if (string.charAt(0) == '-') {
			if (string.length() > 1) {
				i++;
			} else {
				return false;
			}
		}
		for (; i < string.length(); i++) {
			if (!Character.isDigit(string.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public static HashMap<String, Integer> insertMovies(HashMap<String, Movie> idToMovie,
			HashMap<String, Integer> movieCache) throws SQLException {


		HashMap<String, Integer> genreCache = new HashMap<String, Integer>();
		boolean newMovies = false;
		boolean newGenres = false;

		int maxMovieID = 0;
		int maxGenreID = 0;
		ResultSet result;
		Connection connection = initConnection();
		Statement select = connection.createStatement();

		String batchInsertMovies = "Insert into movies (id, title, year, director)" + " VALUES ";

		String batchInsertGenres = "Insert into genres(id, name) VALUES ";

		String batchInsertGenresInMovies = "Insert into genres_in_movies(genre_id," + " movie_id) VALUES ";

		// keep track of max ID
		result = select.executeQuery("Select MAX(ID) from movies");
		while (result.next()) {
			maxMovieID = result.getInt(1);
		}

		result = select.executeQuery("Select MAX(ID) from genres");
		while (result.next()) {
			maxGenreID = result.getInt(1);
		}

		String movieQuery = "Select ID from movies where title = ?";
		String genreQuery = "Select ID from genres where name = ?";
		String movieGenreQuery = "Select * from genres_in_movies where genre_id = ? AND movie_id = ?";

		PreparedStatement selectMovieID = connection.prepareStatement(movieQuery);
		PreparedStatement selectGenreID = connection.prepareStatement(genreQuery);
		PreparedStatement selectMovieGenreID = connection.prepareStatement(movieGenreQuery);

		for (Movie m : idToMovie.values()) {

			selectMovieID.setString(1, m.getTitle());
			result = selectMovieID.executeQuery();
			if (!result.first()) {
				maxMovieID++;
				String director = null;
				int movieYear = 0;
				if (m.getDirector().size() == 0) {
					director = "";
				} else if (m.getDirector().size() != 0) {
					director = m.getDirector().get(0);
				}
				if (!isNumber(m.getYear())) {
					movieYear = 0;
				} else if (isNumber(m.getYear())) {
					movieYear = Integer.parseInt(m.getYear());
				}
				movieCache.put(m.getId(), maxMovieID);

				batchInsertMovies += "(" + maxMovieID + ", \"" + m.getTitle() + "\", " + movieYear + ", \"" + director
						+ "\"),";
				newMovies = true;

			} else {

				int movieID = result.getInt(1);
				movieCache.put(m.getId(), movieID);

			}

			ArrayList<String> movieGenres = m.getGenres();

			for (String genre : movieGenres) {
				// String genreName = "'"+genre+"'";
				selectGenreID.setString(1, genre);
				result = selectGenreID.executeQuery();
				if (!result.first()) {
					maxGenreID++;
					genreCache.put(genre, maxGenreID);
					batchInsertGenres += "(" + maxGenreID + ", \"" + genre + "\"),";
					newGenres = true;
				} else {
					int genreID = result.getInt(1);
					genreCache.put(genre, genreID);
				}

				int genreID;

				int newMovieID = movieCache.get(m.getId());
				for (String genreName : genreCache.keySet()) {
					if (genreName.equals(genre)) {
						genreID = genreCache.get(genreName);
						selectMovieGenreID.setInt(1, genreID);
						selectMovieGenreID.setInt(2, newMovieID);
						result = selectGenreID.executeQuery();
						if (!result.first())
							batchInsertGenresInMovies += "(" + genreID + ", " + newMovieID + "),";
					}
				}
			}
		}

		batchInsertMovies = batchInsertMovies.substring(0, batchInsertMovies.length() - 1) + "; ";
		batchInsertGenres = batchInsertGenres.substring(0, batchInsertGenres.length() - 1) + "; ";
		batchInsertGenresInMovies = batchInsertGenresInMovies.substring(0, batchInsertGenresInMovies.length() - 1)
				+ "; ";

		Statement statement = connection.createStatement();

		if (newMovies) {
			statement.executeUpdate(batchInsertMovies);
		}
		if (newGenres) {
			statement.executeUpdate(batchInsertGenres);
		}
		if (newMovies && newGenres) {
			statement.executeUpdate(batchInsertGenresInMovies);

		}

		connection.close();

		return movieCache;
	}

	public static void insertStars(HashMap<String, Star> nameToStar, HashMap<String, Integer> movieCache)
			throws SQLException {

		int currentMovieID;

		boolean newStars = false;
		boolean newStarsInMovies = false;
		int maxStarID = 0;
		ResultSet result;
		Connection connection = initConnection();
		Statement select = connection.createStatement();

		result = select.executeQuery("Select MAX(ID) from stars");
		while (result.next()) {
			maxStarID = result.getInt(1);
		}

		String batchInsertStarsInMovies = "Insert into stars_in_movies(star_id," + " movie_id) VALUES ";

		String batchInsertStars = "Insert into stars(id, first_name, last_name, dob)" + " VALUES ";
		String starQuery = "Select ID from stars where first_name = ? AND last_name = ?";

		String starMovieQuery = "Select * from stars_in_movies where star_id = ? AND movie_id = ?";

		PreparedStatement selectStarID = connection.prepareStatement(starQuery);

		PreparedStatement selectStarMovieID = connection.prepareStatement(starMovieQuery);

		for (Star star : nameToStar.values()) {

			int starID = 0;
			selectStarID.setString(1, star.getFirstName());
			selectStarID.setString(2, star.getLastName());
			result = selectStarID.executeQuery();
			if (!result.first()) {
				maxStarID++;
				String DOB = null;
				if (isNumber(star.getDOB())) {
					DOB = star.getDOB();
					if (DOB.length() == 4) {
						DOB += "0101";
					}
				}

				String firstName = star.getFirstName().replaceAll("\"", "'");
				String lastName = star.getLastName().replaceAll("\"", "'");
				firstName = firstName.replace("\\", "");
				lastName = lastName.replace("\\", "");
				batchInsertStars += "(" + maxStarID + ", \"" + firstName + "\", \"" + lastName + "\", " + DOB + "),";

				starID = maxStarID;
				newStars = true;
			} else {
//				while (result.next()) {
					starID = result.getInt(1);
//				}
			}

			for (String movie : star.getMovies()) {

				if (movieCache.get(movie) == null) {
					continue;
				}
				currentMovieID = movieCache.get(movie);
				selectStarMovieID.setInt(1, starID);
				selectStarMovieID.setInt(2, currentMovieID);
				result = selectStarMovieID.executeQuery();
				if (!result.first()) {
					batchInsertStarsInMovies += "(" + starID + ", " + currentMovieID + "),";
					newStarsInMovies = true;
				}
			}
		}

		batchInsertStars = batchInsertStars.substring(0, batchInsertStars.length() - 1) + ";";
		batchInsertStarsInMovies = batchInsertStarsInMovies.substring(0, batchInsertStarsInMovies.length() - 1) + ";";


		Statement statement = connection.createStatement();
		if (newStars) {
			statement.executeUpdate(batchInsertStars);
		}if(newStarsInMovies){
			statement.executeUpdate(batchInsertStarsInMovies);
		}
//		System.out.println("YOU ARE FINISHED WOOOOO!!!1");
		connection.close();
	}

	public static void main(String[] args) throws SQLException {
		 if (args.length != 3) {
			 
			 System.out.println("ERROR you have the incorrect amount of files!");
		 }


		// Initialize Parser
		MovieParser movieParser = new MovieParser();
		StarParser starParser = new StarParser();
		CastParser castParser = new CastParser();

		// Parse documents and retrieve results
		HashMap<String, Movie> idToMovie = movieParser.parseDocument(args[0]);
		HashMap<String, Star> nameToStar = starParser.parseDocument(args[1]);
//		HashMap<String, Movie> idToMovie = movieParser.parseDocument("mains243.xml");
//		HashMap<String, Star> nameToStar = starParser.parseDocument("actors63.xml");
		// Strings for movies are XML's ID's
		// key for stars are their names.

		nameToStar = castParser.parseDocument(args[2], nameToStar);
//		nameToStar = castParser.parseDocument("casts124.xml", nameToStar);

		HashMap<String, Integer> MovieCache = new HashMap<String, Integer>();
		HashMap<String, Integer> NewMovieCache = new HashMap<String, Integer>();


		NewMovieCache = insertMovies(idToMovie, MovieCache);

		insertStars(nameToStar, NewMovieCache);
//		System.out.println("Finished! HAVE A NICE DAY");
	}
}
