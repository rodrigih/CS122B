import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import Model.*;

public class getSingleMovie extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String movie_id = request.getParameter("movie_id");


        Model.Movie movie = Model.MovieInfo.getSingleMovie(Integer.valueOf(movie_id));

        request.setAttribute("movie",movie);
        RequestDispatcher rd = request.getRequestDispatcher("/showSingleMovie.jsp");
        rd.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request,response); 
    }
}
