import Model.*;
import java.util.*;

public class test
{
    public static void main(String[] args)
    {
        ArrayList<Model.Movie> movies = Model.Search.createList("star");

        for(Model.Movie m: movies)
        {
            m.printStats();
        }
    }

}
