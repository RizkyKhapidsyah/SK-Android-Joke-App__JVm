package com.dmbteam.joke.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Utility class
 * @author dmbTean
 *
 */
public class Utils {

	/**
	 * Font name constant
	 */
	public static final String FONT_NAME = "SHOWG.TTF";

	/**
	 * Sets a custom font to a textview
	 * @param c
	 * @param textView
	 */
	public static void setFontForTextView(Context c, TextView textView) {
		Typeface font = Typeface.createFromAsset(c.getAssets(), FONT_NAME);

		textView.setTypeface(font);
	}
}
