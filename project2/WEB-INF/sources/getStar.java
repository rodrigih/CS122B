import java.io.*;
import java.net.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.JspWriter;

import Model.*;

public class getStar extends HttpServlet
{
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String star_id = request.getParameter("star_id");


        Model.Star star = new Model.Star(Integer.valueOf(star_id));

        request.setAttribute("star",star);
        RequestDispatcher rd = request.getRequestDispatcher("/showStar.jsp");
        rd.forward(request,response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        doGet(request,response);
    }
}
