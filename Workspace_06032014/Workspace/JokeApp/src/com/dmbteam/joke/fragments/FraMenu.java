package com.dmbteam.joke.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dmbteam.joke.R;
import com.dmbteam.joke.fragments.FraMenu.Navigations;

/**
 * @author dmbteam
 * 
 *         Represents the content for menu screen
 */
public class FraMenu extends Fragment {

	/**
	 * Navigation cases for menu screen
	 * 
	 * @author dmbteam
	 */
	public enum Navigations {
		Rate;
	}

	// Tag that will be used for showing the current fragment
	public static final String TAG = FraMenu.class.getSimpleName();
	

	// View for rate navigation
	private TextView menuRate;

	/**
	 * Create and return instance of the current fragment
	 * 
	 * @return FraMenu
	 */
	public static FraMenu newInstance() {

		FraMenu fraMenu = new FraMenu();

		return fraMenu;
	}

	// Main view of the menu
	private View view;


	// Base listener of all navigation events
	private MenuNavigationListener mNavigationListener;

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
	 * Life cycle method. Set the layout view for current fragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fra_menu, null);

		mNavigationListener = new MenuNavigationListener(this);

		return view;
	}

	/**
	 * Life cycle method. Instantiate the views and set the data for them.
	 */
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		menuRate = (TextView) view.findViewById(R.id.rate_app);
		menuRate.setTag(Navigations.Rate);
		menuRate.setOnClickListener(mNavigationListener);

	};

	/**
	 * Show the menu
	 */
	public void showMenu() {
		if (view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.GONE);
		} else {
			view.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * Hide the menu
	 */
	public void hide() {
		view.setVisibility(View.GONE);
	}
	
	public boolean isFraMenuVisible() {
		if (view.getVisibility() == View.VISIBLE) {
			return true;
		}

		return false;
	}
}

/**
 * @author dmbteam
 * 
 *         Base listener for all navigation click events
 */
class MenuNavigationListener implements OnClickListener {

	FraMenu mFraMenu;
	Navigations mNavigation;
	
	public MenuNavigationListener(FraMenu fraMenu) {
		super();
		mFraMenu = fraMenu; 
	}

	/**
	 * Executed every time when some of the navigation events is executed. It
	 * just watch for what exactly was clicked and execute navigation method
	 */
	@Override
	public void onClick(View v) {
		if (Navigations.Rate == (Navigations) v.getTag()) {
			showRateDialog();
		}
		mFraMenu.hide();
	}


	/**
	 * Shows Rate dialog
	 */
	private void showRateDialog() {
		Uri uri = Uri.parse("market://details?id=" + mFraMenu.getActivity().getPackageName());
	    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
	    try {
	    	mFraMenu.startActivity(goToMarket);
	    } catch (ActivityNotFoundException e) {
	        Toast.makeText(mFraMenu.getActivity(), R.string.cannot_launch_market, Toast.LENGTH_LONG).show();
	    }
	}

	
}
