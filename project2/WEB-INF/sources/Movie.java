package Model;

import java.io.*;
import java.util.*;

public class Movie
{
    int id;
    int year;
    String title;
    String director;
    String b_url;
    String t_url;
    ArrayList<String> stars;
    ArrayList<String> genres;

    public Movie(int id, String title, int year, String director,
                        String b_url, String t_url)
    {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.b_url = b_url;
        this.t_url = t_url;

        try
        {
            stars = MovieInfo.getMovieInfo("stars",id);
            genres = MovieInfo.getMovieInfo("genres",id);
        }
        catch(Exception e)
        {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
    }

    private void printImage()
    {
         System.out.println("\t<table>\n\t\t<tbody>\n" + 
                    "\t\t<tr>\n\t\t\t<td rowspan=8>\n" +
                    "\t\t\t\t<img src=" + this.b_url + 
                    " height=320 width=280>\n" + 
                   "\t\t\t</td>\n\t\t</tr>");    
    }

    private void printInfo(String column, String data)
    {
        System.out.println("\n\t\t<tr>\n\t\t\t<td>" + column + 
                "\n\t\t\t</td>\n\t\t\t<td>" + data + 
                "\n\t\t\t</td>\n\t\t</tr>");        
    }
    //PrintWriter out
    public void printHtml()
    {
        String star_string = "";
        String genre_string = "";

        // Construct star and genre string
        for(int i = 0; i < stars.size(); i++)
        {
            star_string += stars.get(i);

            if(i != stars.size()-1)
                star_string += " ,";
        }

        for(int i = 0; i< genres.size();i++)
        {
            genre_string += genres.get(i);

            if(i != genres.size()-1)
                genre_string += " ,";
        
        }
        
        // First print out image
        printImage();

        // Now print out each info
        printInfo("Title: ",title);
        printInfo("Year: ",String.valueOf(year));
        printInfo("Director: ",director);
        printInfo("Movie id: ",String.valueOf(id));
        printInfo("Stars: ",star_string);
        printInfo("Genres: ",genre_string);

        //Close tags
        System.out.println("\n\t\t</tbody>\n\t<table>");
    }
    
    public void printStats()
    {
        System.out.println("Id: " + id + "\n" +  
                    "Title: " + title + "\n" +
                    "Year: " + year + "\n" +
                    "b_url: " + b_url + "\n" +
                    "t_url: " + t_url);

        System.out.println("Stars in movies:");
        for(String star: stars)
        {
            System.out.println(star);
        }
        
        for(String genre: genres)
        {
            System.out.println(genre);
        }
    }
}
