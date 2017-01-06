/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import utilities.Log;
import gui.Config;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.http.conn.HttpHostConnectException;
import org.panos.SSLConnection;

public class Server {

    public static boolean isMainServerAlive = true;
    private ServerSocket serverSocket = null;
    BufferedReader bufferedReaderInput;
    Socket clientSocket = null;
    private static Server server;
    private final static String SERVER_URL = "https://localhost:8181/MainServerREST/api/";
    private final static String POST_INCIDENT_SERVICE = "incidents";
    private final static String HEARTBEAT_SERVICE = "incidents/isalive";
    private final static String GET_CUSTOMER_ID_SERVICE = "devicescustomers/id";

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
                SSLConnection connection = new SSLConnection(SERVER_URL);
                String response = connection.doPost(POST_INCIDENT_SERVICE, jsonIncidentString, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.TEXT, SSLConnection.USER_MODE.EMPLOYEE);
                System.out.println("POST INCIDENT response code: " + response + " jsonIncidentString: " + jsonIncidentString);
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
            
            SSLConnection connection = new SSLConnection(SERVER_URL);
            int responseCode = Integer.valueOf(connection.doPost(HEARTBEAT_SERVICE, 
                    "", SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.TEXT, 
                    SSLConnection.USER_MODE.EMPLOYEE));
            if (responseCode == 204) {
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
        String response = null;
        try {
            SSLConnection connection = new SSLConnection(SERVER_URL);
            response = connection.doGet(GET_CUSTOMER_ID_SERVICE, deviceID, SSLConnection.CONTENT_TYPE.JSON, SSLConnection.ACCEPT_TYPE.TEXT, SSLConnection.USER_MODE.ADMIN);
            System.out.println("customerID: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
