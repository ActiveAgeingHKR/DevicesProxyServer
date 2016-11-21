/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

/**
 *
 * @author Karolis
 */
public class HeartbeatToMainTask implements Runnable {

    @Override
    public void run() {
        Server.heartbeatToMain();
    }
    
}
