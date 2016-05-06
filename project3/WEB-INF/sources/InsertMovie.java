import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import Model.*;

public class InsertMovie extends HttpServlet
{
    
    public void insertMovie(String title,String year, String director,
            String turl, String burl, String first, String last, String genre)
        throws SQLException
    {
        
        Connection conn = Model.DBConnection.connectToDatabase();
        PreparedStatement stat = conn.prepareStatement(
               "CALL add_movie(?,?,?,?,?,?,?,?);");

        stat.setString(1,title);
        stat.setInt(2,Integer.valueOf(year));
        stat.setString(3,director);
        stat.setString(4,turl);
        stat.setString(5,burl);
        stat.setString(6,first);
        stat.setString(7,last);
        stat.setString(8,genre);

        stat.execute();
    }
    
    @SuppressWarnings({"unchcked"})
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        String title = request.getParameter("title");
        String year = request.getParameter("year");
        String director = request.getParameter("dir");
        String turl = request.getParameter("turl");
        String burl = request.getParameter("burl");
        String first = request.getParameter("first");
        String last = request.getParameter("last");
        String genre = request.getParameter("genre");

        try
        {
            insertMovie(title,year,director,turl,burl,first,last,genre); 
        }
        catch(SQLException e)
        {
            out.println(e.getMessage());
            request.setAttribute("message",e.getMessage());
            return;
        }

        request.setAttribute("message","Sucessfully added movie.");
        request.getRequestDispatcher("_InsertMovie.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request,response);
    }
}
