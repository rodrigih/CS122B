package Model;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspWriter;

public class Star
{
    int id;
    String first_name;
    String last_name;
    String dob;
    String url;
    ArrayList<String> movies;
    ArrayList<String> movies_id;

    public Star(int id)
    {
        this.id = id;

        String[] info = StarInfo.getStarInfo(id);
        this.first_name = info[0];
        this.last_name = info[1];
        this.dob = info[2];
        this.url = info[3];

        try
        {
            movies = StarInfo.getMovies(id,false);
            movies_id = StarInfo.getMovies(id,true); 
        }
        catch(Exception e)
        {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
    }

    private void printImage(JspWriter out) throws IOException
    {
        out.println("\t\t<tr>\n\t\t\t<td rowspan=5>\n" + 
                "\t\t\t\t<img src=" + url + 
                "height=320 width=280\n" + 
                "\t\t\t</td>\n\t\t</tr>");
    }

    private void printInfo(JspWriter out, String column, String data) throws IOException
    {
        out.println("\n\t\t<tr>\n\t\t\t<td>" + column + 
                "\n\t\t\t</td>\n\t\t\t<td>" + data + 
                "\n\t\t\t</td>\n\t\t</tr>");
    }
    
    private String createLink(String link, String value)
    {
        return "<a href='" + link + "'>" + value + "</a>";
    }
    
    public void printMovies(JspWriter out) throws IOException
    {
        String movie_string = "";
        String link = "getSingleMovie?movie_id=";

        for(int i = 0; i < movies.size();i++)
        {
            movie_string += createLink(link + movies_id.get(i),movies.get(i));
                if( i != movies.size()-1)
                    movie_string += ",";
        }

        printInfo(out,"Movies: ",movie_string);

    }
    public void printHtml(JspWriter out) throws IOException
    {
        printImage(out);
        printInfo(out,"Name: ",first_name + " " + last_name);
        printInfo(out,"ID: ",String.valueOf(id));
        printInfo(out,"Date of Birth: ",dob); 
        printMovies(out);
    }
}
