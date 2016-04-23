import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import Model.*;

public class getMovies extends HttpServlet
{

    private static String createLetterQuery(String browseBy,String key)
    {
       String query = "SELECT * FROM movies WHERE title " +
            "LIKE '" + key + "%';";
       return query;
    }

    private static String createGenreQuery(String browseBy, String key)
    {
       String query = "SELECT * FROM movies WHERE id IN " + 
           "(SELECT movie_id FROM genres_in_movies a NATURAL JOIN genres b" + 
           " WHERE a.genre_id = b.id AND (b.name='" + key + 
           "'));";
           
       return query;
    }

    private void orderByTitles(ArrayList<Model.Movie> movies, String desc)
    {
        Comparator<Model.Movie> comp;

        if(desc.equals("true"))
            comp = new Model.Movie.MovieTitleComparatorDesc();
        else
            comp = new Model.Movie.MovieTitleComparatorAsc();

        Collections.sort(movies,comp);
    }

    private void orderByYear(ArrayList<Model.Movie> movies, String desc)
    {
        Comparator<Model.Movie> comp;

        if(desc.equals("true"))
            comp = new Model.Movie.MovieYearComparatorDesc();
        else
            comp = new Model.Movie.MovieYearComparatorAsc();

        Collections.sort(movies,comp);
    }

    private void sortMovieList(ArrayList<Model.Movie> movies, String orderBy, String desc)
    {
       if(orderBy.equals("title"))
            orderByTitles(movies,desc);    
        else
            orderByYear(movies,desc);
    }
    
    private static Model.Movie createMovie(ResultSet rs) throws SQLException
    {
        Model.Movie movie = null;

        try
        {
            //Get parameters
            int id = rs.getInt("id");  
            String title = rs.getString("title");
            int year = rs.getInt("year");
            String director = rs.getString("director");
            String b_url = rs.getString("banner_url");
            String t_url = rs.getString("trailer_url");

            movie = new Model.Movie(id,title,year,director,b_url,t_url);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return movie;
    }
    
    public ArrayList<Model.Movie> createMovieList(String browseBy,String key, PrintWriter out)
        throws IOException, ServletException
    {

        ArrayList<Model.Movie> movies = new ArrayList<Model.Movie>();
        try
        {

            String query;

            Connection conn = Model.DBConnection.connectToDatabase();
            Statement statement = conn.createStatement();

            if(browseBy.equals("genre"))
                query = createGenreQuery(browseBy,key);
            else if (browseBy.equals("letter"))
                query = createLetterQuery(browseBy,key);
            else
                return movies;

            ResultSet rs = statement.executeQuery(query);

            while(rs.next())
            {
                movies.add(createMovie(rs));
            }

            conn.close();
            rs.close();
            statement.close();
        }
        catch(SQLException e)
        {
            out.println("SQL Exception: " + e.getMessage());
        }

        return movies;
    }

    @SuppressWarnings({"unchecked"})
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html"); // Response mime type
        PrintWriter out = response.getWriter();

        String browseBy = request.getParameter("browseBy");
        String key = request.getParameter("key");
        String orderBy = request.getParameter("orderBy");
        String desc = request.getParameter("desc");
        String page = request.getParameter("page");
        String ipp = request.getParameter("ipp"); 

        Model.MovieList movieList;

        HttpSession session = request.getSession();
        movieList = (Model.MovieList) session.getAttribute("movieList");

        
        if(session.isNew() || 
           movieList== null || 
           (!movieList.checkSearchConditions(browseBy,key)))
            
            movieList = new Model.MovieList(browseBy,key,orderBy,desc,page,ipp,out);

        movieList.sortMovieList(orderBy,Boolean.valueOf(desc));

        movieList.changeIpp(Integer.valueOf(ipp));
        movieList.advanceIndex(Integer.valueOf(page));
        
        session.setAttribute("movieList",movieList);
        RequestDispatcher rd = request.getRequestDispatcher("/showMovies.jsp");
        rd.forward(request,response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request,response);
    }
}
