package com.dmbteam.joke;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dmbteam.joke.network.ManagerOffline;
import com.dmbteam.joke.network.NetworkManager;

/**
 * This is the first activity which will be started when the application is
 * opened. It contains an image file to display to the users. The idea is to
 * present your brand at start.
 * 
 * @author |dmb TEAM|
 * 
 */
public class SplashScreenActivity extends Activity {

	/**
	 * Called when the activity is starting. This is where most initialization
	 * should go.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this <code>Bundle</code> contains the data it
	 *            most recently supplied in
	 *            <code>onSaveInstanceState(Bundle)</code>. Note: Otherwise it
	 *            is null.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// Create an object of type SplashHandler
		SplashHandler mHandler = new SplashHandler();
		// Set the layout for this activity
		setContentView(R.layout.splash);
		// Create a Message object
		Message msg = new Message();
		// Assign a unique code to the message.
		// Later, this code will be used to identify the message in Handler
		// class.
		msg.what = 0;
		// Send the message with a delay of 4 seconds(4000 = 3 sec).
		mHandler.sendMessageDelayed(msg, 4000);
	}

	/**
	 * Handler class implementation to handle the message
	 * 
	 * @author |dmb TEAM|
	 * 
	 */
	private class SplashHandler extends Handler {

		/**
		 * This method is used to handle received messages
		 * 
		 * @param msg
		 *            <code>Message</code> to handle
		 */
		public void handleMessage(Message msg) {
			// switch to identify the message by its code
			switch (msg.what) {
			default:
			case 0:
				super.handleMessage(msg);

				if (NetworkManager
						.isNetworkConnected(SplashScreenActivity.this)) {

					// Create an intent to start the new activity.
					// Next activity to start is MainActivity
					Intent intent = new Intent();
					intent.setClass(SplashScreenActivity.this,
							MainActivity.class);
					startActivity(intent);
					// Finish the current activity
					SplashScreenActivity.this.finish();
				} else {
					ManagerOffline.loadErrorActivity(SplashScreenActivity.this);
				}
			}
		}
	}
}