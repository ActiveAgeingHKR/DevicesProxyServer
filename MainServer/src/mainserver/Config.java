/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wasim
 */
public class Config {
    

    private static int defaultPort ;
    private static final String configFileName = "C:\\Users\\wasim\\Documents\\NetBeansProjects\\MainServer\\DevicesProxyServer\\MainServer\\config.txt";

    private static int port;

    static {
        //TODO: read form config file (use the above default name configFileName) the port value in case there is not value then save default one
    String content = null;
    File file = new File(configFileName); //for ex foo.txt
    FileReader reader = null;
    try {
        reader = new FileReader(file);
        char[] chars = new char[(int) file.length()];
        reader.read(chars);
        content = new String(chars);
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    } 
     //return content;
     
        //for now we will just read the new value from default value 
        //port =;
        
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
            //TODO: check if port number valid if not return false
            //TODO: save the new setting in config file if fail return false 
               InputStreamReader cin = null;

      try {
         cin = new InputStreamReader(System.in);
         
         char c;
         do {
            c = (char) cin.read();
            System.out.print(c);
         } while(c != 'q');
      }finally {
         if (cin != null) {
            cin.close();
         }
      }
            //if every thing ok (the value saved and the port is valid then assigen the new value and return true
            port = newPort;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
