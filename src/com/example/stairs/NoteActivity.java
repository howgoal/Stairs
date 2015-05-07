package com.example.stairs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class NoteActivity extends Activity {

	ImageButton img_normal, img_angel, img_devil, img_meat;
	boolean angel, devil, meat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		getActionBar().hide();

		init();
		checkSheep();
	}

	public void init() {
		img_normal = (ImageButton) findViewById(R.id.sheep_normal);
		img_angel = (ImageButton) findViewById(R.id.sheep_angel);
		img_devil = (ImageButton) findViewById(R.id.sheep_devil);
		img_meat = (ImageButton) findViewById(R.id.sheep_meat);

		img_normal.setOnClickListener(sheep_kind);
		img_angel.setOnClickListener(sheep_kind);
		img_devil.setOnClickListener(sheep_kind);
		img_meat.setOnClickListener(sheep_kind);
	}

	public void checkSheep() {
		Bundle bundle = getIntent().getExtras();
		angel = bundle.getBoolean("angel");
		devil = bundle.getBoolean("devil");
		meat = bundle.getBoolean("meat");

		if (angel == true) {
			img_angel.setImageDrawable(getResources().getDrawable(
					R.drawable.sheep_angel));
		}
		if (devil == true) {
			img_devil.setImageDrawable(getResources().getDrawable(
					R.drawable.sheep_devil));
		}
		if (meat == true) {
			img_meat.setImageDrawable(getResources().getDrawable(
					R.drawable.sheep_meat));
		}
	}

	private OnClickListener sheep_kind = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.sheep_normal:
				AlertDialog.Builder builder_normal = new AlertDialog.Builder(
						NoteActivity.this);
				builder_normal.setTitle(R.string.note_title);
				builder_normal.setMessage("普通的草泥馬\n沒什麼好說的");
				builder_normal.setPositiveButton(R.string.note_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {

							}
						});
				builder_normal.show();
				break;
			case R.id.sheep_angel:
				AlertDialog.Builder builder_angel = new AlertDialog.Builder(
						NoteActivity.this);
				builder_angel.setTitle(R.string.note_title);
				builder_angel.setMessage("好好草泥馬\n當取得夠多分數時會出現");
				builder_angel.setPositiveButton(R.string.note_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {

							}
						});
				builder_angel.show();
				break;
			case R.id.sheep_devil:
				AlertDialog.Builder builder_devil = new AlertDialog.Builder(
						NoteActivity.this);
				builder_devil.setTitle(R.string.note_title);
				builder_devil.setMessage("壞壞草泥馬\n當分數達到負數時會出現");
				builder_devil.setPositiveButton(R.string.note_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {

							}
						});
				builder_devil.show();
				break;
			case R.id.sheep_meat:
				AlertDialog.Builder builder_meat = new AlertDialog.Builder(
						NoteActivity.this);
				builder_meat.setTitle(R.string.note_title);
				builder_meat.setMessage("愛吃肉的草泥馬\n請餵牠吃肉><");
				builder_meat.setPositiveButton(R.string.note_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {

							}
						});
				builder_meat.show();
				break;

			default:
				break;
			}
		}
	};

	@Override
	public void onBackPressed() {
		finish();
	}

}
