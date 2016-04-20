import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.jsp.JspWriter;

import Model.*;

public class MovieList
{

    private static String createLetterQuery(String key, String orderBy,
            String desc, String page)
    {
        String query = "SELECT * FROM movies WHERE title " +
            "LIKE '" + key + "' ORDER BY " + orderBy;

        if(desc.equals("true"))
        {
            query += " DESC";
        }

        int start = (Integer.valueOf(page) - 1) * 11;
        int end = (Integer.valueOf(page) * 11) - 1;

        query = query + " LIMIT " + String.valueOf(start) + "," + 
            String.valueOf(end) + ";";

        return query;
    }

    private static String createGenreQuery(String key, String orderBy,
            String desc, String page)
    {
        String query = "SELECT * FROM movies WHERE id IN " + 
           "(SELECT movie_id FROM genres_in_movies a NATURAL JOIN genres b" + 
           " WHERE a.genre_id = b.id AND (b.name='" + key + 
           "')) ORDER BY " + orderBy;

        if(desc.equals("true"))
        {
            query += " DESC";
        }

        int start = (Integer.valueOf(page) - 1) * 11;
        int end = (Integer.valueOf(page) * 11) - 1;

        query = query + " LIMIT " + String.valueOf(start) + "," + 
            String.valueOf(end) + ";";

        return query;
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
    
    public static void showMovie(String browseBy,String key, String orderBy,
            String desc, String page, JspWriter out)
    {
        String query;

        try
        {
            ArrayList<Model.Movie> movies = new ArrayList<Model.Movie>();

            Connection conn = Model.DBConnection.connectToDatabase();
            Statement statement = conn.createStatement();

            if(browseBy.equals("genre"))
                query = createGenreQuery(key,orderBy,desc,page);
            else if (browseBy.equals("letter"))
                query = createLetterQuery(key,orderBy,desc,page);
            else
                return;
                
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

    }
    /*
    public String getServletInfo()
    {
        return "Servlet creates mySQL query based on parameters"; 
    }    

    // Use HTTP GET

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html"); // Response mime type

        // Output stream to STDOUT
        PrintWriter out = response.getWriter();

        String browseBy = request.getParameter("browseBy");
        String query;

        try
        {
            out.println("<html><head><title>" + 
                "<article>\n\t<table id=content>\n\t\t<tbody>\n"); 

            ArrayList<Model.Movie> movies = new ArrayList<Model.Movie>();


            //Model.Movie movie;

            Connection conn = Model.DBConnection.connectToDatabase();
            Statement statement = conn.createStatement();

            if(browseBy.equals("genre"))
                query = createGenreQuery(request);
            else if (browseBy.equals("letter"))
                query = createLetterQuery(request);
            else
                return;
                
            ResultSet rs = statement.executeQuery(query);

            while(rs.next())
            {

                movies.add(createMovie(rs));
             //   movie.printHtml(out);
            }

            out.println("\n\t\t</tbody>\n\t</table></article>" + 
                    "</title></head></html>");

            request.setAttribute("MovieList",movies);

            // USE FOR DEBUGGING
            request.setAttribute("test1",browseBy);
            request.getSession().setAttribute("test2",browseBy);
            this.getServletConfig().getServletContext().setAttribute("test3",browseBy);

            request.setAttribute("test4","Hello World");
            request.getSession().setAttribute("test5","Hello World");
            this.getServletConfig().getServletContext().setAttribute("test6","Hello World");

            conn.close();
            rs.close();
            statement.close();
        }
       catch(SQLException e)
        {
            out.println("SQL Exception: " + e.getMessage());
        }
        catch(java.lang.Exception e)
        {
            out.println("<html>" + 
                        "<head><title>" + 
                        "MovieDB: Error" + 
                        "</title></head>\n<body>" + 
                        "<p>SQL error in doGet: " + 
                        e.getMessage() + "</p></body></html>");
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/showMovies.jsp"); 
        dispatcher.forward(request,response);
      }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request,response);
    } 
    */
}
