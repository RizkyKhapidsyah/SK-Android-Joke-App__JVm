package com.dmbteam.joke;

import android.app.Application;

import com.dmbteam.joke.database.DatabaseManager;
import com.dmbteam.joke.network.NetworkManager;

/**
 * Application context
 * @author dmbTeam
 *
 */
public class JokeApplicationContext extends Application {

	/**
	 * On create event
	 */
	@Override
	public void onCreate() {
		super.onCreate();

		// initialize Database manager
		DatabaseManager.init(this);

		// Request for data
		NetworkManager manager = new NetworkManager();
		manager.executeInputStreamData();

	}
}
