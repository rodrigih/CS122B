<%@ include file="header.html" %>

<%@ page import="Model.*" %>

<article>

    <table>
        <tbody>

        <%
            Model.Star star = (Model.Star) request.getAttribute("star");
            star.printHtml(out);
        %>

        </tbody>
    </table>
</article>
