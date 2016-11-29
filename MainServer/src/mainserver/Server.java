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
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Server
{

    public static boolean isMainServerAlive = true; 
    public static  int PORT = Config.getPortNumber(); 
    private ServerSocket serverSocket = null;
    BufferedReader bufferedReaderInput;
    Socket clientSocket = null;
    private static Server server;
    private final static String POST_INCIDENT_URL
            = "http://localhost:8080/MainServerREST/api/entities.incidents";
    private final static String HEARTBEAT_URL
            = "http://localhost:8080/MainServerREST/api/entities.incidents/isalive";

    private Server()
    {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("waiting for client at port:" + PORT);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized Server getInstance()
    {
        if (server == null) {
            server = new Server();
        }
        return server;
    }

    public Socket establishContact()
    {
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientSocket;
    }

    public static String getMessage(Socket clientSocket)
    {
        String message = null;
        try {
            BufferedReader bufferedReaderInput = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            message = bufferedReaderInput.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void postIncidentToMainServer(String jsonIncidentString)
    {
        if (isMainServerAlive) {
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
        } else {
            Log.offline(jsonIncidentString);
        }
        Log.archive(jsonIncidentString);
    }

    public static void heartbeatToMain()
    {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(HEARTBEAT_URL);
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 204) {
                isMainServerAlive = true;
                Log.sendLoggedIncidents();
            }
        } catch (HttpHostConnectException e) {
            isMainServerAlive = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void closeConnection() {
        // Socket clientSocket = null;
		try {
//			clientSocket.close();
			serverSocket.close();
//			bufferedReaderInput.close();
		} catch (IOException e) {
			System.out.println("Could not close");
			System.exit(-1);
		}
                
	}
    
   
}
