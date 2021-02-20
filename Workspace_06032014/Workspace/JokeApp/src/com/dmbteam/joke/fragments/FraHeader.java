package com.dmbteam.joke.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmbteam.joke.MainActivity;
import com.dmbteam.joke.R;

/**
 * @author dmbteam
 * 
 *         Represents the header part of all screens
 */
public class FraHeader extends Fragment {

	// Tag that will be used for showing the current fragment
	public static final String TAG = FraHeader.class.getSimpleName();

	private ImageView mBack;

	// Icon view for the header
	private ImageView mIcon;

	// Title view for the header
	private TextView mTitle;

	private boolean mShowBack;

	private ImageView mLogo;

	/**
	 * Create and return instance of the current fragment
	 * 
	 * @param showBack
	 * 
	 * @return FraHeader
	 */
	public static FraHeader newInstance(boolean showBack) {

		FraHeader fraHeader = new FraHeader();
		fraHeader.mShowBack = showBack;
		return fraHeader;
	}

	/**
	 * Life cycle method. Set the layout view for current fragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fra_header, container, false);
		

		return view;
	}

	/**
	 * Life cycle method. Instantiate the views and set the data for them.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		mBack = (ImageView) getView().findViewById(R.id.fra_header_back);
		mLogo = (ImageView) getView().findViewById(R.id.fra_header_logo);
		if (mShowBack) {
			mBack.setVisibility(View.VISIBLE);

			mBack.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					getActivity().onBackPressed();

				}
			});

			mLogo.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					getActivity().onBackPressed();

				}
			});

		} else {
			mBack.setVisibility(View.GONE);
		}
		
		ImageView menu = (ImageView) getView().findViewById(
				R.id.fra_header_action);
		menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).hideFraSearch();
				((MainActivity) getActivity()).openFraMenu();
			}
		});
		
		ImageView search = (ImageView) getView().findViewById(
				R.id.fra_header_search);
		search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).hideFraMenu();
				((MainActivity) getActivity()).openFraSearch();
			}
		});

	}

	/**
	 * Show back button or not
	 * @param show
	 */
	public void showBackButton(boolean show) {
		mShowBack = show;
	}

	
}
