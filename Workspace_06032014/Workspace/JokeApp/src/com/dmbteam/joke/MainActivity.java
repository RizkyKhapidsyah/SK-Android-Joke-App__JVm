package com.dmbteam.joke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.dmbteam.joke.fragments.FraFont;
import com.dmbteam.joke.fragments.FraFooter;
import com.dmbteam.joke.fragments.FraFooter.FooterIcons;
import com.dmbteam.joke.fragments.FraHeader;
import com.dmbteam.joke.fragments.FraHomeContent;
import com.dmbteam.joke.fragments.FraMenu;
import com.dmbteam.joke.fragments.FraSearch;
import com.dmbteam.joke.fragments.FraShare;
import com.dmbteam.joke.settings.AppConstants;
import com.dmbteam.joke.share.FacebookManager;
import com.facebook.widget.LoginButton;
import com.google.ads.AdView;

/**
 * @author dmbTeam
 * 
 *         The main and only activity in current project. Manage all user
 *         interaction about screens navigation flow
 * 
 */
public class MainActivity extends FragmentActivity {

	private AdView admobView;

	public AdView getAdmobView() {
		return admobView;
	}

	// Fragment manager of the current activity
	private FragmentManager fm;

	public FragmentManager getFm() {
		return fm;
	}

	// Fragment transaction used to replace fragments
	private FragmentTransaction ft;

	/**
	 * Progress bar for loading Facebook events
	 */
	private ProgressBar mLoadingFace;

	/**
	 * Button to login to Facebook
	 */
	private LoginButton mAuthButton;

	/**
	 * Activity life cycle method. Executed first in current activity. Set
	 * correct theme and the main container layout.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		initFacebookManager(savedInstanceState);

		admobView = (AdView) findViewById(R.id.home_screen_admob);
		if (AppConstants.ENABLE_ADMOB_HOME_PAGE) {
			admobView.setVisibility(View.VISIBLE);
		} else {
			admobView.setVisibility(View.GONE);
		}

	}

	/**
	 * Activity life cycle method. Executed second in current activity. Set the
	 * home screen when the activity is first created.
	 */
	@Override
	protected void onResume() {
		super.onResume();

		if (getSupportFragmentManager().findFragmentByTag(FraHomeContent.TAG) == null) {
			prepareAndShowHomeScreen();
		}

		initFacebookManager(null);

	}

	/**
	 * Activity life cycle method. Hide menu fragment on hardware back button
	 * pressed.
	 */
	@Override
	public void onBackPressed() {

		boolean callSuper = true;

		FraSearch search = (FraSearch) getSupportFragmentManager()
				.findFragmentByTag(FraSearch.TAG);

		if (search != null && search.isAdded() && !search.isDetached()
				&& !search.isRemoving() && search.isSearchVisible()) {
			search.hide();
			callSuper = false;
		}
		
		FraMenu menu = (FraMenu) getSupportFragmentManager()
				.findFragmentByTag(FraMenu.TAG);

		if (menu != null && menu.isAdded() && !menu.isDetached()
				&& !menu.isRemoving() && menu.isFraMenuVisible()) {
			menu.hide();
			callSuper = false;
		}

		
		final RelativeLayout share = (RelativeLayout) findViewById(R.id.share);

		if (share != null && share.getVisibility() == View.VISIBLE) {
			share.setVisibility(View.INVISIBLE);
			callSuper = false;
		}

		final RelativeLayout font = (RelativeLayout) findViewById(R.id.font);

		if (font != null && font.getVisibility() == View.VISIBLE) {
			font.setVisibility(View.INVISIBLE);
			callSuper = false;
		}
		
		if (callSuper) {
			super.onBackPressed();
		}
	}

	/**
	 * Prepare and show home screen
	 */
	private void prepareAndShowHomeScreen() {
		FraHeader header = FraHeader.newInstance(true);
		header.showBackButton(false);
		FraHomeContent homeContent = FraHomeContent.newInstance();
		FraFooter footer = FraFooter.newInstance(FooterIcons.TOP10);
		FraSearch search = FraSearch.newInstance();
		FraFont font = FraFont.newInstance();
		FraShare share = FraShare.newInstance();

		replaceScreenWithNavBarContentAndMenu(header, FraHeader.TAG,
				homeContent, FraHomeContent.TAG, footer, FraFooter.TAG,
				FraMenu.newInstance(), FraMenu.TAG, search, FraSearch.TAG,
				font, FraFont.TAG, share, FraShare.TAG, false);
		prepareAndShowHomeScreen(false);
		/*
		 * replaceScreenWithNavBarContentAndFooter(homeContent,
		 * FraHomeContent.TAG, footer, FraFooter.TAG, false);
		 */
	}

	/**
	 * Prepare and show home screen with backstack or not
	 */
	private void prepareAndShowHomeScreen(boolean addToBackstack) {
		FraHeader header = FraHeader.newInstance(true);
		header.showBackButton(false);
		FraHomeContent homeContent = FraHomeContent.newInstance();
		FraFooter footer = FraFooter.newInstance(FooterIcons.TOP10);
		FraSearch search = FraSearch.newInstance();
		FraFont font = FraFont.newInstance();
		FraShare share = FraShare.newInstance();

		replaceScreenWithNavBarContentAndMenu(header, FraHeader.TAG,
				homeContent, FraHomeContent.TAG, footer, FraFooter.TAG,
				FraMenu.newInstance(), FraMenu.TAG, search, FraSearch.TAG,
				font, FraFont.TAG, share, FraShare.TAG, addToBackstack);

		/*
		 * replaceScreenWithNavBarContentAndFooter(homeContent,
		 * FraHomeContent.TAG, footer, FraFooter.TAG, false);
		 */
	}

