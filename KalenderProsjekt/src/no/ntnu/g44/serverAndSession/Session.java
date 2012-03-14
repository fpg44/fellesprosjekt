package no.ntnu.g44.serverAndSession;

/**
 * This class receives a String from A1 and sends it to MainFrame
 *
 */
public class Session {

	public static String IP;
	public static int PORT;
	
	public Session(String IP, int PORT){
		this.IP = IP;
		this.PORT = PORT;
	}
}
