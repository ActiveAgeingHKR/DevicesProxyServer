/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Karolis
 */
public class Log {

    public static synchronized void archive(String jsonIncident) {
        Logger logger = Logger.getLogger(Log.class.getName());
        FileHandler fh;
        try {
            fh = new FileHandler("archive.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info(jsonIncident);
            fh.close();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void offline(String jsonIncident) {
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File("offline.log"), true));
            pw.write(jsonIncident);
            pw.println();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void sendLoggedIncidents() {
        try {
            File file = new File("offline.log");
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                System.out.println("Sending logged incidents");
                String json;
                while ((json = br.readLine()) != null) {
                    Server.postIncidentToMainServer(json);
                }
                br.close();
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
