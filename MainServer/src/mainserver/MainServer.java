/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.io.IOException;

/**
 *
 * @author wasim
 */
public class MainServer {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
//        while (true) {
//            new ProxyThread(Server.getInstance().establishContact()).start();
//        }
        Server.postIncidentToMainServer("");
    }
}
