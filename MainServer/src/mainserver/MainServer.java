/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import arduino.SerialTest;
import utilities.HeartbeatToMainTask;
import gui.Gui;
import gui.Controller;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;

/**
 *
 * @author wasim
 */
public class MainServer {

    

    private Controller controller;

    public static final int HEARTBEAT_PERIOD = 30; //seconds

    public static void main(String[] args) throws IOException {
       
        Gui gui = new Gui();
        gui.setVisible(true);

        //start heartbeat task to check if main server is alive
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new HeartbeatToMainTask(), 0, HEARTBEAT_PERIOD, TimeUnit.SECONDS);

        ArduinoThread arduinoThread = new ArduinoThread();
        arduinoThread.start();
    }
}
