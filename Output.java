/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.nhl.idp;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.widget.SeekBar;

/**
 *
 * @author Edwin
 */
public class Output extends Thread {

	private BufferedOutputStream out;
	private Socket client;
	private SeekBar direction;
	private SeekBar speed;    

	public Output(Socket clientt, SeekBar direction, SeekBar speed) throws IOException
	{
		this.client = clientt;
		this.direction = direction;
		this.speed = speed;
		out = new BufferedOutputStream(client.getOutputStream());
	}

	@Override
	public void run()
	{
		while(true)
		{
			try {
				// Test.Stuur.setText(Integer.toString(Test.jSlider1.getValue()));
				//  Test.Gas.setText(Integer.toString(Test.jSlider2.getValue()));
				out.write('b' & 0xFF);
				out.write(direction.getProgress() & 0xFF);
				out.write(speed.getProgress() & 0xFF);
				//out.write(254 & 0xFF);
				//out.write(1 & 0xFF);
				out.write( 255 & 0xFF );
				out.flush();
				try {
					Thread.sleep(100);
				} catch (InterruptedException ex) {
					Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
				}
			} catch (IOException ex) {
				Logger.getLogger(Output.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

}
