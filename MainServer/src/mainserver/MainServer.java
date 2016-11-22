/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author wasim
 */
public class MainServer {
    
     private Controller controller;
    private int port;

    public static final int HEARTBEAT_PERIOD = 30; //seconds

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        Gui gui = new Gui();
        gui.setVisible(true);
        
        int port = 12345;
        
        
        //start heartbeat task to check if main server is alive
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new HeartbeatToMainTask(), 0, HEARTBEAT_PERIOD, TimeUnit.SECONDS);
        
        while (true) {
            new ProxyThread(Server.getInstance().establishContact()).start();
        }
    }
    public void startServer(int port) {
       this.port = port;
    }
    public void closeServer(int port) {
        this.port = port;
    }
}
