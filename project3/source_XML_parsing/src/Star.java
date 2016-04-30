public class Star
{
    // Class Variables
    private int id;
    private String firstName;
    private String lastName;
    private String DOB;
    private String photo_url;

    public Star(int id, String first, String last, String date, String url)
    {
        this.id = id;
        firstName = first;
        lastName = last;
        DOB = date;
        photo_url = url;
    }

    public Star()
    {
        this(0,"","","","");
    }

    
    // Getter Method

    public int getId()
    {
        return id;    
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

    public String getPhoto()
    {
        return photo_url; 
    }

    // Setter Method
    public void setId(int id)
    {
        this.id = id;
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

    public void setPhoto(String url)
    {
        photo_url = url;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();

        sb.append("Star - ");
        sb.append("ID: " + getId() + ",");
        sb.append("First Name: " + getFirstName() + ",");
        sb.append("Last Name: " + getLastName() + ",");
        sb.append("DOB: " + getDOB() + ",");
        sb.append("Photo URL: " + getPhoto());

        return sb.toString();
    }
}
