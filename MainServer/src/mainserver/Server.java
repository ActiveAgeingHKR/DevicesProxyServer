/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import utilities.Log;
import entities.DevicesCustomers;
import gui.Config;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

public class Server {

    public static boolean isMainServerAlive = true;
    private ServerSocket serverSocket = null;
    BufferedReader bufferedReaderInput;
    Socket clientSocket = null;
    private static Server server;
    private final static String POST_INCIDENT_URL
            = "http://localhost:8080/MainServerREST/api/incidents";
    private final static String HEARTBEAT_URL
            = "http://localhost:8080/MainServerREST/api/incidents/isalive";
    private final static String GET_CUSTOMER_ID_URL
            = "http://localhost:8080/MainServerREST/api/devicescustomers/";

    private Server() {
        try {
            serverSocket = new ServerSocket(Config.getPortNumber());
        } catch (Exception e) {
            e.printStackTrace();
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
            System.out.println("Waiting for client at port:" + Config.getPortNumber());
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void sendMessage(String text, Socket clientSocket) {
        try {
            BufferedWriter outputToServer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            outputToServer.write(text + "\r\n");
            outputToServer.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void closeConnection() {
        try {
            serverSocket.close();
            server = null;
        } catch (IOException e) {
            System.out.println("Could not close");
            System.exit(-1);
        }
    }

    public static void postIncidentToMainServer(String jsonIncidentString) {
        if (isMainServerAlive) {
            try {
                CredentialsProvider provider = new BasicCredentialsProvider();
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EMPLOYEE", "password");
                provider.setCredentials(AuthScope.ANY, credentials);
                HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
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

    public static void heartbeatToMain() {
        try {
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("EMPLOYEE", "password");
            provider.setCredentials(AuthScope.ANY, credentials);
            HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();

            HttpPost post = new HttpPost(HEARTBEAT_URL);
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 204) {
                System.out.println("MainServerREST is ALIVE");
                isMainServerAlive = true;
                Log.sendLoggedIncidents();
            }
        } catch (HttpHostConnectException e) {
            isMainServerAlive = false;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getCustomerID(String deviceID) {
        String customerID = null;
        try {
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("ADMIN", "password");
            provider.setCredentials(AuthScope.ANY, credentials);
            HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
            HttpGet get = new HttpGet(GET_CUSTOMER_ID_URL + deviceID);
            System.out.println("deviceID: " + deviceID);
            get.setHeader("Content-type", "application/json");
            HttpResponse response = httpClient.execute(get);
            customerID = String.valueOf(extractCustomerId(response));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerID;
    }

    public static int extractCustomerId(HttpResponse response) {
        System.out.println(response.getEntity());
        int customerID = -1;
        try {
            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
            if (response != null) {
                response.getEntity().writeTo(outstream);
                byte[] responseBody = outstream.toByteArray();
                String str = new String(responseBody, "UTF-8");
                Gson gson = new Gson();
                customerID = gson.fromJson(str, DevicesCustomers.class).getCustomersCuId().getCuId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customerID;

    }
}
