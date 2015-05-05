package com.example.stairs;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database {
	private Context mContext = null;
	private DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	private static final String DATABASE_NAME = "Rank.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_TABLE = "rank";

	public static final String KEY_ROWID = "_id";
	public static final String KEY_POINT = "point";
	public static final String KEY_NAME = "name";
	public static final String KEY_CREATED = "created";

	/** Constructor */
	public Database(Context context) {
		this.mContext = context;
	}

	public Database open() throws SQLException {
		dbHelper = new DatabaseHelper(mContext);
		db = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		private static final String DATABASE_CREATE = "CREATE TABLE "
				+ DATABASE_TABLE + "(" + KEY_ROWID + " INTEGER PRIMARY KEY,"
				+ KEY_NAME + " TEXT NOT NULL," + KEY_POINT + " TEXT NOT NULL,"+ KEY_CREATED + " TIMESTAMP"
				+ ");";

		// public DatabaseHelper(Context context, String name,
		// CursorFactory factory, int version) {
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
			Log.v("test", DATABASE_CREATE);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}

	}

	// CRUD
	// public Cursor getAll() {
	// return db.rawQuery("SELECT * FROM "+ DATABASE_TABLE + " ORDER BY "+
	// KEY_CREATED +" DESC", null);
	// }

	// String[] strCols = new String[] {
	// KEY_ROWID,
	// KEY_ITEM,
	// KEY_CREATED
	// };

	// get all entries
	public Cursor getAll() {
		return db.query(DATABASE_TABLE, // Which table to Select
				// strCols,// Which columns to return
				new String[] { KEY_ROWID, KEY_NAME,KEY_POINT, KEY_CREATED }, null, // WHERE
																			// clause
				null, // WHERE arguments
				null, // GROUP BY clause
				null, // HAVING clause
				KEY_POINT + " DESC" // Order-by clause
		);
	}

	// add an entry
	public long create(String name,String point) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm",
				Locale.ENGLISH);
		Date now = new Date();
		ContentValues args = new ContentValues();
		args.put(KEY_NAME, name);
		args.put(KEY_POINT, point);
		
		args.put(KEY_CREATED, df.format(now.getTime()));
		Log.v("success", "success");
		return db.insert(DATABASE_TABLE, null, args);
	}

	// remove an entry
	public boolean delete(long rowId) {
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}

	// query single entry
	public Cursor get(long rowId) throws SQLException {
		Cursor mCursor = db.query(true, DATABASE_TABLE, new String[] {
				KEY_ROWID,KEY_NAME, KEY_POINT, KEY_CREATED }, KEY_ROWID + "=" + rowId,
				null, null, null, null, null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}

	// update
	public boolean update(long rowId, String name,String point) {
		Date now = new Date();
		ContentValues args = new ContentValues();
		args.put(KEY_POINT, name);
		args.put(KEY_NAME, point);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}
}
