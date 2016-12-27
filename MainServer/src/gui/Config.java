/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wasim
 */
public class Config {

    private static int defaultPort = 12345;
    private static final String configFileName = "config.txt";

    private static int port;

    static {
        //TODO: read form config file (use the above default name configFileName) the port value in case there is not value then save default one
        try {
            File file = new File(configFileName);

            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                System.out.println("Reading port from file");
                String port;
                while ((port = br.readLine()) != null) {
                    Config.setPortNumber(port);
                }
                br.close();
                //file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("file nhe hai");

        }
        //for now we will just read the new value from default value 
        //port = defaultPort; 
        port = Config.getPortNumber();

    }

    static public int getPortNumber() {
        return port;
    }

    static public boolean setPortNumber(String newPort) { //this function will reture false if port change fail
        try {

            return setPortNumber(Integer.parseInt(newPort));
        } catch (Exception e) {
            return false;
        }
    }

    static public boolean setPortNumber(int newPort) { //this function will reture false if port change fail

        try {
            try ( //TODO: check if port number valid if not return false
                    PrintStream out = new PrintStream(new FileOutputStream(configFileName))) {
                    out.print(newPort);
                    out.close();
            }
            //TODO: save the new setting in config file if fail return false 
            //if every thing ok (the value saved and the port is valid then assigen the new value and return true
            port = newPort;
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
