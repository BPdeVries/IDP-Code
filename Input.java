/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nhl.idp;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Edwin
 */
public class Input extends Thread {
    
    private Socket client;
    private BufferedInputStream in; 
    
    public Input(Socket clientt) throws IOException
    {
        this.client = clientt;
        in = new BufferedInputStream(client.getInputStream());
    }
    
    @Override
    public void run()
    {
       while(true){
         byte buf[] = new byte[100];
        try {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
                }
            if (in.read(buf) > 0)
            {    String bufs = new String(buf);
   //               Test.debug.setText(Test.debug.getText() + bufs + "\n");
    //              Test.debug.setCaretPosition(Test.debug.getDocument().getLength());
            }
            
        } catch (IOException ex) {
            ex.getStackTrace();
            
        }
        
       }
    }
}
