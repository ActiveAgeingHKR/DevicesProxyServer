/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Server {

    public static final int PORT = 12345;
    private ServerSocket serverSocket = null;
    private static Server server;
    private final static String POST_INCIDENT_URL = 
            "http://localhost:8080/MainServerREST/api/entities.incidents";

    private Server() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
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
            clientSocket = serverSocket.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientSocket;
    }

    public static String getMessage(Socket clientSocket) {
        String message = null;
        try {
            BufferedReader bufferedReaderInput = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            message = bufferedReaderInput.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void postIncidentToMainServer(String jsonIncidentString) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(POST_INCIDENT_URL);
            System.out.println("jsonIncidentString: " + jsonIncidentString);
            StringEntity postString = new StringEntity(jsonIncidentString);
            post.setEntity(postString);
            post.setHeader("Content-type", "application/json");
            httpClient.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
