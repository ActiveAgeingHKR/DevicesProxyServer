/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Server {

    public static final int PORT = 12345;
    private ServerSocket serverSocket = null;
    private static Server server;
    private final static String POST_INCIDENT_URL = "http://localhost:8080/MainServerREST/api/entities.incidents";
    private final static String POST_CUSTOMER_URL = "http://localhost:8080/SpackMaster/webresources/entities.customers";

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
            BufferedReader bufferedReaderInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            message = bufferedReaderInput.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }

    public static void postIncidentToMainServer(String jsonIncidentString) {
        try {
            Gson gson = new Gson();
            Date date = new Timestamp(new Date().getTime());
            System.out.println(date);
            Customers customer = new Customers(1515, "Karolis", "Mineikis", "1986-09-23", "Onkel", "8609230101");
            Incidents incident = new Incidents(null, date.toString(), "MILD", customer);
            String jsonString = new String(gson.toJson(incident));
            System.out.println(jsonString);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(POST_INCIDENT_URL);
            //HttpPost post = new HttpPost(POST_CUSTOMER_URL);
            //just a sout for debugging so I can check the json string was created properly by gson
            System.out.println("json string: " + jsonString);
            StringEntity postString = new StringEntity(jsonString);

            post.setEntity(postString);
            post.setHeader("Content-type", "application/json");
            //httpClient.execute(post);
            HttpResponse  response = httpClient.execute(post);
            
//            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
//            if(response!=null){
//                response.getEntity().writeTo(outstream);
//            byte [] responseBody = outstream.toByteArray();
//            String str = new String(responseBody, "UTF-8");
//            System.out.print(str);
//            } else {
//                System.out.println("Success");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
