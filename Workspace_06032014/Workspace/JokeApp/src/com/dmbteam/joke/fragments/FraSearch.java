package com.dmbteam.joke.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmbteam.joke.MainActivity;
import com.dmbteam.joke.R;
import com.dmbteam.joke.fragments.FraJokesListContent.ListModes;
import com.dmbteam.joke.fragments.FraMenu.Navigations;

/**
 * @author dmbteam
 * 
 *         Represents the content for search screen
 */
public class FraSearch extends Fragment {

	// Tag that will be used for showing the current fragment
	public static final String TAG = FraSearch.class.getSimpleName();

	// Main view of the search
	private View view;

	private EditText searchCriteria;

	private ImageView searchButton;

	/**
	 * Create and return instance of the current fragment
	 * 
	 * @return FraSearch
	 */
	public static FraSearch newInstance() {

		FraSearch fraSearch = new FraSearch();

		return fraSearch;
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

		view = (View) inflater.inflate(R.layout.fra_search, null);

		return view;
	}

	/**
	 * Life cycle method. Instantiate the views and set the data for them.
	 */
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		searchCriteria = (EditText) view.findViewById(R.id.search_criteria);
		searchButton = (ImageView) view.findViewById(R.id.search_button);
		searchButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				String searchCriteriaText = searchCriteria.getText().toString();
				if (searchCriteriaText != null
						&& !searchCriteriaText.trim().equals("")) {
					((MainActivity) getActivity())
							.replaceScreenWithNavBarContentAndFooter(
									FraJokesListContent.newInstance(
											ListModes.SEARCH,
											searchCriteriaText),
									FraJokesListContent.TAG, FraFooter
											.newInstance(true), FraFooter.TAG,
									true, true);
				}
			}
		});

	};

	/**
	 * Show the menu
	 */
	public void showSearch() {
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

	public boolean isSearchVisible() {
		if (view.getVisibility() == View.VISIBLE) {
			return true;
		}

		return false;
	}
}
