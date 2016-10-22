/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 12345; 
    
    ServerSocket serverSocket = null;
    Socket clientSocket = null;
    BufferedReader bufferedReaderInput = null;
    boolean listening = true;
    public void establishContact() {
//        try {
//            serverSocket = new ServerSocket(PORT);
//            clientSocket = serverSocket.accept();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        
         
        
         try {
             serverSocket = new ServerSocket(PORT);
         } catch (IOException e) {
             System.out.println("Could not listen on port: 12345");
             System.exit(-1);
         }
 
         try {
             clientSocket = serverSocket.accept();
         } catch (IOException e) {
             System.out.println("Accept failed: 8080");
             System.exit(-1);
         }
         

    }

    public void getMessage() {
//        try {
//            bufferedReaderInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//            String message = bufferedReaderInput.readLine();
//            System.out.println(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            OutputStream out = clientSocket.getOutputStream();
            InputStream in = clientSocket.getInputStream();
            PrintWriter pw = new PrintWriter(out, true);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while (true) {
                String str = br.readLine();
                System.out.println("Reciving from client : " + str);
                pw.println("Message from server : " + str);
            }
        } catch (IOException e) {
            System.out.println(e);
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
