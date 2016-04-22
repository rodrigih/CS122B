<%@ include file="header.html" %>

<%@ page import="Model.*,
java.util.ArrayList"
%>

<%
        String param1 = request.getParameter("browseBy");
        String param2 = request.getParameter("key");
        String param3 = request.getParameter("order");
        String param4 = request.getParameter("desc");
        String param5 = request.getParameter("page");
        String param6 = request.getParameter("ipp");
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
    
    
        Model.MovieList movies = 
            (Model.MovieList) request.getSession().getAttribute("movieList");
            /*
        for(Model.Movie movie: movies)
        {
            out.println("\n\t<table>\n\t<tbody>");
            movie.printHtml(out);
            out.println("\n\t</table>\n\t</tbody>");
        } 
        */
        movies.printMovies(out); 
        request.getSession().setAttribute("movieList",movies);
    
   %>
    
</article>
