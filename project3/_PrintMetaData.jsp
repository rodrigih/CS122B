<%@ include file="_dash_header.html"%>

<%@ page import="Model.*,
 java.sql.*,
 javax.sql.*,
 java.io.IOException,
 javax.servlet.http.*,
 javax.servlet.*,
 javax.servlet.jsp.JspWriter" %>

<article>

    <%!
    public void printMetaData(JspWriter out) throws IOException
    {
        Connection conn = Model.DBConnection.connectToDatabase();

        try
        {
            DatabaseMetaData metadata = conn.getMetaData();
            printDBMetaData(out,metadata);
            printTableMetaData(out,conn);
            conn.close();
        }
        catch(SQLException e)
        {
            out.println(e.getMessage());
            return;
        }
    }

    /* Prints out the tables present in the current database
     *
     * @param tables the tables present in the database
     * @return none
     */
    public static void printDBMetaData(JspWriter out,DatabaseMetaData metadata)
        throws SQLException, IOException
    {
        ResultSet tables = metadata.getTables(null,null,null,null); // Parameters all null to get
                                                           // all tables in database

        out.println("TABLES IN DATABASE: ");
        out.println("<br>");
        while(tables.next())
        {
            out.println("\t" + tables.getString("TABLE_NAME"));
            out.println("<br>");
        }
        out.println("<br>");
    }

    /* Prints out the names of the columns and its type for 
     * each table in the database
     *
     * @param none
     * @return none
     */
    public static void printTableMetaData(JspWriter out,Connection conn)
        throws SQLException, IOException
    {
        Statement select = conn.createStatement();
        ResultSet table = null;
        ResultSetMetaData metadata = null;
        String[] table_names = {"creditcards","customers","genres",
            "genres_in_movies","movies","sales","stars","stars_in_movies"};

        for(String name:table_names)
        {
            out.println("DATA FOR " + name + " TABLE");
            out.println("<br>");
            table = select.executeQuery("SELECT * FROM " + name);
            metadata = table.getMetaData();
            for(int i = 1; i <= metadata.getColumnCount();i++)
            {
                out.println("\tCOLUMN NAME: " + metadata.getColumnLabel(i));
                out.println("<br>");
                out.println("\t\tTYPE: " + metadata.getColumnTypeName(i));
                out.println("<br>");
            }
            out.println("<br>");
        }
    }

    %>
    <%
    try
    {
        printMetaData(out);
    }
    catch(IOException e)
    {
        out.println(e.getMessage());
    }
    %>
</article>
