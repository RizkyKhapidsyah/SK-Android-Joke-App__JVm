package com.dmbteam.joke.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.dmbteam.joke.cmn.Joke;

/**
 * This class is responsible for all Sorting operations
 * @author dmbTeam
 *
 */
public class SortingManager {

	public static void sortJokesMostReaded(List<Joke> jokes) {
		Collections.sort(jokes, new JokeMostReadComparator());
	}

	public static void sortJokesLatest(List<Joke> jokes) {
		Collections.sort(jokes, new JokeLatesComparator());
	}
}

/**
 * Comparator for Most Read Jokes
 * @author dmbTeam
 *
 */
class JokeMostReadComparator implements Comparator<Joke> {

	@Override
	public int compare(Joke joke1, Joke joke2) {
		return Integer.valueOf(joke2.getReadCounter()).compareTo(
				Integer.valueOf(joke1.getReadCounter()));
	}

}

/**
 * Comparator for Latest Jokes
 * @author dmbTeam
 *
 */
class JokeLatesComparator implements Comparator<Joke> {

	@Override
	public int compare(Joke joke1, Joke joke2) {
		return Integer.valueOf(joke2.getId()).compareTo(
				Integer.valueOf(joke1.getId()));
	}

}
