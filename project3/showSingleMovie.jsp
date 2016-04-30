<%@ include file="header.html" %>

<%@ page import="Model.*" %>

<article>

    <table>
        <tbody>

        <%
            Model.Movie movie = (Model.Movie) request.getAttribute("movie");
            movie.printSingleMovie(out);
        %>

        </tbody>
    </table>
</article>
