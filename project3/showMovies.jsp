<%@ include file="header.html" %>

<%@ page import="Model.*,
java.util.ArrayList"
%>

<%

        Model.MovieList movies = 
            (Model.MovieList) request.getSession().getAttribute("movieList");

    String param1;
    String param2;
    String param3;
    String param4;
    String param5;
    String param6;

    if(movies != null)
    {
        param1 = movies.getBrowseBy();
        param2 = movies.getKey();
        param3 = movies.getOrderBy();
        param4 = String.valueOf(movies.getDesc());
        param5 = String.valueOf(movies.getPage());
        param6 = String.valueOf(movies.getIpp());
    }   
    else
    {
        param1 = request.getParameter("browseBy");
        param2 = request.getParameter("key");
        param3 = request.getParameter("order");
        param4 = request.getParameter("desc");
        param5 = request.getParameter("page");
        param6 = request.getParameter("ipp");
    }
%>

<article>

    <table>
        <tbody>
            <tr>
                <td>
                        <%out.println("<a href='getMovies?browseBy=" + 
                        param1 + "&key=" + param2 + "&orderBy=title" + 
                        "&desc=false&page=" + param5 + "&ipp=" + param6 + 
                        "'>" + "Title Ascending</a> | ");%>
                </td> 

                <td> 

                        <%out.println("<a href='getMovies?browseBy=" + 
                        param1 + "&key=" + param2 + "&orderBy=title" + 
                        "&desc=true&page=" + param5 + "&ipp=" + param6 + 
                        "'>" + "Title Descending</a> | ");%>
                </td>

                <td>
                        <%out.println("<a href='getMovies?browseBy=" + 
                        param1 + "&key=" + param2 + "&orderBy=year" + 
                        "&desc=false&page=" + param5 + "&ipp=" + param6 + 
                        "'>" + "Year Ascending</a> | ");%>
                </td>

                <td>
                         <%out.println("<a href='getMovies?browseBy=" + 
                        param1 + "&key=" + param2 + "&orderBy=year" + 
                        "&desc=true&page=" + param5 + "&ipp=" + param6 + 
                        "'>" + "Year Descending</a> | ");%>               
                </td>

                </tr>
        </tbody>
    </table>

    <%
    
    
    /*
        Model.MovieList movies = 
            (Model.MovieList) request.getSession().getAttribute("movieList");
    */
        movies.printMovies(out); 
        request.getSession().setAttribute("movieList",movies);
    
   %>
    
</article>
