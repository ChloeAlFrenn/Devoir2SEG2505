// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 

  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    openConnection();
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
    clientUI.display(msg.toString());
 
  }
  
  /**
	 * Implementation of the hook method called after the connection has been closed. The default
	 * implementation does nothing. The method may be overriden by subclasses to
	 * perform special processing such as cleaning up and terminating, or
	 * attempting to reconnect.
	 */
  	@Override
	public void connectionClosed() {
  		clientUI.display("the connexiton has been closed");
	}

	/**
	 * Implementation of the hook method called each time an exception is thrown by the client's
	 * thread that is waiting for messages from the server. The method may be
	 * overridden by subclasses.
	 * 
	 * @param exception
	 *            the exception raised.
	 */
  	@Override
	protected void connectionException(Exception exception) {
  		clientUI.display("the server stopped");
  		System.exit(0);
	}
	

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
    	if(message.startsWith("#")) {
    		handleCommand(message);
    	} else
    		sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  
  private void handleCommand(String cmd) {
	  if(cmd.equals("#quit")) {
		  clientUI.display("the client will quit");
		  quit();
	  } else if (cmd.equals("#logoff")) {
		  try {
			if (this.isConnected()) {
			this.closeConnection();
			} else {
				clientUI.display("the client is already disconnected");
			}
		} catch (IOException e) {
			//imprime un message
			clientUI.display("there is a problem with the deconnection");
		}
	  } else if (cmd.startsWith("#sethost")) {
	
		  if (this.isConnected()) {
			  clientUI.display("we can't set the host value while the client is logged in");
				} else {
					String temp = cmd.substring(10,14);
					this.setHost(temp);
				}
				  
	  } else if (cmd.startsWith("#setport")) {
		  if (this.isConnected()) {
			  clientUI.display("we can't set the port value while the client is logged in");
				} else {
					int temp = Integer.parseInt(cmd.substring(10,14));
					this.setPort(temp);
				}
		  
	  } else if (cmd.equals("#login")) {
		  if (this.isConnected()) {
			  clientUI.display("the client is already logged in");
				} else {
					try {
						this.openConnection();
					} catch (IOException e) {
						clientUI.display("there is a problem with the connection");
					}
				}
		  
	  } else if (cmd.equals("#gethost")) {
		
		  clientUI.display(String.valueOf(this.getHost()));
		  
	  }else if (cmd.equals("#getport")) {
		  
		  clientUI.display(String.valueOf(this.getPort()));
		  
	  } else {
		  
		  clientUI.display("the command does not exist");
	  }
  }
  
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
