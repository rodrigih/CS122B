package Model;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.jsp.JspWriter;

public class BrowseInfo 
{
    public static ArrayList<String> getGenres()
    {
        ArrayList<String> genres = new ArrayList<String>();

        Connection connection = DBConnection.connectToDatabase();   

        try
        {
            Statement statement = connection.createStatement();
            String query = "SELECT name FROM genres ORDER BY name;";
            ResultSet rs = statement.executeQuery(query);

            while(rs.next())
            {
                genres.add(rs.getString("name"));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return genres;
    }

    public static String linkString(String link, String word)
    {
        return "\n\t\t<a href='" + link + "'>" + word + "</a>";
    }

    private static void printGenres(JspWriter out) throws IOException
    {
        String link;
        ArrayList<String> genres = getGenres();

        out.println("\n\t<div id='genre'>" + 
                "\n\t\t<table>\n\t\t\t<tbody><thead><h3>Genres</h3></thead>"); 

        // Print each genres in rows. Number of rows TBA
        for(int i = 0; i < genres.size(); i++)
        {
            link = "showMovies?browseBy=genre&key=" + genres.get(i) + 
                   "&order=title&desc=false&page=1";


            if((i%6 == 0) && i != 0)
                out.println("\n\t\t\t</tr>");

            if(i%6 == 0)
                out.println("\n\t\t\t<tr>");

            out.println("\n\t\t\t<td>" + linkString(link,genres.get(i)) + "</td>");

       }

        out.println("\n\t\t\t</tr>\n\t\t\t</tbody>\n\t\t</table>\n\t</div>");
    }

    private static void printLetters(JspWriter out) throws IOException
    {
        String numAlph = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 

        out.println("\n\t<div id='Letters'>" + 
                "\n\t\t<table>\n\t\t\t<tbody><thead><h3>Letters</h3></thead>" + 
                "\n\t\t\t<tr>\n\t\t\t<td>"); 
        
        String link;

        for(int i = 0; i < numAlph.length(); i++)
        {
            link = "showMovies?browseBy=letter&key=" + numAlph.charAt(i) + 
                   "%&order=title&desc=false&page=1";

            out.println(linkString(link,Character.toString(numAlph.charAt(i))) + " | ");
        }

        out.println("\n\t\t\t</tr>\n\t\t\t</tbody>\n\t\t</table>\n\t</div>");
    }

    public static void printHtml(JspWriter out) throws IOException
    {
        out.println("\n<article>");

        printGenres(out);
        printLetters(out);

        out.println("\n</article>");
    }
}
