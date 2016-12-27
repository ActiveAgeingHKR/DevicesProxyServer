/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.net.Socket;

/**
 *
 * @author wasim
 */
public class ProxyThread extends Thread {

    private Socket socket;

    public ProxyThread(ThreadGroup tg, String name, Socket socket) {
        super(tg, name);
        this.socket = socket;
    }

    public void run() {
        if (socket != null) {
            String message = Server.getInstance().getMessage(socket);
            if (message == null) {
                System.out.println("Heartbeat from smartwatch");
            } else if (message.length() > 14) { //json incident is guaranteed longer than 14 chars
                Server.postIncidentToMainServer(message);
            } else if (message.length() < 14) { //serial number is never longer than 14 chars
                String customerID = Server.getCustomerID(message);
                Server.sendMessage(customerID, socket);
            }
        }
    }
}