	/**
	 * Replace header content and menu of the current screen
	 */
	private void replaceScreenWithNavBarContentAndMenu(Fragment header,
			String headerTag, Fragment content, String contentTag,
			Fragment footer, String footerTag, FraMenu menu, String menuTag,
			FraSearch search, String searchTag, FraFont font, String fontTag,
			FraShare share, String shareTag, boolean addToBackStack) {
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();

		ft.replace(R.id.main_container_header, header, headerTag);

		ft.replace(R.id.main_container_content, content, contentTag);

		ft.replace(R.id.main_container_footer, footer, footerTag);

		ft.replace(R.id.main_container_menu, menu, menuTag);

		ft.replace(R.id.main_container_search, search, searchTag);

		// ft.replace(R.id.main_container_share, share, shareTag);

		// ft.replace(R.id.main_container_font, font, fontTag);

		if (addToBackStack) {
			ft.addToBackStack(String.valueOf(System.identityHashCode(content)));
		}
		ft.commitAllowingStateLoss();
		fm.executePendingTransactions();
	}

	/**
	 * Replace header icon and content of the current screen
	 */
	public void replaceScreenWithNavbarAndContent(Fragment content,
			String contentTag, int navBarIcon, boolean showBack) {

		FraHeader testHeader = FraHeader.newInstance(showBack);
		testHeader.showBackButton(false);
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();

		ft.replace(R.id.main_container_header, testHeader, FraHeader.TAG);

		ft.replace(R.id.main_container_content, content, contentTag);

		ft.addToBackStack(String.valueOf(System.identityHashCode(content)));

		ft.commit();
		fm.executePendingTransactions();

		hideFraMenu();
		hideFraSearch();
	}

	/**
	 * Replace header content and footer of the current screen with backstack or
	 * not
	 */
	public void replaceScreenWithNavBarContentAndFooter(Fragment content,
			String contentTag, Fragment footer, String footerTag,
			boolean showBack, boolean addToBackStack) {
		FraHeader testHeader = FraHeader.newInstance(showBack);
		fm = getSupportFragmentManager();
		ft = fm.beginTransaction();

		ft.replace(R.id.main_container_header, testHeader, FraHeader.TAG);

		ft.replace(R.id.main_container_content, content, contentTag);

		ft.replace(R.id.main_container_footer, footer, footerTag);

		if (addToBackStack) {
			ft.addToBackStack(String.valueOf(System.identityHashCode(content)));
		}

		ft.commitAllowingStateLoss();
		fm.executePendingTransactions();

		hideFraMenu();
		hideFraSearch();
	}

	/**
	 * Replace header content and footer of the current screen
	 */
	public void replaceScreenWithNavBarContentAndFooter(Fragment content,
			String contentTag, Fragment footer, String footerTag,
			boolean showBack) {
		replaceScreenWithNavBarContentAndFooter(content, contentTag, footer,
				footerTag, showBack, false);
	}

	/**
	 * Open menu
	 */
	public void openFraMenu() {

		FraMenu menu = (FraMenu) getSupportFragmentManager().findFragmentByTag(
				FraMenu.TAG);

		if (menu != null) {

			menu.showMenu();
		}
	}

	/**
	 * Hide menu
	 */
	public void hideFraMenu() {

		FraMenu menu = (FraMenu) getSupportFragmentManager().findFragmentByTag(
				FraMenu.TAG);

		if (menu != null) {

			menu.hide();
		}
	}

	/**
	 * Open search
	 */
	public void openFraSearch() {

		FraSearch search = (FraSearch) getSupportFragmentManager()
				.findFragmentByTag(FraSearch.TAG);

		if (search != null) {

			search.showSearch();
		}
	}

	/**
	 * Hide search
	 */
	public void hideFraSearch() {

		FraSearch search = (FraSearch) getSupportFragmentManager()
				.findFragmentByTag(FraSearch.TAG);

		if (search != null) {

			search.hide();
		}
	}

	/**
	 * Initializes the <code>FacebookManager</code>
	 * 
	 * @param savedInstanceState
	 *            saved instance state
	 */
	private void initFacebookManager(Bundle savedInstanceState) {

		this.mLoadingFace = (ProgressBar) findViewById(R.id.progr_bar_face);

		this.mAuthButton = (LoginButton) findViewById(R.id.authButton);

		if (AppConstants.ENABLE_FACEBOOK_SHARE) {
			FacebookManager.getInstance().initManager(this, null,
					savedInstanceState, mLoadingFace, mAuthButton);

			FacebookManager.getInstance().onCreateView(savedInstanceState);
		}
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
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (AppConstants.ENABLE_FACEBOOK_SHARE) {
			FacebookManager.getInstance().onActivityResult(requestCode,
					resultCode, data, this);
		}

	}

	/**
	 * Called when the fragment is no longer in use. This is called after
	 * onStop() and before onDetach().
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (AppConstants.ENABLE_FACEBOOK_SHARE) {
			FacebookManager.getInstance().onDestroy();
		}
	}

	/**
	 * Called when the Fragment is no longer resumed. This is generally tied to
	 * Activity.onPause of the containing Activity's lifecycle.
	 */
	@Override
	public void onPause() {
		super.onPause();
		if (AppConstants.ENABLE_FACEBOOK_SHARE) {
			FacebookManager.getInstance().onPause();
		}
	}

	/**
	 * Removes from backstack
	 */
	public void removeFromBackStack() {
		if (fm.getBackStackEntryCount() > 1) {
			fm.popBackStack(null, 0);
		}
	}

}
