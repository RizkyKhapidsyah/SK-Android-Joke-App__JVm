package com.dmbteam.joke.database;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dmbteam.joke.cmn.Joke;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Helper class for database operations
 * @author dmb Team
 *
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String LOG_TAG = DatabaseHelper.class.getName();

	/**
	 * Database related constants
	 */
	private static final String DATABASE_NAME = "JokeApp.db";
	private static final int DATABASE_VERSION = 1;

	/**
	 * Joke Dao
	 */
	private Dao<Joke, Long> mJokeDao = null;

	/**
	 * Class constructor
	 * @param context
	 */
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * On create event
	 */
	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Joke.class);
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Error creating database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * On upgrade event
	 */
	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Joke.class, true);
			TableUtils.createTable(connectionSource, Joke.class);
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Error upgrading database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Returns Joke Dao object
	 * @return
	 * @throws SQLException
	 */
	public Dao<Joke, Long> getJokeDao() throws SQLException {
		if (mJokeDao == null) {
			mJokeDao = getDao(Joke.class);
		}

		return mJokeDao;
	}
}
