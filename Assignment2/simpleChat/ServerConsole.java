
import java.util.Scanner;

import common.ChatIF;
import ocsf.server.ConnectionToClient;

public class ServerConsole implements ChatIF {

	 //Instance variables **********************************************
	  
	  /**
	   * The instance of the server that created this ConsoleChat.
	   */
	  EchoServer server;
	  
	  
	  /**
	   * Scanner to read from the console
	   */
	  Scanner fromConsole; 
	  
	  //Class variables *************************************************
	  
	  /**
	   * The default port to listen on.
	   */
	  final public static int DEFAULT_PORT = 5555;

	  
	  //Constructors ****************************************************

	  /**
	   * Constructs an instance of the ServerConsole UI.
	   *
	   * @param host The host to connect to.
	   * @param port The port to connect on.
	   */
	  public ServerConsole(int port) 
	  {
	   
	      server = new EchoServer(port, this);
	      
	    // Create scanner object to read from console
	    fromConsole = new Scanner(System.in); 
	  }

	  //Instance methods ************************************************
	  
	  /**
	   * This method waits for input from the console.  Once it is 
	   * received, it sends it to the client's message handler.
	   */
	  public void accept() 
	  {
	    try
	    {

	      String message;

	      while (true) 
	      {
	        message = fromConsole.nextLine();
	        server.handleMessageFromServerUI( message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");
	    }
	  }

	  /**
	   * This method overrides the method in the ChatIF interface.  It
	   * displays a message onto the screen.
	   *
	   * @param message The string to be displayed.
	   */
	  public void display(String message) 
	  {
		System.out.println("SERVER MSG> " + message);
	  }
	  
	//Class methods ***************************************************
	  
	  /**
	   * This method is responsible for the creation of the Server UI.
	   *
	   * @param args[0] The host to connect to.
	   */
	  public static void main(String[] args) 
	  {
	    int port = 0; //Port to listen on


	    try
	    {
	      port = Integer.parseInt(args[0]); //Get port from command line
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	     
	      port = DEFAULT_PORT; //Set port to 5555
	     
	    }
	    catch(NumberFormatException ne) {
	    	port = DEFAULT_PORT; //Set port to 5555
	    }
	    ServerConsole sv = new ServerConsole(port);
	    sv.accept();
	   
	  }
	}

	//End of ServercONSOLE class


