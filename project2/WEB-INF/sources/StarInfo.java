package Model;

import java.util.*;
import java.sql.*;

public class StarInfo
{
    private static ArrayList<Integer> getIds(Connection connection, int starId)
    {
        ArrayList<Integer> results = new ArrayList<Integer>();

        try
        {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM stars_in_movies WHERE star_id=" + starId + ";";
            ResultSet rs = statement.executeQuery(query);

            while(rs.next())
            {
                results.add(rs.getInt("movie_id"));
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

    private static String getMoviesFromId(Connection connection, int id)
    {
        String result = "";

        try
        {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM movies WHERE id=" + id + ";";

            ResultSet rs = statement.executeQuery(query);
            rs.first();
            result = rs.getString("title");

            statement.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public static ArrayList<String> getMovies(int starId, boolean returnID) throws SQLException
    {
        Connection conn = DBConnection.connectToDatabase();
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Integer> ids = new ArrayList<Integer>();

        if(conn == null)
        {
            System.out.println("Error connecting to Database");
        }

        ids = getIds(conn,starId);

        if(returnID)
        {
            for(int id: ids)
            {
                result.add(String.valueOf(id));
            }
        }
        else
        {
            for(int id: ids)
            {
                result.add(getMoviesFromId(conn,id));
            }
        }

        conn.close();
        return result;
    }

    public static String[] getStarInfo(int starId)
    {
        String[] result = new String[4];

        Connection conn = DBConnection.connectToDatabase();
        if(conn == null)
            System.out.println("Error connecting to Database");

        try
        {
            Statement statement = conn.createStatement();
            String query = "SELECT * FROM stars WHERE id=" + starId + ";";
            ResultSet rs = statement.executeQuery(query);

            rs.first();
            result[0] = rs.getString("first_name");
            result[1] = rs.getString("last_name");
            result[2] = rs.getString("dob");
            result[3] = rs.getString("photo_url");

            conn.close();
            statement.close();
            rs.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace(); 
        }

        return result;
    }
}
