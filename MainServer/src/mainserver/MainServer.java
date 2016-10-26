/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.io.IOException;
import java.net.ServerSocket;

/**
 *
 * @author wasim
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
   
      
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true; 
        int port =12345;	//default    
                 
      
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Started on: " + port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + args[0]);
            System.exit(-1);
        }

        while (listening) {
            new ProxyThread(serverSocket.accept()).start();
        }
        //should be called in a new thread 
         while (true) {
            receiveDataFromClient();
           
        } 
       
    }

    public static void receiveDataFromClient() throws IOException {
        Server server = new Server();
        server.establishContact();
        //server.getMessage();         
        //server.closeConnection();      
    
    }

}
