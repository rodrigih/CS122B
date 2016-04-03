import java.util.Scanner;
import java.sql.*;

public class JDBCShell
{
    // Variables for shell
    //static enum mainCommands{p,i,d,m,e,x,q};
    //static enum insertCommands{s,c,x};
    
    static String MainMenu = 
        "MAIN MENU\n" + 
        "\tp - Prints movies given a star\n" +  
        "\ti - Insert customer or star into database\n" + 
        "\td - Delete customer from database\n" + 
        "\tm - Print out metadata from database\n" + 
        "\te - Enter a SQL command\n"+ 
        "\tx - Exit menu and return to login\n"+ 
        "\tq - Exit program\n";

    static String PrintMenu = 
       "PRINT MENU\n" + 
       "\tf - Search by first name\n" +
       "\tl - Search by last name\n" +
       "\tb - Search by first and last name\n" +
       "\ti - Search by Star ID\n" +
       "\tx - Go back to main menu\n";

    static String InsertMenu = 
        "INSERT MENU\n" + 
        "\ts - Insert star\n" +
        "\tc - Insert customer\n" +
        "\tx - Go back to main menu\n";

    static Scanner in = new Scanner(System.in);
    static Connection connection;
    // Helper functions

    /* Given a prompt, prints out the prompt and reads
     * the user input.
     * Input is returned as string.
     *
     * @param prompt - the prompt you want to print to the console
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
     * @return 1 if login successful, 0 if user wants to quit
     */
    public static boolean userLogin() throws SQLException
    {
        String user;
        String pass;
        
        while(true)
        {
            // Ask user to username and password
            System.out.println("Enter login information");
            user = promptInput("Username: ");
            pass = promptInput("Password: ");

            // Try connecting to database using entered information
            try
            {
                connection = DriverManager.getConnection("jdbc:mysql:///moviedb",user,pass);
                System.out.println("Login successful.\n");
                return true;
            }
            // Catch exception when user/pass does not exist
            catch(java.sql.SQLException e)
            {
                System.out.println(e.getMessage());

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
                        connection.close();
                        return false;
                    }
                    else
                    {
                        System.out.println("Invalid Input. Please enter 'Y' or 'N'");
                    }
                }while(true); // Runs until user enters valid input (Y or N)
            }
        }
    }
    
    /* Prints out the metadata of the database.
     * Namely, it prints out the tables in the database
     * and for each table, prints out the columns
     *
     * @param None
     * @return None
     */
    public static void printMetaData() throws SQLException
    {
        DatabaseMetaData metadata = null;

        // Try getting metadata from database
        try
        {
            metadata = connection.getMetaData();
        }catch(SQLException e)
        {
            System.err.println(e.getMessage());
            return;
        }

        printDBMetaData(metadata); 
        printTableMetaData();
    }

    /* Prints out the tables present in the current database
     *
     * @param tables the tables present in the database
     * @return none
     */
    public static void printDBMetaData(DatabaseMetaData metadata) throws SQLException
    {
        ResultSet tables = metadata.getTables(null,null,null,null); // Parameters all null to get
                                                           // all tables in database

        System.out.println("TABLES IN DATABASE: ");

        while(tables.next())
        {
            System.out.println("\t" + tables.getString("TABLE_NAME"));
        }
        System.out.println(); // print newline to separate from menu
    }

    /* Prints out the names of the columns and its type for 
     * each table in the database
     *
     * @param none
     * @return none
     */
    public static void printTableMetaData() throws SQLException
    {
        Statement select = connection.createStatement();
        ResultSet table = null;
        ResultSetMetaData metadata = null;
        String[] table_names = {"creditcards","customers","genres",
            "genres_in_movies","movies","sales","stars","stars_in_movies"};

        for(String name:table_names)
        {
            System.out.println("DATA FOR " + name + " TABLE");
            table = select.executeQuery("SELECT * FROM " + name);
            metadata = table.getMetaData();
            for(int i = 1; i <= metadata.getColumnCount();i++)
            {
                System.out.println("\tCOLUMN NAME: " + metadata.getColumnLabel(i));
                System.out.println("\t\tTYPE: " + metadata.getColumnTypeName(i)); 
            }
        }
    }
    
    /* Inserts a value into the database given the first part of the
     * INSERT SQL statement.
     *
     * @param table - the name of the table where value is inserted
     * @param args - the values of each column in the table
     * @return none
     */
    public static void insertValue(String table, String[] args) throws SQLException
    {
        Statement insertStatement = connection.createStatement();
        String value;
        String statement = "INSERT INTO " + table + " VALUES(" + args[0];

        for(int i = 1; i < args.length; i++)
        {

            statement = statement + ",";

            if("".equals(args[i]))
            {
                statement += "NULL";
            }
            else
            {
                statement = statement + "'" + args[i] + "'";
            }
        }
        statement += ");";

        try
        {
            insertStatement.executeUpdate(statement);
            System.out.println("Successfully entered value to " + table + " table.");
        }catch(java.sql.SQLException e)
        {
            System.out.println(e.getMessage());
            System.out.println(statement);
        }
    }

    /* Inserts a star into the star table
     *
     * If only one name is given, it is put in the last_name column
     * and the first_name column is left blank
     *
     * @param none
     * @return none
     */
    public static void insertStar() throws SQLException
    {
        // Ask for necessary data
        String firstName = promptInput("Enter first name: ");
        String lastName = promptInput("Enter last name: ");
        System.out.println("Following fields are optional. Press ENTER if you do not wish to input a value");
        String dob = promptInput("Enter date in following format: YYYY-MM-DD");
        String url = promptInput("Enter photo URL: ");
        
        // Put parameters into string array
        // First parameter is NULL because the id auto increments
        String[] parameters = {"NULL",firstName,lastName,dob,url};

        // If user only provides one name, set it equal to
        // the last name and make the first name empty
        if(firstName.equals("") || lastName.equals(""))
        {
            lastName = firstName + lastName;
            firstName = "";
        }

        // Prepare SQL statement
        insertValue("stars",parameters);
    }  

    /* Inserts a customer into the star table
     *
     * If only one name is given, it is put in the last_name column
     * and the first_name column is left blank
     *
     * The credit card entered must be present in the database.
     * If this is not the case, the insertion is cancelled.
     *
     * @param none
     * @return none
     */
    public static void insertCustomer() throws Exception
    {
        // Ask for necessary data
        String firstName = promptInput("Enter first name: ");
        String lastName = promptInput("Enter last name: ");
        String ccID = promptInput("Enter credit card: ");
        String address = promptInput("Enter address: ");
        String email = promptInput("Enter e-mail: ");
        String password = promptInput("Enter password: ");

        // Put parameters into string array
        // First parameter is NULL because the id auto increments
        String[] parameters = {"NULL",firstName,lastName,ccID,address,email,password}; 

        // Check if the customer's credit card info is in the database
        // If not, cancel insertion. Otherwise, add customer.
        String checkStatement = "SELECT * FROM creditcards WHERE id='" + ccID + "'";
        PreparedStatement checkCC = connection.prepareStatement(checkStatement);
        ResultSet results = checkCC.executeQuery();

        if(!results.next())
        {
            System.out.println("Credit card does not exist in our database\n" + 
                                "Cancelling customer insertion");
        }else
        {
            insertValue("customers",parameters);
        }
    }

    /* Enters the "print movie" submenu. 
     *
     * If invalid commands are entered, it continually asks
     * the user for a valid command. 
     *
     * @param none
     * @return none
     */
    public static void handlePrint()
    {
        String command;
        boolean run = true;

        System.out.println(PrintMenu);

        while(run)
        {
            command = promptInput("Enter command: ");
            switch(command)
            {
                case "f":
                    break;
                case "l":
                    break;
                case "b":
                    break;
                case "i":
                    break;
                case "x":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid command entered");
            }
        }
    }

    /* Enters the "print movie" submenu. 
     *
     * If invalid commands are entered, it continually asks
     * the user for a valid command. 
     *
     * @param none
     * @return none
     */
    public static void handleInserts() throws Exception
    {
        String command;
        boolean run = true;

        while(run)
        {
            System.out.println(InsertMenu);
            command = promptInput("Enter Command: ");
            switch(command)
            {
                case "s":
                    insertStar();
                    break;
                case "c":
                    insertCustomer();
                    break;
                case "x":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid command entered");
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        String command;
        boolean run = true;
        in.useDelimiter("\\n"); 
        Class.forName("com.mysql.jdbc.Driver").newInstance(); // Incorporate mySQL driver

        // First login into mySQL database
        userLogin();

        // MAIN SHELL
        while(run)
        {
            System.out.print(MainMenu);
            command = promptInput("Enter Command:");

            switch(command)
            {
                case "p":
                    handlePrint();
                    break;
                case "i":
                    handleInserts();
                    break;
                case "d":
                    break;
                case "m":
                    printMetaData();
                    break;
                case "e":
                    break;
                case "x":
                    System.out.println("Exiting menu and logging out\n");
                    connection.close();
                    if(!userLogin()) // if user quits during login
                        run = false;
                    break;
                case "q":
                    run = false;
                    break;
                default:
                    System.out.println("Invalid command entered.");
            }
        }
        in.close();
    }
}
