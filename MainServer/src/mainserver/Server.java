/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author wasim
 */
public class Server {
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    ObjectInputStream in = null;
    PrintWriter out = null;

    public void receive() {
        connection();
       
    }

    public void connection() {
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 8080");
            System.exit(-1);
        }

        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.out.println("Accept failed: 8080");
            System.exit(-1);
        }

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException ioe) {
            System.out.println("Failed in creating streams");
            System.exit(-1);
        }

    }
    
}
