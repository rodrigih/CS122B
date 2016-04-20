package Model;

import java.util.*;
import java.sql.*;

public class MovieInfo
{
    /*
    public static Connection connectToDatabase()
    {

        Connection connection = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/moviedb";
            connection = DriverManager.getConnection(url,"testuser","testpass");

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return connection;
    }
   */ 
    private static ArrayList<Integer> getIds(Connection connection, String table, int movieid)
    {
        ArrayList<Integer> results = new ArrayList<Integer>();
        
        try
        {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + table + "_in_movies WHERE movie_id=" + movieid + ";";
            ResultSet rs = statement.executeQuery(query);

            while(rs.next())
            {
                if(table.equals("stars"))
                    results.add(rs.getInt("star_id")); 
                else
                    results.add(rs.getInt("genre_id"));
            }

            statement.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return results;
    }

    private static String getInfoFromId(Connection connection,String table, int id)
    {
        String result = "";

        try
        {
            Statement statement = connection.createStatement(); 
            String query = "SELECT * FROM " + table + " WHERE id=" + id + ";";

            ResultSet rs = statement.executeQuery(query);

            while(rs.next())
            {
                if(table.equals("stars"))
                    result = rs.getString("first_name") + " " + rs.getString("last_name");
                else
                    result = rs.getString("name");
            }

            statement.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }
    
    public static ArrayList<String> getMovieInfo(String info,int movieid) throws SQLException
    {
        Connection connection = DBConnection.connectToDatabase();
        ArrayList<String> objs = new ArrayList<String>();
        ArrayList<Integer> obj_ids = new ArrayList<Integer>();

        if(connection == null)
        {
            System.out.println("Error connecting to Database");
        }
        
        obj_ids = getIds(connection,info,movieid);

        for(int id:obj_ids)
        {
            objs.add(getInfoFromId(connection,info,id));
        }

        return objs;
    }
}
