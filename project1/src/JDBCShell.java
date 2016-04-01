import java.util.Scanner;
import java.sql.*;

public class JDBCShell
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
                connection = DriverManager.getConnection("jdbc:mysql:///moviedb",user,pass);
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
        in.close();
    }
    
    public static printMovies()
    {
        String star = promptInput("Enter star:");
        Connection connection = 
    }

    public static void main(String[] args) throws Exception
    {

        in.useDelimiter("\\n"); 
        Class.forName("com.mysql.jdbc.Driver").newInstance(); // Incorporate mySQL driver
        userLogin();

        in.close();
        
    }

}
