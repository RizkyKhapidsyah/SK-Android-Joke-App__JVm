package com.dmbteam.joke.fragments;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dmbteam.joke.R;
import com.dmbteam.joke.cmn.Joke;
import com.dmbteam.joke.database.DatabaseManager;
import com.dmbteam.joke.settings.AppConstants;
import com.dmbteam.joke.share.FacebookManager;
import com.dmbteam.joke.share.MailManager;
import com.dmbteam.joke.share.TwitterManager;
import com.dmbteam.joke.util.Utils;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * @author dmbteam
 * 
 *         Represents the content for home screen
 */
public class FraHomeContent extends Fragment {

	/**
	 * <code>PagerAdapter</code> for home joke slider
	 * 
	 * @author |dmb TEAM|
	 * 
	 */
	private class JokesPagerAdapter extends PagerAdapter {

		/**
		 * Inflater for layouts
		 */
		private LayoutInflater inflater;

		/**
		 * List of jokes to be displayed
		 */
		private List<Joke> jokes;

		/**
		 * Class constructor
		 * 
		 * @param jokes
		 *            list of jokes to be displayed
		 * @param inflater
		 *            layout inflater
		 */
		public JokesPagerAdapter(List<Joke> jokes, LayoutInflater inflater) {
			this.jokes = jokes;
			this.inflater = inflater;
		}

		/**
		 * Remove a page for the given position. The adapter is responsible for
		 * removing the view from its container, although it only must ensure
		 * this is done by the time it returns from
		 * {@link #finishUpdate(android.view.ViewGroup)}.
		 * 
		 * @param collection
		 *            The containing View from which the page will be removed.
		 * @param position
		 *            The page position to be removed.
		 * @param view
		 *            The same object that was returned by
		 *            {@link #instantiateItem(android.view.View, int)}.
		 */
		@Override
		public void destroyItem(ViewGroup collection, int position, Object view) {
			collection.removeView((ScrollView) view);
		}

		/**
		 * Called when the a change in the shown pages has been completed. At
		 * this point you must ensure that all of the pages have actually been
		 * added or removed from the container as appropriate.
		 * 
		 * @param arg0
		 *            The containing View which is displaying this adapter's
		 *            page views.
		 */
		@Override
		public void finishUpdate(ViewGroup arg0) {
		}

		/**
		 * Return the number of views available.
		 */
		@Override
		public int getCount() {
			return jokes.size();
		}

		/**
		 * Create the page for the given position. The adapter is responsible
		 * for adding the view to the container given here, although it only
		 * must ensure this is done by the time it returns from
		 * {@link #finishUpdate(android.view.ViewGroup)}.
		 * 
		 * @param collection
		 *            The containing View in which the page will be shown.
		 * @param position
		 *            The page position to be instantiated.
		 * @return Returns an Object representing the new page. This does not
		 *         need to be a View, but can be some other container of the
		 *         page.
		 */
		@Override
		public Object instantiateItem(ViewGroup collection, final int position) {

			ScrollView singleJokeView = (ScrollView) this.inflater
					.inflate(R.layout.fra_main_pager_item, null);

			TextView tvJokeText = (TextView) singleJokeView
					.findViewById(R.id.joke_text);
			tvJokeText.setText(jokes.get(position).getJokeText());
			tvJokeText.setTag(AppConstants.TEXT + position);
			tvJokeText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
					prefs.getFloat(AppConstants.FONT_SIZE, 10) + 20);
			TextView tvDate = (TextView) singleJokeView
					.findViewById(R.id.date_text);
			tvDate.setText(jokes.get(position).getDate());
			Utils.setFontForTextView(getActivity(), tvDate);

			TextView tvByText = (TextView) singleJokeView
					.findViewById(R.id.by_text);
			Utils.setFontForTextView(getActivity(), tvByText);

			TextView tvAuthor = (TextView) singleJokeView
					.findViewById(R.id.author_text);
			tvAuthor.setText(jokes.get(position).getAuthor());
			Utils.setFontForTextView(getActivity(), tvAuthor);
			collection.addView(singleJokeView, 0);

			/*singleJokeView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// Update the joke first
					mDmManager.incrementJokeReadCounter(mDmManager
							.getFirstFiveJokes().get(position));

					FraSingleJokeContent fraSingleJoke = FraSingleJokeContent
							.newInstance(position);

					((MainActivity) getActivity())
							.replaceScreenWithNavBarContentAndFooter(
									fraSingleJoke, FraSingleJokeContent.TAG, 0,
									"", FraFooter.newInstance(), FraFooter.TAG,
									true);

				}
			});*/

			return singleJokeView;

		}

