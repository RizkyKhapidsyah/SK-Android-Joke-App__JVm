package com.dmbteam.joke.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dmbteam.joke.R;


/**
 * @author dmbteam
 * 
 *         Represents the content for share screen
 */
public class FraShare extends Fragment {

	// Tag that will be used for showing the current fragment
	public static final String TAG = FraShare.class.getSimpleName();

	private View view;

	/**
	 * Create and return instance of the current fragment
	 * 
	 * @return FraAbout
	 */
	public static FraShare newInstance() {

		FraShare fraShare = new FraShare();

		return fraShare;
	}

	/**
	 * Life cycle method. In our case used to get needed settings (You could
	 * move it to constructor but we prefer to use life cycle methods from the
	 * SDK)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	/**
	 * Life cycle method. Set the layout view for current fragment and set the
	 * adapter for it.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = (View) inflater.inflate(R.layout.fra_share, null);


		return view;
	}
	
	public boolean isShareVisible() {
		if (view.getVisibility() == View.VISIBLE) {
			return true;
		}

		return false;
	}

}
