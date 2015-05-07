package com.example.stairs;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	SurfaceActivity surfaceView;
	Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		getActionBar().hide();
		// surfaceView = new SurfaceActivity(this);
		init();

	}

	public void init() {
		ImageButton btn_newgame = (ImageButton) findViewById(R.id.btn_newgame);
		ImageButton btn_continue = (ImageButton) findViewById(R.id.btn_continue);
		ImageButton btn_note = (ImageButton) findViewById(R.id.btn_note);
		ImageButton btn_rank = (ImageButton) findViewById(R.id.btn_rank);
		ImageButton btn_story = (ImageButton) findViewById(R.id.btn_story);
		bundle = new Bundle();

		btn_newgame.setOnClickListener(btnListener);
		btn_continue.setOnClickListener(btnListener);
		btn_note.setOnClickListener(btnListener);
		btn_rank.setOnClickListener(btnListener);
		btn_story.setOnClickListener(btnListener);
	}

	private OnClickListener btnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_newgame:
				// setContentView(surfaceView);
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, GameActivity.class);
				bundle.putBoolean("CONTINUE", false);
				intent.putExtras(bundle);
				startActivity(intent);
				break;
			case R.id.btn_continue:
				Intent intent2 = new Intent();
				intent2.setClass(MainActivity.this, GameActivity.class);
				bundle.putBoolean("CONTINUE", true);
				intent2.putExtras(bundle);
				startActivity(intent2);
				break;
			case R.id.btn_note:

				break;
			case R.id.btn_rank:
				Intent intent3 = new Intent();
				intent3.setClass(MainActivity.this,RankActivity.class);
				startActivity(intent3);
				MainActivity.this.finish();
				break;
			case R.id.btn_story:

				break;

			default:
				break;
			}

		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("ZR", "main in resume");
		// surfaceView.setStart();
		// surfaceView.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("ZR", "main in pause");
		// surfaceView.pause();

		// int point = surfaceView.getPoint();
		// Log.i("###", String.valueOf(point));
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i("ZR", "main in stop");
		// surfaceView.pause();

		// int point = surfaceView.getPoint();
		// Log.i("###", String.valueOf(point));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
