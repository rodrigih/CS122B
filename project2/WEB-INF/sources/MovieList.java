package Model;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.jsp.JspWriter;

public class MovieList
{
    ArrayList<Movie> movies;
    int index;
    int size;

    String browseBy;
    String key;
    String orderBy;
    boolean desc;
    int page;
    int ipp;

    // CONSTRUCTOR
    public MovieList(String browseBy, String key, String orderBy,
            String desc, String page, String ipp, PrintWriter out)
        throws IOException
    {

        this.browseBy = browseBy;
        this.key = key;
        this.orderBy = orderBy;
        this.desc = Boolean.valueOf(desc);
        this.page = Integer.valueOf(page);
        this.ipp = Integer.valueOf(ipp);

        this.movies = createMovieList(browseBy,key, out);

        this.index = 0;
        this.size = movies.size();

        sortMovieList(this.orderBy,this.desc);
    }

    // Methods for constructing queries
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

    // Methods for sorting MovieList
    private void orderByTitles(boolean descending)
    {
        Comparator<Movie> comp;

        if(descending)
            comp = new Movie.MovieTitleComparatorDesc();
        else
            comp = new Movie.MovieTitleComparatorAsc();

        Collections.sort(movies,comp);
    }

    private void orderByYear(boolean descending)
    {
        Comparator<Movie> comp;

        if(descending)
            comp = new Movie.MovieYearComparatorDesc();
        else
            comp = new Movie.MovieYearComparatorAsc();

        Collections.sort(movies,comp);
    }

    public void sortMovieList(String order, boolean descending)
    {
        if(orderBy.equals(order) && (desc == descending))
            return; //Do nothing as ordering is the same

        if(order.equals("title"))
            orderByTitles(descending);    
        else
            orderByYear(descending);
    }
 
    // Method for creating movie
    private static Movie createMovie(ResultSet rs) throws SQLException
    {
        Movie movie = null;

        try
        {
            //Get parameters
            int id = rs.getInt("id");  
            String title = rs.getString("title");
            int year = rs.getInt("year");
            String director = rs.getString("director");
            String b_url = rs.getString("banner_url");
            String t_url = rs.getString("trailer_url");

            movie = new Movie(id,title,year,director,b_url,t_url);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return movie;
    }
 
    public ArrayList<Movie> createMovieList(String browseBy,String key, PrintWriter out)
        throws IOException 
    {

        ArrayList<Movie> movies = new ArrayList<Movie>();
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


    public void advanceIndex(int n)
    {
        page = n; 
        index = (n-1) * ipp;
    }

    public void changeIpp(int n)
    {
        ipp = n;
    }

    public boolean checkSearchConditions(String param, String value)
    {
        return (browseBy.equals(param) && key.equals(value));
    }
    public void printMovies(JspWriter out) throws IOException
    {
        for(int i = index; i < index+ipp && i < size-1; i++)
        {
            out.println("\n\t<table>\n\t<tbody>");
            movies.get(i).printHtml(out); 
            out.println("\n\t</table>\n\t</tbody>");
        }
        printPageNav(out);
        printIpp(out);
    }
    public String createLink(String link, String page, String text)
    {
        String result = "<a href=" + link + page + "&ipp=" + ipp + 
            ">" + text + "</a>";

        return result;
    }
    
    public void printPageNav(JspWriter out) throws IOException
    {
        String link = "getMovies?browseBy=" + browseBy + 
            "&key=" + key + "&orderBy=" + orderBy + 
            "&desc" + String.valueOf(desc) + "&page="; 
        
        out.println("\n\t<div id='pageNav'>" + 
                "\n\t\t<ul class='pagination'>");

        //First print prev Page
        out.print("\n\t\t\t<li>");
        if(page !=1)
            out.print(createLink(link,String.valueOf(page-1),"<<"));
        else
            out.print("<<");
        out.print("</li>");

        // Now print each page
        for(int i = 0;i < (size/ipp); i++)
        {
            out.print("\n\t\t\t<li>");
            out.print(createLink(link,String.valueOf(i+1),String.valueOf(i+1)));
            out.print("</li>");
        }

        // Finally print next page
        out.print("\n\t\t\t<li>");
        if(page < ((size/10) + 1))
            out.print(createLink(link,String.valueOf(page+ 1),">>"));
        else
            out.print(">>");
        out.print("</li>");

        out.println("\n\t\t</ul>\n\t</div>");
    }

    public void printIpp(JspWriter out) throws IOException
    {
        String[] items = {"5","10","15","25","100"}; 

         String link = "getMovies?browseBy=" + browseBy + 
            "&key=" + key + "&orderBy=" + orderBy + 
            "&desc" + String.valueOf(desc) + "&page=1" + 
            "&ipp="; 

         out.println("\n\t<div id='ipp'>" + 
                 "\n\t\t<ul class='pagination'>");

         out.println("\n\t\t\t<li>Items per Page: </li>");
         for(String s: items)
         {
            out.print("\n\t\t\t<li>");
            out.print("<a href='" + link + s + "'>" + s + "</a>");
            out.print("</li>");
         }
         out.println("\n\t<div id='ipp'>" + 
                 "\n\t\t<ul class='ipp'>");
    }
}
