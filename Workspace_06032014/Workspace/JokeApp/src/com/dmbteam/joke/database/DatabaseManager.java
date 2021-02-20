package com.dmbteam.joke.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.dmbteam.joke.cmn.Joke;
import com.dmbteam.joke.util.SortingManager;
import com.dmbteam.joke.xml.XmlManager;
import com.j256.ormlite.dao.Dao;

/**
 * This class is responsible for all database operations
 * @author dmbTean
 *
 */
public class DatabaseManager {

	private static DatabaseManager mInstance;
	private DatabaseHelper mHelper;
	private List<Joke> jokeList;

	/**
	 * Constructor
	 * @param ctx
	 */
	private DatabaseManager(Context ctx) {
		mHelper = new DatabaseHelper(ctx);
	}

	/**
	 * Initialisez the database manager
	 * @param context
	 */
	public static void init(Context context) {
		if (mInstance == null) {
			mInstance = new DatabaseManager(context);
		}
	}

	/**
	 * Returns instance of this class
	 * @return
	 */
	public static DatabaseManager getInstance() {
		return mInstance;
	}

	/**
	 * Returns the database helper
	 * @return
	 */
	public DatabaseHelper getHelper() {
		return mHelper;
	}

	/**
	 * Returns all jokes
	 * @return
	 */
	public List<Joke> requestAllJokes() {
		jokeList = new ArrayList<Joke>();

		try {
			jokeList = mHelper.getJokeDao().queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return jokeList;
	}

	public List<Joke> getAllJokes() {
		return jokeList;
	}

	/**
	 * Returns all Jokes sorted by Most Read
	 * @return
	 */
	public List<Joke> getAllJokesSortedMostRead() {
		requestAllJokes();

		ArrayList<Joke> sortedMostRead = new ArrayList<Joke>(jokeList);

		SortingManager.sortJokesMostReaded(sortedMostRead);

		return sortedMostRead;
	}

	/**
	 * Returns all Jokes sorted by Latest
	 * @return
	 */
	public List<Joke> getAllJokesSortedLatest() {
		requestAllJokes();

		ArrayList<Joke> sortedLatest = new ArrayList<Joke>(jokeList);

		SortingManager.sortJokesLatest(sortedLatest);

		return sortedLatest;
	}
	
	/**
	 * Returns all new Jokes sorted by Latest
	 * @return
	 */
	public List<Joke> getNewStuffSortedLatest() {
		requestAllJokes();

		ArrayList<Joke> sortedLatest = new ArrayList<Joke>();
		
		for (Joke joke : jokeList) {
			if (joke.isNewStuff()) {
				sortedLatest.add(joke);
			}
		}

		SortingManager.sortJokesLatest(sortedLatest);

		return sortedLatest;
	}
	
	/**
	 * Returns all new Jokes sorted by Most Read
	 * @return
	 */
	public List<Joke> getNewStuffSortedMostRead() {
		requestAllJokes();

		ArrayList<Joke> sortedLatest = new ArrayList<Joke>(jokeList);
		
		for (Joke joke : sortedLatest) {
			if (joke.isNewStuff()) {
				sortedLatest.remove(joke);
			}
		}

		SortingManager.sortJokesMostReaded(sortedLatest);

		return sortedLatest;
	}

	/**
	 * Returns all favourite Jokes sorted by Latest
	 * @return
	 */
	public List<Joke> getAllFavoriteJokesSortedLatest() {
		requestAllJokes();

		ArrayList<Joke> allFavoriteJokesLatest = new ArrayList<Joke>();

		for (int i = 0; i < jokeList.size(); i++) {
			if (jokeList.get(i).isFavorite()) {
				allFavoriteJokesLatest.add(jokeList.get(i));
			}
		}

		SortingManager.sortJokesLatest(allFavoriteJokesLatest);

		return allFavoriteJokesLatest;

	}

	/**
	 * Returns all favourite Jokes sorted by Most Read
	 * @return
	 */
	public List<Joke> getAllFavoriteJokesSortedMostRead() {
		requestAllJokes();

		ArrayList<Joke> allFavoriteJokesLatest = new ArrayList<Joke>();

		for (int i = 0; i < jokeList.size(); i++) {
			if (jokeList.get(i).isFavorite()) {
				allFavoriteJokesLatest.add(jokeList.get(i));
			}
		}

		SortingManager.sortJokesMostReaded(allFavoriteJokesLatest);

		return allFavoriteJokesLatest;

	}

	/**
	 * Updates or created the jokes from XML
	 */
	public void updateOrCreateJokesFromXML() {
		List<Joke> listForUpdate = XmlManager.getInstance().getAllJokes();

		try {
			Dao<Joke, Long> jokeDao;
			jokeDao = mHelper.getJokeDao();

			requestAllJokes();
			
			for (int i = 0; i < jokeList.size(); i++) {
				if (!listForUpdate.contains(jokeList.get(i))) {
					jokeDao.delete(jokeList.get(i));
				}
			}
			
			for (int i = 0; i < listForUpdate.size(); i++) {
			    if(!jokeList.contains(listForUpdate.get(i))) {
			        jokeDao.createOrUpdate(listForUpdate.get(i));
			    }
			}
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Returns the first ten jokes
	 * @return
	 */
	public List<Joke> getFirstFiveJokes() {
		List<Joke> allJokes = getAllJokesSortedLatest();

		if (allJokes.size() > 10) {
			ArrayList<Joke> subList = new ArrayList<Joke>();

			for (int i = 0; i < 10; i++) {
				subList.add(allJokes.get(i));
			}

			return subList;
		} else {
			return allJokes;
		}
	}
	
	/**
	 * Returns Jokes by given search criteria
	 * @param searchCriteria
	 * @return
	 */
	public List<Joke> getSearchedJokes(String searchCriteria) {
		List<Joke> matchedJokes = new ArrayList<Joke>();
		List<Joke> allJokes = getAllJokesSortedLatest();

		for (Joke joke : allJokes) {
			if (joke.getJokeText().toLowerCase().contains(searchCriteria.toLowerCase())) {
				matchedJokes.add(joke);
			}
		}
		return matchedJokes;
	}

	/**
	 * Increments the Joke read counter
	 * @param joke
	 */
	public void incrementJokeReadCounter(Joke joke) {
		joke.incrementReadCounter();

		try {
			// Update the joke
			mHelper.getJokeDao().update(joke);

			// Refresh the list with updated joke
			requestAllJokes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Marks the Joke as read
	 * @param joke
	 */
	public void markJokeAsRead(Joke joke) {
		joke.setFavorite(true);

		try {
			// Update the joke
			mHelper.getJokeDao().update(joke);

			// Refresh the list with updated joke
			requestAllJokes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Marks the joke as not favourite
	 * @param joke
	 */
	public void markJokeAsNotFavourite(Joke joke) {
		joke.setFavorite(false);

		try {
			// Update the joke
			mHelper.getJokeDao().update(joke);

			// Refresh the list with updated joke
			requestAllJokes();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
