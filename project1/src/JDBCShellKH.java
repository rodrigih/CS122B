import java.util.Scanner;
import java.sql.*;

public class JDBCShellKH
{
    // Variables for shell
    static enum mainCommands{p,i,d,m,e,x,q};
    static enum insertCommands{s,c,x};

    static Scanner in = new Scanner(System.in);
    static Connection connection;
    // Helper functions

    /* Given a prompt, prints out the prompt and reads
     * the user input.
     * Input is returned as string.
     *
     * @param prompt The promp you want to print to the console
     * @return input from the console
     */
    public static String promptInput(String prompt)
    {
        System.out.println(prompt);
        return in.next();
    }

    /* Initiates the login for the SQL database.
     * If user input is incorrect, it allows the user to try again
     * or quit the program
     *
     * @param None
     * @return None
     */
    public static void userLogin()
    {
        String user;
        String pass;
        boolean quit = false;
        
        while(!quit)
        {
            // Ask user to username and password
            System.out.println("Enter login information");
            user = promptInput("Username: ");
            pass = promptInput("Password: ");

            // Try connecting to database using entered information
            try
            {
                connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/moviedb",user,pass);
                quit = true;
                System.out.println("Login successful.\n");
            }
            // Catch exception when user/pass does not exist
            catch(java.sql.SQLException e)
            {
                System.out.println("Incorrect login info");

                // Continually ask if user want to try logging in again
                // or want to quit the program.
                do
                {
                    user = promptInput("Quit? (Y/N)");
                    if (user.compareToIgnoreCase("N")== 0)
                    {
                            break;
                    }
                    else if (user.compareToIgnoreCase("Y")==0)
                    {
                        System.out.println("Closing program");
                        in.close();
                        quit = true;
                        break;
                    }
                    else
                    {
                        System.out.println("Invalid Input. Please enter 'Y' or 'N'");
                    }
                }while(true); // Runs until user enters valid input (Y or N)
            }
        }
//        in.close();
    }
    //delete customer and handle query commands.
    //ill be given customer name and sql 
    public static void printMovies(String input) throws Exception
    {
        Statement select = connection.createStatement();
        ResultSet result;
        if(input.equals("f")){
        	String fName = promptInput("Enter first name: ");
            result = 
            		select.executeQuery(
            				"SELECT * FROM movies WHERE id IN "
            				+ "(SELECT movie_id FROM stars_in_movies a natural JOIN stars b"
            				+ " WHERE a.star_id = b.id and b.first_name = \""+fName+"\")");
        }
        else if(input.equals("l")){
        	String lName = promptInput("Enter last name: ");
            result = 
            		select.executeQuery(
            				"SELECT * FROM movies WHERE id IN "
            				+ "(SELECT movie_id FROM stars_in_movies a natural JOIN stars b"
            				+ " WHERE a.star_id = b.id and b.last_name = \""+lName+"\")");
        }
        else if(input.equals("b")){
        	String fName = promptInput("Enter first name: ");
        	String lName = promptInput("Enter last name: ");
            result = 
            		select.executeQuery(
            				"SELECT * FROM movies WHERE id IN "
            				+ "(SELECT movie_id FROM stars_in_movies a natural JOIN stars b"
            				+ " WHERE a.star_id = b.id and"
            				+ " b.first_name = \""+fName+"\" and b.last_name = \""+lName+"\")");
        }
        else{
        	String id = promptInput("Enter ID: ");
            result = 
            		select.executeQuery(
            				"SELECT * FROM movies WHERE id IN "
            				+ "(SELECT movie_id FROM stars_in_movies a natural JOIN stars b"
            				+ " WHERE a.star_id = \""+id+"\")");
        }
        while(result.next()){
        	System.out.println("\nId = " + result.getInt(1));
        	System.out.println("Title = " + result.getString(2));
        	System.out.println("Year = " + result.getInt(3));
        	System.out.println("Director = "+result.getString(4));
        	System.out.println("bannerURL = "+result.getString(5));
        	System.out.println("trailerURL = "+result.getString(6));
        }
    }
    public static void deleteCustomer() throws Exception{
    	String ccID = promptInput("Enter the customer's credit card ID: ");
    	Statement update = connection.createStatement();
    	int result = update.executeUpdate("DELETE FROM customers "
    			+ "where cc_id = \""+ccID+"\"");
    	System.out.println("Records deleted: "+result);
    }
    
    public static void executeSQLCommand(String input) throws Exception{
    	String SQLCommand = promptInput("Enter your SQL command: ");
    	Statement command = connection.createStatement();
    	if (input.equals("select")){
            ResultSet result;
            result = command.executeQuery(SQLCommand);
            ResultSetMetaData resultMD = result.getMetaData();
            int count = resultMD.getColumnCount();
            while(result.next()){
            	System.out.println(result.getString(1));
            	if (count < 2){
            		break;
            	}
            	for(int i=2; i<=count; i++){
            		System.out.println(" | ");
            		System.out.println(result.getString(i));
            	}
            }
    	}
    	else if(input.equals("update")){
        	int result = command.executeUpdate(SQLCommand);
        	System.out.println("Records updated: "+result);
    	}
    	else if(input.equals("insert")){
        	int result = command.executeUpdate(SQLCommand);
        	System.out.println("Records inserted: "+result);		
    	}else{
        	int result = command.executeUpdate(SQLCommand);
        	System.out.println("Records deleted: "+result);
    	}
    }

    public static void main(String[] args) throws Exception
    {
        in.useDelimiter("\\n"); 
        Class.forName("com.mysql.jdbc.Driver").newInstance(); // Incorporate mySQL driver
        userLogin();
        deleteCustomer();
//        printMovies("b");
        in.close();
    }

}
