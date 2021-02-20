package com.dmbteam.joke.fragments;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.dmbteam.joke.MainActivity;
import com.dmbteam.joke.R;
import com.dmbteam.joke.adapter.AdapterAllJokes;
import com.dmbteam.joke.cmn.Joke;
import com.dmbteam.joke.database.DatabaseManager;

/**
 * @author dmbteam
 * 
 *         Represents the jokes list content
 */
public class FraJokesListContent extends Fragment {

	public enum ListModes {
		ALL, FAVORITES, NEW_STUFF, SEARCH;
	}

	// Tag that will be used for showing the current fragment
	public static final String TAG = FraJokesListContent.class.getSimpleName();

	private DatabaseManager dbManager;

	private ImageView mLatestReadImage;

	private ImageView mMostReadImage;

	private ListView mJokesListView;

	private AdapterAllJokes mAdapterJokes;

	private ListModes mListMode;
	
	private SharedPreferences prefs;
	
	private String searchCriteria;
	
	private List<Joke> mJokes;
	
	private boolean isMostRead = false;

	/**
	 * Create and return instance of the current fragment
	 * 
	 * @return FraHomeContent
	 */
	public static FraJokesListContent newInstance(ListModes listMode) {

		FraJokesListContent fraJokesListContent = new FraJokesListContent();
		fraJokesListContent.dbManager = DatabaseManager.getInstance();
		fraJokesListContent.mListMode = listMode;

		return fraJokesListContent;
	}
	
	/**
	 * Create and return instance of the current fragment by given search criteria
	 * 
	 * @param searchCriteria
	 * @return FraHomeContent
	 */
	public static FraJokesListContent newInstance(ListModes listMode, String searchCriteria) {

		FraJokesListContent fraJokesListContent = newInstance(listMode);
		fraJokesListContent.searchCriteria = searchCriteria;

		return fraJokesListContent;
	}

	/**
	 * Life cycle method. Set the layout view for current fragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fra_jokes_list_content, null);

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
	 * Init the views and set listeners and data for them
	 */
	private void initViews() {

			mLatestReadImage = (ImageView) getView().findViewById(
					R.id.fra_jokes_list_content_latest_read);

			mMostReadImage = (ImageView) getView().findViewById(
					R.id.fra_jokes_list_content_most_read);

		if (mListMode == ListModes.SEARCH || mListMode == ListModes.NEW_STUFF
				|| mListMode == ListModes.FAVORITES) {
			getView().findViewById(R.id.sorting_layout)
					.setVisibility(View.GONE);

		} else {
			mLatestReadImage
					.setOnClickListener(new JokesLatestReadClickListener());
			mMostReadImage.setOnClickListener(new JokesMostReadClickListener());
			
			if (isMostRead) {
				mLatestReadImage.setImageResource(R.drawable.latest);
				mMostReadImage.setImageResource(R.drawable.mostread_active);
			} else {
				mLatestReadImage.setImageResource(R.drawable.latest_active);
				mMostReadImage.setImageResource(R.drawable.mostread);
			}
		}

		mJokesListView = (ListView) getView().findViewById(
				R.id.fra_jokes_list_content_listview);
		mJokesListView.setOnItemClickListener(new JokesItemClickListener());

		if (mJokes == null) {
			if (mListMode == ListModes.ALL) {
				mJokes = dbManager.getAllJokesSortedLatest();
			} else if (mListMode == ListModes.FAVORITES) {
				mJokes = dbManager.getAllFavoriteJokesSortedLatest();
			} else if (mListMode == ListModes.NEW_STUFF) {
				mJokes = dbManager.getNewStuffSortedLatest();
			} else if (mListMode == ListModes.SEARCH && searchCriteria != null) {
				mJokes = dbManager.getSearchedJokes(searchCriteria);
			}
			
		}
		mAdapterJokes = new AdapterAllJokes(getActivity(), mJokes);
		mJokesListView.setAdapter(mAdapterJokes);

	}

	/**
	 * Listener for latest icon click
	 * @author dmbTeam
	 *
	 */
	private class JokesLatestReadClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			isMostRead = false;
			mLatestReadImage.setImageDrawable(getResources().getDrawable(
					R.drawable.latest_active));
			mMostReadImage.setImageDrawable(getResources().getDrawable(
					R.drawable.mostread));

			if (mListMode == ListModes.ALL) {
						mJokes = dbManager.getAllJokesSortedLatest();
			} else if (mListMode == ListModes.FAVORITES) {
						mJokes = dbManager.getAllFavoriteJokesSortedLatest();
			}
			mAdapterJokes = new AdapterAllJokes(getActivity(), mJokes);
			mJokesListView.setAdapter(mAdapterJokes);
		}

	}

	/**
	 * Listener for most read icon click
	 * @author dmbTeam
	 *
	 */
	private class JokesMostReadClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			isMostRead = true;
			mLatestReadImage.setImageDrawable(getResources().getDrawable(
					R.drawable.latest));
			mMostReadImage.setImageDrawable(getResources().getDrawable(
					R.drawable.mostread_active));

			if (mListMode == ListModes.ALL) {
				mJokes = dbManager.getAllJokesSortedMostRead();
			} else if (mListMode == ListModes.FAVORITES) {
				mJokes = dbManager.getAllFavoriteJokesSortedMostRead();
			} else if (mListMode == ListModes.NEW_STUFF) {
				mJokes = dbManager.getNewStuffSortedMostRead();
			}

			mAdapterJokes = new AdapterAllJokes(getActivity(), mJokes);
			mJokesListView.setAdapter(mAdapterJokes);
		}

	}

	/**
	 * Listener for a single joke click
	 * @author dmbTeam
	 *
	 */
	private class JokesItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {

			// Update the joke first
			dbManager.incrementJokeReadCounter(mAdapterJokes.getItem(position));

			FraSingleJokeContent fraSingleJoke = FraSingleJokeContent
					.newInstance(position, mJokes);

			((MainActivity) getActivity())
					.replaceScreenWithNavBarContentAndFooter(fraSingleJoke,
							FraSingleJokeContent.TAG,
							FraFooter.newInstance(), FraFooter.TAG, true, true);
		}

	}

}
