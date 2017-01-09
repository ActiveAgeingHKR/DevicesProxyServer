/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import utilities.ArduinoSerial;

/**
 *
 * @author Karolis
 */
public class ArduinoThread extends Thread {
    
        public void run() {
            ArduinoSerial as = new ArduinoSerial();
            as.initialize();
    }
}
