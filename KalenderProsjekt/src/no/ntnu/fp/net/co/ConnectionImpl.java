/*
 * Created on Oct 27, 2004
 */
package no.ntnu.fp.net.co;

import java.io.EOFException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;


import no.ntnu.fp.net.admin.Log;
import no.ntnu.fp.net.cl.ClException;
import no.ntnu.fp.net.cl.ClSocket;
import no.ntnu.fp.net.cl.KtnDatagram;
import no.ntnu.fp.net.cl.KtnDatagram.Flag;

/**
 * Implementation of the Connection-interface. <br>
 * <br>
 * This class implements the behaviour in the methods specified in the interface
 * {@link Connection} over the unreliable, connectionless network realised in
 * {@link ClSocket}. The base class, {@link AbstractConnection} implements some
 * of the functionality, leaving message passing and error handling to this
 * implementation.
 * 
 * @author Sebjørn Birkeland and Stein Jakob Nordbø
 * @see no.ntnu.fp.net.co.Connection
 * @see no.ntnu.fp.net.cl.ClSocket
 */
public class ConnectionImpl extends AbstractConnection {

	private static final int CONNECTION_TRIES = 3;
	private static int nextPort = 6600;
	/** Keeps track of the used ports for each server port. */
	private static Map<Integer, Boolean> usedPorts = Collections.synchronizedMap(new HashMap<Integer, Boolean>());
	

	/**Next sequence number we expect from the other side*/
	private int nextExpectedSeqNr;
	
	/**
	 * Initialise initial sequence number and setup state machine.
	 * 
	 * @param myPort
	 *            - the local port to associate with this connection
	 */
	public ConnectionImpl(int myPort) {
		this.myPort = myPort;
		usedPorts.put(myPort, true);
		this.myAddress = getIPv4Address();
	}

	public ConnectionImpl() {
		this(nextPort++);
	}

	private String getIPv4Address() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		}
		catch (UnknownHostException e) {
			return "127.0.0.1";
		}
	}

	/**
	 * Establish a connection to a remote location.
	 * 
	 * @param pRemoteAddress
	 *            - the remote IP-address to connect to
	 * @param pRemotePort
	 *            - the remote portnumber to connect to
	 * @throws IOException
	 *             If there's an I/O error.
	 * @throws java.net.SocketTimeoutException
	 *             If timeout expires before connection is completed.
	 * @see Connection#connect(InetAddress, int)
	 */
	public void connect(InetAddress pRemoteAddress, int pRemotePort) throws IOException,
	SocketTimeoutException {
		this.remoteAddress = pRemoteAddress.getHostAddress();
		this.remotePort = pRemotePort;

		KtnDatagram syn = constructInternalPacket(Flag.SYN);
		KtnDatagram syn_ack = null;
		int tries = 0;
		
		//Send syn and wait for the synack
		do{
			if(tries++ >= CONNECTION_TRIES){
				throw new SocketTimeoutException("Could not get a syn_ack after "+CONNECTION_TRIES+" tries.");
			}
			
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new SendTimer(new ClSocket(), syn), 0, RETRANSMIT);

			//recieveAck will block for a while.
			syn_ack = receiveAck();
			timer.cancel();
			System.out.println("Recieved syn-ack " + syn_ack);
			
			
		}while(syn_ack == null /*|| !validAck(syn, syn_ack)*/);

		//We have now recieved a valid syn-ack
		//assert validAck(syn,syn_ack);
		nextExpectedSeqNr = syn_ack.getSeq_nr()+1;
		this.remotePort = syn_ack.getSrc_port();
		sendAck(syn_ack, false);
		
		state = State.ESTABLISHED;
	}

	private boolean validAck(KtnDatagram datagram, KtnDatagram ack) {
		boolean value = datagram.getSeq_nr() == ack.getAck() && isValid(ack);
		return value;
	}

	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	public Connection accept() throws IOException, SocketTimeoutException {
		
		//Wait forever for a syn:
		KtnDatagram syn = null;
		do{
			syn = receivePacket(true);
		}while(syn== null || syn.getFlag() != Flag.SYN);
		
		
		//Wohooo, syn recieved!
		
		
		ConnectionImpl con = new ConnectionImpl();
		con.synRcvd(syn);
		
		
		
		//Listen for syn packet
		//respond with synack with retransmit
		//init vars
		return con;
	}


	private void synRcvd(KtnDatagram syn) throws ConnectException, IOException {
		state = State.SYN_RCVD;
		//SYN-flooding will be fun >.<
		nextExpectedSeqNr = syn.getSeq_nr()+1;
		remoteAddress = syn.getSrc_addr();
		remotePort = syn.getSrc_port();
		
		KtnDatagram ack = null;
		do{
			sendAck(syn, true);
			//ack = receiveAck();
			//System.out.println("Recieved ack " + ack);
		}while(false/*ack == null /*|| !isValidAndExpectedSeq(ack) || validAck(syn, ack)*/ );
		nextExpectedSeqNr++;
		state = State.ESTABLISHED;
	}

	/**
	 * Send a message from the application.
	 * 
	 * @param msg
	 *            - the String to be sent.
	 * @throws ConnectException
	 *             If no connection exists.
	 * @throws IOException
	 *             If no ACK was received.
	 * @see AbstractConnection#sendDataPacketWithRetransmit(KtnDatagram)
	 * @see no.ntnu.fp.net.co.Connection#send(String)
	 */
	public void send(String msg) throws ConnectException, IOException {
		
		KtnDatagram datagram = constructDataPacket(msg);
		KtnDatagram ack = sendDataPacketWithRetransmit(datagram);
		
		//TODO CHECK SHIT!
		nextExpectedSeqNr++; //for the ack.
		
		//    	check connection state
		//make packet
		//	send with retransmit
		//isvalid ACK
		//check ACK
		//
		//WHAT IF WRONG ACK?!!!
		//	Throw exceptions if something goes wrong.
	}

	/**
	 * Wait for incoming data.
	 * 
	 * @return The received data's payload as a String.
	 * @see Connection#receive()
	 * @see AbstractConnection#receivePacket(boolean)
	 * @see AbstractConnection#sendAck(KtnDatagram, boolean)
	 */
	public String receive() throws ConnectException, IOException {
		KtnDatagram datagram = receivePacket(false);
		nextExpectedSeqNr++;
		sendAck(datagram, false);
		//TODO:check packet
		return (String) datagram.getPayload();
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	public void close() throws IOException {
		//Go through fin stages
		throw new NotImplementedException();
	}

	/**
	 * Test a packet for transmission errors. This function should only called
	 * with data or ACK packets in the ESTABLISHED state.
	 * 
	 * @param packet
	 *            Packet to test.
	 * @return true if packet is free of errors, false otherwise.
	 */
	protected boolean isValid(KtnDatagram packet) {
		return (packet.calculateChecksum() == packet.getChecksum())
				&& (packet.getSrc_addr() == remoteAddress) //To get those ghost packets!
				&& (packet.getSrc_port() == remotePort);
		//Should we check sequence number here?! NO! As we use this in connect before we know what sequencenr to check
	}
	
	private boolean isValidAndExpectedSeq(KtnDatagram packet){
		return packet.getSeq_nr() == nextExpectedSeqNr && isValid(packet);
	}

	private class NotImplementedException extends RuntimeException{}

}
