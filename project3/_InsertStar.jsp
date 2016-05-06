<%@ include file="_dash_header.html"%>

<article>

    <form action="InsertStar" method="post">

        First Name: <input type="text" name="first" > <br>
        Last Name: <input type="text" name="last"> <br>
        DOB: <input type="date" name="dob"> <br>
        Photo URL: <input type="url" name="url"> <br>
        <input type="submit" value="Insert Star">
    </form>

    <%
    String message = (String) request.getAttribute("message");
    if(message != null)
        out.println(message);
        %>
</article>
