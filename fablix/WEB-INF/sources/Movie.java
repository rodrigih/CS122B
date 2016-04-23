package Model;

import java.io.*;
import java.util.*;
import javax.servlet.jsp.JspWriter;

public class Movie
{
    int id;
    int year;
    String title;
    String director;
    String b_url;
    String t_url;
    ArrayList<String> stars;
    ArrayList<String> stars_id;
    ArrayList<String> genres;
    float price;

    // Constructor
    public Movie(int id, String title, int year, String director,
                        String b_url, String t_url)
    {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.b_url = b_url;
        this.t_url = t_url;
        this.price = 10;

        try
        {
            stars = MovieInfo.getMovieInfo("stars",id,false);
            stars_id = MovieInfo.getMovieInfo("stars",id,true);
            genres = MovieInfo.getMovieInfo("genres",id,false);
        }
        catch(Exception e)
        {
            System.out.println("Error");
            System.out.println(e.getMessage());
        }
    } 

    // Getter Methods
    public int getId()
    {
        return this.id;
    }

    public int getYear()
    {
        return this.year;
    }

    public String getTitle()
    {
        return this.title;
    }
    private void printImage(JspWriter out) throws IOException
    {
        // Have table/tbody in movieList.java
         out.println("\t\t<tr>\n\t\t\t<td rowspan=10>\n" +
                    "\t\t\t\t<img src=" + this.b_url + 
                    " height=320 width=280>\n" + 
                   "\t\t\t</td>\n\t\t</tr>");    
    }

    private void printInfo(JspWriter out, String column, String data) throws IOException
    {
        out.println("\n\t\t<tr>\n\t\t\t<td>" + column + 
                "\n\t\t\t</td>\n\t\t\t<td>" + data + 
                "\n\t\t\t</td>\n\t\t</tr>");        
    }

    private void printCartButton(JspWriter out) throws IOException
    {
        out.println("\n\t\t<form action='AddToCart' method='GET'>" + 
                "\n\t\t<tr>\n\t\t\t<td>\n\t\t\t\t" + 
                "<input type='hidden' name='name' value='" + title + "'>\n\t\t\t\t" + 
                "<input type='hidden' name='id' value='" + id + "'>\n\t\t\t\t" + 
                "<input type='hidden' name='price' value=10>" + 
                "<input type='number' name='quantity' value=1 min=1 required >\n\t\t\t\t" + 
                "<input type='submit' value='Add to cart'>\n\t\t\t" + 
                "</td>\n\t\t</tr>\n\t\t</form>");
    }
    
    private String createLink(String link,String value)
    {
        return "<a href='" + link + "'>" + value + "</a>";
    }
    //JspWriter out
    public void printHtml(JspWriter out) throws IOException
    {
        String star_string = "";
        String genre_string = "";

       for(int i = 0; i< genres.size();i++)
        {
            genre_string += genres.get(i);

            if(i != genres.size()-1)
                genre_string += " ,";
        
        }
        
        // First print out image
        printImage(out);

        // Now print out each info
        printInfo(out,"Title: ",
                createLink("getSingleMovie?movie_id=" + id,title));

        printInfo(out,"Year: ",String.valueOf(year));
        printInfo(out,"Director: ",director);
        printInfo(out,"Movie id: ",String.valueOf(id));
        printStars(out);
        printInfo(out,"Genres: ",genre_string);
        printInfo(out,"Price: ","$" + String.valueOf(price));
        printCartButton(out);
    }

    public void printStars(JspWriter out) throws IOException
    {
        String star_string = "";
        String link = "getStar?star_id="; 

        for(int i = 0; i < stars.size();i++)
        {
            star_string += createLink(link + stars_id.get(i),stars.get(i));
             if(i != stars.size()-1)
                star_string += " ,";
        }
        printInfo(out,"Stars: ",star_string); 
    }

    public void printGenres(JspWriter out) throws IOException
    {
        String genre_string = "";
        String link = "getMovies?browseBy=genre&key=";
        String endLink = "&orderBy=title&desc=false&page=1&ipp=5";    

        for(int i = 0; i < genres.size();i++)
        {
            genre_string += createLink(link + genres.get(i) + endLink,genres.get(i));
            if(i != genres.size()-1)
                genre_string += ",";
        }
        printInfo(out,"Genres: ",genre_string);
    }
    
    public void printSingleMovie(JspWriter out) throws IOException
    {
        // First print out image
        printImage(out);

        // Now print out each info
        printInfo(out,"Title: ",title);
        printInfo(out,"Year: ",String.valueOf(year));
        printInfo(out,"Director: ",director);
        printInfo(out,"Movie id: ",String.valueOf(id));
        printStars(out);
        printGenres(out);
        printInfo(out,"Trailer: ",createLink(t_url,t_url));
        printInfo(out,"Price: ","$" + String.valueOf(price));
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
        
        System.out.println("Stars_ids in movies:");
        for(String star: stars)
        {
            System.out.println(star);
        }

        System.out.println("Genres in movies:");
        for(String genre: genres)
        {
            System.out.println(genre);
        }
    }

    //Comparators for sorting Movies
    public static class MovieTitleComparatorAsc implements Comparator<Movie>
    {
        public int compare(Movie m1, Movie m2)
        {
            String title1 = m1.getTitle();
            String title2 = m2.getTitle();

            return title1.compareTo(title2); 
        }
    }

    public static class MovieTitleComparatorDesc implements Comparator<Movie>
    {
        public int compare(Movie m1, Movie m2)
        {
            String title1 = m1.getTitle();
            String title2 = m2.getTitle();

            return title2.compareTo(title1); 
        }
    }

    public static class MovieYearComparatorAsc implements Comparator<Movie>
    {
        public int compare(Movie m1, Movie m2)
        {
            return ((Integer)m1.getYear()).compareTo(m2.getYear());
        }
    }

    public static class MovieYearComparatorDesc implements Comparator<Movie>
        {
        public int compare(Movie m1, Movie m2)
        {
            return ((Integer)m2.getYear()).compareTo(m1.getYear());
        }
    }
}


