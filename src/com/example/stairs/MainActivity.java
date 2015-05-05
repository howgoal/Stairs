package com.example.stairs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	SurfaceActivity surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homepage);
		surfaceView = new SurfaceActivity(this);
		init();

	}

	public void init() {
		Button btn_newgame = (Button) findViewById(R.id.btn_newgame);
		Button btn_continue = (Button) findViewById(R.id.btn_continue);
		Button btn_note = (Button) findViewById(R.id.btn_note);
		Button btn_rank = (Button) findViewById(R.id.btn_rank);
		Button btn_story = (Button) findViewById(R.id.btn_story);

		btn_newgame.setOnClickListener(btnListener);
		btn_continue.setOnClickListener(btnListener);
		btn_note.setOnClickListener(btnListener);
		btn_rank.setOnClickListener(btnListener);
		btn_story.setOnClickListener(btnListener);
	}
	
	private void dialog_story() {
		AlertDialog.Builder alert_story = new AlertDialog.Builder(MainActivity.this);
		alert_story.setTitle("規則說明");
		alert_story.setMessage("");
		alert_story.setPositiveButton("OK", 
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		alert_story.show();
	}

	private OnClickListener btnListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_newgame:
				setContentView(surfaceView);
				break;
			case R.id.btn_continue:

				break;
			case R.id.btn_note:

				break;
			case R.id.btn_rank:

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
		surfaceView.setStart();
		surfaceView.resume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("ZR", "main in pause");
		surfaceView.pause();

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
