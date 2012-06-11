/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nhl.idp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edwin
 */
public class Connect extends Thread {
    
    public Socket client;
    
    public Connect(String IP, int port)
    {
        try {
            client = new Socket(IP, port);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void run()
    {
        
    }
    
}
