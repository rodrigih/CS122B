import java.util.ArrayList;

public class Movie
{
    // Class Variables
    private int id;
    private String title;
    private int year;
    private ArrayList<String> directors;
    private ArrayList<String> genres;
    private String b_url;
    private String t_url;

    //Constructor
    public Movie(int id, String title, int year, ArrayList<String> directors,
            ArrayList<String> genres,String b_url, String t_url)
    {
        this.id = id;
        this.title = title;
        this.year = year;
        this.directors = directors;
        this.genres = genres;
        this.b_url = b_url;
        this.t_url = t_url;
    }

    public Movie()
    {
        this(0,"",0,new ArrayList<String>(),new ArrayList<String>(),"","");
    }

    //Getter Methods

    public int getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }


    public int getYear()
    {
        return year;
    }


    public ArrayList<String> getDirectors()
    {
        return directors;
    }

    public ArrayList<String> getGenres()
    {
        return genres;
    }

    public String getB_url()
    {
        return b_url;
    }


    public String getT_url()
    {
        return t_url;
    }

    //Setter Methods
    
    public void setId(int id)
    {
        this.id = id; 
    }

    public void setTitle(String title)
    {
        this.title = title;
    }


    public void setYear(int year)
    {
        this.year = year; 
    }

    public void setDirectors(ArrayList<String> directors)
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

    public void setB_url(String b_url)
    {
        this.b_url = b_url; 
    }

    public void setT_url(String t_url)
    {
        this.t_url = t_url; 
    }

    // Class Methods
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Movie - ");
        sb.append("Id: " + getId() + ",");
        sb.append("Title: " + getTitle() + ",");
        sb.append("Year: " + getYear() + ",");
        
        // Add Directors
        sb.append("Directors: {");
        for(int i = 0; i<directors.size();i++)
        {
            sb.append(directors.get(i));
            if(i != directors.size()-1)
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
 
        sb.append("Banner URL: " + getB_url() + ",");
        sb.append("Trailer URL: " + getT_url());

        return sb.toString();
    }
}
