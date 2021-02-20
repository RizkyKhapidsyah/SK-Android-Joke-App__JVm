package com.dmbteam.joke.cmn;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * This class represents a Joke object
 * @author dmbTeam
 *
 */
@DatabaseTable(tableName = "joke")
public class Joke {

	@DatabaseField(columnName = "id", id = true, canBeNull = false, unique = true, generatedId = false)
	private int mId;

	@DatabaseField(columnName = "author")
	private String mAuthor;

	@DatabaseField(columnName = "date")
	private String mDate;
	
	@DatabaseField(columnName = "new_stuff")
	private boolean mNewStuff;

	@DatabaseField(columnName = "content")
	private String mjokeText;

	@DatabaseField(columnName = "favorite")
	private boolean isFavorite;

	@DatabaseField(columnName = "read_counter")
	private int mReadCounter;

	/**
	 * Constructor
	 */
	public Joke() {
		super();
	}

	/**
	 * Constructor with parameters
	 * @param id
	 * @param author
	 * @param date
	 * @param jokeText
	 */
	public Joke(int id, String author, String date, boolean isNewStuff, String jokeText) {
		super();
		mId = id;
		mAuthor = author;
		mDate = date;
		mNewStuff = isNewStuff;
		mjokeText = jokeText;
	}

	/**
	 * Returns the Joke id
	 * @return
	 */
	public int getId() {
		return mId;
	}

	/**
	 * Sets the Joke id
	 * @param id
	 */
	public void setId(int id) {
		mId = id;
	}

	/**
	 * Returns the Joke author
	 * @return
	 */
	public String getAuthor() {
		return mAuthor;
	}

	/**
	 * Sets the Joke author
	 * @param author
	 */
	public void setAuthor(String author) {
		mAuthor = author;
	}

	/**
	 * Checks if the Joke is new
	 * @return
	 */
	public boolean isNewStuff() {
		return mNewStuff;
	}

	/**
	 * Sets if the Joke is new
	 * @param mNewStuff
	 */
	public void setNewStuff(boolean mNewStuff) {
		this.mNewStuff = mNewStuff;
	}

	/**
	 * Returns the Joke date
	 * @return
	 */
	public String getDate() {
		return mDate;
	}

	/**
	 * Sets the Joke date
	 * @param date
	 */
	public void setDate(String date) {
		mDate = date;
	}

	/**
	 * Returns the Joke text
	 * @return
	 */
	public String getJokeText() {
		return mjokeText;
	}

	/**
	 * Sets the Joke text
	 * @param mjokeText
	 */
	public void setJokeText(String mjokeText) {
		this.mjokeText = mjokeText;
	}

	/**
	 * Shows if a Joke is favourite or not
	 * @return
	 */
	public boolean isFavorite() {
		return isFavorite;
	}

	/**
	 * Sets if a Joke is favourite or not
	 * @param isFavorite
	 */
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	/**
	 * Returns the Joke read counter
	 * @return
	 */
	public int getReadCounter() {
		return mReadCounter;
	}

	/**
	 * Sets the Joke read counter
	 */
	public void setReadCounter(int readCounter) {
		mReadCounter = readCounter;
	}

	/**
	 * Increments the joke read counter
	 */
	public void incrementReadCounter() {
		this.mReadCounter = this.mReadCounter+1;
	}
	
	/**
	 * Compares this instance with the specified object and indicates if they
	 * are equal. In order to be equal, o must represent the same object as this
	 * instance using a class-specific comparison. The general contract is that
	 * this comparison should be reflexive, symmetric, and transitive. Also, no
	 * object reference other than null is equal to null.
	 */
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        Joke joke = (Joke) obj;
        return mId == joke.mId
                && (mNewStuff == joke.mNewStuff)
                && (mjokeText == joke.mjokeText || (mjokeText != null && mjokeText
                        .equals(joke.getJokeText())))
                && (mAuthor == joke.mAuthor || (mAuthor != null && mAuthor
                        .equals(joke.getAuthor())))
                && (mDate == joke.mDate || (mDate != null && mDate.equals(joke
                        .getDate())));
    }
	
	/**
	 * Returns an integer hash code for this object. By contract, any two
	 * objects for which equals(Object) returns true must return the same hash
	 * code value. This means that subclasses of Object usually override both
	 * methods or neither method.
	 * 
	 * Note that hash values must not change over time unless information used
	 * in equals comparisons also changes.
	 */
	@Override
	public int hashCode() {
	    final int prime = 31;
        int result = 1;
        result = prime * result
                + ((mjokeText == null) ? 0 : mjokeText.hashCode());
        result = prime * result
                + ((mDate == null) ? 0 : mDate.hashCode());
        result = prime * result + mId;
        result = prime * result + (mNewStuff ? 1231 : 1237);
        result = prime * result
                + ((mDate == null) ? 0 : mDate.hashCode());
        return result;
	}
	
	

}
