package com.dmbteam.joke.share;


import android.app.Activity;
import android.widget.Toast;

import com.dmbteam.joke.R;
import com.dmbteam.joke.cmn.Joke;
import com.dmbteam.joke.network.NetworkManager;
import com.dmbteam.joke.share.twitter.TwittSharing;

/**
 * This class is responsible for the Twitter functionality of the application.
 * 
 * @author |dmb TEAM|
 * 
 */
public class TwitterManager {
	
    /**
     * Activity that uses the <code>TwitterManager</code>
     */
    private Activity mActivity;
    
    /**
     * Post to be shared via Twitter
     */
    private Joke mJoke;
    
    /**
     * Variable for Singleton 
     */
    private static TwitterManager mInstance;
    
    
    /**
     * Returns a single instance of the class
     * @return singleton
     */
    public static TwitterManager getInstance() {
        if (mInstance == null) {
            mInstance = new TwitterManager();
        }

        return mInstance;
    }
    
    /**
     * Initializes the manger
     * @param activity
     * @param post
     */
    public void initManager(Activity activity, Joke joke) {
        getInstance().mActivity = activity;
        getInstance().mJoke = joke;
    }
    
    /**
     * Sends a tweet
     */
    public void tweet() {
        if (NetworkManager.isNetworkConnected(mActivity)) {
            TwittSharing twitt = new TwittSharing(mActivity,
                                   mActivity.getResources().getString(R.string.consumer_key), 
                                   mActivity.getResources().getString(R.string.consumer_secret));
            if (mJoke.getJokeText().length() > 139) {
            	showToast("The Joke is too long to be shared in Twitter!");
            } else {
            	twitt.shareToTwitter(mJoke.getJokeText());
            }
            

        } else {
            showToast("No Network Connection Available !!!");
        }
    }


    /**
     * Shows a Toast with message
     * @param msg
     */
    private void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show();
    }

}
