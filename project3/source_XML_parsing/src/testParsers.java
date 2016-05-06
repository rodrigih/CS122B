import java.util.HashMap;

public class testParsers
{

    public static void main(String[] args)
    {
        if(args.length() != 3)
            System.out.println("Invalid arguements. Put movie, actor, cast files");

        // Test Movie Parser
        System.out.println("Testing MovieParser");
        MovieParser mp = new MovieParser();
        HashMap<String,Movie> movies = mp.parseDocument(args[0]);

        for(Movie m: movies.values())
            System.out.println("\t" + m);

        // Test Star Parser
        System.out.println("\nTesting StarParser");
        StarParser sp = new StarParser();
        HashMap<String,Star> stars = sp.parseDocument(args[1]);

        for(Star s: stars.values())
            System.out.println("\t" + s);

        // Test Cast Parser
        System.out.println("\nTesting CastParser (includes results from StarParser)");
        CastParser cp = new CastParser();
        stars = cp.parseDocument(args[2],stars);

        for(Star s: stars.values())
            System.out.println("\t" + s);
    }
}
