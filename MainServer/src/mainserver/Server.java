/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Clock;

public class Server {
	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	ObjectInputStream in = null;
	PrintWriter out = null;
	BufferedReader bufferedReaderInput = null;

	public void receive() {
		establishContact();

		closeConnection();

	}

	public void establishContact() {
		try {
			serverSocket = new ServerSocket(12345);
		} catch (IOException e) {
			System.out.println("Could not listen on port: 12345");
			System.exit(-1);
		}
		try {
			clientSocket = serverSocket.accept();
		} catch (IOException e) {
			System.out.println("Accept failed: 12345");
			System.exit(-1);
		}
		try {
			bufferedReaderInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException ioe) {
			System.out.println("Failed in creating streams");
			System.exit(-1);
		}
	}

	public void closeConnection() {
		try {
			clientSocket.close();
			serverSocket.close();
			bufferedReaderInput.close();
		} catch (IOException e) {
			System.out.println("Could not close");
			System.exit(-1);
		}
	}

	public void getMessage() {
		try {
			String message = bufferedReaderInput.readLine();
			System.out.println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();

	}
}
