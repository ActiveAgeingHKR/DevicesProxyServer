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
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

        closeConnection();
    }

    public void connection() {
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            System.out.println("Could not listen on port: 8080");
            System.exit(-1);
        }

        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        System.out.println("It is now : " + formatter.format(now.getTime()));

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

    public void closeConnection() {
        try {
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close");
            System.exit(-1);
        }
    }

    public void run() {
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
        closeConnection();
    }

}
