package com.example.stairs;

import java.util.ArrayList;
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
	private int xSheep = 180, ySheep = 20;
	private int wSheep = 78, hSheep = 78;
	private int wSteps = 140, hSteps = 50;
	private int speedUp = 1, speedDown = 1;
	private int gap = 250;
	private int tmp_hit = 0, hit = 0;
	private boolean change = false;
	
	ArrayList <Integer> xList;
	ArrayList <Integer> yList;


	public SurfaceActivity(Context context) {
		super(context);
		// TODO Auto-generated constructor stub

		surfHold = getHolder();
		surfHold.addCallback(this);
		// setFocusable(true);
		xList = new ArrayList<Integer>();
		yList = new ArrayList<Integer>();
		countX();
		countY();

	}

	private void countX() {
		xList.add(130);
		for(int i=1; i<50; i++) {
			xPosition = (int) (Math.random() * 300 + 10);
			xList.add(xPosition);
		}
	}
	
	private void countY() {
		yList.add(gap);
		for(int i=1; i<50; i++) {
			gap = gap + 100;
			yPosition = (int) (Math.random() * 30 )+ gap;
			yList.add(yPosition);
		}
	}
	
	public void checkOn() {
		for(int i=0; i<10; i++) {
			if(Math.abs(ySheep+hSheep - yList.get(i)) < 2) {
				if(xSheep-xList.get(i) <= wSteps-20 && -hSheep+30 <= xSheep-xList.get(i)) {
					Log.i("up", "up");
					ySheep -= speedUp;
					hit++;
				}
			}
		}
		if(hit == tmp_hit) {
			ySheep += speedDown;
		}
		else{
			tmp_hit = hit;
		}
	}

	public void draw() {
		Canvas canvas = getHolder().lockCanvas();
		Resources res = getResources();
		Bitmap steps = BitmapFactory.decodeResource(res, R.drawable.img);
		Bitmap sheep = BitmapFactory.decodeResource(res, R.drawable.sheep);
		Paint paint = new Paint();
		paint.setAntiAlias(true); // remove edge effect
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(sheep, xSheep, ySheep, paint);
		for(int i=0; i<10; i++) {
			canvas.drawBitmap(steps, xList.get(i), yList.get(i), paint);
		}

		getHolder().unlockCanvasAndPost(canvas);
	}

	public void startTimer() {
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				draw();
				for(int i=0; i<yList.size(); i++) {
					yList.set(i, yList.get(i) - speedUp);
					if(yList.get(i) < 0) {
						yList.remove(i);
						xList.remove(i);
						//Log.i("remove", "ok");
					}
				}
				checkOn();
			}
		};
		timer.schedule(task, 1, 1); // do task per 0.1 second
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
