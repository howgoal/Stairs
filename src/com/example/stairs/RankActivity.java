package com.example.stairs;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class RankActivity extends Activity {
	Database db;
	Cursor mCursor;
	LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rank_layout);
		getActionBar().hide();
		inflater = LayoutInflater.from(getApplicationContext());
		init();

	}
	public void init() {
		db = new Database(getApplicationContext());
		db.open();
		mCursor = db.getAll();
		String[] from_column = new String[] { db.KEY_NAME, db.KEY_POINT };
		int[] to_layout = new int[] { R.id.rank_tv_name,R.id.rank_tv_point };

		// Now create a simple cursor adapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(),
				R.layout.rank_row, mCursor, from_column,
				to_layout);
		ListView listView = (ListView) findViewById(R.id.listview_rank);

		listView.setAdapter(adapter);
	}
}
