package nl.nhl.idp;

import java.util.concurrent.TimeUnit;
import android.os.Handler;
import android.widget.TextView;

/**
 * StopWatch class that has multiple functions like start, stop, reset
 * @author Lolke Bouma
 *
 */
public class StopWatch 
{
	private Handler mHandler = new Handler();
	private long startTime;
	private long elapsedTime;
	private final int REFRESH_RATE = 100;
	private String minutes,seconds;
	private long secs,mins;
	private boolean stopped = false;
	int valueNumber = 0;
	public boolean isRunning;
	TextView tvMinutesAndSeconds;
	TextView tvMilliSeconds;
	private int intSeconds; // used for the horizontal axe of the graph

	/**
	 * Given two TextViews, this stopwatch will update those 100 times per second.
	 * @param minutesAndseconds
	 * @param milliseconds
	 */
	public StopWatch(TextView minutesAndseconds, TextView milliseconds)
	{
		this.tvMinutesAndSeconds = minutesAndseconds;
		this.tvMilliSeconds = milliseconds;
	}

	/**
	 * Starts the time
	 * @author Lolke Bouma
	 *
	 */
	public void start()
	{
		if(stopped){
			startTime = System.currentTimeMillis() - elapsedTime;
		}
		else{
			startTime = System.currentTimeMillis();
		}
		mHandler.removeCallbacks(startTimer);
		mHandler.postDelayed(startTimer, 0);
		isRunning = true;
	}

	/**
	 * Stop the time
	 * @author Lolke Bouma
	 *
	 */
	public void stop()
	{
		mHandler.removeCallbacks(startTimer);
		stopped = true;
		isRunning = false;
	}

	/**
	 * Reset the time to 00:00
	 * @author Lolke Bouma
	 *
	 */
	public void reset()
	{
		stopped = true;

		tvMinutesAndSeconds.setText("00:00");
		tvMilliSeconds.setText("000");
		elapsedTime = 0;
	}

	/**
	 * Updates the timer display, this is called 100 times per second
	 * @author Lolke Bouma
	 * 
	 * @param time 
	 */
	public void update(float time)
	{
		secs = 0;
		mins = 0;
		int msecs = 0;

		intSeconds = (int)TimeUnit.MILLISECONDS.toSeconds((long) time);
		mins = TimeUnit.MILLISECONDS.toMinutes((long) time);
		secs = TimeUnit.MILLISECONDS.toSeconds((long)time) - TimeUnit.MINUTES.toSeconds(mins);
		msecs = (int)((long)time - TimeUnit.MINUTES.toMillis(mins) - TimeUnit.SECONDS.toMillis(secs));

		// Make sure that the minutes always are two digits
		// by adding a leading 0
		minutes = String.valueOf(mins);
		if (minutes.length() == 0)
			minutes = "00";
		if (minutes.length() == 1)
			minutes = "0" + minutes;

		seconds = String.valueOf(secs);

		// Make sure that the minutes always are two digits
		// by adding a leading 0
		if (seconds.length() == 0)
			seconds = "00";
		if (seconds.length() == 1)
			seconds = "0" + seconds;

		tvMinutesAndSeconds.setText(minutes + ":" + seconds);
		tvMilliSeconds.setText(String.valueOf(msecs));		
	}

	/**
	 * 
	 * @author Lolke Bouma
	 * 
	 * @return the time on the stopwatch in seconds as an int
	 */
	public int getTime()
	{
		return intSeconds;
	}

	/**
	 * Runnable that calls update 100 times per second
	 */
	private Runnable startTimer = new Runnable() 
	{
		public void run() 
		{
			elapsedTime = System.currentTimeMillis() - startTime;
			update(elapsedTime);
			mHandler.postDelayed(this,REFRESH_RATE);
		}
	};
	
}