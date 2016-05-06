<%@ include file="_dash_header.html"%>

<article>

    <h3> Movie Information</h3>

    <form action="InsertMovie" method="post">

        Title: <input type="text" name="title" > <br>
        Year: <input type="number" name="year"> <br>
        Director: <input type="name" name="dir"> <br>
        Trailer URL: <input type="url" name="turl"> <br>
        Banner URL: <input type="url" name="burl"> <br>
        Star First Name: <input type="text" name="first"> <br>
        Star Last Name: <input type="text" name="last"> <br>
        Genre: <input type="text" name="genre"> <br>
        <input type="submit" value="Insert Movie">
    </form>
    <%
    String message = (String) request.getAttribute("message");
    if(message != null)
        out.println(message);
        %>
</article>
