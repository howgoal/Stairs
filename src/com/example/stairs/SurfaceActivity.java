package com.example.stairs;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SurfaceActivity extends SurfaceView implements
		SurfaceHolder.Callback {

	private SurfaceHolder surfHold;
	private Timer timer;
	private TimerTask task;
	private int xPosition, yPosition;
	private int[] X = new int[50];
	private int[] Y = new int[50];

	public SurfaceActivity(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		surfHold = getHolder();
		surfHold.addCallback(this);
		// setFocusable(true);
		
		countX();
		countY();

	}

	private void countX() {
		X[0] = 30;
		for(int i=1; i<50; i++) {
			xPosition = (int) (Math.random() * 300 + 10);
			X[i] = xPosition;
		}
	}
	
	private void countY() {
		int tmp;
		Y[0] = 50;
		for(int i=1; i<50; i++) {
			tmp = Y[i-1] + 100;
			yPosition = (int) (Math.random() * 30 + tmp);
			Y[i] = yPosition;
		}
	}

	public void draw() {
		Canvas canvas = getHolder().lockCanvas();
		Resources res = getResources();
		Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.img);
		Paint paint = new Paint();
		paint.setAntiAlias(true); // remove edge effect
		canvas.drawColor(Color.WHITE);
		for(int i=0; i<10; i++) {
			canvas.drawBitmap(bitmap, X[i], Y[i], paint);
		}

		getHolder().unlockCanvasAndPost(canvas);
	}

	public void startTimer() {
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				draw();
				for(int i=0; i<10; i++) {
					Y[i] = Y[i] - 3;
				}
			}
		};
		timer.schedule(task, 100, 100); // do task per 0.1 second
	}

	public void stopTimer() {
		timer.cancel();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		startTimer();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		stopTimer();
	}

}
