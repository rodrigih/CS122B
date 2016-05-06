import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import Model.*;

public class InsertStar extends HttpServlet
{
    
    public void insertStar(String first, String last, String dob, String url)
        throws SQLException
    {
        
        if(first.equals("") || last.equals(""))
        {
            last = last + first;
            first = "";
        }

        Connection conn = Model.DBConnection.connectToDatabase();
        PreparedStatement stat = conn.prepareStatement(
               "INSERT INTO stars VALUES(NULL,?,?,?,?);");

        stat.setString(1,first);
        stat.setString(2,last);
        stat.setString(3,dob);
        stat.setString(4,url);

        stat.execute();
    }
    
    @SuppressWarnings({"unchcked"})
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html"); 
        PrintWriter out = response.getWriter();

        String firstName = request.getParameter("first");
        String lastName = request.getParameter("last");
        String dob = request.getParameter("dob");
        String url = request.getParameter("url");

        try
        {
            insertStar(firstName,lastName,dob,url); 
        }
        catch(SQLException e)
        {
            out.println(e.getMessage());
            request.setAttribute("message",e.getMessage());
        }

        request.setAttribute("message","Sucessfully added star.");
        request.getRequestDispatcher("_InsertStar.jsp").forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request,response);
    }
}
