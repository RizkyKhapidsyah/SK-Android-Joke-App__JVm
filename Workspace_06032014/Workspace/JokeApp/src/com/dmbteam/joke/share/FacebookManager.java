package com.dmbteam.joke.share;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dmbteam.joke.R;
import com.dmbteam.joke.cmn.Joke;
import com.dmbteam.joke.settings.AppConstants;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

/**
 * This class contains all operations used for Facebook integration.
 * 
 * @author |dmb TEAM|
 * 
 */
public class FacebookManager {

	/**
	 * Variable for Singleton 
	 */
	private static FacebookManager mInstance;
	
	/**
	 * Constant for the pendingPublishReauthorization key
	 */
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	
	/**
	 * Constant for facebook permissions
	 */
	private static final List<String> PERMISSIONS = Arrays
			.asList("publish_actions");
	
	/**
	 * Returns a single instance of the class
	 * 
	 * @return Singleton
	 */
	public static FacebookManager getInstance() {
		if (mInstance == null) {
			mInstance = new FacebookManager();
		}

		return mInstance;
	}
	
	/**
	 * Activity that uses the <code>FacebookManager</code>
	 */
	private Activity mActivity;
	
	/**
	 * Facebook specific login button component
	 */
	private LoginButton mAuthButton;
	
	private Session.StatusCallback mCallback = new Session.StatusCallback() {

		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
		}
	};

	/**
	 * Progress bar to show when using Facebook components
	 */
	private ProgressBar mLoadingFace;
	
	/**
	 * Flag for pending publish reauthorization
	 */
	private boolean mPendingPublishReauthorization = false;
	
	/**
	 * Joke to be shared via Facebook
	 */
	private Joke mJoke;

	/**
	 * Facebook specific component that controls the UI
	 */
	private UiLifecycleHelper mUiHelper;

	/**
	 * Class constructor
	 */
	private FacebookManager() {

	}

	/**
	 * Initializes the <code>FacebookManager</code>
	 * @param activity
	 * @param joke
	 * @param savedInstanceState
	 * @param loadingFace
	 * @param authButton
	 */
	public void initManager(Activity activity, Joke joke,
			Bundle savedInstanceState, ProgressBar loadingFace, LoginButton authButton) {
		getInstance().mActivity = activity;
		getInstance().mJoke = joke;
		getInstance().mUiHelper = new UiLifecycleHelper(getInstance().mActivity, mCallback);
		if (savedInstanceState != null) {
			getInstance().mUiHelper.onCreate(savedInstanceState);
		}
		getInstance().mLoadingFace = loadingFace;
		
		authButton.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
		authButton.setPublishPermissions(PERMISSIONS);
		getInstance().mAuthButton = authButton;
	}

	/**
	 * Checks whether the device is connected to a network or not
	 * 
	 * @return true if the device is connected
	 */
	public boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) mActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}

	/**
	 * Called when an activity you launched exits, giving you the requestCode
	 * you started it with, the resultCode it returned, and any additional data
	 * from it. The resultCode will be RESULT_CANCELED if the activity
	 * explicitly returned that, didn't return any result, or crashed during its
	 * operation.
	 * 
	 * You will receive this call immediately before onResume() when your
	 * activity is re-starting.
	 * 
	 * @param requestCode
	 *            The integer request code originally supplied to
	 *            startActivityForResult(), allowing you to identify who this
	 *            result came from.
	 * @param resultCode
	 *            The integer result code returned by the child activity through
	 *            its setResult().
	 * @param data
	 *            An Intent, which can return result data to the caller (various
	 *            data can be attached to Intent "extras").
	 * @param activity
	 * 			  Activity that returns result	
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data, Activity activity) {
		Session.getActiveSession().onActivityResult(activity,
				requestCode, resultCode, data);

	}

	/**
	 * Called to have the fragment instantiate its user interface view. This is
	 * optional, and non-graphical fragments can return null (which is the
	 * default implementation). This will be called between onCreate(Bundle) and
	 * onActivityCreated(Bundle).
	 * 
	 * @param savedInstanceState
	 *            If non-null, this fragment is being re-constructed from a
	 *            previous saved state as given here.
	 */
	public void onCreateView(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			getInstance().mPendingPublishReauthorization = savedInstanceState
					.getBoolean(PENDING_PUBLISH_KEY, false);
		}

	}

	/**
	 * Perform any final cleanup before an activity is destroyed. This can
	 * happen either because the activity is finishing (someone called finish()
	 * on it, or because the system is temporarily destroying this instance of
	 * the activity to save space. You can distinguish between these two
	 * scenarios with the isFinishing() method.
	 * 
	 * Note: do not count on this method being called as a place for saving
	 * data! For example, if an activity is editing data in a content provider,
	 * those edits should be committed in either onPause() or
	 * onSaveInstanceState(Bundle), not here. This method is usually implemented
	 * to free resources like threads that are associated with an activity, so
	 * that a destroyed activity does not leave such things around while the
	 * rest of its application is still running. There are situations where the
	 * system will simply kill the activity's hosting process without calling
	 * this method (or any others) in it, so it should not be used to do things
	 * that are intended to remain around after the process goes away.
	 * 
	 * Derived classes must call through to the super class's implementation of
	 * this method. If they do not, an exception will be thrown.
	 */
	public void onDestroy() {
		if (getInstance().mUiHelper != null) {
			getInstance().mUiHelper.onDestroy();
		}
	}

	/**
	 * Called as part of the activity lifecycle when an activity is going into
	 * the background, but has not (yet) been killed. The counterpart to
	 * onResume().
	 * 
	 * When activity B is launched in front of activity A, this callback will be
	 * invoked on A. B will not be created until A's onPause() returns, so be
	 * sure to not do anything lengthy here.
	 * 
	 * This callback is mostly used for saving any persistent state the activity
	 * is editing, to present a "edit in place" model to the user and making
	 * sure nothing is lost if there are not enough resources to start the new
	 * activity without first killing this one. This is also a good place to do
	 * things like stop animations and other things that consume a noticeable
	 * amount of CPU in order to make the switch to the next activity as fast as
	 * possible, or to close resources that are exclusive access such as the
	 * camera.
	 * 
	 * In situations where the system needs more memory it may kill paused
	 * processes to reclaim resources. Because of this, you should be sure that
	 * all of your state is saved by the time you return from this function. In
	 * general onSaveInstanceState(Bundle) is used to save per-instance state in
	 * the activity and this method is used to store global persistent data (in
	 * content providers, files, etc.)
	 * 
	 * After receiving this call you will usually receive a following call to
	 * onStop() (after the next activity has been resumed and displayed),
	 * however in some cases there will be a direct call back to onResume()
	 * without going through the stopped state.
	 * 
	 * Derived classes must call through to the super class's implementation of
	 * this method. If they do not, an exception will be thrown.
	 */
	public void onPause() {
		if (mUiHelper != null) {
			mUiHelper.onPause();
		}
	}

	/**
	 * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(),
	 * for your activity to start interacting with the user. This is a good
	 * place to begin animations, open exclusive-access devices (such as the
	 * camera), etc.
	 * 
	 * Keep in mind that onResume is not the best indicator that your activity
	 * is visible to the user; a system window such as the keyguard may be in
	 * front. Use onWindowFocusChanged(boolean) to know for certain that your
	 * activity is visible to the user (for example, to resume a game).
	 * 
	 * Derived classes must call through to the super class's implementation of
	 * this method. If they do not, an exception will be thrown.
	 */
	public void onResume() {
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}
		getInstance().mUiHelper.onResume();
	}

	/**
	 * Called to retrieve per-instance state from an activity before being
	 * killed so that the state can be restored in onCreate(Bundle) or
	 * onRestoreInstanceState(Bundle) (the Bundle populated by this method will
	 * be passed to both).
	 * 
	 * This method is called before an activity may be killed so that when it
	 * comes back some time in the future it can restore its state. For example,
	 * if activity B is launched in front of activity A, and at some point
	 * activity A is killed to reclaim resources, activity A will have a chance
	 * to save the current state of its user interface via this method so that
	 * when the user returns to activity A, the state of the user interface can
	 * be restored via onCreate(Bundle) or onRestoreInstanceState(Bundle).
	 * 
	 * Do not confuse this method with activity lifecycle callbacks such as
	 * onPause(), which is always called when an activity is being placed in the
	 * background or on its way to destruction, or onStop() which is called
	 * before destruction. One example of when onPause() and onStop() is called
	 * and not this method is when a user navigates back from activity B to
	 * activity A: there is no need to call onSaveInstanceState(Bundle) on B
	 * because that particular instance will never be restored, so the system
	 * avoids calling it. An example when onPause() is called and not
	 * onSaveInstanceState(Bundle) is when activity B is launched in front of
	 * activity A: the system may avoid calling onSaveInstanceState(Bundle) on
	 * activity A if it isn't killed during the lifetime of B since the state of
	 * the user interface of A will stay intact.
	 * 
	 * The default implementation takes care of most of the UI per-instance
	 * state for you by calling onSaveInstanceState() on each view in the
	 * hierarchy that has an id, and by saving the id of the currently focused
	 * view (all of which is restored by the default implementation of
	 * onRestoreInstanceState(Bundle)). If you override this method to save
	 * additional information not captured by each individual view, you will
	 * likely want to call through to the default implementation, otherwise be
	 * prepared to save all of the state of each view yourself.
	 * 
	 * If called, this method will occur before onStop(). There are no
	 * guarantees about whether it will occur before or after onPause().
	 * 
	 * @param outState
	 *            Bundle in which to place your saved state.
	 */
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {

			outState.putBoolean(PENDING_PUBLISH_KEY,
					getInstance().mPendingPublishReauthorization);
		}

		if (getInstance().mUiHelper != null) {
			getInstance().mUiHelper.onSaveInstanceState(outState);
		}
	}

	/**
	 * Called when there is change in the Session state
	 * 
	 * @param session
	 *            Facebook Session
	 * @param state
	 *            Session state
	 * @param exception
	 *            exception to be thrown
	 */
	public void onSessionStateChange(Session session, SessionState state,
			Exception exception) {

		if (state.isOpened()) {

			if (getInstance().mPendingPublishReauthorization
					&& state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
				getInstance().mPendingPublishReauthorization = false;
			}
		}
	}

	/**
	 * Shares the Post to Facebook
	 */
	public void publishStory() {
		Session session = Session.getActiveSession();

		if (session != null && session.isOpened()) {
			getInstance().mLoadingFace.setVisibility(View.VISIBLE);

			Bundle postParams = new Bundle();
			postParams.putString("name", "Joke by: " + getInstance().mJoke.getAuthor());
			postParams.putString("caption",
					mActivity.getResources().getString(R.string.app_name));
			postParams.putString("description", getInstance().mJoke.getJokeText());
			postParams.putString("link", "https://play.google.com/store/apps/details?id=" + mActivity.getPackageName());

			if (AppConstants.APP_PICTURE_LINK != null) {
				postParams.putString("picture", AppConstants.APP_PICTURE_LINK);
			}

			Request.Callback callback = new Request.Callback() {
				public void onCompleted(Response response) {
					getInstance().mLoadingFace.setVisibility(View.GONE);

					JSONObject graphResponse = response.getGraphObject()
							.getInnerJSONObject();
					String postId = null;
					try {
						postId = graphResponse.getString("id");
					} catch (JSONException e) {
						//
					}
					FacebookRequestError error = response.getError();
					if (error != null) {
						Toast.makeText(mActivity.getApplicationContext(),
								error.getErrorMessage(), Toast.LENGTH_SHORT)
								.show();
					} else {
						Toast.makeText(
								mActivity.getApplicationContext(),
								mActivity.getResources().getString(
										R.string.share_joke_success),
								Toast.LENGTH_LONG).show();
					}
				}
			};

			Request request = new Request(session, "me/feed", postParams,
					HttpMethod.POST, callback);

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
		} else {
			mAuthButton.performClick();
		}
	}
	
	/**
	 * Setter for the Joke
	 * 
	 * @param post
	 *            Joke to be shared via Facebook
	 */
	public void setJoke(Joke joke) {
		getInstance().mJoke = joke;
	}
	
	/**
	 * Checks whether there is an open Session or not
	 * 
	 * @return true if there is open Session
	 */
	public boolean isSessionOpened() {
		boolean result = false;
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened())) {
			result = true;
		}
		return result;
	}
}
