/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import com.sun.jna.platform.win32.OaIdl;
import java.net.Socket;

/**
 *
 * @author wasim
 */
public class ProxyThread extends Thread {
    
    private Socket socket;

    public ProxyThread(Socket socket) {
        super("ProxyThread");
        this.socket = socket;
    }

    public void run() {
        String jsonIncident = Server.getMessage(socket);
        if (jsonIncident == null) {
            System.out.println("Heartbeat from smartwatch");
        } else {
            Server.postIncidentToMainServer(jsonIncident);
        }
    }
}
