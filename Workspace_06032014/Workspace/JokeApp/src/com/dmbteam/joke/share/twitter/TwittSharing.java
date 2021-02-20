package com.dmbteam.joke.share.twitter;

import com.dmbteam.joke.share.twitter.TwitterHandler.TwDialogListener;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;



/**
 * This class is responsible for the Twitter sharing
 * 
 * @author |dmb TEAM|
 * 
 */
public class TwittSharing {

    /**
     * Twitter Handler
     */
    private final TwitterHandler mTwitter;
    
    /**
     * Delegate activity
     */
    private final Activity activity;
    
    /**
     * Message to tweet
     */
    private String twitterMessage;

    /**
     * Class constructor
     * @param act
     * @param consumerKey
     * @param consumerSecret
     */
    public TwittSharing(Activity act, String consumerKey,
                        String consumerSecret) {
        this.activity = act;
        mTwitter = new TwitterHandler(activity, consumerKey, consumerSecret);
    }

    /**
     * Shares a message to Twitter
     * @param msg
     */
    public void shareToTwitter(String msg) {
        this.twitterMessage = msg;
        mTwitter.setListener(mTwLoginDialogListener);

        if (mTwitter.hasAccessToken()) {
            // this will post data in asyn background thread
            showTwittDialog();
        } else {
            mTwitter.authorize();
        }
    }

    /**
     * Shows the Twitter dialog
     */
    private void showTwittDialog() {
        new PostTwittTask().execute(twitterMessage);
    }

    private final TwDialogListener mTwLoginDialogListener = new TwDialogListener() {

        /**
         * Action on error event
         */
        @Override
        public void onError(String value) {
            showToast("Login Failed");
            mTwitter.resetAccessToken();
        }

        /**
         * Action on complete event
         */
        @Override
        public void onComplete(String value) {
            showTwittDialog();
        }
    };


    /**
     * Shows a Toast with a message
     * @param msg
     */
    void showToast(final String msg) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

            }
        });

    }

    /**
     * Async Task to post tweets
     * 
     * @author |dmb TEAM|
     * 
     */
    class PostTwittTask extends AsyncTask<String, Void, String> {
        ProgressDialog pDialog;

        /**
         * Runs on the UI thread before doInBackground(Params...).
         */
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(activity);
            pDialog.setMessage("Posting Twitt...");
            pDialog.setCancelable(false);
            pDialog.show();
            super.onPreExecute();
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to execute(Params...) by
         * the caller of this task. This method can call
         * publishProgress(Progress...) to publish updates on the UI thread.
         * 
         * @param result
         *            The result of the operation computed by
         *            doInBackground(Params...).
         * 
         */
        @Override
        protected String doInBackground(String... twitt) {
            try {

                sharePost(twitterMessage,
                          mTwitter.twitterObj);
                return "success";

            } catch (Exception e) {
                if (e.getMessage().toString().contains("duplicate")) {
                    return "Posting Failed because of Duplicate message...";
                }
                e.printStackTrace();
                return "Posting Failed!!!";
            }

        }

        /**
         * Runs on the UI thread after doInBackground(Params...). The specified
         * result is the value returned by doInBackground(Params...).
         * 
         * This method won't be invoked if the task was cancelled.
         * 
         * @param result
         *            The result of the operation computed by
         *            doInBackground(Params...).
         */
        @Override
        protected void onPostExecute(String result) {
            pDialog.dismiss();

            if (null != result && result.equals("success")) {
                showToast("Posted Successfully");

            } else {
                showToast(result);
            }

            super.onPostExecute(result);
        }
    }


    /**
     * Shares a post with given message
     * 
     * @param message
     * @param twitter
     * @throws Exception
     */
    public void sharePost(String message,
                          Twitter twitter) throws Exception {
        try {
            StatusUpdate st = new StatusUpdate(message);

            twitter.updateStatus(st);

        } catch (TwitterException e) {
            throw e;
        }
    }

}
