package com.example.stairs;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class GameActivity extends Activity {

	SurfaceActivity surfaceView;
	Bundle bundle;
	Boolean CONTINUE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		surfaceView = new SurfaceActivity(this);
		setContentView(surfaceView);
		getActionBar().hide();
		bundle = this.getIntent().getExtras();
		CONTINUE = bundle.getBoolean("CONTINUE");

	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.i("ZR", "game in start");
		if (CONTINUE) {
			surfaceView.setRestart();
		} else {
			surfaceView.setStart();
		}
		surfaceView.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		surfaceView.pause();
		Log.i("ZR", "game in pause");
		// surfaceView.pause();

		// int point = surfaceView.getPoint();
		// Log.i("###", String.valueOf(point));
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.i("ZR", "game in stop");
		// surfaceView.pause();

		// int point = surfaceView.getPoint();
		// Log.i("###", String.valueOf(point));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
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
