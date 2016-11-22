/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainserver;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;

/**
 *
 * @author wasim
 */
public class Controller implements ActionListener {
    private final Gui gui;
    private final MainServer server;
        
    public Controller(Gui gui, MainServer server) {
        this.gui = gui;
        this.server = server;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
       /* switch (event.getActionCommand()) {
                case "Listen":
                    listenButtonActionPerformed(event);
                    break;
                default:
                   // gui.append("Button not yet registered");
                    break;
            }*/
    }
    
    private void listenButtonActionPerformed(ActionEvent event) {
        JToggleButton listen = (JToggleButton)event.getSource();
        if (listen.isSelected()) { 
            // Start ProxyServer
            gui.setPortAsLocked(true);
            server.startServer(gui.getPortNumber());
        } else {
            // Close server
            gui.setPortAsLocked(false);
            server.closeServer(gui.getPortNumber());
        }
    }
}