package com.joseluishdz.eartquake.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;


public class DBAdapter {

	private static final String mDbName = "earthquake.s3db";
	private static int mDbVersion = 1;
	private SQLiteDatabase mDb;
	private final Context ctx;
	private DatabaseHelper mDbHelper;

	private static final String TABLE_EQ = "" + "CREATE TABLE "
			+ EqDAO.TABLE_NAME + " (" + EqDAO.ID_EQ
			+ " TEXT PRIMARY KEY, " + EqDAO.PLACE_EQ
			+ " TEXT, " + EqDAO.DATE_EQ
			+ " INTEGER, "+ EqDAO.TITLE_EQ
			+ " TEXT, "+ EqDAO.MAG_EQ
			+ " REAL, " + EqDAO.LAT_EQ
			+ " REAL, " + EqDAO.LONG_EQ
			+ " REAL, " + EqDAO.DEPTH_EQ + " REAL);";

	public DBAdapter(Context ctx) {
		this.ctx = ctx;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, mDbName, null, mDbVersion);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_EQ);
		}


		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			/*switch (oldVersion) {
				case 1:
					//Sin break para que se ejecuten todos
			}*/
		}
	}

	/**
	 * Open the notes database. If it cannot be opened, try to create a new
	 * instance of the database. If it cannot be created, throw an exception to
	 * signal the failure
	 * 
	 * @return this (self reference, allowing this to be chained in an
	 *         initialization call)
	 * @throws android.database.SQLException
	 *             if the database could be neither opened or created
	 */
	public DBAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(ctx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	/**
	 * Close the opened Data Base.
	 */
	public void close() {
		mDbHelper.close();
	}

	public void beginTransaction() {
		open();
		mDb.beginTransaction();
	}

	public void setTransactionSuccessful() {
		mDb.setTransactionSuccessful();
		mDb.endTransaction();
		close();
	}

	/**
	 * Insert a new record into the specified table
	 * 
	 * @param table
	 *            - The name of the table.
	 * @param cValues
	 *            - The values to set.
	 * @return
	 */
	public long insert(String table, ContentValues cValues) {
		long isInserted = -1;

		try {
			isInserted = mDb.insert(table, null, cValues);
		} catch (Exception e) {
			setTransactionSuccessful();
		}
		return isInserted;
	}

	/**
	 * Update a record from the specified table
	 * 
	 * @param table
	 *            - The name of the table.
	 * @param cValues
	 *            - The values to set.
	 * @return
	 */
	public void update(String table, ContentValues cValues, String condition) {
		try {
			mDb.update(table, cValues, condition, null);
		} catch (Exception e) {
			setTransactionSuccessful();
		}
	}

	/**
	 * Delete an entry from database
	 * 
	 * @param table
	 *            - The name of the table.
	 * @param condition
	 *            - sql delete condition
	 * @return Return 1 if was successful else returns -1.
	 */
	public int delete(String table, String condition) {
		try {
			open();
			mDb.delete(table, condition, null);
			close();
			return 1;
		} catch (Exception e) {
			close();
			return -1;
		}
	}

	/**
	 * Delete all records in the specified table.
	 * 
	 * @param table
	 *            - The table to delete records.
	 * @return 1 if the delete was successful, otherwise -1.
	 */
	public int deleteAll(String table) {
		try {
			open();
			mDb.delete(table, null, null);
			close();
			return 1;
		} catch (Exception e) {
			close();
			return -1;
		}
	}

	/**
	 * Make a query to the local Data Base.
	 * 
	 * @param tables
	 *            - The table to request data.
	 * @param columns
	 *            - The columns to obtain data.
	 * @param conditional
	 *            - The conditional(WHERE clause). Put this on NULL if there is
	 *            no conditional.
	 * @return A cursor with the data obtained.
	 */
	public Cursor getData(String tables, String[] columns, String conditional) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(tables);
		return queryBuilder.query(mDb, columns, conditional, null, null, null,
				null);
	}

	public Cursor getData(String tables, String[] columns, String conditional,
			String orderBy) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(tables);

		return queryBuilder.query(mDb, columns, conditional, null, null, null,
				orderBy);
	}

	public Cursor getData(String tables, String[] columns, String conditional,
			String groupBy, String orderBy) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(tables);

		return queryBuilder.query(mDb, columns, conditional, null, groupBy,
				null, orderBy);
	}

	public Cursor getData(boolean distinct, String tables, String[] columns,
			String conditional, String groupBy, String orderBy) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder.setTables(tables);
		queryBuilder.setDistinct(distinct);

		return queryBuilder.query(mDb, columns, conditional, null, groupBy,
				null, orderBy);
	}

}
