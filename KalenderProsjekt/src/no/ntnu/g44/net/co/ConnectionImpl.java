/*
 * Created on Oct 27, 2004
 */
package no.ntnu.g44.net.co;

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
 * @author Sebj�rn Birkeland and Stein Jakob Nordb�
 * @see no.ntnu.g44.net.co.Connection
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
	@Override
	public void connect(InetAddress pRemoteAddress, int pRemotePort) throws IOException,
	SocketTimeoutException {
		this.remoteAddress = pRemoteAddress.getHostAddress();
		this.remotePort = pRemotePort;

		KtnDatagram syn = constructInternalPacket(Flag.SYN);
		KtnDatagram syn_ack = null;
		long startStamp = System.currentTimeMillis();

		//Send syn and wait for the synack
		do{

			if(System.currentTimeMillis() - startStamp > CONNECTION_TRIES*RETRANSMIT + RETRANSMIT){
				throw new SocketTimeoutException("Could not get a syn_ack after "+CONNECTION_TRIES+" tries.");
			}

			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new SendTimer(new ClSocket(), syn), 0, RETRANSMIT);

			state = State.SYN_SENT;
			//recieveAck will block for a while.
			syn_ack = receiveAck();
			timer.cancel();
			System.out.println("Recieved syn-ack " + syn_ack);
		}while(syn_ack == null || !validAck(syn, syn_ack));

		//We have now recieved a valid syn-ack
		//assert validAck(syn,syn_ack);
		nextExpectedSeqNr = syn_ack.getSeq_nr()+1;
		this.remotePort = syn_ack.getSrc_port();
		sendAck(syn_ack, false);

		state = State.ESTABLISHED;
	}

	private boolean validAck(KtnDatagram datagram, KtnDatagram ack) {
		System.out.println("Acked seqnr = "+ack.getAck()+" seqnr = " + datagram.getSeq_nr());
		boolean value = datagram.getSeq_nr() == ack.getAck();
		System.out.println("Ack valid:  " + value);
		return value;
	}

	/**
	 * Listen for, and accept, incoming connections.
	 * 
	 * @return A new ConnectionImpl-object representing the new connection.
	 * @see Connection#accept()
	 */
	@Override
	public Connection accept() throws IOException, SocketTimeoutException {

		//Wait forever for a syn:
		KtnDatagram syn = null;
		do{
			syn = receivePacket(true);
		}while(syn== null || syn.getFlag() != Flag.SYN);


		//Wohooo, syn recieved!


		ConnectionImpl con = new ConnectionImpl();
		con.synRcvd(syn);

		return con;
	}


	private void synRcvd(KtnDatagram syn) throws ConnectException, IOException {
		state = State.SYN_RCVD;
		//SYN-flooding will be fun >.<
		nextExpectedSeqNr = syn.getSeq_nr()+1;
		remoteAddress = syn.getSrc_addr();
		remotePort = syn.getSrc_port();

		KtnDatagram ack = null;
		KtnDatagram synack = null;
		
		do{
			synack = sendAck(syn, true);
			ack = receiveAck();
			System.out.println("Recieved ack " + ack);
		}while(ack == null || /*!isValid(ack) ||*/ !validAck(synack, ack));
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
	 * @see no.ntnu.g44.net.co.Connection#send(String)
	 */
	@Override
	public void send(String msg) throws ConnectException, IOException {

		KtnDatagram datagram = constructDataPacket(msg);
		System.out.println("Making new datagram with sequence number " + nextSequenceNo + " got " + datagram.getSeq_nr());
		KtnDatagram ack;
		KtnDatagram potentialReack = null;
		do{
			ack = sendDataPacketWithRetransmit(datagram);
			
			//If we're stuck here and recieve an old package that has to be re-acked.
			if(!externalQueue.isEmpty()){
				potentialReack = receivePacket(false);
				if(potentialReack.getSeq_nr() < nextExpectedSeqNr){
					sendAck(potentialReack, false); //reack an old package
				}
			}
			
		}while(ack == null || !validAck(datagram, ack) /*|| !isValid(ack)*/);

		nextExpectedSeqNr++; //for the ack.

	}

	/**
	 * Wait for incoming data.
	 * 
	 * @return The received data's payload as a String.
	 * @see Connection#receive()
	 * @see AbstractConnection#receivePacket(boolean)
	 * @see AbstractConnection#sendAck(KtnDatagram, boolean)
	 */
	@Override
	public String receive() throws ConnectException, IOException {
		while(true){
			KtnDatagram datagram = receivePacket(false);
			if(datagram.getSeq_nr() < nextExpectedSeqNr){
				nextSequenceNo--;
				sendAck(datagram,false);
				System.out.println("Reacking old packet.");
				continue;
			}else if(!isValidAndExpectedSeq(datagram)){
				System.out.println("Recieved an invalid packet. Thrown away.");
				continue; //Throw away this packet!
			}else{
				System.out.println("Got a valid packet!");
				nextExpectedSeqNr++;
				sendAck(datagram, false);
				return (String) datagram.getPayload();
			}
		}
	}

	/**
	 * Close the connection.
	 * 
	 * @see Connection#close()
	 */
	@Override
	public void close() throws IOException {

		if(disconnectRequest != null){
			closeOnFinRecieved();
			return;
		}


		int timeout = 180000; //2 min timeout

		long startTime = System.currentTimeMillis();

		KtnDatagram fin = constructInternalPacket(Flag.FIN);
		KtnDatagram ack = null;

		//Send fin and wait for the ack
		do{
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new SendTimer(new ClSocket(), fin), 0, RETRANSMIT);
			state = State.FIN_WAIT_1;
			//recieveAck will block for a while.
			ack = receiveAck();
			timer.cancel();
			System.out.println("Might have recieved ack " + ack);
		}while(ack == null && System.currentTimeMillis() - startTime < timeout/*|| !validAck(syn, syn_ack)*/);
		
		nextExpectedSeqNr++; //recieved the ack
		//recieved ack on the fin.
		//Wait for the fin

		state = State.FIN_WAIT_2;
		fin = null;
		do{
			fin = receivePacket(true);
		}while((fin == null || fin.getFlag() != Flag.FIN) && System.currentTimeMillis() - startTime < timeout);

		nextExpectedSeqNr++; //recieved the fin
		
		//Keep listening for fins to be acked!
		do{
			if(fin != null && fin.getFlag() == Flag.FIN){
				nextSequenceNo--; //Resending an ack, do not increment seqno.
				sendAck(fin, false);
			}
			fin = receivePacket(true);
		}while(System.currentTimeMillis() - startTime < timeout);


		//Connection closed!!
		state = State.CLOSED;
		release();
		return;
	}

	private void closeOnFinRecieved() throws EOFException, IOException{
		int timeout = 1800000; //2 min timeout

		state = State.CLOSE_WAIT;
		//send ack
		sendAck(this.disconnectRequest, false);


		long startTime = System.currentTimeMillis();

		KtnDatagram fin = constructInternalPacket(Flag.FIN);
		KtnDatagram ack = null;

		//Send fin and wait for the ack
		do{
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new SendTimer(new ClSocket(), fin), 0, RETRANSMIT);
			state = State.LAST_ACK ;
			//recieveAck will block for a while.
			ack = receiveAck();
			timer.cancel();

			System.out.println("Might have recieved ack " + ack == null ? "NULL" : "not null: " + ack.getFlag().toString());

			if(ack != null && ack.getFlag() == Flag.FIN){
				//Our syn ack might've been lost. Lets reack the syn.
				nextSequenceNo--;
				sendAck(ack, true);
				continue;
			}

		}while((ack == null && System.currentTimeMillis() - startTime < timeout) || !validAck(fin, ack));

		//recieved ack on the fin.
		release();
		return; //Connection closed!!
	}


	private void release() {
		remoteAddress = "";
		remotePort = 0;
		lastDataPacketSent = null;
		lastValidPacketReceived = null;
	}

	/**
	 * Test a packet for transmission errors. This function should only called
	 * with data or ACK packets in the ESTABLISHED state.
	 * 
	 * @param packet
	 *            Packet to test.
	 * @return true if packet is free of errors, false otherwise.
	 */
	@Override
	protected boolean isValid(KtnDatagram packet) {
		return (packet.calculateChecksum() == packet.getChecksum())
				&& (packet.getSrc_addr() == remoteAddress) //To get those ghost packets!
				&& (packet.getSrc_port() == remotePort);
		//Should we check sequence number here?! NO! As we use this in connect before we know what sequencenr to check
	}

	private boolean isValidAndExpectedSeq(KtnDatagram packet){
		System.out.println("seq nr: " +packet.getSeq_nr()+" next expected nr: " + nextExpectedSeqNr);
		return packet.getSeq_nr() == nextExpectedSeqNr ;//&& isValid(packet);
	}


}
