import java.util.HashMap;

public class testMovieParser
{

    public static void main(String[] args)
    {
        MovieParser p = new MovieParser();
        HashMap<String,Movie> movies = p.parseDocument("../stanford-movies/mains243.xml");
    }
}
