package com.dmbteam.joke.fragments;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dmbteam.joke.MainActivity;
import com.dmbteam.joke.R;
import com.dmbteam.joke.cmn.Joke;
import com.dmbteam.joke.database.DatabaseManager;
import com.dmbteam.joke.settings.AppConstants;
import com.dmbteam.joke.share.FacebookManager;
import com.dmbteam.joke.share.MailManager;
import com.dmbteam.joke.share.TwitterManager;
import com.dmbteam.joke.util.Utils;

/**
 * @author dmbteam
 * 
 *         Represents the content for a single joke
 */
public class FraSingleJokeContent extends Fragment {
	

	/**
	 * <code>PagerAdapter</code> for home joke slider
	 * 
	 * @author |dmb TEAM|
	 * 
	 */
	private class JokesSinglePagerAdapter extends PagerAdapter {

		/**
		 * Inflater for layouts
		 */
		private LayoutInflater inflater;

		/**
		 * Class constructor
		 * 
		 * @param jokes
		 *            list of jokes to be displayed
		 * @param inflater
		 *            layout inflater
		 */
		public JokesSinglePagerAdapter(	LayoutInflater inflater) {
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
			collection.removeView((RelativeLayout) view);
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
		public Object instantiateItem(ViewGroup collection, int position) {

			RelativeLayout singleJokeView = (RelativeLayout) this.inflater
					.inflate(R.layout.fra_single_joke_pager_item, null);

			TextView dateText = (TextView) singleJokeView
					.findViewById(R.id.date_text);
			Utils.setFontForTextView(getActivity(), dateText);
			dateText.setText(jokes.get(position)
					.getDate());

			TextView byText = (TextView) singleJokeView
					.findViewById(R.id.by_text);
			Utils.setFontForTextView(getActivity(), byText);

			TextView authotText = (TextView) singleJokeView
					.findViewById(R.id.author_text);
			Utils.setFontForTextView(getActivity(), authotText);
			authotText.setText(jokes.get(position)
					.getAuthor());

			TextView contentText = (TextView) singleJokeView
					.findViewById(R.id.joke_text);
			contentText.setTag(AppConstants.TEXT + position);
			contentText.setTextSize(TypedValue.COMPLEX_UNIT_PX,
					prefs.getFloat(AppConstants.FONT_SIZE, 10) + 20);

			contentText.setText(jokes.get(position)
					.getJokeText());

			collection.addView(singleJokeView, 0);

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
	public static final String TAG = FraSingleJokeContent.class.getSimpleName();

	private int mPosition;

	private DatabaseManager mDatabasManager;
	
	private List<Joke> jokes;

	private ImageView favImage;

	private SharedPreferences prefs;

	private SeekBar seekbar;

	private ViewPager pager;

	/**
	 * Create and return instance of the current fragment
	 * 
	 * @param position
	 * 
	 * @return FraHomeContent
	 */
	public static FraSingleJokeContent newInstance(int position, List<Joke> jokes) {

		FraSingleJokeContent fraSingleJokeContent = new FraSingleJokeContent();
		fraSingleJokeContent.mPosition = position;
		fraSingleJokeContent.jokes = jokes;

		return fraSingleJokeContent;
	}

	public FraSingleJokeContent() {
		mDatabasManager = DatabaseManager.getInstance();
	}

	/**
	 * Life cycle method. Set the layout view for current fragment.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fra_single_joke_content, null);
		
		if(AppConstants.ENABLE_ADMOB_SINGLE_NEW){
            ((MainActivity) getActivity()).getAdmobView().setVisibility(View.VISIBLE);
        } else {
            ((MainActivity) getActivity()).getAdmobView().setVisibility(View.GONE);
        }


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
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		float fs = prefs.getFloat(AppConstants.FONT_SIZE, 10);

		seekbar = (SeekBar) getView().findViewById(R.id.seekbar_font);
		seekbar.setMax(40);
		seekbar.setProgress((int) fs);
	}

	/**
	 * Init the views and set listeners and data for them
	 */
	private void initViews() {

		pager = (ViewPager) getView().findViewById(R.id.fra_single_jokes_pager);
		JokesSinglePagerAdapter adapter = new JokesSinglePagerAdapter(getInflater());

		pager.setAdapter(adapter);
		pager.setCurrentItem(mPosition);
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
				if (jokes.get(position).isFavorite()) {
					favImage.setImageDrawable(getActivity().getResources()
							.getDrawable(R.drawable.favorites_single_added));
				} else {
					favImage.setImageDrawable(getActivity().getResources()
							.getDrawable(R.drawable.favorites_single));
				}

				favImage.invalidate();
				((TextView) pager.findViewWithTag(AppConstants.TEXT + position))
						.setTextSize(TypedValue.COMPLEX_UNIT_PX,
								prefs.getFloat(AppConstants.FONT_SIZE, 10) + 20);

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		/*ImageView leftArrowImage = (ImageView) getView().findViewById(
				R.id.fra_single_arrow_left);
		ImageView rightArrowImage = (ImageView) getView().findViewById(
				R.id.fra_single_arrow_right);
		ImageView fontImage = (ImageView) getView().findViewById(
				R.id.fra_single_font);
		ImageView shareImage = (ImageView) getView().findViewById(
				R.id.fra_single_share);*/
		favImage = (ImageView) getView().findViewById(
				R.id.fra_single_favourites);

		favImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Joke joke = jokes.get(
						pager.getCurrentItem());
				if (joke.isFavorite()) {
					mDatabasManager.markJokeAsNotFavourite(joke);
					((ImageView) (v)).setImageDrawable(getResources().getDrawable(
							R.drawable.favorites_single));
				} else {
					mDatabasManager.markJokeAsRead(joke);
					((ImageView) (v)).setImageDrawable(getResources().getDrawable(
							R.drawable.favorites_single_added));
				}
				
			}
		});

		initButtonActions();
	}

	/**
	 * Layout inflater
	 * @return
	 */
	private LayoutInflater getInflater() {
		return LayoutInflater.from(getActivity());
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
				R.id.fra_single_share);
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
				MailManager.openMailIntent(
						getActivity(),
						"Joke by: "
								+ jokes
										.get(pager.getCurrentItem())
										.getAuthor(), jokes.get(pager.getCurrentItem())
								.getJokeText());

			}
		});
		ImageView shareButtonTwitter = (ImageView) getView().findViewById(
				R.id.twitter_share);
		shareButtonTwitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TwitterManager twitter = TwitterManager.getInstance();
				twitter.initManager(getActivity(), jokes.get(pager.getCurrentItem()));
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
							jokes.get(pager.getCurrentItem()));
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
				R.id.fra_single_font);
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
				prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
				SharedPreferences.Editor ed = prefs.edit();
				ed.putFloat(AppConstants.FONT_SIZE, seekbar.getProgress() );
				ed.commit();

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				((TextView) pager.findViewWithTag(AppConstants.TEXT + pager.getCurrentItem()))
						.setTextSize(TypedValue.COMPLEX_UNIT_PX,
								seekbar.getProgress() + 20);
			}
		});
		
		ImageView leftArrowImage = (ImageView) getView().findViewById(
				R.id.fra_single_arrow_left);
		leftArrowImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(pager.getCurrentItem() - 1);
			}
		});
		ImageView rightArrowImage = (ImageView) getView().findViewById(
				R.id.fra_single_arrow_right);
		rightArrowImage.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(pager.getCurrentItem() + 1);
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

}
