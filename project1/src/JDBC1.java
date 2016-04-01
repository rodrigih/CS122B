// This file is an example of using JDBC to obtain metadata
// and table contents
import java.sql.*;

public class JDBC1
{
    public static void main(String[] args) throws Exception
    {
        // Incorporate mySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        // Connect to the test database
        Connection connection = DriverManager.getConnection("jdbc:mysql:///sampledb","testuser","password");

        // Create and execute a SQL statement to select all records from "stars" table
        Statement select = connection.createStatement();
        ResultSet result = select.executeQuery("Select * from stars");

        // Get metadata from stars; print # of attributes in table
        System.out.println("The results of the query");
        ResultSetMetaData metadata = result.getMetaData();
        System.out.println("There are " + metadata.getColumnCount() + " columns");

        // Print type of each attribute
        for(int i = 1; i <= metadata.getColumnCount(); i++)
           System.out.println("Type of column " + i + " is " + metadata.getColumnTypeName(i)); 
        // Print table's contents, field by field
        while (result.next())
        {
            System.out.println("Id = " + result.getInt(1));
            System.out.println("Name = " + result.getString(2) + result.getString(3));
            System.out.println("DOB = " + result.getString(4));
            System.out.println("photoURL = " + result.getString(5));
            System.out.println();
        }
    }
}
