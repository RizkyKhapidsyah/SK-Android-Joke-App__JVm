package com.dmbteam.joke.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.dmbteam.joke.cmn.Joke;

/**
 * This class is responsible for all XML operations
 * @author dmbTeam
 *
 */
public class XmlManager {

	/**
	 * XML attributes
	 * @author dmbTeam
	 *
	 */
	static class Attributes {
		public static final String SINGLE_JOKE_MAIN_TAG = "joke";
		public static final String SINGLE_JOKE_ID = "id";
		public static final String SINGLE_JOKE_AUTHOR = "author";
		public static final String SINGLE_JOKE_DATE = "date";
		public static final String SINGLE_JOKE_NEW_STUFF = "newStuff";
		public static final String SINGLE_JOKE_TEXT = "jokeText";
	}

	public static XmlManager instance;

	/**
	 * Instance of the class
	 * @return
	 */
	public static XmlManager getInstance() {

		if (instance == null) {
			instance = new XmlManager();
		}

		return instance;
	}

	/**
	 * Constructor
	 */
	private XmlManager() {
		this.jokes = new ArrayList<Joke>();
	}

	private List<Joke> jokes;

	private InputStream insputStream;
	private int eventType;

	/**
	 * Parses the input stream
	 */
	public void parseInputStream() {
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;

		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			parser = factory.newPullParser();

			parser.setInput(insputStream, null);

			eventType = parser.getEventType();

			while (true) {

				if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
					break;
				}

				if (parser.getEventType() == XmlPullParser.START_TAG
						&& parser.getName().equals(
								Attributes.SINGLE_JOKE_MAIN_TAG)) {

					Joke currentJoke = new Joke();

					while (true) {

						if (parser.getEventType() == XmlPullParser.END_TAG
								&& parser.getName().equals(
										Attributes.SINGLE_JOKE_MAIN_TAG)) {
							break;
						}

						if (parser.getEventType() == XmlPullParser.START_TAG
								&& parser.getName().equals(
										Attributes.SINGLE_JOKE_ID)) {
							currentJoke
									.setId(Integer.valueOf(parser.nextText()));

						}

						if (parser.getEventType() == XmlPullParser.START_TAG
								&& parser.getName().equals(
										Attributes.SINGLE_JOKE_AUTHOR)) {
							currentJoke.setAuthor(parser.nextText());

						}

						if (parser.getEventType() == XmlPullParser.START_TAG
								&& parser.getName().equals(
										Attributes.SINGLE_JOKE_DATE)) {
							currentJoke.setDate(parser.nextText());

						}
						
						if (parser.getEventType() == XmlPullParser.START_TAG
								&& parser.getName().equals(
										Attributes.SINGLE_JOKE_NEW_STUFF)) {
						    String text = parser.nextText();
							if (text != null && text.equalsIgnoreCase("true")) {
								currentJoke.setNewStuff(true);
							} else {
								currentJoke.setNewStuff(false);
							}
						}

						if (parser.getEventType() == XmlPullParser.START_TAG
								&& parser.getName().equals(
										Attributes.SINGLE_JOKE_TEXT)) {
							currentJoke.setJokeText(parser.nextText());

							jokes.add(currentJoke);
						}

						parser.next();
					}

				}

				eventType = parser.next();
			}
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("");
		}

		System.out.println("");

	}

	/**
	 * Sets the input stream
	 * @param insputStream
	 */
	public void setInsputStream(InputStream insputStream) {
		this.insputStream = insputStream;
	}

	/**
	 * Returns all jokes
	 * @return
	 */
	public List<Joke> getAllJokes() {
		return jokes;
	}
}
