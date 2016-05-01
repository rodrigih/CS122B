import java.util.Collections;
import java.util.ArrayList;

public class Movie
{
    // Class Variables
    private String title;
    private String year;
    private ArrayList<String> directors;
    private ArrayList<String> genres;

    //Constructor
    public Movie(String title, String year, 
            ArrayList<String> directors, ArrayList<String> genres)
    {
        this.title = title;
        this.year = year;
        this.directors = directors;
        this.genres = genres;
    }

    public Movie()
    {
        this("","",new ArrayList<String>(),new ArrayList<String>());
    }

    //Getter Methods

    public String getTitle()
    {
        return title;
    }


    public String getYear()
    {
        return year;
    }


    public ArrayList<String> getDirector()
    {
        return directors;
    }

    public ArrayList<String> getGenres()
    {
        return genres;
    }
    //Setter Methods
    
    public void setTitle(String title)
    {
        this.title = title;
    }


    public void setYear(String year)
    {
        this.year = year; 
    }

    public void setDirector(ArrayList<String> directors)
    {
        this.directors = directors; 
    }

    public void addDirector(String director)
    {
        this.directors.add(director);
    }

    public void setGenres(ArrayList<String> genres)
    {
        this.genres = genres;
    }

    public void addGenre(String genre)
    {
        this.genres.add(genre);
    }
    // Class Methods
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Movie - ");
        sb.append("Title: " + getTitle() + ",");
        sb.append("Year: " + getYear() + ",");
        sb.append("Director: {");

        // Add directors
        for(int i = 0; i < directors.size(); i++)
        {
            sb.append(directors.get(i));
            if( i != directors.size()-1)
                sb.append(",");
            else
                sb.append("}, ");
        }


        // Add Genres
        sb.append("Genres: {");
        for(int i = 0; i < genres.size(); i++)
        {
            sb.append(genres.get(i));
            if( i != genres.size()-1)
                sb.append(",");
            else
                sb.append("}, ");
        }

        return sb.toString();
    }

    public int hashCode()
    {
        String hashString = "";

        hashString = title + year;
        for(String dir: directors)
            hashString += dir;
        for(String genre: genres)
            hashString += genre;

        return hashString.hashCode();
    }

    public boolean equals(Object o)
    {
        if( o instanceof Movie)
        {
            Movie other = (Movie) o;

            // Sort ArrayLists before comparing
            Collections.sort(directors);
            Collections.sort(genres);
            Collections.sort(other.directors);
            Collections.sort(other.genres);

            return (title.equals(other.title) &&
                    year.equals(other.year) &&
                    directors.equals(other.directors) &&
                    genres.equals(other.genres));
        }
        return false;
    }
}
