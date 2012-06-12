package nl.nhl.idp;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * MainActivity for main screen
 * @author BP de Vries
 *
 */
public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	private SeekBar direction, speed;
	public Button start, stop, connect;
	private TextView tvMinutesAndSeconds, tvMilliSeconds;
	private StopWatch stopWatch;
	private Socket client;
	private Output out;
	private Input in;
	private ProgressDialog progressDialog;
	boolean IsRunning = false;

	/**
	 * onCreate with listeners
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		start = (Button) findViewById(R.id.btnStart);
		stop = (Button) findViewById(R.id.btnStop);
		connect = (Button) findViewById(R.id.btnConnect);
		tvMinutesAndSeconds = (TextView) findViewById(R.id.txtviewTimer);
		tvMilliSeconds = (TextView) findViewById(R.id.txtviewTimerms);
		direction = (SeekBar) findViewById(R.id.sbDirection);
		speed = (SeekBar) findViewById(R.id.sbSpeed);

		handler = new Handler();
		stopWatch = new StopWatch(tvMinutesAndSeconds, tvMilliSeconds);

		direction.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE){
					return false;
				}
				direction.setProgress(128);
				return true;
			}
		});		
	}   

	/**
	 * Handler for Progressdialog
	 */
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			progressDialog.dismiss();
		}

	};

	/**
	 * Start the timer
	 * @author BP de Vries, Lolke Bouma
	 * 
	 * @param v
	 */
	public void btnStartClick(View v)
	{
		if (!stopWatch.isRunning)
		{
			stopWatch.start();
			start.setText("Pauze");
		}
		else
		{
			stopWatch.stop();
			start.setText("Continue");
		}
	}

	/**
	 * Stop and reset the timer
	 * @author BP de Vries, Lolke Bouma
	 * 
	 * @param v
	 */
	public void btnStopClick(View v)
	{
		stopWatch.stop();
		stopWatch.reset();
		start.setText("Start");
		// Save the data to the database
	}

	/**
	 * Called when you want to connect to vehicle
	 * @author BP de Vries
	 * 
	 * @param view
	 */
	public void btnConnectClick(View view)
	{
		processThread();
	}

	/**
	 * Connect to vehicle and show Progessdialog 
	 * @author BP de Vries
	 *
	 */
	private void processThread() {
		progressDialog = ProgressDialog.show(MainActivity.this, "", "Connecting to vehicle...");
		new Thread() {
			public void run() {
				connect();
				handler.sendEmptyMessage(0);
			}
		}.start();
	}

	/**
	 * Method used to connect to the vehicle
	 * @author BP de Vries
	 *
	 */
	private void connect() {
		try {
			client = new Socket("192.168.1.105", 4321);
			in = new Input(client);
			in.start();
			out = new Output(client, direction, speed);
			out.start();		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Called first time when user clicks on the menu button
	 * @author BP de Vries
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater(); 
		inflater.inflate(R.menu.menu, menu); 
		return true;
	}

	/**
	 * Called when an options item is clicked
	 * @author BP de Vries
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) { 
		case R.id.itemprefs:
			startActivity(new Intent(this, PrefsActivity.class));  
			break;
		}
		return true;
	}

}