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

public class Server {
    public static final int PORT = 12345; 
    
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    BufferedReader bufferedReaderInput = null;

    public void establishContact() {
        try {
            serverSocket = new ServerSocket(PORT);
            clientSocket = serverSocket.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getMessage() {
        try {
            bufferedReaderInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = bufferedReaderInput.readLine();
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        public void closeConnection() {
        try {
            clientSocket.close();
            serverSocket.close();
            bufferedReaderInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
