package controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBController {

	static final String KEY_ROWID = "id";
	static final String KEY_NAME_EN = "name";
	static final String TAG = "DBController";
	static final String DATABASE_NAME = "ProductsDB";
	static final String PRODUCTS_TABLE = "Products";

	static final int DATABASE_VERSION = 1;
	final Context context;
	DatabaseHelper DBHelper;
	SQLiteDatabase db;

	public DBController(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			/*
			 * try { db.execSQL(DATABASE_CREATE); } catch (SQLException e) {
			 * e.printStackTrace(); }
			 */
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			/*
			 * Log.w(TAG, "Upgrading database from version " + oldVersion +
			 * " to " + newVersion + ", which will destroy all old data");
			 * db.execSQL("DROP TABLE IF EXISTS contacts"); onCreate(db);
			 */
		}
	}

	// ---opens the database---
	public DBController open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
	}

	public Product getProductByBarcode(String id) {
		Cursor mCursor = db.query(true, PRODUCTS_TABLE, new String[] {
				KEY_ROWID, KEY_NAME_EN}, KEY_ROWID + "=" + id,
				null, null, null, null, null);
		
		if (mCursor != null) {
			if(mCursor.moveToFirst()) {
				Product p = new Product();
				p.setId(id);
				p.setName(mCursor.getString(1));
				p.setDescription(mCursor.getString(2));
				return p;
			}
		}
		
		return null;
	}
}
