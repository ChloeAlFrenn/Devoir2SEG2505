// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 



import java.io.IOException;

import common.ChatIF;
import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 * 
 */

//commit test
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Constructors ****************************************************
  
//Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the server.
   */
  ChatIF serverUI; 

  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port, ChatIF serverUI) 
  {
    super(port);
    this.serverUI = serverUI;
    try {
		listen();
	} catch (IOException e) {
		serverUI.display("ERROR - Could not listen for clients!");
	}
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
    (Object msg, ConnectionToClient client)
  {
    System.out.println("Message received: " + msg + " from " + client);
    this.sendToAllClients(msg);
  }
    
  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromServerUI(String message)
  {
	  
	  if(message.startsWith("#")) {
		handleCommand(message);
	} else
		serverUI.display(message);
  }
  
  private void handleCommand(String cmd) {
	  if(cmd.equals("#quit")) {
		  serverUI.display("the server will quit");
		  quit();
	  }
	  else if(cmd.equals("#stop")) {
		  serverUI.display("the server will stop listening to new clients");
		  stopListening();
	  }
	  else if(cmd.equals("#close")) {
		  try {
			serverUI.display("the server will close everything");
			close();
		} catch (IOException e) {
			serverUI.display("the server was unable to close");
		}
	  } else if(cmd.startsWith("#setport")) {
		  if(this.isListening()) {
			  serverUI.display("we can't set the port value while the server is open");
			} else {
				int temp = Integer.parseInt(cmd.substring(10,14));
				this.setPort(temp);
		  }
		  
	  } else if(cmd.equals("#start")) {
		  if(this.isListening()) {
			  serverUI.display("the server is already listening");
			} else { 
				try {
					this.listen();
				} catch (IOException e) {
					 serverUI.display("the server is not able to listen");
				}
			}
		 
	  } else if(cmd.equals("#getport")) {
		  serverUI.display(String.valueOf(this.getPort()));  
	  } else {
		  
		  serverUI.display("the command does not exist");
	  }

  }
	  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  
  protected void serverStarted()
  {
    serverUI.display("Server listening for connections on port " + getPort());
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
      serverUI.display("Server has stopped listening for connections.");
  }
  
  /**
   * Hook method called each time a new client connection is
   * accepted. The default implementation does nothing.
   * @param client the connection connected to the client.
   */
  @Override
  protected void clientConnected(ConnectionToClient client) {
	  serverUI.display(client+ "has connected");
	  }

  /**
   * Hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  @Override
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  serverUI.display(client+ "has disconnected");
  }
  
  /**
   * This method terminates the server.
   */
  public void quit()
  {
    try
    {
      close();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}

//End of EchoServer class
