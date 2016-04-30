package Model;

import java.util.*;
import java.sql.*;

public class DBConnection
{
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
  
}
