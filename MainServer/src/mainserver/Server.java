/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static final int PORT = 12345;
    private ServerSocket serverSocket = null;
    private static Server server;

    private Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not listen on port: 12345");
        }
    }

    public static synchronized Server getInstance() {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public Socket establishContact() {
        Socket clientSocket = null;
        try {
            System.out.println("Started on port ::> " +serverSocket.getLocalPort());
            clientSocket = serverSocket.accept();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Accept failed: 12345");
        }
        return clientSocket;
    }

    public static String getMessage(Socket clientSocket) {
        String message = null;
        try {
            BufferedReader bufferedReaderInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            message = bufferedReaderInput.readLine();
        } catch (IOException e) {
            System.out.print(e);
            e.printStackTrace();
            System.out.println("couldn,t recieve messages");
        }
        return message;
    }
}
