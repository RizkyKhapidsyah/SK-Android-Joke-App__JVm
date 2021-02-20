package com.dmbteam.joke.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dmbteam.joke.MainActivity;
import com.dmbteam.joke.R;
import com.dmbteam.joke.fragments.FraJokesListContent.ListModes;

/**
 * @author dmbteam
 * 
 *         Represents the content for footer part of the screen
 */
public class FraFooter extends Fragment {

	// Tag that will be used for showing the current fragment
	public static final String TAG = FraFooter.class.getSimpleName();
	
	private ImageView top10Jokes;
	private ImageView allJokes;
	private ImageView newStuff;
	private ImageView favoritesJokes;
	private boolean cleanButtons = false;
	
	private FooterIcons selectedIcon;

	/**
	 * Icons in the footer
	 * @author dmbTeam
	 *
	 */
	public enum FooterIcons {
		TOP10, FAVORITES, NEW_STUFF, ALL;
	}
	
	/**
	 * Create and return instance of the current fragment
	 * 
	 * @return FraFooter
	 */
	public static FraFooter newInstance() {

		FraFooter fraFooter = new FraFooter();

		return fraFooter;
	}
	
	/**
	 * Create and return instance of the current fragment
	 * @param cleanButtons
	 * @return
	 */
	public static FraFooter newInstance(boolean cleanButtons) {

		FraFooter fraFooter = newInstance();
		fraFooter.cleanButtons = cleanButtons;

		return fraFooter;
	}
	
	/**
	 * Create and return instance of the current fragment
	 * @param selectedIcon
	 * @return
	 */
	public static FraFooter newInstance(FooterIcons selectedIcon) {

		FraFooter fraFooter = newInstance();
		fraFooter.selectedIcon = selectedIcon;

		return fraFooter;
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
	 * Life cycle method. Set the layout view for current fragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fra_footer, container, false);

		return view;
	}

	/**
	 * Life cycle method. Instantiate the views and set the data for them.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initViews();
	}

	/**
	 * Init the views
	 */
	private void initViews() {
		top10Jokes = (ImageView) getView().findViewById(
				R.id.fra_footer_top_ten);
		top10Jokes.setOnClickListener(new Top10JokesClickListener());
		top10Jokes.setImageResource(R.drawable.top10);
		
		allJokes = (ImageView) getView().findViewById(
				R.id.fra_footer_all_jokes);
		allJokes.setOnClickListener(new AllJokesClickListener());

		newStuff = (ImageView) getView().findViewById(
				R.id.fra_footer_new_stuff);
		newStuff.setOnClickListener(new NewStuffClickListener());

		favoritesJokes = (ImageView) getView().findViewById(
				R.id.fra_footer_favourites);
		favoritesJokes.setOnClickListener(new FavJokesClickListener());
		
		if (!cleanButtons) {
			if (selectedIcon == FooterIcons.ALL) {
				allJokes.setImageResource(R.drawable.alljokes_active);
			} else if (selectedIcon == FooterIcons.NEW_STUFF) {
				newStuff.setImageResource(R.drawable.newstuff_active);
			} else if (selectedIcon == FooterIcons.FAVORITES) {
				favoritesJokes.setImageResource(R.drawable.favorites2_active);
			} else if (selectedIcon == FooterIcons.TOP10) {
				top10Jokes.setImageResource(R.drawable.top10_active);
			}
			
		}

	}

	/**
	 * Listener for all jokes icon click
	 * @author dmbTeam
	 *
	 */
	private class AllJokesClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
		    ((MainActivity) getActivity()).removeFromBackStack();
			((MainActivity) getActivity())
			.replaceScreenWithNavBarContentAndFooter(
					FraJokesListContent.newInstance(ListModes.ALL),
					FraJokesListContent.TAG, FraFooter.newInstance(FooterIcons.ALL),
					FraFooter.TAG, false);
			allJokes.setImageResource(R.drawable.alljokes_active);
			top10Jokes.setImageResource(R.drawable.top10);
			newStuff.setImageResource(R.drawable.newstuff);
			favoritesJokes.setImageResource(R.drawable.favorites2);
		}

	}

	/**
	 * Listener for top 10 icon click
	 * @author dmbTeam
	 *
	 */
	private class Top10JokesClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
		    ((MainActivity) getActivity()).removeFromBackStack();
			((MainActivity) getActivity())
			.replaceScreenWithNavBarContentAndFooter(
					FraHomeContent.newInstance(), FraHomeContent.TAG, FraFooter.newInstance(FooterIcons.TOP10),
					FraFooter.TAG, false);
			allJokes.setImageResource(R.drawable.alljokes);
			top10Jokes.setImageResource(R.drawable.top10_active);
			newStuff.setImageResource(R.drawable.newstuff);
			favoritesJokes.setImageResource(R.drawable.favorites2);
		}

	}

	/**
	 * Listener for new stuff icon click
	 * @author dmbTeam
	 *
	 */
	private class NewStuffClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
	        ((MainActivity) getActivity()).removeFromBackStack();
			((MainActivity) getActivity())
			.replaceScreenWithNavBarContentAndFooter(
					FraJokesListContent
					.newInstance(ListModes.NEW_STUFF),
					FraJokesListContent.TAG, FraFooter.newInstance(FooterIcons.NEW_STUFF),
					FraFooter.TAG, false);
			allJokes.setImageResource(R.drawable.alljokes);
			top10Jokes.setImageResource(R.drawable.top10);
			newStuff.setImageResource(R.drawable.newstuff_active);
			favoritesJokes.setImageResource(R.drawable.favorites2);

		}

	}

	/**
	 * Listener for favourite jokes icon click
	 * @author dmbTeam
	 *
	 */
	private class FavJokesClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
		    ((MainActivity) getActivity()).removeFromBackStack();
			((MainActivity) getActivity())
					.replaceScreenWithNavBarContentAndFooter(
							FraJokesListContent
									.newInstance(ListModes.FAVORITES),
							FraJokesListContent.TAG, FraFooter.newInstance(FooterIcons.FAVORITES),
							FraFooter.TAG, false);
			allJokes.setImageResource(R.drawable.alljokes);
			top10Jokes.setImageResource(R.drawable.top10);
			newStuff.setImageResource(R.drawable.newstuff);
			favoritesJokes.setImageResource(R.drawable.favorites2_active);
		}

	}
	
}
