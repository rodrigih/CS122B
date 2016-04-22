package com.khcart;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.sql.*;

public class Search {
    static Connection connection = DBConnection.connectToDatabase();
//    ArrayList<Movie> movies = new ArrayList<Movie>();
//    Set<Movie> movieSet = new HashSet<Movie>();
    
    public static ArrayList<Movie> createList(String keywords) throws SQLException {
        ArrayList<Movie> movies = new ArrayList<Movie>();
        Set<Movie> movieSet = new HashSet<Movie>();
 
        for(Movie movie : getMoviesByStars(keywords)){
        	movieSet.add(movie);
        }
        for(Movie movie : getMoviesByGenre(keywords)){
        	movieSet.add(movie);
        }
        for(Movie movie : getMoviesByKey(keywords)){
        	movieSet.add(movie);
        }
        for(Movie movie : movieSet){
        	movies.add(movie);
//        	movie.printStats();
        }
		return movies;
        
    }
    
    private static ArrayList<Movie> getMoviesByStars(String keyword) throws SQLException{
    	String [] splitKeyword = keyword.split(" ");
    	ResultSet result;
        Statement select = connection.createStatement();
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        String queryStatement ="SELECT * FROM movies WHERE id IN "
                + "(SELECT movie_id FROM "
                + "stars_in_movies a natural JOIN stars b "
                + "WHERE a.star_id = b.id AND ";
        if (splitKeyword.length <= 1){
        	result = select.executeQuery(queryStatement +
        			"(b.first_name LIKE \"%"+keyword+"%\" OR "+
        			"b.last_name LIKE \"%"+keyword+"%\"))");
        } else if(splitKeyword.length ==2){
        	result = select.executeQuery(queryStatement +
        			"(b.first_name LIKE \"%"+ keyword +"%\" OR "+
        			"b.first_name LIKE \"%"+splitKeyword[0]+"%\" AND "+
        			"b.last_name LIKE \"%"+splitKeyword[1]+"%\" OR "+
        			"b.last_name LIKE \"%"+keyword+"%\"))");
        }else{
        	result = select.executeQuery(queryStatement +
        			"(b.first_name LIKE \"%"+splitKeyword[0]+"%\" OR "+
        			"b.first_name LIKE \"%"+splitKeyword[0]+" "+splitKeyword[1]+"%\" OR "+
        			"b.first_name LIKE \"%"+keyword+"%\" OR "+
        			"b.last_name LIKE \"%"+splitKeyword[2]+"%\" OR "+
        			"b.last_name LIKE \"%"+splitKeyword[1]+" "+splitKeyword[2]+"%\" OR "+
        			"b.last_name LIKE \"%"+keyword+"%\"))");
        }
        while(result.next()){
            int id = result.getInt("id");  
            String title = result.getString("title");
            int year = result.getInt("year");
            String director = result.getString("director");
            String b_url = result.getString("banner_url");
            String t_url = result.getString("trailer_url");
            Movie movie = new Movie(id,title,year,director,b_url,t_url);
            movieList.add(movie);
        }
		return movieList;
    }
    
    private static ArrayList<Movie> getMoviesByGenre(String keyword) throws SQLException
    {
//    	ResultSet result;
        Statement select = connection.createStatement();
        ArrayList<Movie> movieList = new ArrayList<Movie>();

        String query = "SELECT * FROM movies WHERE id IN " + 
           "(SELECT movie_id FROM genres_in_movies a NATURAL JOIN genres b" + 
           " WHERE a.genre_id = b.id AND (b.name LIKE '%" + keyword + 
           "%'))";

        ResultSet result = select.executeQuery(query);

        while(result.next()){
            int id = result.getInt("id");  
            String title = result.getString("title");
            int year = result.getInt("year");
            String director = result.getString("director");
            String b_url = result.getString("banner_url");
            String t_url = result.getString("trailer_url");
            Movie movie = new Movie(id,title,year,director,b_url,t_url);
            movieList.add(movie);
        }
		return movieList;
    }
    
    private static ArrayList<Movie> getMoviesByKey(String keyword) throws SQLException{
//        	ResultSet result;
            Statement select = connection.createStatement();
            ArrayList<Movie> movieList = new ArrayList<Movie>();

            String query = "SELECT * FROM movies WHERE "
            		+ "title LIKE '%" + keyword +"%' OR "
            		+ "director LIKE '%" +keyword +"%';" ;

            ResultSet result = select.executeQuery(query);

            while(result.next()){
                int id = result.getInt("id");  
                String title = result.getString("title");
                int year = result.getInt("year");
                String director = result.getString("director");
                String b_url = result.getString("banner_url");
                String t_url = result.getString("trailer_url");
                Movie movie = new Movie(id,title,year,director,b_url,t_url);
                movieList.add(movie);
            }
    		return movieList;   
    }
}
