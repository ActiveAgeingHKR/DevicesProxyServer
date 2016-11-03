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

    private Socket socket ;

    public ProxyThread(Socket socket) {
        super("ProxyThread");
        this.socket = socket;
    }

    public void run() {
        System.out.println(Server.getMessage(socket));
    }

}
