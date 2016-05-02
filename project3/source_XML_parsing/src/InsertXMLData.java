import java.util.HashMap;
import java.sql.*;

public class InsertXMLData
{

    public static Connection initializeConnection()
    {
        Connection conn; 

        try
        {
            //Connect to database 
        }catch(SQLException e)
        {
            e.printStackTrace();
        }

        return conn;
    }
    
    public static void insertMovies(HashMap<String,Movie> idToMovie, 
            HashMap<String,Integer movieCache)
    {
        HashMap<Integer,ArrayList<Integer>> genreIDToMovieID = new HashMap<Star,ArrayList<Integer>>();
        HashMap<String,Integer> genreCache = new HashMap<String,Integer>();

        for(Movie m: idToMovie.values())
        {
            /*
             * Create Movie Statements
             * Add genre to GenreCache (with arbitrary ID at first)
            */
        }
            /*
             * Execute batch Movie Statements
             * Put Movies in MovieCache 
             */

        for(String genre: genreCache.keys())
        {
            /*
             * Check if genre exist in database:
             *      - If it is: 
             *          - get id from cache (or DB if not in cache)
             *          - update genreCache with actual id (genreCache.put(genre,id))
             *          - update genreIDToMovieID
             *      - If not:
             *          - Insert genre to DB
             *          - get id from DB 
             *          - update genreCache with actual id (genreCache.put(genre,id))
             *          - update genreIDToMovieID
             *
             */
         }

            /* 
             *  Update genres_in_movies using genreID
             */
    }

    public static void insertStars(HashMap<String,Star> nameToStar, 
            HashMap<String,Integer> movieCache)
    {
        HashMap<Integer,ArrayList<Integer>> starIDToMovieID = new HashMap<Star,ArrayList<Integer>>();
        int currentID;

        for(Star s: nameToStar.values())
        {
            for(String movie: s.movies())
            {
                currentID = movieCache.get(movie);        
                /*
                 * Create Star statements
                 * update starIDToMovieID
                 */
            }
        }

        /*
         * Execute batch Star statements
         *
         * Update stars_in_movies using starIDToMovieID
         */

    }

    public static void main(String[] args)
    {
        if(args != 3)
        {
            //Print error message
        }

        //Initialize Parser
        MovieParser movieParser = new MovieParser();
        StarParser starParser = new StarParser(); 
        CastParser castParser = new CastParser();

        // Parse documents and retrieve results
        HashMap<String,Movie> idToMovie = movieParser.parseDocument(args[0]);
        HashMap<String,Star> nameToStar = starParser.parseDocument(args[1]);

        nameToStar = castParser.parseDocuement(args[2],nameToStar);

        HashMap<String,Integer> MovieCache = new HashMap<String,Integer>();

        Connection conn = initializeConnection();

        insertMovies(idToMovie,MovieCache);
        insertStars(nameToStar, MovieCache);
       }
}
