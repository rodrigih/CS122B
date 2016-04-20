<%@ include file="header.html" %>

<%@ page import="Model.*,
java.util.ArrayList"
%>

<article>

<table>
    <tbody>

    <%
        String browseBy = request.getParameter("browseBy");
        String key = request.getParameter("key");
        String orderBy = request.getParameter("order");
        String desc = request.getParameter("desc");
        String page = request.getParameter("page");

        Model.MovieList(browseBy,key,orderBy,desc,page);


        

   %>
    
    </tbody>
</table>
</article>
