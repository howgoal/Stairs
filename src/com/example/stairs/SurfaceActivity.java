package com.example.stairs;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;	
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SurfaceActivity extends SurfaceView implements
		SurfaceHolder.Callback, SensorEventListener {

	private SurfaceHolder surfHold;
	private SensorManager sensorManager;
	private Timer timer;
	private TimerTask task;
	private int xPosition, yPosition;
	private int xSheep = 180, ySheep = 220;
	private int wSheep = 78, hSheep = 78;
	private int wSteps = 140, hSteps = 50;
	private int speedUp = 1, speedDown = 1;
	private int gap = 300;
	private int tmp_hit = 0, hit = 0;

	
	
	private LayoutInflater inflater;
	private String[] rank={"1","1","1"};
	Database db;
	Cursor mCursor;

	private int point = 0, tmp_point = 0;
	private boolean direct = true;
	private boolean start = false;

	ArrayList<Integer> xList;
	ArrayList<Integer> yList;
	ArrayList<Integer> stepList;
	ArrayList<Boolean> onGrass;
	ArrayList<Boolean> onGate;

	Thread threadSave = null;
	volatile boolean end = false;

	public SurfaceActivity(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		inflater = LayoutInflater.from(getContext());
		surfHold = getHolder();
		surfHold.addCallback(this);

		xList = new ArrayList<Integer>();
		yList = new ArrayList<Integer>();
		stepList = new ArrayList<Integer>();
		onGrass = new ArrayList<Boolean>();
		onGate = new ArrayList<Boolean>();
		threadSave = new Thread();
		countX();
		countY();
		setStep();
	}

	private void countX() {
		xList.add(130);
		for (int i = 1; i < 50; i++) {
			xPosition = (int) (Math.random() * 300 + 10);
			xList.add(xPosition);
		}
	}

	private void countY() {
		yList.add(gap);
		for (int i = 1; i < 50; i++) {
			gap = gap + 100;
			yPosition = (int) (Math.random() * 30 + gap);
			yList.add(yPosition);
		}
	}

	public void checkOn() {
		for (int i = 0; i < 10; i++) {
			if (Math.abs(ySheep + hSheep - yList.get(i)) < 2) {
				if (xSheep - xList.get(i) <= wSteps - 20
						&& -hSheep + 30 <= xSheep - xList.get(i)) {
					// Log.i("up", "up");
					switch (stepList.get(i)) {
					case 0:
						onGrass.set(i, true);
						break;
					case 1:
						onGate.set(i, true);
						hit++;
						ySheep -= speedUp;
						break;
					default:
						hit++;
						ySheep -= speedUp;
						break;
					}
				}
			}
		}
		if (hit == tmp_hit) {
			ySheep += speedDown;
		} else {// on
			tmp_hit = hit;
		}
	}

	public void setStep() {
		stepList.add(2);
		onGrass.add(false);
		onGate.add(false);
		for (int i = 1; i < 50; i++) {
			int tmp = (int) (Math.random() * 5); // random 0~4
			stepList.add(tmp);
			onGrass.add(false);
			onGate.add(false);
		}
	}

	public void checkEnd() {
		int deadline = this.getHeight();
		if (ySheep + 70 == deadline || ySheep == 65) {
			end = true;
		}

	}

	public void setStart() {
		start = true;
	}

	public boolean getStart() {
		return start;
	}

	public int getPoint() {
		return point;
	}

	public boolean getEnd() {
		return end;

	}

	public void draw() {
		Canvas canvas = getHolder().lockCanvas();
		Resources res = getResources();
		Bitmap steps = BitmapFactory.decodeResource(res, R.drawable.img);
		Bitmap line = BitmapFactory.decodeResource(res, R.drawable.deadline);
		Bitmap grass = BitmapFactory.decodeResource(res, R.drawable.grass);
		Bitmap gate = BitmapFactory.decodeResource(res, R.drawable.gate);
		Bitmap back = BitmapFactory.decodeResource(res, R.drawable.back);
		Bitmap plus = BitmapFactory.decodeResource(res, R.drawable.plus);
		Bitmap minus = BitmapFactory.decodeResource(res, R.drawable.minus);
		Bitmap left_sheep = BitmapFactory.decodeResource(res,
				R.drawable.left_sheep);
		Bitmap right_sheep = BitmapFactory.decodeResource(res,
				R.drawable.right_sheep);
		Paint paint = new Paint();
		paint.setAntiAlias(true); // remove edge effect
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(line, 0, 0, paint);

		for (int i = 0; i < yList.size(); i++) {
			if (stepList.get(i) == 0) { // on grass
				if (onGrass.get(i) == false) {
					canvas.drawBitmap(grass, xList.get(i), yList.get(i), paint);
				} else {
					canvas.drawBitmap(back, xList.get(i), yList.get(i), paint);
					canvas.drawBitmap(plus, xList.get(i) + 60, yList.get(i),
							paint);
				}

			} else if (stepList.get(i) == 1) { // on gate
				canvas.drawBitmap(gate, xList.get(i), yList.get(i), paint);
				if (onGate.get(i) == true) {
					canvas.drawBitmap(minus, xList.get(i) + 20,
							yList.get(i) - 70, paint);

				}
			} else { // normal
				canvas.drawBitmap(steps, xList.get(i), yList.get(i), paint);
			}
		}

		if (direct == true) {
			canvas.drawBitmap(right_sheep, xSheep, ySheep, paint);
		} else {
			canvas.drawBitmap(left_sheep, xSheep, ySheep, paint);
		}

		getHolder().unlockCanvasAndPost(canvas);
	}

	public void startTimer() {
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				draw();
				for (int i = 0; i < yList.size(); i++) {
					yList.set(i, yList.get(i) - speedUp);
					if (yList.get(i) < 80) {
						yList.remove(i);
						xList.remove(i);
						stepList.remove(i);
						if(onGrass.get(i) == true) {
							Log.i("point+5", String.valueOf(point));
							point += 5;
						} else if(onGate.get(i) == true) {
							point -= 1;
							Log.i("point-1", String.valueOf(point));
						} else {
							
						}
						onGrass.remove(i);
						onGate.remove(i);
						// Log.i("remove", "ok");
					}
				}

				// try {
				// Thread.sleep(100);
				// Log.e("111", "222");
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				checkOn();
				checkEnd();
				if (end == true) {
					stopTimer();

					for(int i = 0; i<yList.size(); i++) {
						if(onGrass.get(i) == true) {
							Log.i("point+5", String.valueOf(point));
							point += 5;
						} else if(onGate.get(i) == true) {
							point -= 1;
							Log.i("point-1", String.valueOf(point));
						} else {
							
						}
					}


					Message msg = new Message();
					msg.what = 1;
					uiHandler.sendMessage(msg);

				}
			}
		};
		timer.schedule(task, 1, 1); // do task per 0.001 second
	}

	public void stopTimer() {
		timer.cancel();
		start = false;
	}

	public void resume() {
		Log.d("ZR", "in resume");
		end = false;
		point = tmp_point;
		threadSave.start();
	}

	public void pause() {
		end = true; // modify later
		tmp_point = point;
		Log.d("pause point", String.valueOf(point));
		Log.d("ZR", "in pause");
		try {
			threadSave.join(); // wait until other thread finish
			Log.d("ZR", "in pause end");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.d("ZR", "surface create");
		sensorManager = (SensorManager) getContext().getSystemService(
				Context.SENSOR_SERVICE);
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);

		if (start == true) {
			startTimer();
		}
		// startTimer();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		Log.d("ZR", "surface destroy");
		stopTimer();
		sensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		if (event.values[0] > 1) {
			// x>0 left
			direct = false;
			if (xSheep > 15) {
				xSheep -= 15;
			}
		}
		if (event.values[0] < -1) {
			// x<0 right
			direct = true;
			if (xSheep < 385) {
				xSheep += 15;
			}
		}
		if (event.values[1] > 1) {
			// y>0 down
			// ySheep += 5;
			// Log.i("123", "DOWN");
		}
		if (event.values[1] < -1) {
			// y<0 up
			// ySheep -= 5;
			// Log.i("123", "UP");
		}
	}

	public void showRankDialog() {
	
		db = new Database(getContext());
		db.open();
		mCursor = db.getAll();
		String rankNumber = getRank(db.getAll());
		String[] from_column = new String[] { db.KEY_NAME, db.KEY_POINT };
		int[] to_layout = new int[] { R.id.rank_tv_name,R.id.rank_tv_point };

		// Now create a simple cursor adapter
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(getContext(),
				R.layout.rank_row, mCursor, from_column,
				to_layout);

		
		View customDialog = inflater.inflate(
				R.layout.input_rank_dialog, null);
		final EditText editText = (EditText) customDialog.findViewById(R.id.rank_edit_name);
		ListView listView = (ListView) customDialog.findViewById(R.id.listview_rank);
		TextView showRankTv = (TextView) customDialog.findViewById(R.id.rank_tv_currentrank);
		TextView rankPointTv = (TextView) customDialog.findViewById(R.id.rank_tv_point);
		showRankTv.setText(rankNumber+"/"+(mCursor.getCount()+1));
		rankPointTv.setText(String.valueOf(point));
		listView.setAdapter(adapter);
		new AlertDialog.Builder(getContext())
				.setView(customDialog)
				.setNegativeButton("��^�D���",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}

						})
				.setPositiveButton("�s�W�Ʀ�",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								//
								db.create(editText.getText().toString(),String.valueOf(point));
								onDetachedFromWindow();
							}

						}).create().show();

	}
	public String getRank(Cursor cursor) {
		int rank = 1;
		if(cursor.getCount() != 0) {
			cursor.moveToFirst();	
			int temp = Integer.parseInt(cursor.getString(2));
			if(point > temp) {
				return String.valueOf(rank);
			}//�N���в��ܲĤ@�����
			else {
				rank+=1;
				while (cursor.moveToNext()) {
					temp = Integer.parseInt(cursor.getString(2));
					if(point > temp) {
						break;
					}
					rank+=1;
				}
			}
			
		}

		return String.valueOf(rank);
	}

	private Handler uiHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 0 => stop,
			// 1=> game over show rank
			switch (msg.what) {
			case 0:
				// game stop code
				break;
			case 1:
				// 1=> game over show rank
				showRankDialog();
				break;
			default:
				break;
			}
		}
	};

}
