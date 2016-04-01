// This is an example of using JDBC to
// update a record

import java.sql.*;

public class JDBC3
{
    public static void main(String[] args) throws Exception
    {
        // Incorporate MySQL driver
        Class.forName("com.mysql.jdbc.Driver").newInstance();

        // Connect to the test database
        Connection connection = DriverManager.getConnection("jdbc:mysql:///sampledb", "testuser","password");

        // Prepare SQL statement template that's to be repeatedly execute
        String updateString = "update stars set first_name = ? where id = ?";
        PreparedStatement updateStars = connection.prepareStatement(updateString);

        // values for first and second "?" wildcard in statement template
        int[] ids = {755011,755017};
        String[] firstNames = {"New Arnlod","New Eddie"};

        // for each record in table, update to new values given
        for (int i = 0;i < ids.length; i++)
        {
            updateStars.setString(1,firstNames[i]);
            updateStars.setInt(2,ids[i]);
            updateStars.executeUpdate();
        }
    }
}
