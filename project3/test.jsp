<%@ include file="header.html" %>

<%@ page import="Model.*,
java.util.ArrayList"%> 

<body>
<%
ArrayList<Model.Movie> movies = (ArrayList<Model.Movie>) request.getSession().getAttribute("movieList");
String param = (String) request.getSession().getAttribute("param");

out.println(param);

    if(movies == null)
    {
        out.println("Movie is null");
    }
    else
    {
        for(Model.Movie movie: movies)
        {
            movie.printHtml(out);
        }
    }
%>
</body>
