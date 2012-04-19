/*
 * Created on Oct 27, 2004
 *
 */
package no.ntnu.g44.net.co;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import no.ntnu.fp.net.admin.Log;
import no.ntnu.g44.net.co.Connection;

/**
 * Simplest possible test application, client part.
 *
 * @author seb, steinjak
 */
public class TestCoClient {

  /**
   * Empty.
   */
  public TestCoClient() {
  }

  /**
   * Program Entry Point.
   */
  public static void main (String args[]){

    // Set up log
    Log log = new Log();
    Log.setLogName("Client");

    // Connection object listening on 4001
    Connection conn = new ConnectionImpl(4001);
    InetAddress addr;  // will hold address of host to connect to
    try {
      // get address of local host and connect
      addr = InetAddress.getLocalHost();
      conn.connect(addr, 5555);
      // send two messages to server
      
      for(int i = 1; i <= 20; i++){
    	  conn.send("Client: Message "+i);
//    	  try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
      }
      // write a message in the log and close the connection
      Log.writeToLog("Client is now closing the connection!",
		     "TestApplication");
//      try {
//		Thread.sleep(450);
//	} catch (InterruptedException e) {
//		e.printStackTrace();
//	}
      conn.close();
    }

    catch (ConnectException e){
      Log.writeToLog(e.getMessage(),"TestApplication");
      e.printStackTrace();
    }
    catch (UnknownHostException e){
      Log.writeToLog(e.getMessage(),"TestApplication");
      e.printStackTrace();
    }
    catch (IOException e){
      Log.writeToLog(e.getMessage(),"TestApplication");
      e.printStackTrace();
    }

    System.out.println("CLIENT TEST FINISHED");
    Log.writeToLog("CLIENT TEST FINISHED","TestApplication");
  }

}
