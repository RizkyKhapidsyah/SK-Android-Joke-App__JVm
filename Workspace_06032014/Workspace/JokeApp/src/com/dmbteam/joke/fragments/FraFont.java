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
 *         Represents the content for font screen
 */
public class FraFont extends Fragment {

	// Tag that will be used for showing the current fragment
	public static final String TAG = FraFont.class.getSimpleName();

	private View view;

	/**
	 * Create and return instance of the current fragment
	 * 
	 * @return FraAbout
	 */
	public static FraFont newInstance() {

		FraFont fraFont = new FraFont();

		return fraFont;
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

		view = (View) inflater.inflate(R.layout.fra_font, null);


		return view;
	}

	
	/**
	 * Hide the menu
	 */
	public void hide() {
		view.setVisibility(View.GONE);
	}

	public boolean isFontVisible() {
		if (view.getVisibility() == View.VISIBLE) {
			return true;
		}

		return false;
	}
}
