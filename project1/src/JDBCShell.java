import java.util.Scanner;
import java.sql.*;
//import JDBCFunctions.java;

public class JDBCShell
{
    
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


        while(run)
        {
            System.out.println(PrintMenu);
            command = JDBCFunctions.promptInput("Enter command: ");
            switch(command)
            {
                case "f":
                case "l":
                case "b":
                case "i":
                    try
                    {
                        JDBCFunctions.printMovies(command);
                    }catch(SQLException e)
                    {
                        System.err.println(e.getMessage()); 
                    }
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
            command = JDBCFunctions.promptInput("Enter Command: ");
            switch(command)
            {
                case "s":
                    JDBCFunctions.insertStar();
                    break;
                case "c":
                    JDBCFunctions.insertCustomer();
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
        Class.forName("com.mysql.jdbc.Driver").newInstance(); // Incorporate mySQL driver

        // First login into mySQL database
        if(!JDBCFunctions.userLogin()) // if user quits during login
            run = false;

        // MAIN SHELL
        while(run)
        {
            System.out.print(MainMenu);
            command = JDBCFunctions.promptInput("Enter Command:");

            switch(command)
            {
                case "p":
                    handlePrint();
                    break;
                case "i":
                    handleInserts();
                    break;
                case "d":
                    JDBCFunctions.deleteCustomer();
                    break;
                case "m":
                    JDBCFunctions.printMetaData();
                    break;
                case "e":
                    JDBCFunctions.executeSQLCommand();
                    break;
                case "x":
                    System.out.println("Exiting menu and logging out\n");
                    if(!JDBCFunctions.userLogin()) // if user quits during login
                        run = false;
                    break;
                case "q":
                    JDBCFunctions.closeConnection();
                    run = false;
                    break;
                default:
                    System.out.println("Invalid command entered.");
            }
        }
    }
}