		/**
		 * Determines whether a page View is associated with a specific key
		 * object as returned by instantiateItem(ViewGroup, int). This method is
		 * required for a PagerAdapter to function properly.
		 * 
		 * @param view
		 *            Page View to check for association with object
		 * @param object
		 *            Object to check for association with view
		 * @return
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return (view == object);
		}

		/**
		 * Restore any instance state associated with this adapter and its pages
		 * that was previously saved by saveState().
		 * 
		 * @param arg0
		 *            State previously saved by a call to saveState()
		 * @param arg1
		 *            A ClassLoader that should be used to instantiate any
		 *            restored objects
		 */
		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		/**
		 * Save any instance state associated with this adapter and its pages
		 * that should be restored if the current UI state needs to be
		 * reconstructed.
		 * 
		 * @return Saved state for this adapter
		 */
		@Override
		public Parcelable saveState() {
			return null;
		}

		/**
		 * Called to inform the adapter of which item is currently considered to
		 * be the "primary", that is the one show to the user as the current
		 * page.
		 * 
		 * @param container
		 *            The containing View from which the page will be removed.
		 * @param position
		 *            The page position that is now the primary.
		 * @param object
		 *            The same object that was returned by instantiateItem(View,
		 *            int).
		 */
		@Override
		public void setPrimaryItem(ViewGroup container, int position,
				Object object) {
			super.setPrimaryItem(container, position, object);

		}

