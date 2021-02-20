package com.dmbteam.joke.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmbteam.joke.R;
import com.dmbteam.joke.cmn.Joke;

/**
 * Adapter for all Jokes
 * @author dmbTeam
 *
 */
public class AdapterAllJokes extends ArrayAdapter<Joke> {

	/**
	 * Class constructor
	 * @param context
	 * @param objects
	 */
	public AdapterAllJokes(Context context, List<Joke> objects) {
		super(context, 0, objects);
	}

	/**
	 * Returns the Joke View
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = getInflater().inflate(R.layout.fra_jokes_listview_item,
				null);

		ImageView isReadImage = (ImageView) convertView
				.findViewById(R.id.fra_jokes_listview_item_unread);

		if (getItem(position).getReadCounter() > 0) {
			isReadImage.setVisibility(View.GONE);
		}

		ImageView isFavImage = (ImageView) convertView
				.findViewById(R.id.fra_jokes_listview_item_star);

		if (getItem(position).isFavorite()) {
			isFavImage.setImageDrawable(getContext().getResources()
					.getDrawable(R.drawable.star_filled));
		}

		TextView jokeText = (TextView) convertView
				.findViewById(R.id.fra_jokes_listview_item_text);
		jokeText.setText(getItem(position).getJokeText());

		return convertView;
	}

	/**
	 * Layout inflater
	 * @return
	 */
	public LayoutInflater getInflater() {
		return LayoutInflater.from(getContext());
	}
}
