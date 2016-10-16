/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

/**
 *
 * @author wasim
 */
public class MainServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //should be called in a new thread
        while (true) {
            receiveDataFromClient();
        }
    }

    public static void receiveDataFromClient() {
        Server server = new Server();
        server.establishContact();
        server.getMessage();
        server.closeConnection();
    }

}