		/**
		 * Called when a change in the shown pages is going to start being made.
		 * 
		 * @param container
		 *            The containing View which is displaying this adapter's
		 *            page views.
		 */
		@Override
		public void startUpdate(ViewGroup arg0) {
		}

	}

	// Tag that will be used for showing the current fragment
	public static final String TAG = FraHomeContent.class.getSimpleName();
	private SeekBar seekbar;

	private SharedPreferences prefs;

	/**
	 * Create and return instance of the current fragment
	 * 
	 * @return FraHomeContent
	 */
	public static FraHomeContent newInstance() {

		FraHomeContent fraHeader = new FraHomeContent();

		return fraHeader;
	}

	private DatabaseManager mDmManager;

	public FraHomeContent() {
		mDmManager = DatabaseManager.getInstance();
	}

	private ViewPager mPager;
	private CirclePageIndicator mIndicator;
	private ImageView mStarImage;

	/**
	 * Life cycle method. Set the layout view for current fragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fra_home_content, container,
				false);

		return view;
	}

	/**
	 * Life cycle method. Instantiate the views and set the data for them.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		initPagerView();

		initArrowViews();

		initSliderMarker();

		initJokeViews();
		
		initButtonActions();

	}

	/**
	 * Init the views and set listeners and data for them
	 */
	private void initPagerView() {
		mPager = (ViewPager) getView().findViewById(R.id.fra_home_jokes_pager);
		JokesPagerAdapter jokesAdapter = new JokesPagerAdapter(DatabaseManager
				.getInstance().getFirstFiveJokes(), getActivity()
				.getLayoutInflater());

		mPager.setAdapter(jokesAdapter);
		mPager.setCurrentItem(0);
	}

	/**
	 * Initializes the arrow views
	 */
	private void initArrowViews() {
		ImageView leftArrowImageView = (ImageView) getView().findViewById(
				R.id.arrow_left_home);
		ImageView rightArrowImageView = (ImageView) getView().findViewById(
				R.id.arrow_right_home);

		leftArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			}
		});

		rightArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(mPager.getCurrentItem() + 1);
			}
		});

	}

	private void initJokeViews() {
		mStarImage = (ImageView) getView().findViewById(
				R.id.fra_home_favourites);

		mStarImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Joke joke = mDmManager.getFirstFiveJokes().get(
						mPager.getCurrentItem());
				if (joke.isFavorite()) {
					mDmManager.markJokeAsNotFavourite(joke);
					((ImageView) (v)).setImageDrawable(getResources().getDrawable(
							R.drawable.favorites));
				} else {
					mDmManager.markJokeAsRead(joke);
					((ImageView) (v)).setImageDrawable(getResources().getDrawable(
							R.drawable.favorites_added));
				}
				
			}
		});
	}

	private void initSliderMarker() {
		mIndicator = (CirclePageIndicator) getView().findViewById(
				R.id.indicator);
		mIndicator.setRadius(getResources().getDisplayMetrics().density * 8);
		mIndicator.setFillColor(getResources().getColor(R.color.yellow));
		mIndicator.setViewPager(mPager);
		mIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


			@Override
			public void onPageSelected(int position) {

			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {

				if (DatabaseManager.getInstance().getFirstFiveJokes()
						.get(position).isFavorite()) {
					mStarImage.setImageDrawable(getResources().getDrawable(
							R.drawable.favorites_added));
				} else {
					mStarImage.setImageDrawable(getResources().getDrawable(
							R.drawable.favorites));
				}

				mStarImage.invalidate();

				TextView tv = (TextView) mPager.findViewWithTag(AppConstants.TEXT + mPager.getCurrentItem());
				if (tv != null) {
					tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
							prefs.getFloat(AppConstants.FONT_SIZE, 10) + 20);
				}


			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub


			}
		});
	}
	
	/**
	 * Initializes the button actions
	 */
	private void initButtonActions() {
		final RelativeLayout shareLayout = (RelativeLayout) getView()
				.findViewById(R.id.share);
		setShareButtonsVisibility(shareLayout);

		final RelativeLayout fontLayout = (RelativeLayout) getView()
				.findViewById(R.id.font);

		ImageView shareLayoutClose = (ImageView) getView().findViewById(
				R.id.close_share);
		shareLayoutClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				shareLayout.setVisibility(View.INVISIBLE);
			}
		});
		ImageView shareButton = (ImageView) getView().findViewById(
				R.id.fra_home_share);
		if (AppConstants.ENABLE_EMAIL_SHARE || AppConstants.ENABLE_FACEBOOK_SHARE || AppConstants.ENABLE_TWITTER_SHARE) {
            shareButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    shareLayout.setVisibility(View.VISIBLE);
                    fontLayout.setVisibility(View.INVISIBLE);
                }
            });
        } else {
            shareButton.setVisibility(View.GONE);
        }
		ImageView shareButtonMail = (ImageView) getView().findViewById(
				R.id.mail_share);
		shareButtonMail.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				MailManager.openMailIntent(getActivity(), "Joke by: " + DatabaseManager
						.getInstance().getFirstFiveJokes().get(mPager.getCurrentItem()).getAuthor(),
						DatabaseManager
						.getInstance().getFirstFiveJokes().get(mPager.getCurrentItem()).getJokeText());

			}
		});
		ImageView shareButtonTwitter = (ImageView) getView().findViewById(
				R.id.twitter_share);
		shareButtonTwitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TwitterManager twitter = TwitterManager.getInstance();
				twitter.initManager(getActivity(), DatabaseManager
						.getInstance().getFirstFiveJokes().get(mPager.getCurrentItem()));
				twitter.tweet();

			}
		});
		ImageView shareButtonFacebook = (ImageView) getView().findViewById(
				R.id.face_share);
		shareButtonFacebook.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (FacebookManager.getInstance().isNetworkConnected()) {
					FacebookManager.getInstance().setJoke(
							DatabaseManager
							.getInstance().getFirstFiveJokes().get(mPager.getCurrentItem()));
					FacebookManager.getInstance().publishStory();
				} else {
					Toast.makeText(
							getActivity(),
							getResources().getString(
									R.string.no_internet_connectivity),
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		ImageView fontLayoutClose = (ImageView) getView().findViewById(
				R.id.close_font);
		fontLayoutClose.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				fontLayout.setVisibility(View.INVISIBLE);
			}
		});

		ImageView fontButton = (ImageView) getView().findViewById(
				R.id.fra_home_font);
		fontButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				fontLayout.setVisibility(View.VISIBLE);
				shareLayout.setVisibility(View.INVISIBLE);
			}
		});

		prefs = getActivity().getPreferences(Context.MODE_PRIVATE);

		float fs = prefs.getFloat(AppConstants.FONT_SIZE, 10);

		seekbar = (SeekBar) getView().findViewById(R.id.seekbar_font);
		seekbar.setMax(40);
		seekbar.setProgress((int) fs);
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				//TextView tv = (TextView) mPager.findViewWithTag(AppConstants.TEXT + mPager.getCurrentItem());
				//if (tv != null) {
					prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
					SharedPreferences.Editor ed = prefs.edit();
					ed.putFloat(AppConstants.FONT_SIZE, seekbar.getProgress());
					ed.commit();
				//}
				

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				TextView tv = (TextView) mPager.findViewWithTag(AppConstants.TEXT + mPager.getCurrentItem());
				if (tv != null) {
					tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
							seekbar.getProgress() + 20);
				}
						
			}
		});
	}
	
	private void setShareButtonsVisibility(RelativeLayout shareLayout) {
        ImageView twitterShare = (ImageView) shareLayout.findViewById(R.id.twitter_share);
        if (!AppConstants.ENABLE_TWITTER_SHARE) {
            twitterShare.setVisibility(View.GONE);
        }

        ImageView facebookShare = (ImageView) shareLayout.findViewById(R.id.face_share);
        if (!AppConstants.ENABLE_FACEBOOK_SHARE) {
            facebookShare.setVisibility(View.GONE);
        }
        
        ImageView emailShare = (ImageView) shareLayout.findViewById(R.id.mail_share);
        if (!AppConstants.ENABLE_EMAIL_SHARE) {
            emailShare.setVisibility(View.GONE);
        }
    }
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		float fs = prefs.getFloat(AppConstants.FONT_SIZE, 10);

		seekbar = (SeekBar) getView().findViewById(R.id.seekbar_font);
		seekbar.setMax(40);
		seekbar.setProgress((int) fs);
	}
}
