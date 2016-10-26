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
import java.net.Socket;

/**
 *
 * @author wasim
 */
public class ProxyThread extends Thread {

    private Socket socket = null;
    private static final int BUFFER_SIZE = 32768;

    public ProxyThread(Socket socket) {
        super("ProxyThread");
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader bufferedReaderInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = bufferedReaderInput.readLine();
            System.out.println(message);
            bufferedReaderInput.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            OutputStream out = socket.getOutputStream();
//            InputStream in = socket.getInputStream();
//            PrintWriter pw = new PrintWriter(out, true);
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//            while (true) {
//                String str = br.readLine();
//                System.out.println("Reciving from client : " + str);
//                pw.println("Message from server : " + str);
//            }
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//        closeConnection();
    }

}
