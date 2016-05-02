import java.util.Collections;
import java.util.ArrayList;

public class Star
{
    // Class Variables
    private String id;
    private String firstName;
    private String lastName;
    private String DOB;
    private ArrayList<String> movies;

    public Star(String id, String first, String last, String date,
            ArrayList<String> movies)
    {
        this.id = id;
        firstName = first;
        lastName = last;
        DOB = date;
        this.movies = movies;
    }

    public Star()
    {
        this("","","","",new ArrayList<String>());
    }

    
    // Getter Method

    public String getId()
    {
        return id;    
    }

    public String getFullName()
    {
        return firstName + " "  + lastName;
    }
    
    public String getFirstName()
    {
        return firstName; 
    }

    public String getLastName()
    {
        return lastName; 
    }

    public String getDOB()
    {
        return DOB;    
    }
    
    public ArrayList<String> getMovies()
    {
        return movies;
    }

    // Setter Method
    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        // Split name 
        String first = name.substring(0,name.indexOf(' ')); 
        String last = name.substring(name.indexOf(' ')+1);

        if(first.equals(""))
        {
            setFirstName(last);
            setLastName(first);
        }
        else
        {
            setFirstName(first);
            setLastName(last); 
        }
    }
    
    public void setFirstName(String first)
    {
        firstName = first;
    }

    public void setLastName(String last)
    {
        lastName = last;
    }

    public void setDOB(String date)
    {
        DOB = date;
    }

    public void setMovies(ArrayList<String> movies)
    {
        this.movies = movies;
    }

    public void addMovie(String movieId)
    {
        movies.add(movieId);
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("Star - ");
        sb.append("ID: " + getId() + ",");
        sb.append("First Name: " + getFirstName() + ",");
        sb.append("Last Name: " + getLastName() + ",");
        sb.append("DOB: " + getDOB() + ",");
        sb.append("Movies: {");

        // Add all movies
        for(int i = 0; i < movies.size();i++)
        {
            sb.append(movies.get(i));
            if(i != movies.size() - 1)
                sb.append(",");    
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode()
    {
        String hashString = "";

        hashString = firstName + lastName + DOB;

        for(String m: movies)
            hashString += m;
        
        return hashString.hashCode();
    }

    public boolean equals(Object o)
    {
        if( o instanceof Star)
        {
            Star other = (Star) o;
            
            //Sort ArrayLists before comparing
            Collections.sort(movies);
            Collections.sort(other.movies); 
            
            return (firstName.equalsIgnoreCase(other.firstName) &&
                    lastName.equalsIgnoreCase(other.lastName) &&
                    DOB.equals(DOB) && movies.equals(other.movies));
        }

        return false;
    }
}
