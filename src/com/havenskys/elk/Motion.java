package com.havenskys.elk;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.microedition.khronos.opengles.GL;

import com.havenskys.elk.R;
import com.havenskys.elk.ast.Request;
import com.havenskys.elk.ast.Scan;
import com.havenskys.huuave.GLColor;
import com.havenskys.huuave.InPart;
import com.havenskys.huuave.InPartShape;
import com.havenskys.huuave.InPurpose;
import com.havenskys.huuave.InRenderer;
import com.havenskys.huuave.MatrixTrackingGL;
import com.havenskys.huuave.World;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Motion extends ListActivity implements
		InRenderer.AnimationCallback {

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i(G, "ondestroy good xxx");
		if (mTts != null) {
			mTts.shutdown();
		}
	}

	@Override
	public void onContentChanged() {
		Log.i(G, "content changed xxx");
		xgxhbatch.sendEmptyMessageDelayed(2, 15);
		sr.sendEmptyMessageDelayed(2, 2);
		Log.i(G, "oncontentchanged good xxx");
		super.onContentChanged();

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		// ListView v1 = (ListView) v;
		// int p = getListView().getPositionForView(v1);
		// long b = getListView().getItemIdAtPosition(p);
		// Log.i(G, "scuba " + p + " is " + b + " " + mMID);
		// mMID = (int) b;
		// midset.sendEmptyMessageDelayed((int) b, 2);

		// midlset.sendEmptyMessageDelayed(mMID, 75);
		Log.i(G, "oncreatecontextmenu good xxx");
		menu.setHeaderTitle(":" + lMID);
		menu.add(0, 3, 1, "Update");
		menu.add(0, 4, 2, "Filter");
		menu.add(0, 5, 3, "Off");

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.i(G, "oncontextitemselected good xxx");
		if (item.getGroupId() == 0) {

			// if (lMID == -1) {
			// setr("fix; no lMID");

			// } else {
			if (item.getItemId() == 3) {
				ElkRequest.updateM(1, "Update", lMID);
			} else if (item.getItemId() == 4) {
				nks.sendEmptyMessageDelayed(2, 15);
			} else if (item.getItemId() == 5) {
				ElkRequest.updateM(-1, "Off", lMID);
			}
			// }
		}

		// Toast.makeText(mCtx, "Really " + item.getItemId() + " " + mMID, 3409)
		// .show();

		return super.onContextItemSelected(item);

	}

	Handler nks = new Handler() {

		public void handleMessage(Message msg) {
			Log.i(G, "nks good xxx");
			Thread xh2 = new Thread() {
				public void run() {

					ContentValues hx = new ContentValues();
					hx.put("title", "Filter");
					hx.put("status", 13);
					hx.put("filtered", "");
					SqliteWrapper.update(mCtx, mCtx.getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), hx, "_id=" + lMID + "", null);

					HandlerThread yx = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
					yx.start();
					Scan gs = new Scan(yx.getLooper());

					gs.set(mCtx, DataProvider.CONTENT_URI, null, null, null, false, lMID, null, null, null, null);

					gs.go(6);
				}
			};

			xh2.start();
		}
	};
	// nb9
	Handler fitco = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "fitco good xxx");
			Bundle bdl = msg.getData();
			final int mid = bdl.getInt("mid", -1);
			int id = bdl.getInt("id", -1);
			int x = bdl.getInt("x", -1);
			boolean upxx = bdl.getBoolean("update", true);
			final int omidf = bdl.getInt("omid", -1);
			int y = bdl.getInt("y", -1);

			int xxf = bdl.getInt("left", 0);
			int xxfe = bdl.getInt("top", 0);

			if (upxx) {
				ContentValues hx = new ContentValues();
				hx.put("cox", x);
				hx.put("coxy", y);
				SqliteWrapper.update(mCtx, mCtx.getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), hx, "_id=" + mid + "", null);
			}
			// ImageView x1 = (ImageView) msu.getChildAt(msu.getChildCount() -
			// 1);

			ImageView x1 = (ImageView) findViewById(id);
			int xw1 = x1.getWidth();
			int xh1 = x1.getHeight();

			// RelativeLayout.LayoutParams x1l = new
			// RelativeLayout.LayoutParams(
			// -2, -2);
			// x1l.setMargins(x / 100 - xw1 / 2, y / 100 - xh1 / 2, 0, 0);
			int xw2 = msu.getHeight();
			if (xw2 < 50) {
				Log.e(G, "WH2 joex");
			}
			int xw = msu.getWidth();
			if (xw < 50) {
				Log.e(G, "WHA joex");
			}

			RelativeLayout.LayoutParams x1l2 = new RelativeLayout.LayoutParams(
					-2, -2);
			x1l2.setMargins(xxf + (x * xw / 100 - xw1 / 2),
					(y * xw2 / 100 - xh1 / 2) + xxfe, 0, 0);

			x1.setLayoutParams(x1l2);
			// sizekln
			Animation nb9 = AnimationUtils.loadAnimation(mCtx, R.anim.woomp);
			x1.clearAnimation();
			x1.startAnimation(nb9);
			x1.setVisibility(View.VISIBLE);
			// RelativeLayout gt = (RelativeLayout) g;
			// gt.startAnimation(boh);

			// setr(hx.getAsString("coxyz"));

			if (omidf > -1) {

				Cursor bb = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "_id" }, "mid =" + omidf , null, "updated desc limit 1");

				if (bb != null && bb.getCount() > 0 && bb.moveToFirst()) {
					Message ml = new Message();
					Bundle bl = new Bundle();
					bl.putInt("mid", bb.getInt(0));
					bl.putBoolean("fromon", true);
					ml.setData(bl);
					joex.sendMessageDelayed(ml, 75);
					// midset.sendEmptyMessageDelayed(bb.getInt(0),)
					// miesr(-1, bb.getInt(0), true, false);
					joexu.removeMessages(2);

					bb.close();

				} else {

					joexu.sendEmptyMessageDelayed(2, 6075);
					pagelink(omidf, 4075, false, false);
				}

			}

			// buccon.sendEmptyMessageDelayed(mid, 4075);

		}
	};
	Animation nb9;

	Handler icl = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "icl good xxx");
			Bundle bdl = msg.getData();
			int vid = bdl.getInt("vid");
			int mid = bdl.getInt("id");
			int position = bdl.getInt("position");

			if (olmx > System.currentTimeMillis() - 250) {
				return;
			}

			// lMID = id;
			// midlset.sendEmptyMessageDelayed(id, 75);

			olmx = System.currentTimeMillis();

			RelativeLayout gt = (RelativeLayout) getListView().getChildAt(
					position);
			if (gt == null) {
				Log.e(G, "icl gt");
				return;
			}
			// gt.startAnimation(boh);
			// midset.sendEmptyMessageDelayed((int) id, 2);
			// mMID = (int) id;
			// lMID = mMID;

			// buc.setVisibility(View.GONE);
			// msu.setVisibility(View.VISIBLE);
			// if (buc.getVisibility() != View.VISIBLE
			// && bucc.getVisibility() != View.VISIBLE
			// && msu.getVisibility() != View.VISIBLE) {
			// msu.setVisibility(View.INVISIBLE);
			// }

			// buc.getVis 20110911
			// if ((pageat == lMID && buc.getVisibility() == View.VISIBLE)) {
			// cvboff.sendEmptyMessageDelayed(2, 27);
			// buccon.sendEmptyMessageDelayed(2, 25);
			// } else
			// Log.i(G,
			// "icl xxx " + pageat + "-" + mid + ","
			// + bucc.getVisibility() + " "
			// + buccoffa.containsKey("g" + mid) + " -- " + mMID);

			// language
			// prepLanguage(lMID);
			// if ((pageat == mid)
			// && (buccoffa.containsKey("g" + mid) && !buccoffa
			// .getAsBoolean("g" + mid))) {

			// miesr(-1, mid, true, false);
			// return;
			// }

			pagelink(mid, 5, false, false);
			// sizekln.sendEmptyMessageDelayed(2, 125);

		}
	};

	public void prepLanguage(String language) {
		Log.i(G, "prepLanguage() good xxx " + language);
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putString("language", language);
		ml.setData(bl);
		prepLanguage.sendMessageDelayed(ml, 5);

	}

	public void prepLanguage(int mid) {
		Log.i(G, "prepLanguage() good xxx " + mid);
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putString("language", getLanguage(mid));
		ml.setData(bl);
		prepLanguage.sendMessageDelayed(ml, 5);
	}

	Handler prepLanguage = new Handler() {
		boolean xx = false;

		public void handleMessage(Message msg) {
			Bundle bdl = msg.getData();
			String ln = bdl.getString("language");
			// int mid = msg.what;
			Log.i(G, "prepLanguage good xxx");
			if (ln == null || ln.length() == 0 || ln.length() > 8) {
				ln = "en";
			}
			if (mTts == null || mTts.getLanguage() == null
					|| mTts.getLanguage().getCountry() == null) {
				return;
			}

			String lc = mTts.getLanguage().getCountry().toLowerCase();

			String[] bbl = ln.split("-");
			ln = bbl[0];
			if (bbl.length > 1) {
				lc = bbl[1];
			}
			if (lc.length() == 0) {
				lc = mTts.getLanguage().getCountry().toLowerCase();
			}
			if (lc == null || lc.length() == 0) {
				lc = "us";
			}
			// ln = ln.replaceAll("-.*", "");

			Locale[] l = Locale.getAvailableLocales();
			Locale b = null;

			for (int i = 0; i < l.length; i++) {
				if (b == null && l[i].getLanguage().contentEquals(ln)
						&& l[i].getCountry().toLowerCase().contentEquals(lc)) {
					String ll = l[i].getLanguage() + "-" + l[i].getCountry();
					b = l[i];
					Log.i(G, "prepLanguage xxx " + ll);
					if (xx) {
						break;
					}
				}

				if (!xx) {
					String ll = l[i].getLanguage() + "-" + l[i].getCountry();
					ll = ll.toLowerCase();
					Log.i(G,
							"prepLanguage xxx " + l[i].getISO3Language() + ","
									+ l[i].getISO3Country() + " " + ll + ", "
									+ l[i].getDisplayName());
				}

				if (b != null) {
					break;
				}

			}

			if (b == null) {
				for (int i = 0; i < l.length; i++) {
					if (l[i].getLanguage().contentEquals(ln)
							|| l[i].getCountry().toLowerCase()
									.contentEquals(lc)) {
						String ll = l[i].getLanguage() + "-"
								+ l[i].getCountry();
						Log.i(G, "prepLanguage xxx xxx " + ll);
						b = l[i];
						break;
					}
				}
			}
			if (b != null) {
				Log.i(G,
						"prepLanguage xxx " + b.getCountry() + " -- "
								+ mTts.isLanguageAvailable(b));
			}

			xx = true;
			if (b != null && mTts != null && mTts.isLanguageAvailable(b) >= 0) {
				Log.i(G, "prepLanguage xxx updating language to " + ln);
				mTts.setLanguage(b);
			} else {
				Log.i(G, "prepLanguage xxx none from: " + ln);
			}

		}
	};

	public String getLanguage(int mid) {
		Log.i(G, "getLanguage good xxx " + mid);
		Cursor u92 = null;
		u92 = SqliteWrapper.query(mCtx, mCtx.getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "_id", "language", "mid" }, "_id = " + mid + "", null, null);

		String xl = "";
		int omid = -1;
		if (u92 != null && u92.moveToFirst()) {
			xl = u92.getString(1);
			if (xl == null) {
				xl = "";
			}
			omid = u92.getInt(2);
		}

		if (xl.length() == 0 && omid > -1 && omid != mid && omidc++ < 50) {
			xl = getLanguage(omid);
		}
		return xl;
	}

	int omidc = -1;
	long olmx = -1;
	int lMID = -1;

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		// o1show.sendEmptyMessageDelayed(2, 75);
		Log.i(G, "onlistitemclick good xxx");
		RelativeLayout gt = (RelativeLayout) v;// getListView().getChildAt(position);
		if (gt == null) {
			Log.e(G, "icl gt");
			return;
		}
		// gt.clearAnimation();
		// gt.startAnimation(boh);
		// lMID = (int) id;
		// midlset.sendEmptyMessageDelayed((int) id, 2);

		// sizekln.sendEmptyMessageDelayed(2, 5);

		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putInt("id", (int) id);
		bl.putInt("vid", v.getId());
		bl.putInt("p", position);
		ml.setData(bl);

		icl.sendMessageDelayed(ml, 5);

	}

	Handler joex = new Handler() {
		public void handleMessage(Message msg) {

			Bundle bdl = msg.getData();
			int sid = bdl.getInt("sid", 0);
			final int mid = bdl.getInt("mid", -1);
			Log.i(G, "joex good xxx " + mid);
			highwide = getHighwide();
			sidewide = getSidewide();
			int x5h = ((mHeight - highwide) / 4);
			int x5w = ((mWidth - sidewide) / 4);

			int x5 = (mWidth > mHeight ? (int) (mHeight - highwide / 4)
					: (int) (mHeight - highwide / 4));
			if (x5h < 30) {
				x5h = 30;
			}
			if (x5w < 30) {
				x5w = 30;
			}
			if (x5 < 30) {
				x5 = 30;
			}

			// setr("" + mid + "_" + sid);
			// project gem
			TextView x6 = new TextView(mCtx);

			RelativeLayout.LayoutParams x6l = new RelativeLayout.LayoutParams(
					-1, 75);
			x6.setBackgroundResource(R.drawable.b802);
			x6l.setMargins(4, 0, 0, 0);
			x6.setLayoutParams(x6l);
			x6.setId(hid++);
			x6.setTextSize((float) 24);
			x6.setText("");
			x6.setPadding(x5 + 30, 20, 0, 0);

			x6.setTextColor(Color.argb(250, 175, 175, 175));
			x6.setGravity(Gravity.BOTTOM);
			// msu.addView(x6);

			TextView x8 = new TextView(mCtx);
			RelativeLayout.LayoutParams x8l = new RelativeLayout.LayoutParams(
					-1, 65);
			x8.setBackgroundResource(R.drawable.b902);
			x8l.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			x8l.setMargins(4, 0, 0, 0);
			x8.setLayoutParams(x8l);
			x8.setId(hid++);
			// msu.addView(x8);

			TextView xa1;
			ImageView x1;

			if (msu.getChildCount() == 0) {

				TextView x9 = new TextView(mCtx);
				RelativeLayout.LayoutParams x9l = new RelativeLayout.LayoutParams(
						-1, -1);

				x9.setBackgroundResource(R.drawable.nin77);
				x9l.setMargins(0, 0, 0, 0);
				// x9l.addRule(RelativeLayout.ABOVE, x8.getId());
				// x9l.addRule(RelativeLayout.BELOW, x6.getId());

				x9.setLayoutParams(x9l);
				x9.setId(hid++);

				x9.setTextSize((float) 24);
				x9.setText("");// + mid);
				x9.setTextColor(Color.argb(250, 175, 175, 175));
				x9.setGravity(Gravity.RIGHT);
				// x9.setBackgroundColor(Color.argb(150, 1, 5, 15));
				// x9.setGravity(Gravity.RIGHT);
				msu.addView(x9);

				xa1 = new TextView(mCtx);

				RelativeLayout.LayoutParams xa1l = new RelativeLayout.LayoutParams(
						-1, -2);
				// xa1.setBackgroundResource(R.drawable.b9);
				// xa1l.addRule(RelativeLayout.RIGHT_OF, x1.getId());
				// xa1l.addRule(RelativeLayout.ALIGN_BOTTOM, x1.getId());
				xa1l.setMargins(23, 25, 20, 8);
				xa1.setMinHeight(x5);
				xa1.setLayoutParams(xa1l);
				xa1.setId(hid++);

				xa1.setTextSize((float) 22);
				xa1.setText("");
				xa1.setTextColor(Color.argb(250, 175, 175, 175));
				// xa1.setGravity(Gravity.BOTTOM);
				msu.addView(xa1);
				x1 = new ImageView(mCtx);

				x1.setId(hid++);
				RelativeLayout.LayoutParams x1l = new RelativeLayout.LayoutParams(
						x5w, x5h);

				x1l.setMargins(x5w * 2, x5h * 2, 0, 0);
				x1.setLayoutParams(x1l);
				// x1.setBackgroundResource(R.drawable.chd);
				x1.setScaleType(ScaleType.FIT_CENTER);
				x1.setAdjustViewBounds(true);
				x1.setMaxHeight(x5h);
				x1.setMaxWidth(x5w);
				x1.setVisibility(View.INVISIBLE);

				msu.addView(x1);

			} else if (msu.getChildCount() > 5) {
				msu.removeViews(3, msu.getChildCount() - 2);
			}

			{
				Message ml = new Message();
				Bundle bl = new Bundle(bdl);
				ml.setData(bl);
				joex1.sendMessageDelayed(ml, 5);
			}

		}
	};

	Handler joex1 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "joex1 good xxx");
			Bundle bdl = msg.getData();
			int sid = bdl.getInt("sid", 0);
			final int mid = bdl.getInt("mid", -1);
			int x5w = ((mWidth - sidewide) / 4);

			ImageView x1 = (ImageView) msu.getChildAt(2);
			TextView xa1 = (TextView) msu.getChildAt(1);

			if (mid > -1) {
				Log.i(G, "joex1 xxx " + mid);
				int omid = -1;

				final int x11 = x1.getId();
				Cursor e = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "_id", "foundtitle", "sample", "cox", "coxy", "mid" }, "_id =" + mid, null, "created desc");

				if (e != null) {
					if (e.moveToFirst()) {
						try {
							byte[] g8 = e.getBlob(2);
							int x = e.getInt(3);
							int y = e.getInt(4);
							omid = e.getInt(5);

							final String x7 = e.getString(1);
							if (x7 != null && x7.length() > 0) {
								xa1.setText(x7);
								// setr(x7);
							} else {
								xa1.setText("" + mid);

							}

							if (g8 != null) {
								Bitmap x3 = null;
								x3 = BitmapFactory.decodeByteArray(g8, 0,
										g8.length);

								if (x3 != null) {
									x1.setImageBitmap(x3);
									// Log.i(G, "good joex");
								}
							} else {
								x1.setImageResource(R.drawable.ld4);
							}

							if (x > 0) {

								int xw2 = msu.getHeight();
								if (xw2 < 50) {
									Log.e(G, "WH2 joex");
								}
								int xw = msu.getWidth();
								if (xw < 50) {
									Log.e(G, "WHA joex");
								}

								RelativeLayout.LayoutParams x1l2 = new RelativeLayout.LayoutParams(
										-2, -2);
								x1l2.setMargins(x * xw / 100 - x5w / 2, y * xw2
										/ 100, 0, 0);
								x1.setLayoutParams(x1l2);

								Animation gx = AnimationUtils.loadAnimation(
										mCtx, R.anim.groan);
								x1.clearAnimation();
								x1.startAnimation(gx);
							}

						} catch (OutOfMemoryError ux) {
							ux.printStackTrace();

						}

					}
					e.close();
				} else {
					x1.setImageResource(R.drawable.ld4);
				}

				final int omidf = omid;
				msu.setClickable(true);
				msu.setFocusable(true);
				msu.setOnTouchListener(new OnTouchListener() {
					int xa4 = -1;
					boolean untri = false;

					public boolean onTouch(View g, MotionEvent e) {

						if (e.getAction() == e.ACTION_DOWN && !untri) {
							untri = true;
							Log.i(G, "joex good down xxx");
							RelativeLayout gt = (RelativeLayout) g;
							// gt.startAnimation(boh);

							int xw2 = gt.getHeight();
							if (xw2 < 50) {
								Log.e(G, "WH2T joex");
							}
							int xw = gt.getWidth();
							if (xw < 50) {
								Log.e(G, "WHAT joex");
							}

							{
								Message ml = new Message();
								Bundle bl = new Bundle();
								bl.putInt("id", x11);
								bl.putInt("mid", mid);
								bl.putInt("omid", omidf);
								bl.putInt("x", (int) (e.getX() / xw * 100));
								bl.putInt("y", (int) (e.getY() / xw2 * 100));
								ml.setData(bl);

								// nb9
								fitco.sendMessageDelayed(ml, 5);

							}

							// cvboff.sendEmptyMessageDelayed(2, 25);

							return false;

						}

						return false;
					}
				});

			} else {

				Log.i(G, "joex xxx v " + mid);
				msu.setClickable(true);
				msu.setFocusable(true);

				msu.setOnTouchListener(new OnTouchListener() {
					int xa4 = -1;

					public boolean onTouch(View g, MotionEvent e) {

						if (e.getAction() == MotionEvent.ACTION_DOWN) {
							Log.i(G, "joex good down xxx");
							xa4 = -1;
						} else

						if (e.getAction() == MotionEvent.ACTION_MOVE
								&& xa4 == -1) {
							xa4 = (int) e.getX();

						} else if (e.getAction() == MotionEvent.ACTION_MOVE
								&& e.getHistorySize() > 1 || xa4 > -1) {
							int x = (int) e.getX();
							int xx = (int) (xa4 > x ? xa4 - x : x - xa4);
							// setr("" + xx);
							// Log.i(G,
							// "xtouch " + e.getAction() + " "
							// + e.getHistorySize() + " " + xx);
							if (xx > 20) {

								joexu.sendEmptyMessageDelayed(2, 25);
								return false;
							}
						}

						return false;
					}
				});

			}

			if (sid == 210) {
				ImageView x4 = new ImageView(mCtx);
				x4.setScaleType(ScaleType.FIT_START);
				x4.setId(hid++);
				RelativeLayout.LayoutParams x4l = new RelativeLayout.LayoutParams(
						-1, 75);

				// x4l.addRule(RelativeLayout.ALIGN_TOP, x1.getId());
				// x4l.addRule(RelativeLayout.BELOW, x1.getId());
				// x4l.setMargins(arg0, arg1, arg2, arg3)
				x4l.setMargins(3, 10, 0, 3);
				x4.setLayoutParams(x4l);
				x4.setImageResource(R.drawable.c2);
				msu.addView(x4);

				Message pm1 = new Message();
				Bundle pb1 = new Bundle();
				if (sid > 0) {
					TextView x2 = (TextView) findViewById(sid);

					x2.setDrawingCacheEnabled(true);
					x2.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
					// x2.postInvalidate();
					pb1.putInt("sid", sid);
					pb1.putInt("id", x4.getId());
					setr("" + sid);
				}

				pm1.setData(pb1);
				popup.sendMessageDelayed(pm1, 575);
			}

			if (msu.getVisibility() != View.VISIBLE) {
				Animation gx = AnimationUtils.loadAnimation(mCtx, R.anim.xgom5);
				msu.clearAnimation();
				msu.startAnimation(gx);
				msu.setVisibility(View.VISIBLE);
			}
		}
	};

	Handler joex2 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "joex2 good xxx");
			ImageView x1 = (ImageView) findViewById(msg.what);
			if (x1 != null) {
				x1.setVisibility(View.VISIBLE);
			}

		}
	};

	Handler popup = new Handler() {
		public void handleMessage(Message msg) {
			Bundle bdl = msg.getData();
			int sid = bdl.getInt("sid", -1);
			int mid = bdl.getInt("mid", -1);
			int id = bdl.getInt("id", -1);
			Log.i(G, "popup good xxx");
			TextView x2 = (TextView) findViewById(sid);
			if (sid > -1) {
				ImageView x1 = (ImageView) findViewById(id);
				Bitmap x3 = x2.getDrawingCache();
				Bitmap x4 = null;
				if (x3 != null) {
					try {
						x4 = Bitmap.createBitmap(x3);
					} catch (OutOfMemoryError em) {
						x4 = null;
						em.printStackTrace();
					}

					ByteArrayOutputStream st = new ByteArrayOutputStream();
					x4.compress(CompressFormat.PNG, 0, st);
					byte[] g8 = st.toByteArray();
					x4 = BitmapFactory.decodeByteArray(g8, 0, g8.length);

					if (x4 == null) {
						Log.e(G, "Bitmap disappeared " + g8.length + " was "
								+ st.size());
					}

					if (x4 != null) {
						x1.setImageBitmap(x4);
					}

				}

			}

		}
	};

	Handler joexu = new Handler() {
		public void handleMessage(Message msg) {
			// Animation x4 = msu.getAnimation();
			// if (x4 != null) {
			// x4.cancel();
			// }
			// msu.clearAnimation();
			Log.i(G, "joexu good xxx");
			if (msu.getVisibility() != View.GONE) {
				Log.i(G, "joexu xxx");

				Animation gx = AnimationUtils.loadAnimation(mCtx, R.anim.fgom4);

				// Log.i(G, "joexu xxx" + gx.getZAdjustment());
				// gx.setZAdjustment(Animation.ZORDER_BOTTOM);
				msu.clearAnimation();
				msu.startAnimation(gx);
				// msu.setClickable(false);
				// msu.setFocusable(false);

				joexu2.sendEmptyMessageDelayed(2, gx.getDuration());
				// msu.setVisibility(View.VISIBLE);
				// sizekln.sendEmptyMessageDelayed(2, 25);
			}
		}
	};
	Handler joexu2 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "joexu2 good xxx");

			msu.setVisibility(View.GONE);
			sizekln.sendEmptyMessageDelayed(2, 5);

		}
	};

	public void pagelink(int id, int dl, boolean loadNew, boolean loadRecentNew) {
		Log.i(G, "pagelink() good xxx");
		// HandlerThread uj = new HandlerThread(G, 10);
		// uj.start();
		// Handler pagelink = new Pagelink(uj.getLooper());
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putInt("id", id);
		bl.putBoolean("loadNew", loadNew);
		bl.putBoolean("loadRecentNew", loadRecentNew);
		ml.setData(bl);

		pagelink.sendMessageDelayed(ml, dl);

	}

	ContentValues lmidX;
	int pageat = -1;
	Handler pagelink = new Handler() {
		// class Pagelink extends Handler {
		// public Pagelink(Looper lh) {
		// super(lh);
		// }

		boolean showsp = false;
		ContentValues pagets = new ContentValues();

		public void handleMessage(Message msg) {
			Log.i(G, "pagelink good xxx");
			Bundle bdl = msg.getData();
			final int mid = bdl.getInt("id");
			boolean xxluu = bdl.getBoolean("loadNew", false);
			boolean xxlux = bdl.getBoolean("loadRecentNew", false);

			lMID = mid;
			midlset.sendEmptyMessageDelayed(mid, 2);
			if (lmidX == null) {
				lmidX = new ContentValues();
			}

			// if (lmidX.containsKey("g" + mid)) {
			// LinearLayout c5 = (LinearLayout) findViewById(lmidX
			// .getAsInteger("g" + mid));
			// if (c5 != null) {
			// c5.clearAnimation();
			// buccoffa.put("b" + c5.getId(), false);
			//
			// RelativeLayout.LayoutParams xru = new
			// RelativeLayout.LayoutParams(
			// -1, -2);
			// xru.setMargins(0, 0, 0, 0);
			// c5.setLayoutParams(xru);
			// c5.setVisibility(View.VISIBLE);
			// }

			// return;
			// }

			// if (mid != lMID) {
			// setr("pagelink " + lMID);
			// return;
			// }
			// if (pageat == mid && bucc.getVisibility() == View.GONE) {
			// bucc.setVisibility(View.VISIBLE);
			// return;
			// }

			String titlen = "";
			// nbsls

			// bucc.setVisibility(View.VISIBLE);

			String[] cls2;
			if (!showsp) {
				cls2 = new String[] { "_id", "foundtitle", "cox", "coxy", "mid" };
			} else {
				cls2 = new String[] { "_id", "foundtitle", "cox", "coxy",
						"mid", "sample" };
			}

			Cursor xe = null;
			xe = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), cls2, "_id =" + mid, null, "created desc limit 15");

			Cursor e = null;
			e = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), cls2, "mid =" + mid + " or _id = " + mid, null, "status desc, created desc limit 15");

			highwide = getHighwide();
			sidewide = getSidewide();

			// int m5h = 5;
			// int m5 = 4;
			// if (mWidth >= 480) {
			// m5 = 90;
			// }

			// int boo = (mHeight - highwide) / m5h;
			// if (boo < 75 && m5h > 1) {
			// m5h--;
			// boo = (mHeight - highwide) / m5h;
			// }

			String b8 = "";
			boolean boot = false;
			int jm = 5;
			if (e != null && xe != null) {

				// int wd = (mWidth - sidewide) / m5;
				// if (wd < 75 && m5 > 1) {
				// m5--;
				// wd = (mWidth - sidewide) / m5;
				// }

				// int wsx = wd + (int) ((mHeight - highwide - m5 * wd) / m5);
				// int wsy = boo + (int) ((mHeight - highwide - m5h * boo) /
				// m5h);
				// int booh = ((mHeight - highwide - wsy * m5h) / 2) + 4;
				// setr("" + wsy);

				xe.moveToFirst();
				titlen = xe.getString(1);
				jm = xe.getInt(4);
				if (titlen == null) {
					titlen = "";
				}
				if (titlen.length() > 0) {
					// setr(titlen);
				} else {
					// setr("nontitled");
				}

				if (e.getCount() == 1) {
					// || (pagets.containsKey("g" + mid) && pagets
					// .getAsBoolean("g" + mid))) {
					pagets.put("g" + mid, false);
					String pa = "tag item";
					// using policy " + pa + " to
					// setr("No mentionables", 5);

					// mTts.speak("voice authorization required to override",
					// TextToSpeech.QUEUE_FLUSH, null);
					// joex.sendEmptyMessageDelayed(2, 25);
					// cvbon.sendEmptyMessageDelayed(2, 25);
					// buccon.sendEmptyMessageDelayed(2, 25);
					miesr(-1, mid, true, false, 5);

					e.close();
					xe.close();
					return;
				}

				pagets.put("g" + mid, true);
				pageat = mid;

				int m5h = (mHeight - highwide) / 88;
				while (m5h > e.getCount()) {
					m5h--;
				}
				int wsy = (mHeight - highwide - (88 * m5h)) / m5h;

				int booh = 88 + wsy;
				while ((booh * m5h) < (mHeight - highwide)) {
					booh++;
				}

				int m5 = (mWidth - sidewide) / 108;
				while ((m5 * m5h) > e.getCount() + 1) {
					m5--;
				}

				int wsx = (mWidth - sidewide - (108 * m5)) / m5;
				int boo = 108 + wsx;
				while ((boo * m5) < (mWidth - sidewide)) {
					boo++;
				}

				sllxbb = boo;

				// setr("" + boo);
				int lnt = 0;
				int lnc = 0;
				LinearLayout ln = null;
				LinearLayout lna = null;
				int j = -1;

				RelativeLayout xu2 = (RelativeLayout) findViewById(R.id.cvxhc);
				// final int xu2f = xu2.getId();
				LinearLayout c5 = new LinearLayout(mCtx);// (LinearLayout)
															// findViewById(R.id.cvxhcl);
				{

					RelativeLayout.LayoutParams xru = new RelativeLayout.LayoutParams(
							-1, -2);
					xru.setMargins(0, 0, 0, 0);
					c5.setLayoutParams(xru);
					c5.setId(hid++);
					c5.setDrawingCacheEnabled(true);
					c5.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

					c5.setVisibility(View.INVISIBLE);
					c5.setOrientation(LinearLayout.VERTICAL);
					// c5.setHorizontalScrollBarEnabled(true);
					// c5.removeAllViews();

					// ScrollView c6 = (ScrollView) findViewById(R.id.nbsls);
					// c6.scrollTo(0, 0);
					// ScrollView c7 = (ScrollView) findViewById(R.id.cubc);
					// c7.scrollTo(0, 0);

					if (lmidX == null) {
						lmidX = new ContentValues();
					}
					lmidX.put("g" + lMID, c5.getId());
					joexu.sendEmptyMessageDelayed(2, 5);
					buccon.sendEmptyMessageDelayed(lMID, 5);
					cvboff.sendEmptyMessageDelayed(2, 5);

					xu2.addView(c5);
				}
				final int cxx = c5.getId();
				// buccon.sendEmptyMessageDelayed(2, 25);
				int hxv4 = 1;
				int mx = 5;
				for (int h = 0; e.moveToPosition(h); h++) {
					// Log.i(G, "pagelink " + e.getInt(0) + "[" + e.getString(1)
					// + "]");
					Bitmap xt = null;

					b8 = e.getString(1);
					j = e.getInt(0);
					mx = e.getInt(4);

					if (lnt == m5h) {
						lnt = 0;
						if (boot) {
							boot = false;
						} else {
							boot = true;
						}

						lnt = 0;

						if (lnc >= m5) {
							// lna = null;
							// lnc = 0;
							// boot = false;
						}

					}

					lnt++;
					if (lnt == 1) {
						lnc++;
						ln = new LinearLayout(mCtx);
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
								-2, -2);
						ln.setLayoutParams(lp);

						ln.setOrientation(LinearLayout.VERTICAL);
						if (lna == null) {
							lna = new LinearLayout(mCtx);
							LinearLayout.LayoutParams lpa = new LinearLayout.LayoutParams(
									-2, -2);

							// lpa.setMargins(0, 0, 0, 88);
							// lpa.setMargins(0, booh, 0, 88);
							lpa.setMargins(0, 0, 0, 0);// 20);
							// lna.setBackgroundColor(Color.argb(100, 30, 80,
							// 4));
							lna.setLayoutParams(lpa);
							lna.setOrientation(LinearLayout.HORIZONTAL);
							c5.addView(lna);

						}
						lna.addView(ln);

						if (lnc > m5) {
							{
								Message ml = new Message();
								Bundle bl = new Bundle();
								bl.putInt("sid", R.id.cubc);
								// bl.putInt("x",boo * -1);
								bl.putInt("x", ((lnc - m5) * boo * -1));
								ml.setData(bl);
								// sllm.sendMessageDelayed(ml, lnc * 180);
							}
							// Dear Jon, Our paths have crossed and upon arrival
							// I have refused to deny truth. I have been
							// independently focused and I have honestly
							// investigated it to solution. Could I have been
							// trained by so many unexplained explanations
							// unexpected to seed indelibly inside of me? My
							// life is partnered and unfortunately I am not
							// liking those childish bruitish pushing
							// interactions relived by our younger or maybe how
							// this one does it. I was not prepared with
							// everyone to expect crazy unexpected sicknesses
							// but I can be.
							{
								Message ml = new Message();
								Bundle bl = new Bundle();
								bl.putInt("sid", R.id.cubc);
								// bl.putInt("x2", ((lnc - m5) * boo * -1));

								bl.putInt("x", boo);
								ml.setData(bl);
								// sllx.sendMessageDelayed(ml, lnc * 180 + 25);
							}

						}

					}

					if (showsp) {
						byte[] g8;
						g8 = e.getBlob(5);
						xt = null;
						if (g8 == null || g8.length == 0) {
						} else {
							try {
								// byte[] g8 = a.getBytes();
								xt = BitmapFactory.decodeByteArray(g8, 0,
										g8.length);

							} catch (OutOfMemoryError eu) {
								eu.printStackTrace();
								xt = null;
							}

						}

						if (xt != null) {
							// Canvas xh = new Canvas();
							// Paint xp = new Paint();
							Bitmap xh = null;
							xh = Bitmap
									.createScaledBitmap(xt, xt.getWidth() / 5,
											xt.getHeight() / 5, true);

							if (xh != null) {
								ImageView o = new ImageView(mCtx);
								o.setScaleType(ScaleType.FIT_CENTER);
								LinearLayout.LayoutParams ol = new LinearLayout.LayoutParams(
										-2, 75);
								ol.setMargins(10, -85, 0, 0);
								o.setLayoutParams(ol);
								o.setImageBitmap(xh);
								// o.setAlpha(95);
								ln.addView(o);
								// Doc Image PNG
							}

						}
					}

					TextView u = new TextView(mCtx);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							boo, booh);

					u.setLayoutParams(lp);
					u.setId(hid++);
					ln.addView(u);

					// if (g8 != null) {
					// lp.setMargins(0, -75, 0, 0);
					// }
					// if (lnt == 1) {
					// u.setBackgroundResource(R.drawable.b802);
					// } else if (lnt + 1 == boo) {
					// u.setBackgroundResource(R.drawable.b9);
					// } else

					// sample

					if (b8 == null) {
						if (xxluu || xxlux) {
							// if ((lnt == 1 && lnc == 1 && xxlux) || xxluu) {
							xxluu = false;

							miesr(-1, j, true, false, 1880 * 3);
							// } else if (xxlux) {
							// xape("Nothing hot.", TextToSpeech.QUEUE_ADD);
						}

					} else {

						u.setTextColor(Color.argb(250, 135, 235, 239));
						u.setTextSize((float) 12);
						u.setText(b8.replaceFirst(" ", "\n"));

					}

					// DealExtreme Affiliate ID mod ("99999999","89653085");

					TextView u04 = new TextView(mCtx);
					LinearLayout.LayoutParams lp04 = new LinearLayout.LayoutParams(
							boo, booh);
					lp04.setMargins(0, -1 * booh, 0, 0);
					u04.setId(hid++);
					u04.setLayoutParams(lp04);
					u04.setTextColor(Color.argb(220, 175, 175, 175));
					u04.setGravity(Gravity.RIGHT);
					u04.setTextSize((float) 12);
					u04.setText("" + j);
					ln.addView(u04);

					if (boot) {
						if (lnt == 1) {
							if (lnc == 2) {
								u.setBackgroundResource(R.drawable.d9g);
								u04.setBackgroundResource(R.drawable.d9gc);
							} else {
								u.setBackgroundResource(R.drawable.d9g);
								u04.setBackgroundResource(R.drawable.d9gc);
							}

						} else if (lnt == m5h) {
							u.setBackgroundResource(R.drawable.d11g);
							u04.setBackgroundResource(R.drawable.d11gc);
						} else {
							u.setBackgroundResource(R.drawable.d7g);
							u04.setBackgroundResource(R.drawable.d7gc);
						}

					} else {
						// miesr

						if (lnt == 1) {
							if (lnc == 1) {
								u.setBackgroundResource(R.drawable.d8tg);
								u04.setBackgroundResource(R.drawable.d8tgc);
							} else {
								u.setBackgroundResource(R.drawable.d8tg);
								u04.setBackgroundResource(R.drawable.d8tgc);
							}

						} else if (lnt == m5h) {

							u.setBackgroundResource(R.drawable.d10g);
							u04.setBackgroundResource(R.drawable.d10gc);

						} else {
							u.setBackgroundResource(R.drawable.d6g);
							u04.setBackgroundResource(R.drawable.d6gc);
						}

					}

					if ((jm > 0)) {// || (j == mid && mx > 0)
						u04.setBackgroundResource(R.drawable.dxx4);
						u.setBackgroundResource(R.drawable.dxx4);
					}

					u.setFocusable(true);
					u.setClickable(true);

					u.setOnFocusChangeListener(t1foc);

					final int jf = j;
					// u.setHapticFeedbackEnabled(true);
					// if (jf == j) {
					// continue;
					// }

					u.setOnLongClickListener(new OnLongClickListener() {
						public boolean onLongClick(View g) {
							Log.i(G, "pagelink long good xxx");
							Message ml = new Message();
							Bundle bl = new Bundle();
							bl.putInt("mid", jf);
							bl.putInt("sid", g.getId());
							ml.setData(bl);

							// joex.sendMessageDelayed(ml, 5);

							// buccoff.sendEmptyMessageDelayed(2, 5);

							return false;
						}
					});

					u.setOnClickListener(new OnClickListener() {
						Animation bohx = AnimationUtils.loadAnimation(mCtx,
								R.anim.oh4);

						public void onClick(View g) {
							Log.i(G, "pagelink click good xxx");

							TextView gt = (TextView) g;

							// gt.clearAnimation();
							gt.startAnimation(bohx);

							if (jf == mid) {
								miesr(-1, jf, true, false, 5);
							} else {
								pagelink(jf, 75, false, false);
							} // cvbon.sendEmptyMessageDelayed(2, 75);
								// buccoff.sendEmptyMessageDelayed(2, 75);
								// buccon.sendEmptyMessageDelayed(2, 6285);

						}
					});

					u.setOnTouchListener(new OnTouchListener() {
						int xa4 = -1;
						boolean xxco = false;
						long xxc = -1;
						boolean untri = false;
						int x = 0;

						Animation bohx4 = AnimationUtils.loadAnimation(mCtx,
								R.anim.oh4);
						Animation bohx2 = AnimationUtils.loadAnimation(mCtx,
								R.anim.oh2);

						public boolean onTouch(View g, MotionEvent e) {

							if (SystemClock.uptimeMillis() - xxc < 125) {
								return false;
							}
							x = (int) e.getRawX();
							// }
							int xx = (int) xa4 - x;
							// int xx = (int) (xa4 > x ? xa4 - x : x - xa4);
							// setr("" + xx);
							Log.i(G,
									"xxx touch " + e.getAction() + " "
											+ e.getHistorySize() + " (" + x
											+ "," + xx + ") " + e.getPressure());

							if (e.getAction() == MotionEvent.ACTION_DOWN) {
								TextView gt = (TextView) g;
								// Animation bohx =
								// AnimationUtils.loadAnimation(
								// mCtx, R.anim.o);
								gt.startAnimation(bohx2);
								xa4 = -1;
								xxc = SystemClock.uptimeMillis();
								untri = false;
								xxco = false;
								// Log.i(G, "pagelink good down xxx");
							} else if (SystemClock.uptimeMillis() - xxc > 2854
									&& !untri) {
								untri = true;
								// buccoff.sendEmptyMessageDelayed(2, 5);

								TextView gt = (TextView) g;
								gt.startAnimation(bohx2);
								// LinearLayout vd = (LinearLayout)
								// gt.getParent();
								// vd.startAnimation(boh);
								// cvboff.sendEmptyMessageDelayed(vd.getId(),
								// 2);
								// Log.i(G, "pagelink good long xxx");
								return true;// true works, longpress then
											// action.
							} else

							if (e.getAction() == MotionEvent.ACTION_MOVE
									&& xa4 == -1) {
								xa4 = (int) e.getRawX();

								TextView t9 = (TextView) g;
								t9.clearAnimation();

								return false;
							}

							if (e.getAction() == MotionEvent.ACTION_MOVE
									&& (e.getHistorySize() > 1 || xa4 > -1)
									&& !untri) {

								xxc = SystemClock.uptimeMillis();

								// if (e.getHistorySize() > 1) {
								// xa4 = (int) e.getHistoricalX(0);
								// x = (int) e.(e
								// .getHistorySize() - 1);
								// } else {

								// TextView t9 = (TextView) g;

								if (xx < -3 || x > 3) {

									Message ml = new Message();
									Bundle bl = new Bundle();
									bl.putInt("sid", R.id.cubc);
									bl.putInt("x",
											(int) (xx * e.getPressure() * 1));// *
									// e.getPressure()
									// ));
									ml.setData(bl);
									sllx.sendMessageDelayed(ml, 5);

								}

								// if (xx < -10) {
								// sllxx.sendEmptyMessageDelayed(
								// ScrollView.FOCUS_RIGHT, 125);
								// } else if (xx > 10) {
								// sllxx.sendEmptyMessageDelayed(
								// ScrollView.FOCUS_LEFT, 125);
								// }

								if (xx < -10 && e.getPressure() > 0.44f) {
									TextView t9 = (TextView) g;

									t9.setClickable(false);
									t9.setLongClickable(false);
									t9.setFocusable(false);
									// if (lmidX.containsKey("g" + lMID) &&
									// !untri) {
									untri = true;
									buccoff.sendEmptyMessageDelayed(
											lmidX.getAsInteger("g" + lMID), 5);
									// } // cvboff.sendEmptyMessageDelayed(2,
									// 75);
									return false;// false works, slide right and
													// away
								}

							} else

							if (e.getAction() == MotionEvent.ACTION_UP
									&& xa4 > -1 && !untri) {
								// TextView gt = (TextView) g;
								// TextView gt = (TextView) g;
								// gt.startAnimation(bohx2);

								// gt.clearAnimation();
								// gt.startAnimation(nb8);
								Log.i(G, "pagelink good up xxx");// go to click
								// miesr(-1, jf, true, false);
								// return true;
								// int xx = (int) (xa4 - e.getRawX());
								{

									Message ml = new Message();
									Bundle bl = new Bundle();
									bl.putInt("sid", R.id.cubc);
									// bl.putInt("x",
									// (int) (xx * e.getPressure() * -1));
									ml.setData(bl);
									sllx.sendMessageDelayed(ml, 5);
								}

								return true;
							}

							// if(e.getAction() == MotionEvent.ACTION_UP){
							// }
							return false;
						}
					});

				}

				if (lnc > m5) {
					// LinearLayout.LayoutParams lpa = new
					// LinearLayout.LayoutParams(
					// boo * lnc, -2);
					// lpa.setMargins((boo * (lnc - m5)) * -1, 0, 0, 0);
					// lna.setLayoutParams(lpa);

					// int xx = (boo * ((lnc - m5))) / 3;
					// xx -= (m5) * boo;

					{

						Message ml = new Message();
						Bundle bl = new Bundle();
						bl.putInt("sid", R.id.cubc);
						bl.putInt("x", (lnc - m5) * boo * -1);
						ml.setData(bl);
						sllm.sendMessageDelayed(ml, 180);// (lnc + 1) * 180);
					}

					{
						Message ml = new Message();
						Bundle bl = new Bundle();
						bl.putInt("sid", R.id.cubc);
						bl.putInt("x2", 0);
						// bl.putInt("x", (lnc - m5) * boo * -1);
						ml.setData(bl);
						sllx.sendMessageDelayed(ml, (lnc + 1) * 180 + 25);
					}

					buccimg.sendEmptyMessageDelayed(c5.getId(),
							(lnc + 10) * 180 + 860);

				}

				xe.close();
				e.close();
			}
		}
	};

	Handler buccimg = new Handler() {
		public void handleMessage(Message msg) {

			LinearLayout xxbucc = (LinearLayout) findViewById(msg.what);

			if (xxbucc != null) {

				Bitmap xt = null;
				Bitmap xxt = null;
				// xxbucc.setDrawingCacheEnabled(true);
				// xxbucc.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
				// xxbucc.postInvalidate();
				xxt = xxbucc.getDrawingCache();

				if (xxt == null) {
					Log.e(G, "buccoff xxx " + xxbucc.isDrawingCacheEnabled());
				} else {
					try {
						xt = Bitmap.createBitmap(xxt);
					} catch (OutOfMemoryError em) {
						xt = null;
						em.printStackTrace();
					}
				}

				ImageView x1 = new ImageView(mCtx);
				LinearLayout.LayoutParams vx = new LinearLayout.LayoutParams(
						75, 85);
				vx.setMargins(3, 3, 3, 3);

				x1.setLayoutParams(vx);
				x1.setScaleType(ScaleType.FIT_XY);
				LinearLayout xcbx = (LinearLayout) findViewById(R.id.cxb);

				if (xt == null) {
					Log.e(G, "buccoff xxx x");
					// buccoff.sendEmptyMessageDelayed(xxbucc.getId(), 125);

				} else {
					Log.i(G, "buccoff xxx ");

					ByteArrayOutputStream st = new ByteArrayOutputStream();
					xt.compress(CompressFormat.PNG, 0, st);

					byte[] g8 = st.toByteArray();
					xt = BitmapFactory.decodeByteArray(g8, 0, g8.length);

					if (xt == null) {
						Log.e(G, "Bitmap disappeared " + g8.length + " was "
								+ st.size());
					} else {

					}
				}

				if (xt == null) {
					x1.setImageResource(R.drawable.ld5);
				} else {
					Bitmap xh = null;
					xh = Bitmap.createScaledBitmap(xt, xt.getWidth() / 5,
							xt.getHeight() / 5, true);

					if (xh != null) {
						x1.setImageBitmap(xh);
					} else {
						x1.setImageResource(R.drawable.ld);
					}

				}

				// x1.setBackgroundResource(R.drawable.c3);
				xcbx.addView(x1, 0);

				// xcbx.bringChildToFront(x1);
				Animation xxc = AnimationUtils
						.loadAnimation(mCtx, R.anim.xgom4);
				x1.startAnimation(xxc);
			}

		}
	};

	ContentValues buccoffa;
	Handler buccoff = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "buccoff good xxx " + msg.what);
			if (buccoffa == null) {
				buccoffa = new ContentValues();
			}

			RelativeLayout xbh = (RelativeLayout) findViewById(R.id.cvxhc);

			if (msg.what == 2) {

				LinearLayout xxbucc = null;
				int hxv = 1;
				for (int i = xbh.getChildCount() - 1; i >= 0; i--) {
					xxbucc = (LinearLayout) xbh.getChildAt(i);
					if (buccoffa.containsKey("b" + xxbucc.getId())
							&& buccoffa.getAsBoolean("b" + xxbucc.getId())) {
						continue;
					}

					buccoff.sendEmptyMessageDelayed(xxbucc.getId(), 725 * hxv++);
					// buccoffa.put("b" + msg.what, true);
					// xxbucc.clearAnimation();
					// Animation gx = AnimationUtils.loadAnimation(mCtx,
					// R.anim.fgom);
					// gx.setStartOffset(125 * (hxv - 1) + 5);
					// xxbucc.clearAnimation();
					// xxbucc.startAnimation(gx);
				}

			} else {
				Animation gx = AnimationUtils.loadAnimation(mCtx, R.anim.fgom4);
				LinearLayout xxbucc = (LinearLayout) findViewById(msg.what);
				if (xxbucc != null) {
					Log.i(G,
							"buccoff xxx xx "
									+ buccoffa.getAsBoolean("b"
											+ xxbucc.getId()));

					if (!buccoffa.containsKey("b" + xxbucc.getId())
							|| !buccoffa.getAsBoolean("b" + xxbucc.getId())) {

						buccoffa.put("b" + msg.what, true);

						// xxbucc.clearAnimation();

						xxbucc.startAnimation(gx);
						buccoff2.sendEmptyMessageDelayed(msg.what,
								gx.getDuration());

					}
				}
			}
		}
	};

	Handler buccoff2 = new Handler() {
		public void handleMessage(Message msg) {
			// cvboff2
			Log.i(G, "buccoff2 good xxx");
			if (msg.what > 2) {
				LinearLayout xxbucc = null;

				// xxbucc = (LinearLayout) findViewById(msg.what);//
				// vbh.getChildAt(vbh.getChildCount()
				// // - 1);
				xxbucc = (LinearLayout) findViewById(msg.what);

				if (xxbucc != null) {
					// xxbucc.setVisibility(View.GONE);
					// xbh.removeView(xxbucc);
					RelativeLayout xbh = (RelativeLayout) findViewById(R.id.cvxhc);
					int i = 1;
					for (i = xbh.getChildCount() - 1; i >= 0; i--) {
						xxbucc = (LinearLayout) xbh.getChildAt(i);
						if (xxbucc.getId() == msg.what) {
							break;
						}
						// xxbucc = (LinearLayout) findViewById(msg.what);
					}

					// RelativeLayout.LayoutParams xh =
					// (RelativeLayout.LayoutParams) xxbucc
					// .getLayoutParams();
					// xh.setMargins(i * (80), 0, 0, 0);
					// xxbucc.setLayoutParams(xh);

					xbh.removeView(xxbucc);
				}

				sizekln.sendEmptyMessageDelayed(2, 5);
				buccoffa.put("b" + msg.what, true);
			}

		}
	};

	Handler buccon = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "buccon good xxx");
			if (buccoffa == null) {
				buccoffa = new ContentValues();
			}

			// bucc.setVisibility(View.VISIBLE);

			if (msg.what > 2 && lmidX.containsKey("g" + msg.what)) {
				Animation gx = AnimationUtils.loadAnimation(mCtx, R.anim.xgom5);
				// RelativeLayout xbh = (RelativeLayout)
				// findViewById(R.id.cvxhc);
				LinearLayout xxbucc = null;
				xxbucc = (LinearLayout) findViewById(lmidX.getAsInteger("g"
						+ msg.what));
				// xxbucc = (LinearLayout) xbh.getChildAt(xbh.getChildCount() -
				// 1);

				if (xxbucc != null) {

					if (buccoffa.containsKey("b" + xxbucc.getId())
							&& buccoffa.getAsBoolean("b" + xxbucc.getId())) {
						xxbucc.clearAnimation();
					}

					// xxbucc.setVisibility(View.INVISIBLE);
					// xbh.bringChildToFront(xxbucc);
					// xxbucc.clearAnimation();
					xxbucc.startAnimation(gx);

					// RelativeLayout.LayoutParams xh =
					// (RelativeLayout.LayoutParams) xxbucc
					// .getLayoutParams();
					// xh.setMargins(0, 0, 0, 0);
					// xxbucc.setLayoutParams(xh);

					// xxbucc.setDrawingCacheEnabled(true);
					// xxbucc.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
					// xxbucc.postInvalidateDelayed(25);

					buccon2.sendEmptyMessageDelayed(msg.what, 14);

				}
			}

			sizekln.sendEmptyMessageDelayed(2, 5);

		}
	};
	Handler buccon2 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "buccon2 good xxx");
			if (msg.what > 2 && lmidX.containsKey("g" + msg.what)) {
				LinearLayout xxbucc = null;
				xxbucc = (LinearLayout) findViewById(lmidX.getAsInteger("g"
						+ msg.what));
				if (xxbucc != null) {
					// int i = 1;
					// for (i = xbh.getChildCount() - 1; i >= 0; i--) {
					// xxbucc = (LinearLayout) xbh.getChildAt(i);
					// if (xxbucc.getId() == msg.what) {
					// break;
					// }
					// }
					buccoffa.put("b" + lmidX.getAsInteger("g" + msg.what),
							false);
					// buccimg.sendEmptyMessageDelayed(xxbucc.getId(), 2725);

					xxbucc.setVisibility(View.VISIBLE);
				}
			}

		}
	};

	Context mCtx;
	// LinearLayout nbs;
	GLSurfaceView b1;
	Renderer rb1;
	int mHeight = 480;
	int mWidth = 320;
	// RelativeLayout buc;
	// RelativeLayout bucb;
	// RelativeLayout bucx;
	// RelativeLayout bucxb;
	TextView bui;
	// ScrollView cub;
	RelativeLayout bc;
	RelativeLayout msu;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		Log.i(G, "oncreataeoptionsmenu good");
		MenuItem o1 = menu.add(1, 1, 0, "Entry");
		MenuItem o2 = menu.add(1, 2, 2, "Save");

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		Log.i(G, "onmenuitemselected good xxx");
		switch (item.getItemId()) {

		case 1:
			Toast.makeText(mCtx, "ok", 5000).show();

			break;
		case 2:
			emailshow.sendEmptyMessageDelayed(2, 75);
			break;
		default:
			// Toast.makeText(mCtx, "us", 540).show();
		}

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		Log.i(G, "onrestoreinstancestate good xxx");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		Log.i(G, "onsaveinstancestate good xxx");
	}

	protected void onPause() {
		super.onPause();
		Log.i(G, "onpause good xxx");
		// b1.onPause();
		if (mGLView != null && mgl) {
			mGLView.onPause();
		}
		if (xuut != null) {
			xuut.release();
			xuut = null;
		}
		if (xuin != null) {
			xuin.release();
			xuin = null;
		}

		sr.removeMessages(2);
		sroff = true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		// b1.onResume();
		mHeight = getWindowManager().getDefaultDisplay().getHeight();
		mWidth = getWindowManager().getDefaultDisplay().getWidth();
		Log.i(G, "onresume good xxx");
		sizekln.sendEmptyMessageDelayed(2, 5);
		if (mMID > -1) {
			// miesr(-1, mMID, false, false);
		}

		if (lMID > -1) {
			// pagelink(lMID, 25);
		}

		if (mGLView != null && mgl) {
			mGLView.onResume();
			// Animation gx = AnimationUtils.loadAnimation(mCtx, R.anim.groan);
			// mGLView.startAnimation(gx);

		}
		sroff = false;
		sr.sendEmptyMessageDelayed(2, 75);

	}

	boolean sroff = false;
	Scan gs54 = null;
	World[] mWorlds = new World[2001];
	RelativeLayout nbb;
	RelativeLayout nba;
	RelativeLayout nbv;
	RelativeLayout nbva;
	LinearLayout ldx;
	// RelativeLayout bucc;
	LinearLayout nbi;

	Animation boh;
	Animation bno;
	Animation bflu;
	ListView mHu;
	boolean mgl = true;
	// RelativeLayout alx;

	private TextToSpeech mTts;
	int MY_DATA_CHECK_CODE = 32;
	int MY_VOICE_CHECK_CODE = 33;

	Handler xuo = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "xuo good xxx");
			Bundle bdl = msg.getData();
			String text = bdl.getString("text");

			File filea = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath(), mCtx.getPackageName());
			filea.mkdirs();
			File file = new File(filea.getAbsolutePath(), "xaudio.3gp");
			String filename = file.getAbsolutePath();
			if (xuin == null) {
				setr(text);
			} else {
				setr(text + ", " + file.length());
			}

			if (xuin != null) {
				// xuin.stop();
				xuin.release();
				xuin = null;

			}

			xuut = new MediaPlayer();
			try {
				xuut.setDataSource(filename);
				xuut.prepare();
				xuut.start();
			} catch (IOException k5) {
				setr("Drat " + k5.getLocalizedMessage());
			}

		}
	};

	String xa = "";
	ContentValues uvccT;
	boolean adtune = false;
	String ok = ".*(yes|proceed|continue|read|that|sure|yeah|fine|confirm|affirm|okay|more|extra|shall|maybe).*";

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.i(G, "onactivityresult good xxx");
		if (requestCode == MY_VOICE_CHECK_CODE && data != null) {

			ArrayList<String> pj;
			pj = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			SharedPreferences mReg = getSharedPreferences("Preferences",
					MODE_WORLD_READABLE);
			Editor mEdt = mReg.edit();

			for (int i = 0; i < pj.size(); i++) {
				Log.i(G, "xxx " + i + ": " + pj.get(i) + " xa:" + xa.length()
						+ " -- " + (ccvb - 1));

				if (pj.get(i).contains("speak") || pj.get(i).contains("speech")
						|| adtune) {

					Log.i(G, "xxx " + adtune + " " + ccvvx + ": " + ccvb);
					if (!adtune) {
						adtune = true;
						// mTts.speak("faster slower lower higher " + ccvb,
						// TextToSpeech.SUCCESS, null);
					}

					// mTts.speak("", TextToSpeech.SUCCESS, null);

					if (uvccT == null) {
						loadUvccT();
					}

					if (pj.get(i).contains("next")) {
						ccvb++;
					}
					if (ccvvx.length() > 0) {
						mTts.speak(ccvvx, TextToSpeech.QUEUE_ADD, null);
					} else {
						mTts.speak("" + ccvb, TextToSpeech.QUEUE_ADD, null);
					}

					if (pj.get(i).contains("load")) {
						String cb = pj.get(i).replaceAll(".*load ", "");
						if (cb.length() == 1) {
							ccvb = cb.charAt(0);
						} else {
							ccvvx = cb;
						}
					}

					if (!uvccT.containsKey("s" + ccvb)) {
						uvccT.put("s" + ccvb, (float) (tempo / 100));
					}
					if (!uvccT.containsKey("p" + ccvb)) {
						uvccT.put("p" + ccvb, (float) (pitch / 100));
					}

					float by = (float) 0.01;
					float to = (float) 0.0;
					if (pj.get(i).matches(".* by ([0-9]+\\.)[0-9]+")) {
						String xx = pj.get(i).replaceAll(".* by ", "").trim();
						by = Float.parseFloat(xx.replaceAll("%", ""));
						if (xx.contains("%")) {
							by = by / 100;
						}
					}
					if (pj.get(i).matches(".* to ([0-9]+\\.)[0-9]+")) {
						String xx = pj.get(i).replaceAll(".* to ", "").trim();
						to = Float.parseFloat(xx.replaceAll("%", ""));
						if (xx.contains("%")) {
							to = to / 100;
						}
					}

					if (pj.get(i).contains("faster")) {
						mTts.speak("faster " + ccvb, TextToSpeech.SUCCESS, null);
						uvccT.put("s" + ccvb,
								uvccT.getAsFloat("s" + ccvb) + 0.01);
					} else if (pj.get(i).contains("slower")) {
						mTts.speak("slower " + ccvb, TextToSpeech.SUCCESS, null);
						uvccT.put("s" + ccvb,
								uvccT.getAsFloat("s" + ccvb) - 0.01);
					} else if (pj.get(i).contains("higher")) {
						mTts.speak("higher " + ccvb, TextToSpeech.SUCCESS, null);
						uvccT.put("p" + ccvb,
								uvccT.getAsFloat("p" + ccvb) + 0.01);
					} else if (pj.get(i).contains("lower")) {
						mTts.speak("lower " + ccvb, TextToSpeech.SUCCESS, null);
						uvccT.put("p" + ccvb,
								uvccT.getAsFloat("p" + ccvb) - 0.01);
					} else if (pj.get(i).matches(".*(save).*")) {// tune
						for (char b = 'a'; b < '{'; b++) {

							if (uvccT.containsKey("s" + b)) {
								mEdt.putFloat("s" + b,
										uvccT.getAsFloat("s" + b));
							}
							if (uvccT.containsKey("p" + b)) {
								mEdt.putFloat("p" + b,
										uvccT.getAsFloat("p" + b));
							}
						}

						adtune = false;
					} else if (pj.get(i).contains("rate")) {
						xxrtg.sendEmptyMessageDelayed(2, 5);
					} else if (pj.get(i).contains("pitch")
							|| pj.get(i).contains("tone")) {
						xxrtp.sendEmptyMessageDelayed(2, 5);
					} else if (pj.get(i).contains("settings")) {
						mTts.speak(
								"tone float " + uvccT.getAsFloat("p" + ccvb),
								TextToSpeech.QUEUE_ADD, null);
						mTts.speak(
								"rate float " + uvccT.getAsFloat("s" + ccvb),
								TextToSpeech.QUEUE_ADD, null);
					}

					// Message ml = new Message();
					// Bundle bl = new Bundle();
					// bl.putString("text", ", " + ccvb + ", " + ccvb + ", "
					// + ccvb);
					// bl.putInt("type", TextToSpeech.SUCCESS);
					// ml.setData(bl);

					// xape.sendMessageDelayed(ml, 75);

				}

				if (xa.length() > 0) {
					if (pj.get(i).contains("add text")) {
						xa += pj.get(i).replaceFirst(".* path ", "").trim();
						setr(xa, 850);
						mTts.speak("Is this correct?",
								TextToSpeech.QUEUE_FLUSH, null);
						break;
					}
					if (pj.get(i).contains("add path")) {
						xa += "/"
								+ pj.get(i).replaceFirst(".* path ", "").trim();
						setr(xa, 850);
						mTts.speak("Is this correct?",
								TextToSpeech.QUEUE_FLUSH, null);
						break;
					}
					if (pj.get(i).contains("correct")) {
						ElkRequest.add(xa, "", "", "");
						xa = "";
						break;
					}
					if (pj.get(i).contains("cancel")) {
						mTts.speak("done", TextToSpeech.QUEUE_FLUSH, null);
						xa = "";
						break;
					}
				}

				// mTts(); proe mEdt
				// mTts.speak("raquo", TextToSpeech.QUEUE_FLUSH, null);

				if (pj.get(i).contains("record audio")) {

					setr("recording");
					xuinh.sendEmptyMessageDelayed(5 * 60000, 1250);
					break;
				}

				if (pj.get(i).contains("computer playback")) {

					Message ml = new Message();
					Bundle bl = new Bundle();
					bl.putString("text", pj.get(i));
					ml.setData(bl);
					xuo.sendMessageDelayed(ml, 35);
					break;

				}

				if (pj.get(i).contains("secur")) {
					xa = pj.get(i).replaceFirst(".*secure ", "")
							.replaceAll(".* to ", "").replaceAll(" ", "")
							.trim();
					if (!xa.startsWith("http")) {
						xa = "https://" + xa;
					}

					mTts.speak("Is this correct?", TextToSpeech.QUEUE_FLUSH,
							null);

					setr(xa, 850);
					break;
				}

				if (pj.get(i).contains("open ")) {
					xa = pj.get(i).replaceFirst(".*open ", "")
							.replaceAll(" ", "").trim();

					if (!xa.startsWith("http")) {
						xa = "http://" + xa;
					}
					mTts.speak("Is this correct?", TextToSpeech.QUEUE_FLUSH,
							null);

					setr(xa, 850);

					// nb4.performClick();
					break;

				}

				if (pj.get(i).contains("search ")) {
					String xi = pj.get(i).replaceFirst(".*search ", "").trim();
					// add http gov
					String xu6 = "http://www.google.com/search?lh=en&q="
							+ Uri.parse(xi).toString();
					setr("Search " + xi + " @ " + xu6);

					Uri xe = ElkRequest.add(xu6, "Search", "", xi);
					break;

				}

				if (pj.get(i).contains("report")) {
					String d = "";
					if (mReg.contains("report")) {
						if (mReg.getString("report", "").length() == 0) {
							d += "Report available.";
						} else {
							d += "Reported last "
									+ mReg.getString(
											"report",
											datetime(System.currentTimeMillis()));
						}
					} else {
						d += "";
					}

					mEdt.putBoolean("proceed report", true);// mEdt.commit();
					Cursor xe;
					xe = SqliteWrapper.query( mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "count(*)" }, "updated > '" + mReg.getString("report", datetime(System.currentTimeMillis() - 365 * 24 * 60 * 60000)) + "' and mid is null and status > 0", null, null);

					Cursor xe2;
					xe2 = SqliteWrapper.query( mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "count(*)" }, "updated > '" + mReg.getString("report", datetime(System.currentTimeMillis() - 365 * 24 * 60 * 60000)) + "' and mid is not null and status > 0", null, null);
					if (xe != null && xe2 != null && xe.moveToFirst()
							&& xe2.moveToFirst()) {
						setr(xe.getInt(0)
								+ " updates"
								+ (xe2.getInt(0) == 0 ? "" : " "
										+ xe2.getInt(0) + " found"), true);
						xe.close();
						xe2.close();

					}
					break;

				}

				// nb4(kilobytes)xape
				if (pj.get(i).matches(".*(cancel).*")) {
					mTts.speak("", TextToSpeech.QUEUE_FLUSH, null);
					cvbback.sendEmptyMessageDelayed(2, 75);
					// setr("Good idea to implement");
					break;

				}

				if (pj.get(i).matches(".*(repeat|what).*")) {
					mTts.speak(mTtsl, TextToSpeech.QUEUE_FLUSH, null);
					// cvbback.sendEmptyMessageDelayed(2, 75);
					// setr("Good idea to implement");
					break;

				}

				if (mReg.getBoolean("proceed report", false)) {

					if (pj.get(i).matches(ok)) {

						mEdt.putString("report",
								datetime(System.currentTimeMillis()));

						Cursor xe;
						xe = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "_id", "title", "foundtitle", "url", "language" }, "updated > '" + mReg.getString( "report", datetime(System.currentTimeMillis() - 365 * 24 * 60 * 60000)) + "' and mid is null and status > 0", null, null);

						Cursor xe2;
						xe2 = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "_id", "foundtitle", "language" }, "updated > '" + mReg.getString( "report", datetime(System .currentTimeMillis() - 365 * 24 * 60 * 60000)) + "' and mid is not null and status > 0", null, null);

						if (xe != null && xe2 != null && xe.moveToFirst()
								&& xe2.moveToFirst()) {
							String tf = "";
							String t = "";
							String t5 = "";
							String lg = "";
							String lg2 = "";

							if (mReg.getInt("reporting", 0) < 2) {
								for (int x5 = 0; x5 < xe.getCount(); x5++) {
									xe.moveToPosition(x5);
									tf = xe.getString(2);
									lg = xe.getString(4);
									prepLanguage(lg);
									t = xe.getString(1);
									t5 = xe.getString(3);
									if (tf == null || tf.length() == 0) {
										tf = t5;
									}
									setr(tf + " ... " + t, x5 * 1450);

								}
								mEdt.putInt("reporting", 2);

							}

							// setr(" with " + xe2.getCount() + " mentionables",
							// true);
							if (mReg.getInt("reporting", 0) < 3) {

								for (int ib = 0; ib < xe2.getCount(); ib++) {
									xe2.moveToPosition(ib);
									if (xe2.getString(1) == null) {
										continue;
									}
									lg2 = xe2.getString(2);
									prepLanguage(lg2);
									setr((xe2.getString(1) != null ? xe2.getString(1)
											: "unopen " + xe2.getInt(0)),
											ib * 1850);
								}
								mEdt.putInt("reporting", 3);

							}

							if (mReg.getInt("reporting", 0) == 3) {
								setr("report complete");
								mEdt.putBoolean("proceed report", false);
							}
							xe.close();
							xe2.close();

						}

					}
					break;

				}

				if (mReg.getBoolean("proceed", false)) {

					if (pj.get(i).matches(ok)) {
						mEdt.putBoolean("proceed", false);
						xmmu.sendEmptyMessageDelayed(mReg.getInt("hid", -1), 75);
					}
					break;

				}

				if (pj.get(i).contentEquals("computer")) {
					mTts.speak("Yes?", TextToSpeech.QUEUE_FLUSH, null);
					break;
				}

			}
			mEdt.commit();

		} else if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// Bundle bl = new Bundle();
				// bl.putObject("ob", this);
				// Message ml = new Message();
				// ml.setData(bl);
				// xxgg.sendMessageDelayed(ml, 5);

				// try{
				mTts = new TextToSpeech(mCtx, oil);
				// }catch(){

				// }
			} else {
				// missing data, install it
				try {

					Intent installIntent = new Intent();
					installIntent
							.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
					// startActivity(installIntent);
				} catch (RuntimeException sd) {
					Log.e(G, "xxx ");
					sd.printStackTrace();

				}
			}
		}
	}

	Handler xxgg = new Handler() {
		public void handleMessage() {

		}
	};

	Handler xxrtg = new Handler() {
		public void handleMessage() {
			Log.i(G, "xxrtg good xxx");
			String xg = "";
			if (ccvvx.length() > 0) {
				xg = ccvvx;
			} else {
				xg = "" + ccvb;
			}
			mTts.setSpeechRate(uvccT.getAsFloat("s" + xg) - (float) 0.01);
			mTts.speak("" + xg, TextToSpeech.QUEUE_FLUSH, null);
			xxrtg2.sendEmptyMessageDelayed(2, 1854);
		}
	};

	Handler xxrtg2 = new Handler() {
		public void handleMessage() {
			Log.i(G, "xxrtg2 good xxx");
			String xg = "";
			if (ccvvx.length() > 0) {
				xg = ccvvx;
			} else {
				xg = "" + ccvb;
			}
			mTts.setSpeechRate(uvccT.getAsFloat("s" + xg));
			mTts.speak("" + xg, TextToSpeech.QUEUE_FLUSH, null);
			xxrtg3.sendEmptyMessageDelayed(2, 1854);

		}
	};

	Handler xxrtg3 = new Handler() {
		public void handleMessage() {
			Log.i(G, "xxrtg3 good xxx");
			String xg = "";
			if (ccvvx.length() > 0) {
				xg = ccvvx;
			} else {
				xg = "" + ccvb;
			}
			mTts.setSpeechRate(uvccT.getAsFloat("s" + xg) + (float) 0.01);
			mTts.speak("" + xg, TextToSpeech.QUEUE_FLUSH, null);
			mTts.setSpeechRate(uvccT.getAsFloat("s" + xg));
		}
	};

	Handler xxrtp = new Handler() {
		public void handleMessage() {
			Log.i(G, "xxrtp good xxx");
			String xg = "";
			if (ccvvx.length() > 0) {
				xg = ccvvx;
			} else {
				xg = "" + ccvb;
			}
			mTts.setPitch(uvccT.getAsFloat("p" + xg) - (float) 0.01);
			mTts.speak(xg, TextToSpeech.QUEUE_FLUSH, null);
			xxrtp2.sendEmptyMessageDelayed(2, 1854);
		}
	};

	Handler xxrtp2 = new Handler() {
		public void handleMessage() {
			Log.i(G, "xxrtp2 good xxx");
			String xg = "";
			if (ccvvx.length() > 0) {
				xg = ccvvx;
			} else {
				xg = "" + ccvb;
			}
			mTts.setPitch(uvccT.getAsFloat("p" + xg));
			mTts.speak(xg, TextToSpeech.QUEUE_FLUSH, null);
			xxrtp3.sendEmptyMessageDelayed(2, 1854);
		}
	};

	Handler xxrtp3 = new Handler() {
		public void handleMessage() {
			Log.i(G, "xxrtp3 good xxx");
			String xg = "";
			if (ccvvx.length() > 0) {
				xg = ccvvx;
			} else {
				xg = "" + ccvb;
			}
			mTts.setPitch(uvccT.getAsFloat("p" + xg) + (float) 0.01);
			mTts.speak(xg, TextToSpeech.QUEUE_FLUSH, null);
			mTts.setPitch(uvccT.getAsFloat("p" + xg));

		}
	};

	String mTtsl = "";
	Handler xmmu = new Handler() {
		public void handleMessage(Message msg) {
			// cvxh
			Log.i(G, "xmmu good xxx " + msg.what);
			LinearLayout x4 = null;

			try {
				TextView x5 = (TextView) findViewById(msg.what);
				if (x5 != null) {
					x4 = (LinearLayout) x5.getParent();
				}
				if (x4 == null) {
					x4 = (LinearLayout) findViewById(msg.what);
				}
			} catch (ClassCastException ck) {
			}
			// LinearLayout x4 = (LinearLayout) xu2
			// .getChildAt(xu2.getChildCount() - 1);
			int cx8 = 2;

			for (int i = 0; i < x4.getChildCount(); i++) {

				try {
					TextView xu5 = (TextView) x4.getChildAt(i);
					if (xu5.getVisibility() != View.VISIBLE) {
						continue;
					}

					Message ml = new Message();
					Bundle bl = new Bundle();
					bl.putString("text", xu5.getText().toString());
					bl.putInt("type", TextToSpeech.QUEUE_ADD);
					ml.setData(bl);

					xape.sendMessageDelayed(ml, cx8++ * 1375);

				} catch (ClassCastException k5) {
					continue;
				}

			}

		}
	};
	int pitch = 77;
	int tempo = 94;
	OnInitListener oil = new OnInitListener() {

		public void onInit(int a) {
			Log.i(G, "oil good xxx " + a);

			mTts.setLanguage(Locale.US);
			mTts.setPitch((float) pitch / 100);
			mTts.setSpeechRate((float) tempo / 100);

		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCtx = this;
		setContentView(R.layout.main);
		Log.i(G, "oncreate good xxx");
		hid = (int) SystemClock.uptimeMillis() + 234084 * 2234;
		nbv = (RelativeLayout) findViewById(R.id.nbv);
		nbva = (RelativeLayout) findViewById(R.id.nbva);
		mHu = getListView();
		mHeight = getWindowManager().getDefaultDisplay().getHeight();
		mWidth = getWindowManager().getDefaultDisplay().getWidth();
		// Log.i(G, mWidth + "," + mHeight);
		// nb4 = (ImageView) findViewById(R.id.nb4);
		nbdd = (ImageView) findViewById(R.id.nbdd);
		olmx = System.currentTimeMillis();

		msu = (RelativeLayout) findViewById(R.id.ldx2);
		// alx = (RelativeLayout) findViewById(R.id.alx);
		base = (RelativeLayout) findViewById(R.id.base);
		nbi = (LinearLayout) findViewById(R.id.nbsl);
		ldx = (LinearLayout) findViewById(R.id.ldx);
		// hu = (LinearLayout) findViewById(R.id.hu);
		bui = (TextView) findViewById(R.id.cg);
		// buc = (RelativeLayout) findViewById(R.id.cvb);// cvb
		// bucx = (RelativeLayout) findViewById(R.id.cvbx);
		// bucc = (RelativeLayout) findViewById(R.id.cvc);
		// bucb = (RelativeLayout) findViewById(R.id.cvbb);
		// bucxb = (RelativeLayout) findViewById(R.id.cvbxb);

		nba = (RelativeLayout) findViewById(R.id.nba);
		nbb = (RelativeLayout) findViewById(R.id.nbb);

		chb = (RelativeLayout) findViewById(R.id.chb);
		// bc = (RelativeLayout) findViewById(R.id.cv);
		// cub = (ScrollView) findViewById(R.id.cub);
		// bv = (ImageView) findViewById(R.id.hbi);
		// bv.setBackgroundResource(R.drawable.breath);

		// bru = (AnimationDrawable) bv.getBackground();
		nb8 = AnimationUtils.loadAnimation(mCtx, R.anim.groan);
		na = AnimationUtils.loadAnimation(mCtx, R.anim.in);
		nb9 = AnimationUtils.loadAnimation(mCtx, R.anim.woomp);

		boh = AnimationUtils.loadAnimation(mCtx, R.anim.oh);
		bno = AnimationUtils.loadAnimation(mCtx, R.anim.no);
		bflu = AnimationUtils.loadAnimation(mCtx, R.anim.flu);
		// nb4.setVisibility(View.VISIBLE);
		// nb4.startAnimation(nb8);

		chs = (RelativeLayout) findViewById(R.id.chs);
		bec = (ImageView) findViewById(R.id.bec);

		// nb = (ImageView) findViewById(R.id.nb);
		// nbs = (LinearLayout) findViewById(R.id.nbs);

		sr.sendEmptyMessageDelayed(2, 275);

		s01p01.sendEmptyMessageDelayed(1, 150);

		vstart.sendEmptyMessageDelayed(2, 5);

		ScrollView cx5 = (ScrollView) findViewById(R.id.cubc);
		cx5.setSaveEnabled(true);

		bgi = new ContentValues();
		bdi = new Drawable[10];

		// HandlerThread txo = new HandlerThread(G,
		// Process.THREAD_PRIORITY_DEFAULT);
		// txo.start();
		// xgxh = new xgxh(txo.getLooper());

		mGLView = (GLSurfaceView) findViewById(R.id.br);

		if (mgl) {
			mGLView.setVisibility(View.VISIBLE);
			// Animation gx = AnimationUtils.loadAnimation(mCtx, R.anim.groan);
			// mGLView.startAnimation(gx);

			mPurpose = new InPurpose[201];
			mWorlds[0] = mkWorld();
			mWorlds[1] = mkGLwork();
			mWorlds[2] = mkConsole();
			mRen = new InRenderer(mCtx, mWorlds);

			mGLView.setGLWrapper(new GLSurfaceView.GLWrapper() {
				public GL wrap(GL gl) {
					return new MatrixTrackingGL(gl);
				}
			}); //

			mGLView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
			mGLView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

			mRen.mLCameraY = mRecentY;
			mRen.mLCameraX = mRecentX;
			mRen.mLCameraZ = mRecentZ;
			mRen.mLAngleY = mRAngleY;
			mRen.mLAngleX = mRAngleX;
			mRen.mLAngleZ = mRAngleZ;

			mGLView.setRenderer(mRen);

			mGLView.setFocusableInTouchMode(true);
			// registerForContextMenu(mGLView);

			mGLView.setOnTouchListener(tocxx);

		}

		SensorService.sendEmptyMessageDelayed(2, 175);

	}

	/*
	 * @Override public boolean onTrackballEvent(MotionEvent e) {
	 * 
	 * if (mSelected > -1 && mPurpose[mSelected] != null) {
	 * 
	 * mPurpose[mSelected].setAngle(InPurpose.kAxisX, e.getX() * 45); } else {
	 * 
	 * mRen.mAngleX += e.getX() * 48f;// * // (mRenderer.mLCameraZ // <= //
	 * -1?(-1 // * // mRenderer.mLCameraZ):mRenderer.mLCameraZ); }
	 * 
	 * // Log.w(G,"trackball " + mRenderer.mLCameraX + "("+(e.getX() * //
	 * TRACKBALL_SCALE_FACTOR)+") " + mRenderer.mLCameraY + "("+(e.getY() * //
	 * TRACKBALL_SCALE_FACTOR)+")"); // mRenderer.mAngleY += e.getY() *
	 * TRACKBALL_SCALE_FACTOR * -1.2f;// * // (mRenderer.mLCameraZ <= -1?(-1 *
	 * // mRenderer.mLCameraZ):mRenderer.mLCameraZ); // requestRender(); //
	 * reani.sendEmptyMessageDelayed(1,pRate); return true; }//
	 */

	public OnTouchListener tocxx = new OnTouchListener() {
		float mp1x = -1;
		float mp1y = -1;
		boolean mRelease = false;

		public boolean onTouch(View v, MotionEvent ev) {
			int act = ev.getAction();
			boolean record = false;
			float x = ev.getX();
			float y = ev.getY();
			Log.i(G, "tocxx good xxx " + act);
			if (act == MotionEvent.ACTION_DOWN) {
				mp1x = x;
				mp1y = y;

				if (mAnPause && mRelease) {
					mRelease = false;
					mAnPause = false;
				} else if (mAnPause) {
					mRelease = true;
				} else {
					mAnPause = true;
					// record = true;
					Log.w(G, "GL View angle(y" + mRen.mAngleY + ",x"
							+ mRen.mAngleX + ",z" + mRen.mAngleZ + ") camera("
							+ mRen.mLCameraY + "," + mRen.mLCameraX + ","
							+ mRen.mLCameraZ + ")");
				}

				return true;
			}

			else if (act == MotionEvent.ACTION_UP) {
				// if (mLMove > SystemClock.uptimeMillis() - 800) {
				// reani.sendEmptyMessageDelayed(1, 2000);
				// }
				if (mRelease) {
					mAnPause = false;
					mRelease = false;
					// record = true;
					Log.w(G, "GL View angle(y" + mRen.mAngleY + ",x"
							+ mRen.mAngleX + ",z" + mRen.mAngleZ + ") camera("
							+ mRen.mLCameraY + "," + mRen.mLCameraX + ","
							+ mRen.mLCameraZ + ")");
				}
			}

			if (mAnPause && act == MotionEvent.ACTION_DOWN) {
				// mRenderer.mAngleX = mRenderer.mLAngleX;
				// mRenderer.mAngleY = mRenderer.mLAngleY;
				// mRenderer.mAngleZ = mRenderer.mLAngleZ;
				// mRenderer.mCameraX = mRenderer.mLCameraX;
				// mRenderer.mCameraY = mRenderer.mLCameraY;
				// mRenderer.mCameraZ = mRenderer.mLCameraZ;

			}

			if (record) {
				mRecentY = mRen.mLCameraY;
				mRecentX = mRen.mLCameraX;
				mRecentZ = mRen.mLCameraZ;
				mRAngleY = mRen.mLAngleY;
				mRAngleX = mRen.mLAngleX;
				mRAngleZ = mRen.mLAngleZ;

				// ContentValues ins = new ContentValues();
				// ins.put("aux", mRen.mLAngleX);
				// ins.put("auy", mRen.mLAngleY);
				// ins.put("auz", mRen.mLAngleZ);
				// ins.put("cux", mRen.mLCameraX);
				// ins.put("cuz", mRen.mLCameraZ);
				// ins.put("cuy", mRen.mLCameraY);
				// Uri source = SqliteWrapper.insert(mCtx,
				// mCtx.getContentResolver(),
				// Uri.parse("content://com.havenskys.wave/perspective"),
				// ins);
				// Log.i(G, "Created " + source.toString());
			}

			if (act == MotionEvent.ACTION_MOVE) {

				// if(mLMove > SystemClock.uptimeMillis()-200){
				// mHome = false;
				// mAtHome = false;
				// }

				float dx = mp1x - x;
				float ox = x > mp1x ? x - mp1x : mp1x - x;
				float dy = mp1y - y;
				float oy = y > mp1y ? y - mp1y : mp1y - y;

				if (mp1x < x) {
					dx++;
				}
				if (mp1y < y) {
					dy++;
				}

				if (oy > .01f && ox > .01f) {
					if (oy > 2.1f && ox > 2.1f) {
						mRen.mCameraZ = mRen.mLCameraZ + dy * -1.5f + dx
								* -1.5f;
						// Log.w(G,"[dx("+dx+") ox("+ox+") "+mp1x+"->"+x+"]                      [dy("+dy+") oy("+oy+") "+mp1y+"->"+y+"]");
					} else if (oy > ox) {
						// mRen.mAngleZ = mRen.mLAngleZ + (float)(dy *
						// 1.5 );
						mRen.mAngleY = mRen.mLAngleY + (float) (dy * 3.5);

						Log.w(G, "                      [dy(" + dy + ") oy("
								+ oy + ") " + mp1y + "->" + y + "]     c("
								+ mRen.mAngleY + ")");
					} else {
						mRen.mAngleX = mRen.mLAngleX + (float) (dx * -4.5);
						// Log.w(G,"[dx("+dx+") ox("+ox+") "+mp1x+"->"+x+"]                      ");
					}

					if (ox > 1.5f || oy > 1.5f) {
						// mLMove = SystemClock.uptimeMillis();
						mHome = false;
						mAtHome = false;
					}

				}

				mp1x = x;
				mp1y = y;

			}
			// */

			return false;

		}
	};

	int orbitlimit = 1;
	Handler SensorService = new Handler() {
		boolean running = false;

		public void handleMessage(Message msg) {
			final Bundle bdl = msg.getData();
			Log.i(G, "sensorService good xxxx");
			/*
			 * SharedPreferences mReg = getSharedPreferences("Preferences",
			 * MODE_WORLD_READABLE);
			 * 
			 * if (!mReg.contains("waveopt")) { Editor mEdt = mReg.edit();
			 * mEdt.putBoolean("wave", true); mEdt.putBoolean("waveopt", true);
			 * mEdt.commit(); } else { if (!mReg.contains("wave")) { return; } }
			 * //
			 */
			if (running) {
				return;
			}

			final int mpt = mPurpose.length;
			running = true;
			Thread tx = new Thread() {
				boolean mStable = true;
				int position = 0;
				float[] lastvalues;
				long smooth = 34;// long smoothtext = 32;//String cn = "";

				public void run() {

					SensorEventListener or = new SensorEventListener() {

						// SharedPreferences mReg =
						// getSharedPreferences("Preferences",
						// MODE_WORLD_READABLE);

						public void onAccuracyChanged(Sensor arg0, int arg1) {
							// TODO Auto-generated method stub

						}

						// float mStable0 = 1;
						public void onSensorChanged(SensorEvent event) {
							// TODO Auto-generated method stub

							// if(smooth > SystemClock.uptimeMillis() ||
							// !getListView().hasFocus() ){return;}
							if (smooth > SystemClock.uptimeMillis()) {
								return;
							}

							smooth = SystemClock.uptimeMillis() + 130;// bdl.getInt("sensorspeed",250);
							float[] values = event.values;
							float valence = 0;

							if (lastvalues == null) {
								Log.w(G, "Loading Initial Sensor Values");
								lastvalues = values;
								for (int b = 0; b < values.length; b++) {
									lastvalues[b] = 0;
								}
							}
							// if(getListView().isShown() ||
							// getListView().hasFocus()){}else{Log.e(G,"List isn't shown and nofocus, sensor watch close");wayGo.sendEmptyMessage(2);}

							if (lastvalues != null
									&& values.length == lastvalues.length) {
								int b = 2;
								// {valence =
								// (lastvalues[b]>values[b]?lastvalues[b]-values[b]:values[b]-lastvalues[b]);}
								// float valence2 = 1f;{b = 1;
								// valence2 =
								// (lastvalues[b]>values[b]?lastvalues[b]-values[b]:values[b]-lastvalues[b]);}
								// float valence3 = 1f;{b = 1;
								// valence3 =
								// (lastvalues[b]>values[b]?lastvalues[b]-values[b]:values[b]-lastvalues[b]);}
								// if(valence == 0 || valence2 == 0){return;}

								// if(){
								for (b = 0; b < values.length; b++) {
									// float o = lastvalues[b];
									valence = (lastvalues[b] > values[b] ? lastvalues[b]
											- values[b]
											: values[b] - lastvalues[b]);
									// if(lastvalues[b] == values[b]){continue;}
									// Log.i(G,"Sensor Orientation ["+b+"] "+lastvalues[b]+" to "+values[b]+" position("+position+") last("+lastposition+") valence "
									// + valence);
									lastvalues[b] = values[b];
									if (b == 0) {// left right roll
										// orbit
										for (int t = lathing + 1; t < mPurpose.length
												&& t < mpt
												&& mPurpose[t] != null
												&& !mAnPause; t++) {
											mPurposeU[t] = values[b]
													* (t * t * -0.035f);
										}

										if (mGLworkid == -1) {
											return;
										}
										mPurposeU[mGLworkid] = (values[b]) * 0.8f;

										mPurposeU[1] = (values[b])
												* TOUCH_SCALE_FACTOR; // rotate

										// mPurpose[1].setAngle(values[b] *
										// TOUCH_SCALE_FACTOR);
										// mPurpose[3].setEt (
										// mPurpose[3].x+values[b]*15*TOUCH_SCALE_FACTOR
										// , mPurpose[3].y, mPurpose[3].z );
										// camera y(up down) x(left-right)
										// z(in-out)
										// mRen.mAngleX = values[b] *
										// TOUCH_SCALE_FACTOR;//mRenderer.mLAngleX-((mRenderer.mAngleX
										// <
										// values[b]?values[b]-mRenderer.mAngleX:mRenderer.mAngleX-values[b])*0.25f);
										// mRenderer.mCameraX =
										// values[b]*(5*5*-0.035f);
										// mWorlds[1].mPivotx +=
										// mPurposeU[3] = values[b] *
										// TOUCH_SCALE_FACTOR;
										// mBthing.setAngle(values[b]*0.15f);

									} else if (b == 1) {

										mPurposeU[0] = (values[b])
												* TOUCH_SCALE_FACTOR;

										// mPurpose[0].setAngle(values[b] *
										// TOUCH_SCALE_FACTOR);

										// mCurrentAngle = values[b]*0.15f;
										// mAthing.setAngle(mCurrentAngle);
										// for(int w=0; w < mWorlds.length &&
										// mWorlds[w] != null; w++){
										// }
										// mRenderer.mAngleY = valence * 36;
										// //(values[b]*26.5f)+mRAngleY;
										// mRenderer.mAngleY =
										// values[b]*TOUCH_SCALE_FACTOR;//
										// mRenderer.mLAngleY-((mRenderer.mAngleY
										// <
										// values[b]?values[b]-mRenderer.mAngleY:mRenderer.mAngleY-values[b])*0.25f);

									} else if (b == 2) {

										mPurposeU[2] = (values[b])
												* TOUCH_SCALE_FACTOR;

										// mPurpose[2].setAngle(values[b] *
										// TOUCH_SCALE_FACTOR);

										if (mAnPause) {
											// mRenderer.mAngleX =
											// (values[b]*-1.5f)+mRAngleX;
										}

										// mRenderer.mAngleX =
										// mRAngleX-(values[b]*-4.5f)-46;
										// mRenderer.mAngleY =
										// mRAngleY+(values[b]*-4.5f);

									}

								}
								// if(position != lastposition){
								// lastvalues = values;
								// }
								// }
							}
						}

						// smoothtext = SystemClock.uptimeMillis() + 1750;
						// String cn =
						// "("+event.sensor.getName()+"+"+values.length+")"+(int)values[0]+(values.length>0?"\n"+(int)values[1]:"")+""+(values.length>1?"\n"+(int)values[2]:"");//+""+(values.length>2?"\n"+(int)values[3]:"")+"";
						// {Message ml = new Message(); Bundle bl = new
						// Bundle(); bl.putInt("id",
						// bdl.getInt("morsv"));bl.putString("text",
						// cn);bl.putInt("color", Color.argb(200, 250, 250,
						// 255));ml.setData(bl);setText.sendMessageDelayed(ml,pRate);}
						// {Message ml = new Message(); Bundle bl = new
						// Bundle(); bl.putInt("id",
						// bdl.getInt("morsv"));bl.putString("text",
						// cn);bl.putInt("color", Color.argb(240, 250, 250,
						// 255));ml.setData(bl);setText.sendMessageDelayed(ml,pRate<100?pRate*4:(int)(pRate+(pRate/4)));}

					};
					{
						Message ml = new Message();
						Bundle bl = new Bundle();
						bl.putString("text", "Getting Sensor Provider");
						ml.setData(bl);
						logoly.sendMessageDelayed(ml, 75);
					}

					SensorManager sm = null;
					try {
						sm = (SensorManager) mCtx
								.getSystemService(SENSOR_SERVICE);
					} finally {
					}

					{
						Message ml = new Message();
						Bundle bl = new Bundle();
						bl.putString("text", "Registering Sensor Service");
						ml.setData(bl);
						logoly.sendMessageDelayed(ml, 75);
					}
					sm.registerListener(
							or,
							sm.getDefaultSensor(SensorManager.SENSOR_ORIENTATION),
							SensorManager.SENSOR_DELAY_GAME);
					{
						Message ml = new Message();
						Bundle bl = new Bundle();
						bl.putString("text", "Wave Sensor Service");
						ml.setData(bl);
						logoly.sendMessageDelayed(ml, 75);
					}
					// easyStatus("Wave Ready");
					/*
					 * try {
					 * 
					 * //for(;;Thread.sleep(1750)){if(!mReg.contains("wave")){
					 * easyStatus("Wave Off");break;}if(getListView().isShown()
					 * || getListView().hasFocus()){}else{Log.e(G,
					 * "List isn't shown and nofocus, sensor watch close"
					 * );wayGo.sendEmptyMessage(2);break;}}
					 * }catch(InterruptedException e){Thread.interrupted();} //
					 */

				}
			};
			tx.start();
		}
	};

	private Handler logoly = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "logoly good xxx");
			Bundle bx = msg.getData();
			int l = bx.getInt("l");
			String text = bx.getString("text");
			switch (l) {
			case 2:
				Log.e(G, ":" + text);
				break;
			case 3:
				Log.w(G, ":" + text);
				break;
			default:
				Log.i(G, ":" + text);
				break;
			}
		}
	};

	InRenderer mRen;
	private GLSurfaceView mGLView;
	String G = "Elk";
	Request ElkRequest;
	// ImageView nb;
	/*
	 * Handler nbgo = new Handler() { long wg = 0; float x = 0f; float bx = 0f;
	 * 
	 * public void handleMessage(Message msg) { Bundle bdl = msg.getData(); x =
	 * bdl.getFloat("x"); bx = nb.getPaddingLeft(); boolean mgo = false;
	 * 
	 * if (wg != bdl.getLong("g", -1)) { if (wg < bdl.getLong("g", 1)) { wg =
	 * bdl.getLong("g"); } else if (wg > bdl.getLong("g", -1)) { return; }
	 * 
	 * }
	 * 
	 * if (bx > x) { if ((int) bx - 5 < x || bdl.getInt("c", 150) > 100) {
	 * nb.setPadding((int) x, 0, 0, 0); } else { mgo = true; nb.setPadding((int)
	 * bx - 15, 0, 0, 0); } } else { if ((int) bx + 5 > x || bdl.getInt("c",
	 * 150) > 100) { nb.setPadding((int) x, 0, 0, 0); } else { mgo = true;
	 * nb.setPadding((int) bx + 15, 0, 0, 0); } }
	 * 
	 * //
	 * Log.i(G," x("+x+") bx("+bx+","+(int)(bx-(bx-x-45)/2)+") c("+bdl.getInt(
	 * "c",-1)+") ");
	 * 
	 * if (mgo) { { Message ml = new Message(); Bundle bl = new Bundle();
	 * bl.putLong("g", wg); bl.putFloat("x", x); bl.putInt("c", bdl.getInt("c",
	 * 0) + 1); ml.setData(bl); nbgo.sendMessageDelayed(ml, 25); } }
	 * 
	 * // if(bx-x-45 > -5 && bx-x-45 < 5){ // // if( bdl.getInt("c",0) < 15){ //
	 * } // }
	 * 
	 * }
	 * 
	 * }; //
	 */

	Handler s01s00 = new Handler() {

		public void handleMessage(Message msg) {
			Bundle bdl = msg.getData();

		}

	};

	Handler s01p04 = new Handler() {

		public void handleMessage(Message msg) {
			Bundle bdl = msg.getData();
			Log.i(G, "s01p04 good xxx");
			// msu
			// RelativeLayout han = (RelativeLayout) findViewById(R.id.nbsw);
			RelativeLayout brc = (RelativeLayout) findViewById(R.id.nbsg2);

			OnClickListener x2 = new OnClickListener() {
				public void onClick(View g) {
					Log.i(G, "nbsg2 click good xxx");
					RelativeLayout gt = (RelativeLayout) g;
					gt.clearAnimation();
					gt.startAnimation(boh);
					Log.i(G, "x2 good click xxx");
					if (msu.getVisibility() == View.VISIBLE) {
						joexu.sendEmptyMessageDelayed(2, 25);
					} else {
						joex.sendEmptyMessageDelayed(mMID > 0 ? mMID : lMID, 25);
					}
				}
			};
			brc.setOnClickListener(x2);
			// han.setOnClickListener(x2);

			// nb4.setImageResource(R.drawable.ld4);
			// nb4.setOnClickListener(xccvb);

			RelativeLayout nbsg = (RelativeLayout) findViewById(R.id.nbsg);

			nbsg.setOnClickListener(new OnClickListener() {
				// anim.flu
				public void onClick(View g) {
					Log.i(G, "nbsg good click xxx");
					RelativeLayout gt = (RelativeLayout) g;
					gt.clearAnimation();
					gt.startAnimation(boh);

					sr.sendEmptyMessageDelayed(2, 88);
					cvbback.sendEmptyMessageDelayed(2, 5);
					elkreq.sendEmptyMessageDelayed(2, 75);
					// xgxhbatch.sendEmptyMessageDelayed(2, 1250);
					// sizekln.sendEmptyMessageDelayed(2, 125);

				}
			});

			bkb01.sendEmptyMessageDelayed(2, 10);

		}

	};

	String ccvvx = "";
	char ccvb = 'a';

	OnClickListener xccvb = new OnClickListener() {

		public void onClick(View g) {
			Log.i(G, "xccvb good click xxx");
			{

				ImageView g4 = (ImageView) g;
				Animation boh = AnimationUtils
						.loadAnimation(mCtx, R.anim.woomp);
				g4.clearAnimation();
				g4.startAnimation(boh);
				xxhj.sendEmptyMessageDelayed(2, 8);

			}
		}
	};

	Handler xxhj = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "xxhj good xxx");
			mTts.speak("", TextToSpeech.SUCCESS, null);

			Intent checkIntent = new Intent();
			checkIntent.setAction(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
			checkIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
			checkIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");
			checkIntent.setFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
			// checkIntent
			// .addFlags();
			startActivityForResult(checkIntent, MY_VOICE_CHECK_CODE);
			// computer
			//

		}
	};

	Handler xuinh = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "xuinh good xxx");
			File filea = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath(), mCtx.getPackageName());

			filea.mkdirs();
			File file = new File(filea.getAbsolutePath(), "xaudio.3gp");
			String filename = file.getAbsolutePath();

			if (!filea.exists()) {
				setr("Drat " + filename);
			}

			xuin = new MediaRecorder();
			xuin.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
			xuin.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			xuin.setOutputFile(filename);
			xuin.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			// xuin.setMaxDuration(msg.what > 1000 ? msg.what : 5867);

			try {
				xuin.prepare();

				xuin.start();
			} catch (IOException k4) {
				setr("Drat " + k4.getLocalizedMessage());
			} catch (RuntimeException k5) {
				setr("Drat " + k5.getLocalizedMessage());
			}
		}
	};

	MediaRecorder xuin;
	MediaPlayer xuut;

	// ImageView nb4;
	ImageView nbdd;
	Handler s01p03 = new Handler() {

		public void handleMessage(Message msg) {
			Log.i(G, "s01p03");
			Bundle bdl = msg.getData();

			// o1show.sendEmptyMessageDelayed(2, 275);
			// buc.setClickable(true);
			// bucx.setClickable(true);

			/*
			 * bv.setClickable(true); bv.setOnClickListener(new
			 * OnClickListener() {
			 * 
			 * public void onClick(View arg0) { o1hide.sendEmptyMessage(2); }
			 * }); bv.requestFocus(); //
			 */
			// */

			/*
			 * nbs.setOnTouchListener(new OnTouchListener() {
			 * 
			 * public boolean onTouch(View iv, MotionEvent md) {
			 * 
			 * switch (md.getAction()) { case MotionEvent.ACTION_MOVE: { Message
			 * ml = new Message(); Bundle bl = new Bundle(); bl.putLong("g",
			 * SystemClock.uptimeMillis()); bl.putFloat("x", md.getX() - 50);
			 * bl.putInt("c", 1); ml.setData(bl); nbgo.sendMessageDelayed(ml,
			 * 25); } return true; case MotionEvent.ACTION_DOWN: { Message ml =
			 * new Message(); Bundle bl = new Bundle(); bl.putLong("g",
			 * SystemClock.uptimeMillis()); bl.putFloat("x", md.getX() - 50);
			 * bl.putInt("c", 1); ml.setData(bl); nbgo.sendMessageDelayed(ml,
			 * 25); } return true; }
			 * 
			 * return false; } });
			 * 
			 * //
			 */
			s01p04.sendEmptyMessageDelayed(1, 75);

		}

	};

	Handler s01p01 = new Handler() {

		public void handleMessage(Message msg) {
			Log.i(G, "s01p01 good xxx");
			Bundle bdl = msg.getData();

			// gs = new Scan(mCtx, DataProvider.CONTENT_URI);
			ElkRequest = new Request(mCtx, DataProvider.CONTENT_URI);
			Log.i(G, "count for request(" + ElkRequest.length + ")");
			// nb4.setVisibility(View.VISIBLE);

			SharedPreferences mReg = getSharedPreferences("Preferences",
					MODE_WORLD_READABLE);
			Editor mEdt = mReg.edit();

			String lu = "http://www.seashepherd.de/news-and-media/sea-shepherd-news/feed/rss.html;;HS;;Sea Shepherd Deutschland";
			// lu +=
			// "\nhttp://twitter.com/statuses/user_timeline/11382292.rss;;HS;;Tech Dirt";
			// lu +=
			// "\nhttp://feeds.feedburner.com/smithsonianmag/Dinosaur;;HS;;Dinosaur Tracking";
			// lu +=
			// "\nhttp://www.seattlepi.com/rss/local_2.rss;;HS;;Seattle PI";
			// lu += "\nhttp://www.npr.org/rss/rss.php?id=1001;;HS;;NPR";
			// lu +=
			// "\nhttp://rss.sciam.com/ScientificAmerican-Global;;HS;;Scientific American";
			// lu +=
			// "\nhttp://rss1.smashingmagazinefee.com/feed/;;HS;;Smashing Magazine";
			lu += "\nhttp://newsrss.bbc.co.uk/rss/newsonline_world_edition/front_page/rss.xml;;HS;;BBC News";
			// lu +=
			// "\nhttp://ccinsider.comedycentral.com/feed/;;HS;;Comedy Insider";
			// lu += "\nhttp://feeds2.feedburner.com/GoogleCodeNews";
			// lu +=
			// "\nhttp://www.economist.com/rss/daily_news_and_views_rss.xml;;HS;;Economist";
			lu += "\nhttp://www.whitehouse.gov/feed/blog/whitehouse/;;HS;;White House Blog";
			lu += "\nhttp://feeds.feedburner.com/spdblotter?format=xml;;HS;;SPD Blotter";
			// lu +=
			// "\nhttp://www.trumba.com/calendars/seattlegov-city-wide.rss";
			// lu +=
			// "\nfeed://www.seashepherd.org/news-and-media/sea-shepherd-news/feed/rss.html;;HS;;Sea Shepherd";
			lu += "\nhttp://hosted.ap.org/lineups/TOPHEADS-rss_2.0.xml?SITE=ALMON&SECTION=HOME";
			// lu +=
			// "\nfeed://www.seashepherd.fr/news-and-media/sea-shepherd-news/feed/rss.html;;HS;;Sea Shepherd France";
			// lu +=
			// "\nfeed://www.seashepherd.it/news-and-media/sea-shepherd-news/feed/rss.html;;HS;;Sea Shepherd Italy";
			// lu +=
			// "\nfeed://www.seashepherd.nl/news-and-media/sea-shepherd-news/feed/rss.html;;HS;;Sea Shepherd Netherlands";
			// lu +=
			// "\nfeed://www.seashepherd.be/news-and-media/sea-shepherd-news/feed/rss.html;;HS;;Sea Shepherd Belgium";
			// lu += "\nhttp://www.thingiverse.com/newest";
			// lu += "\nhttp://blog.makezine.com/index.xml";
			// lu +=
			// "\nhttp://www.instructables.com/tag/type-id/pro-true/rss.xml";
			// lu += "\nhttp://www.dealextreme.com/rss.dx/just.released";
			// lu +=
			// "\nhttp://www.instructables.com/id/Laser-cutter-start-slicing-stuff-for-under-50-dol/?ALLSTEPS";

			lu += "\nhttp://seattle.craigslist.org/search/apa/see?query=yard+walk&srchType=A&minAsk=&maxAsk=1800&bedrooms=&addThree=wooof&format=rss";

			String[] bh = lu.trim().split("\n");

			for (int j = 0; j < bh.length; j++) {
				String[] h9 = bh[j].split(";;HS;;");
				String h8 = h9[0];
				String h10 = h9.length == 2 ? h9[1] : "";

				if (h8.length() == 0) {
					continue;
				}

				if (!mReg.contains("s0" + j + 1)) {
					ElkRequest.add(h8, "", "", h10);
					mEdt.putLong("s0" + j + 1, System.currentTimeMillis());
					mEdt.commit();
				}
			}

			s01p02.sendEmptyMessageDelayed(1, 75);

		}

	};

	Handler s01p02 = new Handler() {

		public void handleMessage(Message msg) {
			Log.i(G, "s01p02 good xxx");
			Bundle bdl = msg.getData();

			try {
				Intent checkIntent = new Intent();
				checkIntent
						.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
				startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

				// xape("Specify Confirm Authorization",
				// TextToSpeech.QUEUE_ADD);

			} catch (ActivityNotFoundException sd) {
				Toast.makeText(mCtx, "Voice module not found", 2385).show();

				Log.e(G, "xxx voice");
				sd.printStackTrace();

			}

			xxvv.sendEmptyMessageDelayed(2, 2235);
			elkreq.sendEmptyMessageDelayed(2, 5800);

			/*
			 * { Message ml = new Message(); Bundle bl = new Bundle();
			 * ml.setData(bl); vstart.sendMessage(ml); } //
			 */
			// breath.sendEmptyMessageDelayed(1, 4200);

			s01p03.sendEmptyMessageDelayed(1, 75);

		}

	};

	Handler bkb01 = new Handler() {

		public void handleMessage(Message msg) {
			// buccoff
			RelativeLayout h8 = (RelativeLayout) findViewById(R.id.bkb);
			Animation nb8 = AnimationUtils.loadAnimation(mCtx, R.anim.fgom6);
			h8.startAnimation(nb8);

			bkb02.sendEmptyMessageDelayed(2, nb8.getDuration());

		}

	};

	Handler bkb02 = new Handler() {

		public void handleMessage(Message msg) {
			RelativeLayout h8 = (RelativeLayout) findViewById(R.id.bkb);
			h8.setVisibility(View.GONE);

			RelativeLayout h4 = (RelativeLayout) findViewById(R.id.bkb5);
			// ImageView bjd = (ImageView) findViewById(R.id.bkbd);
			Animation nb4 = AnimationUtils.loadAnimation(mCtx, R.anim.fgom);
			h4.startAnimation(nb4);

			bkb03.sendEmptyMessageDelayed(2, nb4.getDuration());

		}
	};

	Handler bkb03 = new Handler() {

		public void handleMessage(Message msg) {
			RelativeLayout h4 = (RelativeLayout) findViewById(R.id.bkb5);
			h4.setVisibility(View.GONE);

			RelativeLayout h5 = (RelativeLayout) findViewById(R.id.bkb4);
			// ImageView bj = (ImageView) findViewById(R.id.bkbj);
			Animation nb5 = AnimationUtils.loadAnimation(mCtx, R.anim.fgom);
			h5.startAnimation(nb5);

			bkb04.sendEmptyMessageDelayed(2, nb5.getDuration());

		}
	};

	Handler bkb04 = new Handler() {
		public void handleMessage(Message msg) {
			RelativeLayout h5 = (RelativeLayout) findViewById(R.id.bkb4);
			h5.setVisibility(View.GONE);

		}
	};

	int livemid = -1;
	long livea = -1;
	String livem = "";

	Handler sr = new Handler() {

		public void handleMessage(Message msg) {
			Bundle bdl = msg.getData();
			sr.removeMessages(2);
			Log.i(G, "sr good xxx");
			if (livea > System.currentTimeMillis() - 500) {
				return;
			}
			livea = System.currentTimeMillis();
			if (sroff) {
				return;
			}

			Thread xsd = new Thread() {
				public void run() {
					long hdate = System.currentTimeMillis();

					Cursor bh;
					SharedPreferences mReg = getSharedPreferences(
							"Preferences", MODE_WORLD_READABLE);
					Editor mEdt = mReg.edit();

					String mhdate = mReg
							.getString(
									"lastconsole",
									datetime(System.currentTimeMillis() - 24 * 60 * 60000));

					for (int bbx = 14; bbx < 20; bbx++) {
						int cx = 0;
						// Log.i(G,
						// "sr xxx " + bbx + " -- "
						// + datetime(System.currentTimeMillis()));
						try {

							bh = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "title", "foundtitle", "published", "author", "url", "length(content)", "_id", "updated", "status" }, "mid is null AND updated > '" + mhdate + "'", null, "updated asc");

							if (bh == null) {
								continue;
							}
							cx = bh.getCount();
							// Log.i(G, "sr xxx " + cx);

							// if (bh.moveToFirst()) {
							for (int xiv = 0; bh.moveToPosition(xiv); xiv++) {
								String title = bh.getString(0);
								String titlen = bh.getString(1);
								if (titlen == null
										|| titlen.contentEquals(":)")) {
									titlen = "";
								}
								if (title.length() == 0) {
									continue;
								}

								int mid = bh.getInt(6);
								String ud = bh.getString(7);
								int status = bh.getInt(8);
								mhdate = ud;
								mEdt.putString("lastconsole", mhdate);
								mEdt.commit();

								if (mid != livemid
										|| !title.contentEquals(livem)) {
									livemid = mid;
									livem = title;
									// if (bh.getCount() == 2) {
									// }
									Log.i(G, "console xxx " + title + " "
											+ titlen + " -- " + mhdate);
									String publ = bh.getString(2);
									String auth = bh.getString(3);
									String urlu = bh.getString(4);
									if (urlu == null) {
										urlu = "";
									}

									// titlen = titlen.replaceAll(":.*", "");
									if (title.length() == 0) {
										continue;
									}

									if (title.matches("[0-9]+ found")) {
										// ElkRequest.updateM(13, "", mid);
										// pagelink(mid, 5, true, true);//
										// pagelink
										// then
										// load
										// first
										// continue;
										// miesr(-1, mid, true, false, 23);
									}

									urlu = urlu.replaceFirst(".*?://", "")
											.replaceFirst("/.*", "");

									if (title.contentEquals("Update")
											&& status == 1) {
										elkreq.sendEmptyMessageDelayed(2, 75);
										String xg = (titlen.length() > 0 ? titlen
												+ "\n"
												: "")
												+ (urlu.length() > 0 ? urlu
														+ "\n" : "")
												+ title.trim();

										// setr(xg, 75 + xiv);// (xiv + 1) *

									} else {

										int sz = bh.getInt(5);

										// livem = title;
										// if (!livem.contains("%")) {
										// livem.replaceAll("%.*", "").trim();
										// }
										// livemid = mid;
										// livea = SystemClock.uptimeMillis();

										String xg = (titlen.length() > 0 ? titlen
												: urlu)
												+ "\n" + title.trim();

										// setr(xg, 75 + xiv);// (xiv + 1) *
									}
									// 850);

									// if (sz > 0 && (livem.matches(""))) {
									// miesr(-1, mid, true, false);
									// }

									// break;

								}
							}
							bh.close();

						} catch (IllegalStateException eb) {
							Log.e(G, eb.getLocalizedMessage());
							eb.printStackTrace();
						}

						if ((cx == 0 && bbx > 14) || cx > 0) {
							// if (cx > 0 && bbx > 14) {
							// bbx = 14;
							// }
							try {
								Thread.sleep(300);
							} catch (InterruptedException sf) {
								sf.printStackTrace();
								break;
							}
							continue;
						}

						break;

					}

					sr.sendEmptyMessageDelayed(2, 2225);

				}
			};

			xsd.start();
			// } catch (IllegalThreadStateException k6) {
			// k6.printStackTrace();
			// }

		}

	};

	public String datetime(long xe) {

		String g = "";
		Date d = new Date(xe);
		g = (d.getYear() + 1900) + "-" + ((d.getMonth() < 9) ? "0" : "")
				+ ((d.getMonth() + 1)) + "-" + ((d.getDate() < 10) ? "0" : "")
				+ d.getDate() + " " + ((d.getHours() < 10) ? "0" : "")
				+ d.getHours() + ":" + ((d.getMinutes() < 10) ? "0" : "")
				+ d.getMinutes() + ":" + ((d.getSeconds() < 10) ? "0" : "")
				+ d.getSeconds();

		// Log.i(G, "generated date " + g);
		return g;
	}

	RelativeLayout chb;
	RelativeLayout chs;
	ImageView bec;

	public void addx(int mid, int scub) {
		Log.i(G, "addx good xxx");
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putInt("scub", scub);
		bl.putInt("mid", mid);
		ml.setData(bl);

		HandlerThread tx = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
		tx.start();
		Handler getimagel = new getimagel(tx.getLooper());
		getimagel.sendMessageDelayed(ml, 2750);

	}

	public void addxl(int mid, int lid) {
		Log.i(G, "addxl good xxx");
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putInt("lid", lid);
		bl.putInt("mid", mid);
		ml.setData(bl);

		HandlerThread tx = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
		tx.start();
		Handler getimagel = new getimagel(tx.getLooper());
		getimagel.sendMessageDelayed(ml, 3750);

	}

	public void addxrp(int mid, int box) {
		Log.i(G, "addxrp good xxx");
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putInt("box", box);
		bl.putInt("mid", mid);
		ml.setData(bl);

		HandlerThread tx = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
		tx.start();
		Handler getimagel = new getimagel(tx.getLooper());
		getimagel.sendMessageDelayed(ml, 5750);

	}

	public void addxr(int mid, int bos) {
		Log.i(G, "addxr good xxx");
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putInt("bos", bos);
		bl.putInt("mid", mid);
		ml.setData(bl);

		HandlerThread tx = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
		tx.start();
		Handler getimagel = new getimagel(tx.getLooper());
		getimagel.sendMessageDelayed(ml, 4750);

	}

	public void addx(String rcontent, int mid, int huid) {

		ContentValues cx = new ContentValues();
		cx.put("content", rcontent);
		cx.put("mid", mid);
		Uri xuri = SqliteWrapper.insert(mCtx, mCtx.getContentResolver(), Uri.parse("content://com.havenskys.elk/xsaw"), cx);
		int xid = xuri.getLastPathSegment().matches("[0-9]+") ? Integer
				.parseInt(xuri.getLastPathSegment()) : -1;
		Log.i(G, "addx good xxx Created " + xid + " " + xuri.toString());
		// bl.putInt("xid", xid);

		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putInt("huid", huid);
		bl.putInt("mid", mid);
		ml.setData(bl);

		HandlerThread tx = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
		tx.start();
		Handler getimagel = new getimagel(tx.getLooper());
		getimagel.sendMessageDelayed(ml, 2750);

	}

	class getimagel extends Handler {
		public getimagel(Looper ln) {
			super(ln);
		}

		public void handleMessage(Message msg) {
			Log.i(G, "getimagel good xxx");
			Bundle bdl = msg.getData();
			// int xid = bdl.getInt("xid");
			int lid = bdl.getInt("lid", -1);
			int bos = bdl.getInt("bos", -1);
			int box = bdl.getInt("box", -1);
			int scub = bdl.getInt("scub", -1);
			int mid = bdl.getInt("mid");

			// if (huid == R.id.cub) {
			// buc.setVisibility(View.VISIBLE);
			// bucx.setVisibility(View.VISIBLE);
			// } else if (huid == R.id.cubx) {
			// buc.setVisibility(View.VISIBLE);
			// bucx.setVisibility(View.GONE);
			// }

			// LinearLayout hu = (LinearLayout) findViewById(huid);

			try {

				if (lid > -1) {
					LinearLayout hu = (LinearLayout) findViewById(lid);
					if (hu != null) {
						hu.setDrawingCacheEnabled(true);
						hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
						hu.postInvalidate();
					}
				} else if (box > -1) {
					LinearLayout hul = (LinearLayout) findViewById(box);
					RelativeLayout hu = (RelativeLayout) hul.getParent();
					hu.setDrawingCacheEnabled(true);
					hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
					hu.postInvalidate();

				} else if (bos > -1) {
					RelativeLayout hu = (RelativeLayout) findViewById(bos);
					hu.setDrawingCacheEnabled(true);
					hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
					hu.postInvalidate();

				} else if (scub > -1) {
					ScrollView hu = (ScrollView) findViewById(scub);
					hu.setDrawingCacheEnabled(true);
					hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
					hu.postInvalidate();
				}

			} catch (ClassCastException jks) {
				jks.printStackTrace();
				return;
			}
			// Bitmap xt;
			// xt = hu.getDrawingCache();

			Message ml = new Message();
			Bundle bl = new Bundle();
			bl.putInt("scub", scub);
			bl.putInt("lid", lid);
			bl.putInt("bos", bos);
			bl.putInt("box", box);
			bl.putInt("mid", mid);
			ml.setData(bl);
			getximage.sendMessageDelayed(ml, 175);

		}
	};

	class getimage extends Handler {
		public getimage(Looper ln) {
			super(ln);
		}

		public void handleMessage(Message msg) {
			Log.i(G, "getimage good xxx");
			Bundle bdl = msg.getData();
			// int xid = bdl.getInt("xid");
			int scub = bdl.getInt("scub");
			int mid = bdl.getInt("mid");

			// if (huid == R.id.cub) {
			// buc.setVisibility(View.VISIBLE);
			// bucx.setVisibility(View.VISIBLE);
			// } else if (huid == R.id.cubx) {
			// buc.setVisibility(View.VISIBLE);
			// bucx.setVisibility(View.GONE);
			// }

			// LinearLayout hu = (LinearLayout) findViewById(huid);
			ScrollView hu = (ScrollView) findViewById(scub);
			hu.setDrawingCacheEnabled(true);
			hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
			hu.postInvalidate();

			// Bitmap xt;
			// xt = hu.getDrawingCache();

			Message ml = new Message();
			Bundle bl = new Bundle();
			// bl.putInt("xid", xid);
			bl.putInt("scub", scub);
			bl.putInt("mid", mid);
			ml.setData(bl);
			getximage.sendMessageDelayed(ml, 175);

		}
	};

	RelativeLayout base;

	Handler emailshow = new Handler() {
		public void handleMessage(Message msg) {

			Log.i(G, "emailshow good xxx");
			Bitmap xt2 = null;
			base.setDrawingCacheEnabled(true);
			base.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
			// base.postInvalidate();
			xt2 = base.getDrawingCache();
			Bitmap xt = null;
			if (xt2 != null) {
				try {
					xt = Bitmap.createBitmap(xt2);
				} catch (OutOfMemoryError em) {
					xt = null;
					em.printStackTrace();
				}

			}

			// ByteArrayOutputStream st = new ByteArrayOutputStream();
			// xt.compress(CompressFormat.PNG, 0, st);

			// byte[] g8;
			// g8 = st.toByteArray();
			// xt = BitmapFactory.decodeByteArray(g8, 0, g8.length);

			// if (xt == null) {
			// Log.e(G,
			// "Bitmap disappeared " + g8.length + " was " + st.size());
			// }

			// if
			// (!Environment.getExternalStorageState().contentEquals("mounted"))
			// {
			{

				Log.i(G,
						"emailshow xxx "
								+ Environment.getExternalStorageState());
				FileOutputStream fs = null;

				String filename = "senda.png";
				// File filea = new File("/Android"
				// + Environment.getDownloadCacheDirectory()
				// .getAbsolutePath(), mCtx.getPackageName()
				// + ".app");
				File filea = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath(), mCtx.getPackageName());

				filea.mkdirs();
				File file = new File(filea.getAbsolutePath(), filename);

				try {
					fs = new FileOutputStream(file);
				} catch (FileNotFoundException e) {
					setr("Drat Disk " + e.getLocalizedMessage() + "\nDisk: "
							+ Environment.getExternalStorageState());
					e.printStackTrace();
				} finally {
					// Drawable db =
					// Drawable.createFromPath(file.getAbsolutePath()+"/"+filename);
					// xt.compress(CompressFormat.PNG, 0, st);

					if (xt != null && fs != null) {
						xt.compress(Bitmap.CompressFormat.PNG, 0, fs);

						try {
							fs.flush();
						} catch (IOException e1) {
							Log.e(G, "ComputerStart() 1528 fs.flush() failed");
							e1.printStackTrace();
						}
						try {
							fs.close();
						} catch (IOException e1) {
							Log.e(G, "ComputerStart() 1534 fs.close() failed");
							e1.printStackTrace();
						}

						File file2 = new File(filea.getAbsolutePath(), filename);

						Log.i(G, "Email file " + file2.getAbsolutePath());
						Intent hj = new Intent(Intent.ACTION_SEND);
						hj.putExtra(Intent.EXTRA_TEXT, "\n\n\n\n");
						hj.putExtra(Intent.EXTRA_SUBJECT, "Elk");
						hj.putExtra(Intent.EXTRA_STREAM,
								Uri.parse("file://" + file2.getAbsolutePath()));
						hj.setType("message/rfc822");
						startActivity(Intent.createChooser(hj, "Email"));
					}

				}
			}

		}
	};

	Handler getximage = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "getximage good xxx");
			Bundle bdl = msg.getData();
			// final int xid = bdl.getInt("xid");
			int scub = bdl.getInt("scub", -1);
			int lid = bdl.getInt("lid", -1);
			int bos = bdl.getInt("bos", -1);
			int box = bdl.getInt("box", -1);
			final int mid = bdl.getInt("mid");

			Bitmap xt = null;
			{ // project gem, "sample" here to fill xt
				Cursor e = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "length(sample)" }, "_id =" + mid + " and sample is not null", null, null);

				if (e != null && e.moveToFirst()) {
					// if () {
					// byte[] g8 = e.getBlob(0);
					// if (g8 != null) {
					// xt = BitmapFactory
					// .decodeByteArray(g8, 0, g8.length);

					Log.i(G, "good xxx getximage " + e.getInt(0));

					e.close();
				}
			}

			if (xt == null) {
				// Bitmap xt2 = null;
				try {

					if (lid > -1) {

						LinearLayout hu = (LinearLayout) findViewById(lid);
						if (hu != null) {
							hu.setDrawingCacheEnabled(true);
							hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
							// hu.postInvalidate();
							xt = hu.getDrawingCache();
						}
					} else if (bos > -1) {
						RelativeLayout hu = (RelativeLayout) findViewById(R.id.cvxh);
						hu.setDrawingCacheEnabled(true);
						hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
						// hu.postInvalidate();
						xt = hu.getDrawingCache();

					} else if (box > -1) {
						LinearLayout hul = (LinearLayout) findViewById(box);
						RelativeLayout hu = (RelativeLayout) hul.getParent();
						hu.setDrawingCacheEnabled(true);
						hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
						// hu.postInvalidate();
						xt = hu.getDrawingCache();

					} else {
						ScrollView hu = (ScrollView) findViewById(scub);
						hu.setDrawingCacheEnabled(true);
						hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
						// hu.postInvalidate();
						xt = hu.getDrawingCache();
					}

				} catch (ClassCastException jxk) {
					jxk.printStackTrace();
					return;
				}

				if (xt != null) {
					// project gem, get xt from "sample" with mid

					Bitmap xt2 = null;
					try {
						xt2 = Bitmap.createBitmap(xt);

					} catch (OutOfMemoryError em) {
						xt = null;
						em.printStackTrace();
					} finally {
						xt = xt2;
						xt2 = null;
					}

					// Parcel hp = null;
					ContentValues e = new ContentValues();
					// xt.writeToParcel(hp, 0);

					ByteArrayOutputStream st;

					st = new ByteArrayOutputStream();

					if (st != null) {
						xt.compress(CompressFormat.PNG, 0, st);
					}

					byte[] g8;
					// BitmapFactory e4 = new BitmapFactory();
					try {

						e.put("title", "");
						e.put("sample", st.toByteArray());// .toString(Encoding.UTF_16.name()));
						// String ti = st.toString(Encoding.UTF_16.name());
						// g8 = st.toByteArray();//
						// ti.getBytes(Encoding.UTF_16.name());
						// g8 = e.getAsString("sample").getBytes(
						// Encoding.UTF_16.name());
						g8 = e.getAsByteArray("sample");
						xt = BitmapFactory.decodeByteArray(g8, 0, g8.length);

						if (xt == null) {
							Log.e(G, "Bitmap disappeared " + g8.length
									+ " was " + st.size());
						}

					} catch (OutOfMemoryError em2) {
						em2.printStackTrace();
						// } catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						// e1.printStackTrace();
					} finally {

					}
					// e4.decodeByteArray(data, offset, length);

					// Bitmap xw = new Bitmap().

					SqliteWrapper.update(mCtx, mCtx.getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), e, "_id=" + mid, null);

				}
			}

			if (new String("xxx").length() == 0) {
				Log.e(G, "skipping placing image on screen");
			} else {
				{
					// RelativeLayout nbsg = (RelativeLayout)
					// findViewById(R.id.nbsg);

					ImageView x9 = null;
					// (nbsg.getWidth() - 75)
					for (int i = 1, b = ldx.getChildCount() - 1; b >= 0; i++, b--) {
						// pagelink
						x9 = (ImageView) ldx.getChildAt(b);

						if (x9.getVisibility() == View.VISIBLE) {
							// x9.setVisibility(View.GONE);
						}

						// RelativeLayout.LayoutParams uu4 = new
						// RelativeLayout.LayoutParams(
						// 75, 75);
						// uu4.setMargins((nbsg.getWidth() - 75) / 2, 0, 3, 5);
						// uu4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
						// x9.setLayoutParams(uu4);
						// nbsg
					}

				}
				// buccimg
				RelativeLayout.LayoutParams uu4 = new RelativeLayout.LayoutParams(
						75, getBottomwide() - 17);
				// uu4.addRule(RelativeLayout.CENTER_IN_PARENT);
				ImageView x9 = null;

				// RelativeLayout x8 = new RelativeLayout(mCtx);
				x9 = new ImageView(mCtx);

				// RelativeLayout nbsg = (RelativeLayout)
				// findViewById(R.id.nbsg);
				// RelativeLayout nbsu = (RelativeLayout)
				// findViewById(R.id.nbsu);
				// ImageView x9 = null;

				// nbb.getWidth() +

				// ldx
				// uu4.setMargins(
				// nbb.getWidth() + nbsu.getWidth()
				// + (nbsg.getWidth() - 35), 0, 3, 5);
				// uu4.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				// uu4.setMargins(6, 6, 3, 6);
				x9.setPadding(3, 3, 3, 3);
				x9.setLayoutParams(uu4);

				x9.setScaleType(ScaleType.FIT_XY);
				// x9.setPadding(90 * ++cdi + 1, 0, 0, 0);
				x9.setId(hid++);

				x9.setOnClickListener(new OnClickListener() {
					public void onClick(View gv) {
						Log.i(G, "getximage click good xxx");
						ImageView g = (ImageView) gv;

						Animation nb8 = AnimationUtils.loadAnimation(mCtx,
								R.anim.oh);
						// g.clearAnimation();
						g.startAnimation(nb8);
						buccoff.sendEmptyMessageDelayed(2, 1);

						// pagelink(mid, 25, false, false);
						miesr(-1, mid, false, false, 25);

					}
				});

				if (xt != null) {
					// x8.setBackgroundDrawable();
					x9.setImageBitmap(xt);
					// hu.postInvalidateDelayed(75);

				} else if (mid < -50) {
					FileOutputStream fs = null;

					String filename = "m" + mid + ".png";
					File filea = new File(Environment
							.getExternalStorageDirectory().getAbsolutePath(),
							"elk/xsaw");
					filea.mkdirs();
					File file = new File(filea.getAbsolutePath(), filename);

					try {
						fs = new FileOutputStream(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					// Drawable db =
					// Drawable.createFromPath(file.getAbsolutePath()+"/"+filename);

					xt.compress(Bitmap.CompressFormat.PNG, 0, fs);

					try {
						fs.flush();
					} catch (IOException e1) {
						Log.e(G, "ComputerStart() 1528 fs.flush() failed");
						e1.printStackTrace();
					}
					try {
						fs.close();
					} catch (IOException e1) {
						Log.e(G, "ComputerStart() 1534 fs.close() failed");
						e1.printStackTrace();
					}
					Drawable u9 = null;
					try {
						u9 = Drawable.createFromPath(file.getAbsolutePath());
						x9.setImageDrawable(u9);
					} catch (OutOfMemoryError em) {
						em.printStackTrace();
						u9 = null;
					} finally {
						if (u9 == null) {
							x9.setImageResource(R.drawable.ld4);
						}
					}

				} else {
					x9.setImageResource(R.drawable.ld5);
				}

				// x9.setBackgroundResource(R.drawable.bx5);
				// size ldx.setLay
				ldx.addView(x9, 0);

				{
					Animation nb8 = AnimationUtils.loadAnimation(mCtx,
							R.anim.xgom4);
					nb8.setStartOffset(1375);
					x9.startAnimation(nb8);
					// msu fitco nb9
				}
				// nbs.addView(x9);

			}

		}
	};
	Animation na;
	Handler chbex = new Handler() {
		public void handleMessage(Message msg) {
			Bundle bdl = msg.getData();
			Log.i(G, "chbex good xxx");
			// init
			// chs = (RelativeLayout) findViewById(R.id.chs);
			// bec = (ImageView) findViewById(R.id.bec);

			float x = bdl.getFloat("xx");
			float x4 = bdl.getFloat("x4");
			// int tv = bdl.getInt("tv");

			// Log.i(G, "chbex(" + x + "," + x4 + ")");

			// chs.setPadding(0, (int) (x4), 0, 0);

			// RelativeLayout.LayoutParams gk = (RelativeLayout.LayoutParams)
			// chs
			// .getLayoutParams();
			RelativeLayout.LayoutParams gk = new RelativeLayout.LayoutParams(
					-1, 88 * 2);
			gk.setMargins(0, (int) x4 - 120, 0, 0);
			chs.setLayoutParams(gk);
			bec.setPadding((int) (x - 60), 0, 0, 0);

		}
	};

	Handler elkreq = new Handler() {

		long elt = -1;

		public void handleMessage(Message msg) {
			Bundle bdl = msg.getData();
			Log.i(G, "elkreq good xxx");

			if (elt > System.currentTimeMillis() - 800) {
				return;
			}
			elt = System.currentTimeMillis();
			// Anima
			// nb4.clearAnimation();
			Animation nxh = AnimationUtils.loadAnimation(mCtx, R.anim.nxh);
			nbdd.startAnimation(nxh);
			// requestrun.sendEmptyMessageDelayed(2, 75);

			{

				// w(TAG,"getPage() get ConnectivityManager");
				ConnectivityManager cnnm = (ConnectivityManager) mCtx
						.getSystemService(mCtx.CONNECTIVITY_SERVICE);
				// w(TAG,"getPage() get NetworkInfo");
				if (cnnm == null) {
					elkreq.sendEmptyMessageDelayed(2, 130000);
					Log.w(G, "Network Off");
					setr("Network Off");
					return;
				}

				NetworkInfo ninfo = cnnm.getActiveNetworkInfo();
				if (ninfo == null) {
					elkreq.sendEmptyMessageDelayed(2, 130000);
					Log.w(G, "Network Off");
					setr("Network Off");
					return;
				} else {
					// setr("Network " + ninfo.getState().ordinal() + " "
					// + ninfo.getState().name());
					Log.w(G,
							"NetworkInfo state(" + ninfo.getState().ordinal()
									+ ") name(" + ninfo.getState().name()
									+ ") " + ninfo.getTypeName() + ": "
									+ ninfo.getSubtypeName());

				}
				// android.os.Process.getElapsedCpuTime()

			}

			elkreq.removeMessages(2);

			ElkRequest.Count();
			ElkRequest.scan();
			if (ElkRequest.length > 0) {
				Log.i(G, "elkreq good " + ElkRequest.length);
				// elkreq.removeMessages(2);

				elkreq.sendEmptyMessageDelayed(2, 10000);
				ElkRequest.breader();

			} else {
				elkreq.sendEmptyMessageDelayed(2, 30000);
			}

		}

	};

	int mMID = -1;

	Handler showmid = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "showmid good xxx");
			// RelativeLayout g = (RelativeLayout) findViewById(msg.what);
			// int p = getListView().getPositionForView(g);

			// int mMID2 = (int) getListView().getItemIdAtPosition(p);

			Toast.makeText(mCtx, "scg " + mMID + "->" + "(" + msg.what + ")",
					2385).show();

			mMID = msg.what;
		}
	};

	OnFocusChangeListener ustux = new OnFocusChangeListener() {

		public void onFocusChange(View g, boolean has) {
			Log.i(G, "ustux good focus xxx");
			RelativeLayout xj = (RelativeLayout) g;
			if (!has) {
				xj.setBackgroundResource(R.drawable.c2);
			} else {
				xj.setBackgroundResource(R.drawable.c3);
			}
		}
	};

	Handler xgxhbatch = new Handler() {
		long x92 = -1;

		public void handleMessage(Message msg) {
			// if (buc.getVisibility() != View.VISIBLE
			// && bucc.getVisibility() != View.VISIBLE
			// && msu.getVisibility() != View.VISIBLE) {
			// }
			Log.i(G, "xgxhbatch good xxx");
			if (x92 > SystemClock.uptimeMillis() - 150) {
				return;
			}
			// sr.sendEmptyMessageDelayed(2, 25);

			x92 = SystemClock.uptimeMillis();
			sizekln.sendEmptyMessageDelayed(2, 45);
			for (int ce = 0; ce < getListView().getChildCount(); ce++) {

				RelativeLayout rl = null;
				rl = (RelativeLayout) getListView().getChildAt(ce);
				if (rl == null) {
					Log.e(G, "xgxhbatch");
					continue;
				}

				// if (rl.getId() > hid - 99999 && rl.getId() < hid + 99999) {
				rl.setId(hid++);
				// }
				getListView().setDrawingCacheEnabled(false);

				Message ml = new Message();
				Bundle bl = new Bundle();
				bl.putInt("id", rl.getId());
				bl.putInt("ido", ce);
				// bl.putInt("mid", (int)
				// getListView().getItemIdAtPosition(ce));
				ml.setData(bl);
				// xgxh.sendMessageDelayed(ml, 85 + ce);

			}

			// xgxhbatch.removeMessages(2);
			// xgxhbatch.sendEmptyMessageDelayed(2, 2768);
			if (sroff) {
				return;
			}

		}
	};

	// Handler xgxhbatch;

	Handler xgxh = new Handler() {
		// class xgxh extends Handler {

		// public xgxh(Looper x) {
		// super(x);
		// }

		public void handleMessage(Message msg) {
			Log.i(G, "xgxh good xxx");
			Bundle bdl = msg.getData();
			int id = bdl.getInt("id");
			final int ido = bdl.getInt("ido");
			RelativeLayout rl = (RelativeLayout) findViewById(id);
			if (rl == null) {
				Log.e(G, "xgxh1");
				return;
			}

			TextView tmi = (TextView) rl.getChildAt(6);
			int tmx = Integer.parseInt(tmi.getText().toString().trim());
			final int mid = tmx;
			if (lMID == tmx) {
				// RelativeLayout gt = (RelativeLayout) g;
				// rl.startAnimation(boh);

				// getListView().setSelection(ido);
				rl.setBackgroundColor(Color.argb(145, 3, 16, 138));
				// rl.requestFocusFromTouch();

			} else {
				rl.setBackgroundColor(Color.argb(140, 3, 16, 38));

			}
			// batch
			TextView ti = (TextView) rl.getChildAt(2);
			if (ti == null) {
				Log.e(G, "xgxh2");
				return;
			}
			// ti.setId(hid++);
			TextView ti2 = (TextView) rl.getChildAt(6);
			// ti2.setId(hid++);
			TextView ut = (TextView) rl.getChildAt(0);
			// ut.setId(hid++);
			TextView t1 = (TextView) rl.getChildAt(1);
			// t1.setId(hid++);
			// ut.setVisibility(View.GONE);
			// ti2.setVisibility(View.VISIBLE);

			String xw4 = ti.getText().toString();
			String xw2 = ut.getText().toString();
			String wx3 = t1.getText().toString();
			t1.setTextSize((float) 11);
			t1.setLines(3);

			if (wx3.length() == 0) {
				xw2 = xw2.replaceFirst(".*?://", "");
				xw2 = xw2.replaceFirst("/.*", "");
				xw2 = xw2.replaceFirst("www.", "");
				t1.setText(xw2);

			}

		}
	};

	Handler almm = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "almm good xxx");
			int xx = msg.what;

			RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
					nba.getWidth() + xx, -1);

			// hn.setMargins(0, xs, 0, xs);
			hn.addRule(RelativeLayout.BELOW, R.id.nba);
			hn.addRule(RelativeLayout.ABOVE, R.id.nbb);
			mHu.setLayoutParams(hn);

		}
	};

	Handler midset = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "midset good xxx");
			// possiblem = -1;
			if (mMID == msg.what) {
				return;
			}
			Log.i(G, "midset good " + msg.what);

			mMID = msg.what;

			ContentValues e = new ContentValues();
			e.put("visited", datetime(System.currentTimeMillis()));
			SqliteWrapper.update(mCtx, mCtx.getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), e, "_id=" + mMID, null);

		}
	};

	Handler midlset = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "midlset good xxx");
			lMID = msg.what;
			xgxhbatch.sendEmptyMessageDelayed(2, 5);

			ContentValues e = new ContentValues();
			e.put("visited", datetime(System.currentTimeMillis()));
			SqliteWrapper.update(mCtx, mCtx.getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), e, "_id=" + lMID, null);

		}
	};

	Handler vstart = new Handler() {

		Cursor lCursor;

		public void handleMessage(Message msg) {
			Log.i(G, "vstart good xxx");
			Bundle bdl = msg.getData();

			// "strftime('%Y/%m/%d %H:%M',published) as published",
			String[] columns = new String[] { "_id", "foundtitle", "title",
					"url", "status", "images" };
			String[] from = new String[] { "url", "foundtitle", "title",
					"title", "status", "images", "_id" };
			int[] to = new int[] { R.id.listrow_url, R.id.listrow_titlen,
					R.id.listrow_title, R.id.listrow_titledd,
					R.id.listrow_status, R.id.listrow_images, R.id.listrow_id };

			lCursor = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), columns, "status > 0 AND updatemin > 0", null, "status desc,visited");
			// 0,"+pagesize );// + startrow + ","
			// + numrows

			startManagingCursor(lCursor);

			mHu.setVisibility(View.INVISIBLE);

			SimpleCursorAdapter entries = new SimpleCursorAdapter(mCtx,
					R.layout.listrow, lCursor, from, to);

			// FrameLayout.LayoutParams hn = (FrameLayout.LayoutParams)
			// getListView()
			// .getLayoutParams();
			// hn.setMargins(88, 88, (int) (mWidth / 3 > 240 ? 240 : mWidth /
			// 3),
			// (int) ((mHeight - 100) / 3));
			// getListView().setLayoutParams(hn);

			RelativeLayout vf = new RelativeLayout(mCtx);
			vf.setId(hid++);
			vf.setLayoutParams(new ListView.LayoutParams(-1, mHeight));
			vf.setBackgroundColor(Color.argb(245, 8, 48, 114));
			// getListView().addFooterView(vf, null, false);
			{
				TextView t1 = new TextView(mCtx);
				RelativeLayout.LayoutParams t1r = new RelativeLayout.LayoutParams(
						-1, -2);
				t1.setLayoutParams(t1r);
				t1.setId(hid++);
				t1.setTextSize((float) 24);
				t1.setGravity(Gravity.CENTER_VERTICAL);
				t1.setTextColor(Color.argb(255, 0, 0, 0));
				t1.setGravity(Gravity.CENTER);
				t1.setPadding(7, 13, 7, 13);
				// t1.setText("Play");
				// t1.setBackgroundResource(R.drawable.c2);
				// vf.addView(t1);
			}

			RelativeLayout vh = new RelativeLayout(mCtx);
			vh.setId(hid++);
			vh.setLayoutParams(new ListView.LayoutParams(-1, -2));
			vh.setBackgroundColor(Color.argb(245, 8, 48, 114));
			// getListView().addHeaderView(vh, null, true);
			{
				TextView t1 = new TextView(mCtx);
				RelativeLayout.LayoutParams t1r = new RelativeLayout.LayoutParams(
						-1, -2);
				t1.setLayoutParams(t1r);
				t1.setId(hid++);
				t1.setTextSize((float) 56);
				// t1.setGravity(Gravity.CENTER_VERTICAL);
				t1.setTextColor(Color.argb(255, 8, 180, 255));
				// t1.setGravity(Gravity.CENTER);
				// t1.setPadding(7, 13, 7, 13);
				t1.setText("+");
				// vh.addView(t1);
			}

			setListAdapter(entries);
			// getListView().setSelector(R.drawable.c3);
			// getListView().setBackgroundColor(Color.argb(120, 0, 102, 255));
			// getListView().requestFocusFromTouch();
			getListView().setBackgroundColor(Color.argb(0, 0, 64, 171));
			mHu.setBackgroundColor(Color.argb(0, 0, 64, 171));

			registerForContextMenu(getListView());

			getListView().setDrawingCacheEnabled(false);
			// getListView().setOnItemSelectedListener(this);

			OnItemLongClickListener hugx = new OnItemLongClickListener() {

				public boolean onItemLongClick(AdapterView<?> a, View g,
						int position, long rid) {
					Log.i(G, "hugx good xxx " + rid);
					lMID = (int) rid;

					RelativeLayout gt = (RelativeLayout) g;
					// gt.clearAnimation();
					gt.startAnimation(boh);
					midlset.sendEmptyMessageDelayed((int) rid, 2);

					return false;
				}
			};

			OnItemClickListener hug = new OnItemClickListener() {
				Animation bohx4 = AnimationUtils
						.loadAnimation(mCtx, R.anim.oh7);

				public void onItemClick(AdapterView<?> a, View g, int p,
						long rid) {

					// int p = getListView().getPositionForView(g);
					// Log.i(G, "hug good xxx " + rid + " " + p);

					// midset.sendEmptyMessageDelayed((int) rid, 2);
					// getListView().performItemClick(g, p, rid);
					Log.i(G, "hug good " + rid);

					// cvboff.sendEmptyMessageDelayed(2, 25);
					// xgxhbatch.sendEmptyMessageDelayed(2, 5);
					buccoff.sendEmptyMessageDelayed(2, 5);

					RelativeLayout gt = (RelativeLayout) g;// getListView().getChildAt(p);
					if (gt == null) {
						Log.e(G, "hug gt");
						return;
					}
					// Ani
					// gt.clearAnimation();
					gt.startAnimation(bohx4);// boh
					// midset.sendEmptyMessageDelayed((int) rid, 2);

					// sizekln.sendEmptyMessageDelayed(2, 5);
					// INVISIBLE
					Message ml = new Message();
					Bundle bl = new Bundle();
					bl.putInt("id", (int) rid);
					bl.putInt("vid", g.getId());
					bl.putInt("p", p);
					ml.setData(bl);

					icl.sendMessageDelayed(ml, 5);

				}
			};

			OnItemSelectedListener hon = new OnItemSelectedListener() {
				Animation bohx2 = AnimationUtils
						.loadAnimation(mCtx, R.anim.oh6);

				public void onItemSelected(AdapterView<?> j, View b, int p,
						long d) {

					Log.i(G, "hon good xxx " + d);
					// midset.sendEmptyMessageDelayed((int) d, 2);

					RelativeLayout g = (RelativeLayout) b;
					// g.setBackgroundColor(Color.argb(150, 8, 78, 182));
					// g.clearAnimation();
					g.startAnimation(bohx2);
					// xgxhbatch.sendEmptyMessageDelayed(2, 75);

				}

				public void onNothingSelected(AdapterView<?> arg0) {
					Log.i(G, "honnon good xxx");
					midset.sendEmptyMessageDelayed(-1, 75);

				}
			};

			getListView().setOnItemClickListener(hug);
			getListView().setOnItemLongClickListener(hugx);

			getListView().setOnItemSelectedListener(hon);
			// getListView().setItemsCanFocus(true);
			// getListView().setHapticFeedbackEnabled(true);

			/*
			 * getListView().setOnCreateContextMenuListener( new
			 * OnCreateContextMenuListener() {
			 * 
			 * public void onCreateContextMenu(ContextMenu g0, View g,
			 * ContextMenuInfo a) { // super.onCreateContextMenu(g0, g, a);
			 * //xcc.sendEmptyMessageDelayed(2,10); try { ListView g1 =
			 * (ListView) g;
			 * 
			 * int p = g1.getSelectedItemPosition(); long b =
			 * g1.getSelectedItemId();// getListView().getItemIdAtPosition(p);
			 * Log.i(G, "scuba " + p + " is " + b + " " + g1.getId() + " " +
			 * mMID);
			 * 
			 * if (b > -1) { midset.sendEmptyMessageDelayed((int) b, 2); }
			 * 
			 * g0.setHeaderTitle(": " + mMID); g0.add(0, 3, 1, "Update");
			 * g0.add(0, 4, 2, "Filter");
			 * 
			 * } catch (ClassCastException ep) { g0.setHeaderTitle("- " +
			 * ep.getLocalizedMessage()); g0.add(0, 5, 1, "Fix"); }
			 * 
			 * } }); //
			 */

			getListView()
					.setDivider(getResources().getDrawable(R.drawable.bb8));

			getListView().setDividerHeight(3);

		}

	};

	Handler foc = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "foc good xxx");
			Bundle bdl = msg.getData();

			// getListView().setSelectionAfterHeaderView();
			getListView().setSelection(1);
			// / TextView gt = (TextView) getListView().getChildAt(1);
			// gt.startAnimation(boh);

		}
	};

	LinearLayout hu;

	Handler mPack = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "mPack good xxx");
			Bundle bdl = msg.getData();

			int mk = bdl.getInt("lid");
			int idu = bdl.getInt("u");
			int mid = bdl.getInt("mid");

			LinearLayout mkl = (LinearLayout) findViewById(mk);
			TextView tx;
			tx = (TextView) mkl.getChildAt(idu);
			// TextView tx = (TextView) findViewById(id);
			if (tx == null) {
				Log.i(G, "mPack xxxxxx no tx " + idu);
				return;
			}

			if (bdl.containsKey("uid")) {
				if (tx.getId() != bdl.getInt("uid")) {
					Log.e(G,
							"mPack xxx " + tx.getId() + " not "
									+ bdl.getInt("uid"));
				}
			}

			String xf = tx.getText().toString();
			if (!xf.contains("<item") && xf.contains("content:")) {
				return;
			}

			// Log.i(G, "mPack xxxxxx " + id + " " + xf.replaceAll("\n", "; "));

			ContentValues i9 = new ContentValues();
			i9.put("content", xf);
			i9.put("itext", Uri.encode(xf.replaceAll("<.*?>", ""), " ")
					.replaceAll("%..", "").replaceAll("[-()' +]", "").trim());
			i9.put("status", 2);
			i9.put("mid", mid);

			Cursor u9 = null;
			u9 = SqliteWrapper.query(mCtx, mCtx.getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "_id" }, "itext = '" + i9.getAsString("itext") + "'", null, null);

			Float f9 = (float) 16;
			String ixx = "";
			boolean n = true;
			if (u9 != null) {
				if (u9.moveToFirst()) {
					ixx = "content://com.havenskys.elk/moment/" + u9.getInt(0);
					n = false;
				}
				u9.close();
			}

			if (n) {
				Uri h4 = SqliteWrapper.insert(mCtx, mCtx.getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), i9);

				ixx = h4.toString();
				f9 = (float) 21;
			}

			String tg = (String) tx.getTag();
			if (tg == null || tg.length() == 0) {
				tg = "item";
			}
			// if (tg.length() > 0) {
			// tx.setText("<" + tg + ">" + ixx + "</"
			// + (tg.replaceAll(" .*", "")) + ">");
			// } else {
			// tx.setText(ixx);
			// }
			tx.setText("<" + tg + ">\n" + ixx + "\n</"
					+ tg.replaceAll(" .*", "") + ">");
			tx.setTextSize(f9);

		}
	};

	Handler mUpdate = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "mUpdate good xxx");
			Bundle bdl = msg.getData();
			int id = bdl.getInt("id");
			// Log.e(G, "mUpdate xxxx " + id);

			TextView t1 = (TextView) findViewById(id);
			if (t1 == null) {
				Log.e(G, "mUpdate xxxx t1 fail" + id);
				return;
			}

			if (bdl.containsKey("size")) {
				Float gi = bdl.getFloat("size");
				Log.i(G, "mUpdate xxxx " + id + ": " + gi);
				t1.setTextSize(gi);
			}
			if (bdl.containsKey("bkg")) {
				int gi = bdl.getInt("bkg");
				Log.i(G, "mUpdate xxxx " + id + ": bkg " + gi);
				t1.setBackgroundResource(gi);
			}
			if (bdl.containsKey("tag")) {
				String tag = bdl.getString("tag");
				// Log.i(G, "mUpdate xxxx " + id + ": tag " + tag);
				t1.setTag(tag);
			}

			if (bdl.containsKey("text")) {
				String ix = bdl.getString("text");
				// Log.i(G, "mUpdate xxxx " + id + ": " + ix);
				t1.setText(ix);

				// yes good
				if (ix.length() == 0) {
					t1.setVisibility(View.GONE);
				}
			}

		}
	};

	int bdic = 0;
	Handler sme = new Handler() {

		public void handleMessage(Message msg) {
			Log.i(G, "sme good xxx");
			Bundle bdl = msg.getData();
			int id = bdl.getInt("id");

			TextView x = (TextView) findViewById(id);
			Drawable xd = x.getBackground();
			if (xd == null) {
				return;
			}

			bgi.put("b" + x.getId(), bdic);
			bdi[bdic++] = xd;
			x.setBackgroundResource(R.drawable.c2);

		}
	};

	Handler smeb = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "smeb good xxx");
			Bundle bdl = msg.getData();
			int id = bdl.getInt("id");
			TextView x = (TextView) findViewById(id);
			if (x == null || bgi == null) {
				return;
			}
			if (!bgi.containsKey("b" + x.getId())) {
				return;
			}
			int bid = bgi.getAsInteger("b" + x.getId());

			x.setBackgroundDrawable(bdi[bid]);

		}
	};
	Handler mAppend = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "mAppend good xxx");
			Bundle bdl = msg.getData();
			int id = bdl.getInt("id");
			String ix = bdl.getString("text");

			TextView t1 = (TextView) findViewById(id);
			if (t1 == null) {
				Log.e(G, "mApp xxxx t1 " + id + ": " + ix);
				return;
			}
			// Log.i(G, "mApp ID " + id + ":" + ix);
			t1.append("\n" + ix);
			t1.setVisibility(View.VISIBLE);

		}
	};

	Handler ex1 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "ex1 good xxx");
			TextView t1 = (TextView) findViewById(msg.what);
			if (t1 == null) {
				return;
			}
			if (t1.getLineCount() * t1.getLineHeight() > 240
					|| t1.getMeasuredHeight() > 340 || t1.getHeight() > 340) {
				t1.setMaxHeight(88);

				if (t1.getText().toString().startsWith("<")) {
					t1.setBackgroundResource(R.drawable.b6x02);
				} else {
					t1.setBackgroundResource(R.drawable.b5x02);
				}
			}

		}
	};

	ContentValues bgi = null;
	Drawable[] bdi;
	Handler mNew = new Handler() {

		int n8c = 0;
		int n8b = 0;

		public void handleMessage(Message msg) {
			Log.i(G, "mNew good xxx");
			Bundle bdl = msg.getData();
			int huid = bdl.getInt("lid");
			LinearLayout mL = (LinearLayout) findViewById(huid);

			if (mL == null) {
				Log.e(G, "mNew LinearLayout ?! " + huid);
				return;
			}

			if (bdl.getInt("order") == 0 && mMID > -1) {
				// 1

				// mL.removeAllViews();

				// RelativeLayout xv = (RelativeLayout)
				// findViewById(R.id.cvxhc);
				// LinearLayout c5 = new LinearLayout(mCtx);// (LinearLayout)
				// findViewById(R.id.cvxhcl);
				{
					// mL = c5;

					// c5.setLayoutParams(xrub);
					// c5.setId(hid++);
					// c5.setOrientation(LinearLayout.VERTICAL);
					// xv.addView(c5);

					// mL = c5;
				}
				// Scan
			}

			if (mL.getChildCount() < bdl.getInt("order")) {

				if (bdl.getInt("order") - mL.getChildCount() < 100) {

					for (int i = 0; i < (bdl.getInt("order") - mL
							.getChildCount()); i++) {

						TextView m = new TextView(mCtx);
						m.setLayoutParams(new LinearLayout.LayoutParams(0, 0));
						// 2
						mL.addView(m);
					}
				}

				// Log.e(G,
				// "order xxxxxxxx " + mL.getChildCount() + " , "
				// + bdl.getInt("order"));

				// return;
			}

			if (mL.getChildCount() != bdl.getInt("order")) {
				Log.e(G,
						"order xxxxxxx " + mL.getChildCount() + " , "
								+ bdl.getInt("order"));

			}

			final int hidx = bdl.getInt("id");
			String ix = bdl.getString("text");
			int gravity = bdl.getInt("gravity", Gravity.CENTER);
			Float sz = bdl.getFloat("size");
			int txc = bdl.getInt("color");
			int bgr = bdl.getInt("bgr", R.drawable.b502);
			int mgl = bdl.getInt("marginleft", 0);
			int mgt = bdl.getInt("margintop", 0);
			int mgr = bdl.getInt("marginright", 0);
			int mgb = bdl.getInt("marginbottom", 0);
			int umh = bdl.getInt("minheight", 48);
			final int mid = bdl.getInt("mid");
			final boolean x6 = bdl.getBoolean("x6");
			String texttag = bdl.getString("tag");
			if (texttag == null) {
				texttag = "";
			}

			{

				if (nbi.getChildCount() == 0) {
					Log.e(G, "cvbon from mNew ??");
					// cvbon.sendEmptyMessageDelayed(R.id.hu, 25);
					n8b = 0;
					n8c = 0;
				}

				{
					// if (!(n8c > 31 && n8b == 0)) {
					n8b++;
					if (n8b == 1) {
						n8c++;
						LinearLayout lh = new LinearLayout(mCtx);
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
								9, -2);
						// lp.weight = (float) 1;
						lh.setLayoutParams(lp);
						lh.setMinimumWidth(9);
						// cvb.setOnClick
						lh.setOrientation(LinearLayout.VERTICAL);

						lh.setId(hid++);
						if (n8c == 0) {
							nbi.removeAllViews();
						}
						nbi.addView(lh);
						// 3
						// if (n8c == 1) {
						// TextView th = new TextView(mCtx);
						// LinearLayout.LayoutParams lpx = new
						// LinearLayout.LayoutParams(
						// 7, 7);
						// th.setLayoutParams(lpx);
						// lh.addView(th);
						// }
					}

					{

						LinearLayout lh = (LinearLayout) nbi.getChildAt(nbi
								.getChildCount() - 1);
						TextView th = new TextView(mCtx);
						LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
								-1, 7);
						// lp.weight = (float) 1;
						if ((float) ((float) n8c / (float) 2) == (float) (n8c / 2)) {
							lp.setMargins(1, 1, 2, 0);
						} else {

							if (n8b == 5 && n8c == 1) {
								lp.setMargins(2, 1, 1, 0);
							} else if (n8b > 1 || n8c > 1) {
								lp.setMargins(1, 1, 1, 0);
							} else {
								lp.setMargins(2, 1, 1, 0);
							}

						}

						th.setLayoutParams(lp);
						th.setMinimumWidth(8);
						th.setId(hid++);// th.setMaxHeight(12);th.setMaxWidth(20);
						th.setBackgroundColor(txc);
						// th.setBackgroundResource(bgr);
						lh.addView(th);
						// 4
						if (n8b > 4) {
							n8b = 0;
						}
					}

				}

			}

			if (texttag.length() == 0 && !ix.startsWith("<")) {

				try {
					TextView uk = (TextView) mL
							.getChildAt(bdl.getInt("order") - 1);// mL.getChildCount()
																	// -
																	// 1
																	// findViewById(hid
																	// -
																	// 2);

					if (uk == null) {
						Log.e(G,
								"mNew xxxxxxxxxxxxxxxxxxxxxxxx uk empty "
										+ bdl.getInt("order") + " of "
										+ mL.getChildCount());
					} else {

						String ut = uk.getText().toString();
						String[] ut8 = ut.trim().split("\n");
						String ut7 = "";
						String ut2 = "";

						{
							ut2 = "";
							ut7 = "";

							for (int b = 0; b < ut8.length - 1; b++) {
								ut7 += ut8[b] + "\n";
							}

							ut2 = ut8[ut8.length - 1];
							if (ut2.startsWith("<") && !ut2.startsWith("<![")
									&& !ut2.startsWith("</")) {
								ut7 = ut7.trim();
							} else {
								ut7 += ut2;
								ut2 = "";
							}
							ut2 = ut2.replaceFirst("<", "");
							ut2 = ut2.replaceFirst(">.*", "");
							texttag = ut2;
						}
						// 5
						uk.setText(ut7);

						if (ut7.length() == 0) {
							uk.setVisibility(View.GONE);
						}

						if (texttag.length() > 1000) {
							Log.i(G,
									"mApp ID " + hidx + "("
											+ texttag.replaceAll(" .*", "")
											+ "):" + ix);
							Log.i(G,
									"mNew xxxxxx tag(" + texttag + ") for("
											+ ix + ") from("
											+ ut.replaceAll("\n", ") (") + ")");
						}
					}

				} catch (ClassCastException ey) {
					ey.printStackTrace();
				}

			}

			LinearLayout.LayoutParams t1l = new LinearLayout.LayoutParams(-1,
					-2);
			t1l.setMargins(mgl, mgt, mgr, mgb);
			t1l.gravity = Gravity.CENTER_VERTICAL;
			TextView t1 = new TextView(mCtx);
			t1.setLayoutParams(t1l);
			t1.setGravity(Gravity.CENTER_VERTICAL);

			t1.setMinimumHeight(umh);
			t1.setId(hidx);
			// Log.i(G,
			// "mNew "
			// + ((hidx == t1.getId()) ? "ID " + hidx
			// : " I!!!!!!!!!!!!!!!!!K " + hidx + ","
			// + t1.getId()) + ":" + "["
			// + ix.replaceAll("\n", "][") + "]");

			// t1.setClickable(true);
			t1.setTextSize(sz);
			// if (ix.matches("<img")) {
			// String jux = texttag;
			// texttag = ix.replaceFirst("<", "").replaceFirst(">", "");
			// ix = jux;
			// if (ix.length() == 0) {
			// ix = "Image";
			// }
			// }

			t1.setTag(texttag);
			// if (texttag.matches("(img) .*")
			// || ix.matches("http.*(.png|.jpg|.gif)")) {
			// t1.setBackgroundResource(R.drawable.img);
			// } else if (texttag.matches("(a|link) .*") ||
			// ix.matches("http.*")) {
			// t1.setBackgroundResource(R.drawable.unvis);
			// } else {
			t1.setBackgroundResource(bgr);
			// }

			t1.setTextColor(txc);
			t1.setGravity(gravity);
			t1.setText(ix);

			if (ix.trim().startsWith("<") || bgr == R.drawable.b602) {// && bgr
																		// ==
				// R.drawable.be6)
				// {
				t1.setVisibility(View.GONE);
			}
			// 6
			mL.addView(t1, bdl.getInt("order"));

			// } else if (t1.getLineCount() > 0
			// && t1.getLineCount() * t1.getLineHeight() > 200) {
			// t1.setMaxHeight(88);
			// }

			t1.setFocusable(true);
			t1.setClickable(true);

			t1.setOnFocusChangeListener(t1foc);
			final int mlf = mL.getId();
			final int ou = bdl.getInt("order");
			final int mldf = mid;
			t1.setOnTouchListener(new OnTouchListener() {
				int xa4 = -1;
				int xxl = -1;
				long xxc = 11;
				boolean xxco = false;
				boolean xxou = false;

				Animation bohx4 = AnimationUtils
						.loadAnimation(mCtx, R.anim.oh4);
				Animation bohx2 = AnimationUtils
						.loadAnimation(mCtx, R.anim.oh2);

				public boolean onTouch(View g, MotionEvent e) {
					// Log.i(G,
					// "touch " + e.getAction() + " " + e.getHistorySize());
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						Log.i(G, "mNew good down xxx");
						TextView gt = (TextView) g;
						xa4 = -1;
						xxc = SystemClock.uptimeMillis();
						xxco = false;
						xxou = false;

						gt.startAnimation(bohx2);
						return false;
					}

					if (e.getAction() == MotionEvent.ACTION_MOVE && xa4 == -1) {
						TextView gt = (TextView) g;
						gt.clearAnimation();
						xa4 = (int) e.getX();
						return false;
					}

					if ((e.getAction() == MotionEvent.ACTION_MOVE && (e
							.getHistorySize() > 0 || xa4 > -1))) {
						// t9.clearAnimation();

						int x = (int) e.getX();
						int xx = (int) xa4 - x;// (xa4 > x ? xa4 - x : x - xa4);
						// setr("" + xx);
						// Log.i(G,
						// "touch xxx " + e.getAction() + " "
						// + e.getHistorySize() + " " + xx
						// + " -- "
						// + (SystemClock.uptimeMillis() - xxc));

						int m9 = 4;
						// TextView t9 = (TextView) g;

						{

							Message ml = new Message();
							Bundle bl = new Bundle();
							bl.putInt("tid", g.getId());
							bl.putInt("x", xx * -1);
							ml.setData(bl);
							// llm.sendMessageDelayed(ml, 1);
						}

						if (xxou) {
							return false;
						}

						// int sidewide = getSidewide();
						// sizekl
						if (xx < -15 && !xxou) {
							TextView t9 = (TextView) g;

							xxou = true;
							// TextView t9 = (TextView) g;
							t9.setClickable(false);
							t9.setLongClickable(false);
							cvboff.sendEmptyMessageDelayed(mlf, 5);
							// return true;
							// return false;
							// Message ml = new Message();
							// Bundle bl = new Bundle();
							// bl.putInt("id", g.getId());
							// ml.setData(bl);
							// sme.sendMessageDelayed(ml, 5);
						} else if (xx > 30) {
							// (mWidth - sidewide) / m9
							TextView t9 = (TextView) g;
							t9.setBackgroundResource(R.drawable.b12);
							// LinearLayout xf = (LinearLayout) t9.getParent();
							Message ml = new Message();
							Bundle bl = new Bundle();
							bl.putInt("lid", mlf);
							bl.putInt("uid", t9.getId());
							bl.putInt("u", ou);
							bl.putInt("mid", mldf);
							ml.setData(bl);

							mPack.sendMessageDelayed(ml, 5);

							// return true;
						}
						return false;
					}

					if (SystemClock.uptimeMillis() - xxc > 2850 && !xxco) {

						TextView t9 = (TextView) g;
						t9.setLongClickable(false);
						xxco = true;
						cvboff.sendEmptyMessageDelayed(mlf, 5);
						Log.i(G, "mNew xxx d "
								+ (SystemClock.uptimeMillis() - xxc));

					}

					return false;
				}
			});

			// project gem
			t1.setOnLongClickListener(new OnLongClickListener() {
				Animation bohx2 = AnimationUtils
						.loadAnimation(mCtx, R.anim.oh2);

				public boolean onLongClick(View g) {
					Log.i(G, "mNew long good xxx");
					TextView gt = (TextView) g;
					// gt.clearAnimation();
					gt.startAnimation(bohx2);
					Message ml = new Message();
					Bundle bl = new Bundle();

					bl.putInt("mid", mid);
					bl.putInt("sid", g.getId());// hidx
					ml.setData(bl);
					// setr(mid + " " + g.getId());
					// joex.sendMessageDelayed(ml, 5);

					// buccoff.sendEmptyMessageDelayed(2, 5);
					cvboff.sendEmptyMessageDelayed(1, 5);

					return false;
				}
			});

			t1.setOnClickListener(new OnClickListener() {

				boolean xn = true;
				Animation bohx2 = AnimationUtils
						.loadAnimation(mCtx, R.anim.oh2);

				public void onClick(View gv) {
					Log.i(G, "mNew click good xxx");
					TextView gt = (TextView) gv;

					String tag = gt.getTag().toString();
					if (tag == null) {
						tag = "";
					}

					String hi = gt.getText().toString();
					hi = hi.replaceAll("\n", "").replaceAll("<.*?>", "");

					if (hi.startsWith("content://")) {
						hi = hi.replaceFirst(".*/", "");
						int mid2 = Integer.parseInt(hi);
						// gt.clearAnimation();
						// gt.startAnimation(boh);
						gt.startAnimation(bohx2);

						miesr(-1, mid2, true, false, 5);
						Log.i(G, "mNew onClick for " + hi);

					} else {
						Log.i(G, "mNew onClick for " + hi);

					}

					if (gt.getHeight() == 88) {
						gt.setMaxHeight(5000);
						gt.setBackgroundResource(R.drawable.b502);
					}

					if (xn) {

						if (tag.length() > 0) {
							xape(tag, TextToSpeech.QUEUE_ADD);
						}
					}
				}
			});

			if (bgr == R.drawable.b802) {

				if (mL.getVisibility() != View.VISIBLE) {
					prepLanguage(mid);
					xape(ix, TextToSpeech.QUEUE_ADD);
					// setr(ix, true);// Auto read title
				}
				SharedPreferences mReg = getSharedPreferences("Preferences",
						MODE_WORLD_READABLE);
				Editor mEdt = mReg.edit();

				mEdt.putInt("hid", mL.getId());
				mEdt.putBoolean("proceed", true);
				mEdt.commit();
				t1.setOnClickListener(new OnClickListener() {

					boolean xn = true;

					public void onClick(View gv) {
						Log.i(G, "mNew click good xxx");
						TextView gt = (TextView) gv;
						gt.clearAnimation();
						gt.startAnimation(boh);
						xmmu.sendEmptyMessageDelayed(gt.getId(), 50);
					}
				});

			} else if (bgr == R.drawable.b902) {
				if (mL.getVisibility() != View.VISIBLE) {
					cvboff.sendEmptyMessageDelayed(mL.getId(), 3853);
				}

			} else {

				ex1.sendEmptyMessageDelayed(hidx, 50 + n8b);
			}

		}

	};
	float ghu = 0;
	float ghu2 = 0;
	float ghu3 = 0;
	float ghu4 = 0;
	OnFocusChangeListener t1foc = new OnFocusChangeListener() {

		TextView trx;

		public void onFocusChange(View g, boolean a) {
			Log.i(G, "t1foc good xxx");
			trx = (TextView) g;

			if (a) {
				// t.setBackgroundColor(Color.argb(200, 14, 56, 90));
				Drawable xx = trx.getBackground();
				xx.setColorFilter(Color.argb(200, 0, 98, 234),
						PorterDuff.Mode.SCREEN);

				trx.setBackgroundDrawable(xx);

				// trx.startAnimation(boh);

			} else {

				Drawable xx = trx.getBackground();
				xx.setColorFilter(Color.argb(0, 0, 8, 34),
						PorterDuff.Mode.SCREEN);
				trx.setBackgroundDrawable(xx);
				// trx.setBackgroundColor(Color.argb(200, 14, 56, 190));
				// Animation bflu = AnimationUtils.loadAnimation(mCtx,
				// R.anim.flu);
				// trx.startAnimation(bflu);
			}

		}

	};

	/*
	 * / Handler setnbi = new Handler() { public void handleMessage(Message msg)
	 * { final int midf = msg.what; nbi.setOnClickListener(new OnClickListener()
	 * { public void onClick(View g) { flipnbi.sendEmptyMessageDelayed(midf,
	 * 25); } }); } };
	 * 
	 * Handler flipnbi = new Handler() { public void handleMessage(Message msg)
	 * {
	 * 
	 * if (bucc.getVisibility() == View.VISIBLE) { //
	 * bucc.setVisibility(View.GONE); // buccoff.sendEmptyMessageDelayed(2, 75);
	 * } else { // bucc.setVisibility(View.VISIBLE); //
	 * buccon.sendEmptyMessageDelayed(2, 75); pagelink(msg.what, 75); }
	 * 
	 * } };//
	 */

	Handler miesr = new Handler() {
		// class miesr extends Handler {
		// public miesr(Looper ln) {
		// super(ln);
		// }

		public void handleMessage(Message msg) {
			Log.i(G, "miesr good xxx");
			Bundle b1 = msg.getData();
			final int mid = b1.getInt("mid");
			boolean filter = b1.getBoolean("forceFilter");
			boolean xec = b1.getBoolean("takePicture");

			mMID = mid;
			String incontent = "";

			int huid = -1;
			int scub = 1;
			// Log.i(G, "miesr xxxx " + mid);
			/*
			 * if (tid > -1) {
			 * 
			 * TextView u9 = (TextView) findViewById(tid); if (u9 != null) {
			 * incontent = u9.getText().toString(); Log.i(G, "miesr xxxxx " +
			 * mid + " " + tid + " >> " + incontent.replaceAll("\n",
			 * " ").trim());
			 * 
			 * if (incontent.contains("content://")) { Uri hu9 =
			 * Uri.parse(incontent.replaceAll("<.*?>", "") .replaceAll("\n",
			 * "").trim()); int mb = Integer.parseInt(hu9.getLastPathSegment());
			 * Cursor h8 = null; h8 = SqliteWrapper.query(mCtx,
			 * getContentResolver(),
			 * Uri.parse("content://com.havenskys.elk/moment"), new String[] {
			 * "filtered" }, "_id = " + mb, null, null); if (h8 != null) { if
			 * (h8.moveToFirst()) { incontent = h8.getString(0);
			 * 
			 * } h8.close(); }
			 * 
			 * }
			 * 
			 * }
			 * 
			 * }//
			 */
			// 1
			{
				// RelativeLayout xna = (RelativeLayout)
				// findViewById(R.id.nbva);
				LinearLayout c5 = new LinearLayout(mCtx);
				RelativeLayout.LayoutParams xru = new RelativeLayout.LayoutParams(
						-1, -2);
				// sizekln
				xru.setMargins(0, 27, 0, 0);
				c5.setLayoutParams(xru);
				c5.setId(hid++);
				c5.setOrientation(LinearLayout.HORIZONTAL);
				Animation bflu = AnimationUtils.loadAnimation(mCtx, R.anim.flu);
				nbi.clearAnimation();
				nbi.startAnimation(bflu);
				// nbixx.sendEmptyMessageDelayed(2, bflu.getDuration());

				nbva.addView(c5);
				nbva.bringChildToFront(c5);
				nbi = c5;
				// nbi.removeAllViews();
			}

			// Message ml = new Message();
			// miesr.sendMessage(ml);

			{

				// RelativeLayout.LayoutParams sl4 = new
				// RelativeLayout.LayoutParams(
				// -1, -1);
				// sl4.setMargins(89, 55, 0, 0);
				// ScrollView s8 = new ScrollView(mCtx);
				// s8.setLayoutParams(sl4);
				// s8.setId(hid++);
				// s8.setDrawingCacheEnabled(true);
				// s8.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
				// s8.setBackgroundColor(Color.argb(60, 0, 0, 10));
				// s8.postInvalidate();
				// scub = s8.getId();
				// 2

				RelativeLayout su = (RelativeLayout) findViewById(R.id.cvxh);

				RelativeLayout.LayoutParams adf = new RelativeLayout.LayoutParams(
						getVwide(), -2);

				adf.setMargins(0, 0, 0, 0);
				LinearLayout o8 = new LinearLayout(mCtx);
				o8.setLayoutParams(adf);
				o8.setOrientation(LinearLayout.VERTICAL);
				o8.setId(hid++);
				o8.setTag("mid" + mMID);
				huid = o8.getId();
				// o8.removeAllViews();
				// msu.setDrawingCacheEnabled(true);
				// msu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
				// msu.postInvalidate();
				// o8.setDrawingCacheEnabled(true);
				// o8.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
				// o8.postInvalidate();
				// su.removeAllViews();

				o8.setVisibility(View.GONE);

				su.addView(o8, 0);

				// buc = (RelativeLayout) findViewById(R.id.cvb);
				// buc.addView(s8);

				// LinearLayout hu = (LinearLayout) findViewById(huid);

				if (o8 != null) {
					// Message mx = new Message();
					// Bundle bl = new Bundle();
					// bl.putInt("mid", mid);
					// bl.putInt("huid", huid);
					// bl.putInt("scub", scub);
					// bl.putBoolean("xec", xec);
					// mx.setData(bl);
					// mx.setData(new Bundle(bl));

					{

						// joexu.sendEmptyMessageDelayed(2, 275);
						// buccoff.sendEmptyMessageDelayed(2, 175);
						cvbon.sendEmptyMessageDelayed(o8.getId(), 5);

						HandlerThread yx54 = new HandlerThread(G,
								Process.THREAD_PRIORITY_DEFAULT);
						yx54.start();

						Scan gs54 = new Scan(yx54.getLooper());
						gs54.OVERRIDE = false;
						filter = false;
						// setnbi.sendEmptyMessageDelayed(mid, 25);

						gs54.set(mCtx, DataProvider.CONTENT_URI, setr, o8, lmt,
								filter, mid, mNew, mAppend, mUpdate, mPack);

						if (incontent != null && incontent.length() > 0) {
							Log.i(G,
									"miesr xxxx loaded "
											+ incontent.replaceAll("\n", " "));

							gs54.go(incontent);
						} else {
							gs54.go(3);
						}

					}

					if (xec) {
						// addxr(mid, R.id.cvxh);
						addxl(mid, o8.getId());
						// addxr(mid, msu.getId());
					}

				} else {
					setr("Fix matrix");
				}

			}

			// Log.i(G, "past load");

		}
	};

	Handler nbixx = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "nbixx good xxx");
			nbi.setVisibility(View.GONE);
		}
	};

	public void miesr(int tid, int mid, boolean takePicture,
			boolean forceFilter, int wh) {
		Log.i(G, "miesr() good xxx");

		prepLanguage(mid);
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putInt("mid", mid);
		bl.putBoolean("forceFilter", forceFilter);
		bl.putBoolean("takePicture", takePicture);
		ml.setData(bl);
		// HandlerThread tx = new HandlerThread(G,
		// Process.THREAD_PRIORITY_DEFAULT);
		// tx.start();
		// Handler miesr = new miesr(tx.getLooper());
		miesr.sendMessageDelayed(ml, wh);

	}

	Handler lmt = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "lmt good xxx");
			Bundle bdl = msg.getData();
			int id = bdl.getInt("id");
			// int scub = bdl.getInt("scub");
			int plus = bdl.getInt("plus", 0);
			int umh = bdl.getInt("minheight", 48);

			TextView tx = (TextView) findViewById(id);
			if (tx != null) {
				LinearLayout xl = (LinearLayout) tx.getParent();
				if (xl != null) {
					RelativeLayout xxb = (RelativeLayout) xl.getParent();
					if (xxb != null) {

						// RelativeLayout xxb2 = (RelativeLayout)
						// xxb.getParent();
						// if (xxb2 != null) {
						ScrollView sx = (ScrollView) xxb.getParent();// findViewById(scub);
						if (sx != null) {
							sx.scrollTo(0, tx.getTop() + plus);
						}
						// }

					}
				}
			}

		}
	};

	public void miex(int xid, int mid, int huid, int scub) {
		Log.i(G, "miex() good xxx");
		boolean gs = false;
		Cursor bx;
		bx = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/xsaw"), new String[] { "content" }, "_id=" + xid, null, null);
		if (bx != null) {
			if (bx.moveToFirst()) {
				// String rcontent = bx.getString(0);
				// mie(bx.getString(0), mid, huid, scub, false, false);

				Message ml = new Message();
				Bundle bl = new Bundle();
				bl.putInt("mid", mid);
				bl.putInt("huid", huid);
				bl.putInt("scub", scub);
				bl.putBoolean("xec", false);
				ml.setData(bl);

				LinearLayout hu = (LinearLayout) findViewById(huid);
				ScrollView sc = (ScrollView) findViewById(scub);
				HandlerThread yx = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
				yx.start();
				Scan gs4 = new Scan(yx.getLooper());

				gs4.set(mCtx, DataProvider.CONTENT_URI, setr, hu, lmt, false, mid, mNew, mAppend, mUpdate, mPack);
				gs4.go(4);

				gs = true;
			}
			bx.close();
		}

		if (!gs) {
			setr("Contentless");
		}

	}

	int highwide = 50;

	int getTopwide() {
		Log.i(G, "getTopwide() good xxx");
		int xs = -1;
		// if (mHeight > mWidth) {
		xs = 120;// (int) ((float) mHeight * 0.16);
		// } else {
		// xs = (int) ((float) mHeight * 0.18);
		// }

		// if (buc.getVisibility() != View.VISIBLE
		// && bucc.getVisibility() != View.VISIBLE
		// && msu.getVisibility() != View.VISIBLE) {
		// if (mHeight > mWidth) {
		// xs = (int) ((float) mHeight * 0.16);
		// } else {
		// xs = (int) ((float) mHeight * 0.16);
		// }

		// }
		return xs;
	}

	int getBottomwide() {

		Log.i(G, "getBottomwide() good xxx");
		int xs = -1;
		// if (mHeight > mWidth) {
		xs = 88;// (int) ((float) mHeight * 0.16);
		// } else {
		// xs = (int) ((float) mHeight * 0.19);
		// }

		// if (buc.getVisibility() == View.GONE
		// && bucc.getVisibility() == View.GONE
		// && msu.getVisibility() == View.GONE) {
		// if (mHeight > mWidth) {
		// xs = (int) ((float) mHeight * 0.21);
		// } else {
		// xs = (int) ((float) mHeight * 0.25);
		// }

		// }
		return xs;
	}

	int getHighwide() {
		Log.i(G, "getHighwide() good xxx");
		// int x = nbva.getHeight();
		int xs = getBottomwide();
		int xs2 = getTopwide();
		// fullscreen?
		int border = -28;

		return (xs + xs2 + border);

	}

	int sidewide = 160;

	int getVwide() {

		return (int) (mWidth < 450 ? mWidth - getSidewide() : 450);
	}

	int getSidewide() {
		Log.i(G, "getSidewide() good xxx");
		int xx = (int) ((float) mWidth * 0.19);
		// if (buc.getVisibility() == View.GONE
		// && bucc.getVisibility() == View.GONE
		// && msu.getVisibility() == View.GONE) {
		//
		// xx = (int) ((float) mWidth * 0.33);
		// }

		if (xx > 430) {
			xx = 410;
		}

		return xx;
	}

	Handler sizekln = new Handler() {
		long x92 = -1;

		public void handleMessage(Message msg) {
			Log.i(G, "sizekln good xxx");
			if (x92 > SystemClock.uptimeMillis() - 250) {
				return;
			}

			x92 = SystemClock.uptimeMillis();

			int xx = getSidewide();
			sidewide = xx;

			int xs = getBottomwide();
			int xs2 = getTopwide();
			highwide = getHighwide();
			{
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						xx, xs2);
				nba.setBackgroundResource(R.drawable.che2);
				nba.setMinimumHeight(xs2);
				hn.setMargins(0, 0, 0, 0);

				nba.setLayoutParams(hn);
				// nba.startAnimation(nb9);
			}

			{// xs2
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						-1, xs2);

				hn.addRule(RelativeLayout.ALIGN_PARENT_TOP);
				hn.setMargins(xx, 0, 0, 0);

				nbva.setLayoutParams(hn);
				nbva.setMinimumHeight(xs2);
				// nbva.setHorizontalScrollBarEnabled(true);
			}

			{
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						-1, xs);
				hn.setMargins(xx, 0, 0, 0);
				hn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				nbv.setLayoutParams(hn);
			}

			// nbva

			if (msu.getVisibility() == View.INVISIBLE) {
				// joexu2.sendEmptyMessageDelayed(2, 370);
				msu.setVisibility(View.GONE);
			}
			// bucc.setVisibility(View.INVISIBLE);
			// buc.setVisibility(View.GONE);
			{
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						xx, xs);
				hn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				nbb.setLayoutParams(hn);
				// nbb.startAnimation(nb9);
			}

			{
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						-1, -1);
				hn.setMargins(0, 0, 0, 0);
				hn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

				ldx.setLayoutParams(hn);
				ldx.setPadding(xx, 0, 0, 0);
			}

			cnw2.sendEmptyMessageDelayed(xx, 25);

			{

				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						xx, -1);

				// hn.setMargins(0, xs, 0, xs);
				hn.addRule(RelativeLayout.BELOW, R.id.nba);
				hn.addRule(RelativeLayout.ABOVE, R.id.nbb);
				mHu.setLayoutParams(hn);
			}

			int xhm = 45;
			// nbdd
			{

				int xh = xs - (int) (xs / 10);
				if (xh > xhm) {
					xh = xhm;
				}
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						xh, xh);
				hn.addRule(RelativeLayout.CENTER_IN_PARENT);
				hn.setMargins(0, -26, 0, 0);
				// hn.height = (int) (xs / 1.3);

				nbdd.setScaleType(ScaleType.FIT_CENTER);
				nbdd.setLayoutParams(hn);
				nbdd.setImageResource(R.drawable.dd);
			}

			{
				int xh = xs - (int) (xs / 10);
				if (xh > xhm) {
					xh = xhm;
				}
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						xh, xh);
				int x8 = xs - 13;
				if (x8 < 0) {
					x8 = 0;
				}
				// hn.height = (xs / 2);

				hn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				// hn.setMargins(sidewide / 4, 0, 0, -1 * x8);
				hn.setMargins(0, 0, 0, 0);// -1*(xs/10)/4);
				// nb4.setScaleType(ScaleType.FIT_CENTER);
				// nb4.setLayoutParams(hn);
			}

			int xg8 = xs2 - 16;
			int xg9 = xs - 17;
			// fitco
			{
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						-1, -1);
				hn.setMargins(xx, xg8, 0, xg9);
				// hn.addRule(RelativeLayout.BELOW, nbva.getId());
				// hn.addRule(RelativeLayout.ABOVE, nbb.getId());
				msu.setLayoutParams(hn);
			}
			// cvboff
			{// cvxh
				ScrollView xxcub = (ScrollView) findViewById(R.id.cub);
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						-1, -1);
				hn.setMargins(xx, xg8, 0, xg9);
				// hn.addRule(RelativeLayout.BELOW, nbva.getId());
				// hn.addRule(RelativeLayout.ABOVE, nbb.getId());
				xxcub.setLayoutParams(hn);
			}

			{// cvxhc
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						-2, -1);
				hn.setMargins(xx, xg8, 0, xg9);
				// hn.addRule(RelativeLayout.BELOW, nbva.getId());
				// hn.addRule(RelativeLayout.ABOVE, nbb.getId());
				ScrollView xxcubc = (ScrollView) findViewById(R.id.cubc);

				// RelativeLayout rx = (RelativeLayout)
				// findViewById(R.id.cvxhc);
				// rx.setLayoutParams(hn);
				xxcubc.setLayoutParams(hn);
			}

			{
				LinearLayout cxb = (LinearLayout) findViewById(R.id.cxb);
				RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
						-1, -2);
				hn.setMargins(xx, 0, 0, 0);
				// hn.addRule(RelativeLayout.ALIGN_TOP, getListView().getId());
				cxb.setLayoutParams(hn);
				cxb.setVisibility(View.VISIBLE);
			}

		}
	};

	Handler cnw5 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "cnw5 good xxx");
			int xx = msg.what;

			if (mHu.getVisibility() != View.VISIBLE) {
				mHu.setVisibility(View.VISIBLE);
				Animation nb9 = AnimationUtils.loadAnimation(mCtx, R.anim.no);
				mHu.clearAnimation();
				mHu.startAnimation(nb9);
			}// alx =

		}
	};

	Handler cnw3 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "cnw3 good xxx");
			int xx = msg.what;

			if (nba.getVisibility() != View.VISIBLE) {
				Animation nb9 = AnimationUtils.loadAnimation(mCtx, R.anim.no5);
				nba.clearAnimation();
				nba.startAnimation(nb9);
				nba.setVisibility(View.VISIBLE);
			}// nba =

			if (nbva.getVisibility() != View.VISIBLE) {
				Animation nb9 = AnimationUtils
						.loadAnimation(mCtx, R.anim.no502);
				nbva.clearAnimation();
				nbva.startAnimation(nb9);
				nbva.setVisibility(View.VISIBLE);
			}// nbva =

		}
	};

	Handler cnw4 = new Handler() {

		public void handleMessage(Message msg) {
			Log.i(G, "cnw4 good xxx");
			int xx = msg.what;
			if (nbv.getVisibility() != View.VISIBLE) {
				Animation nb9 = AnimationUtils
						.loadAnimation(mCtx, R.anim.no402);
				nbv.clearAnimation();
				nbv.startAnimation(nb9);
				nbv.setVisibility(View.VISIBLE);
			}// nbv =
			if (nbb.getVisibility() != View.VISIBLE) {
				Animation nb9 = AnimationUtils.loadAnimation(mCtx, R.anim.no4);
				nbb.clearAnimation();
				nbb.startAnimation(nb9);
				nbb.setVisibility(View.VISIBLE);
			}// nbb =

		}
	};

	Handler cnw2 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "cnw2 good xxx");
			int xx = msg.what;

			cnw3.sendEmptyMessageDelayed(xx, 55);
			cnw4.sendEmptyMessageDelayed(xx, 55);
			cnw5.sendEmptyMessageDelayed(xx, 5);

			// if (msu.getVisibility() == View.VISIBLE) {
			// msu.startAnimation(nb9);
			// }

		}
	};

	// Scan gs;
	int hid = 1009323431;
	ContentValues umidD;
	ContentValues cmidD;
	ContentValues xmidD;
	Handler cvboff = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "cvboff good xxx");
			// buc.clearAnimation();
			// buc.setLongClickable(false);
			// buc.setClickable(false);
			// buc.setFocusable(false);
			// buc.getVis 20110911
			// if (buc.getVisibility() != View.GONE)
			{

				if (msg.what > 22) {
					LinearLayout xl = (LinearLayout) findViewById(msg.what);
					if (xl != null) {
						Log.i(G, "cvboff good " + msg.what + ", " + xl.getId());
						Animation gx = AnimationUtils.loadAnimation(mCtx,
								R.anim.fgom4);// nb9
						xl.clearAnimation();
						xl.startAnimation(gx);
						// xl.setFocusable(false);
						// xl.setVisibility(View.GONE);

						cvboff2.sendEmptyMessageDelayed(xl.getId(),
								gx.getDuration());
						return;
					}
				}

				{
					ScrollView sc = (ScrollView) findViewById(R.id.cub);
					RelativeLayout su = (RelativeLayout) sc.getChildAt(0);// cvxh
					// RelativeLayout su = (RelativeLayout)
					// findViewById(R.id.cvxh);

					if (su != null && su.getChildCount() == 0) {
						cvboff2.sendEmptyMessageDelayed(2, 5);
					}
					if (su != null && su.getChildCount() > 0 && msg.what == 2) {
						// msu.removeViews(1, msu.getChildCount() - 3);
						for (int x = su.getChildCount(); x >= 0; x--) {
							try {
								LinearLayout ji = (LinearLayout) su
										.getChildAt(x);
								if (ji == null) {
									continue;
								}
								// ji.setVisibility(View.GONE);

								// Click
								// Log.i(G,
								// "cvboff goodm " + msg.what + ", "
								// + ji.getId());
								Animation xxm = AnimationUtils.loadAnimation(
										mCtx, R.anim.fgom4);
								ji.clearAnimation();
								xxm.setStartOffset(1725 * x);

								ji.startAnimation(xxm);
								ji.setFocusable(false);
								ji.setClickable(false);
								ji.setFocusableInTouchMode(false);

								cvboff2.sendEmptyMessageDelayed(ji.getId(),
										xxm.getDuration() + x * 725);

								// if (msg.what == 2) {
								// break;
								// }

							} catch (ClassCastException cb) {
								continue;
								// cb.printStackTrace();
							}
						}

						// if (su != null && su.getChildCount() > 15) {
						// 4
						// su.removeViews(1, su.getChildCount() - 15);
						// }

					}

				}

			}
		}
	};

	Handler xxvv = new Handler() {
		String uux = "";

		public void handleMessage(Message msg) {
			// xxvv.sendEmptyMessageDelayed(2, 30000);
			// ScrollView sc = (ScrollView) findViewById(R.id.cub);
			// scub = sc.getId();
			Log.i(G, "xxvv good xxx");
			if (umidD == null) {
				umidD = new ContentValues();
				cmidD = new ContentValues();
				xmidD = new ContentValues();
			}

			RelativeLayout sux = (RelativeLayout) findViewById(R.id.umid);// sc.getChildAt(0);

			if (sux == null) {
				setr("xxvv");
				return;
			}

			int xw2 = mHeight - getTopwide() - getBottomwide();// su.getHeight();
			int xw = mWidth - getSidewide();// su.getWidth();

			int xx = xw / 4;
			int yx = xw2 / 4;

			Log.i(G, "xxvv good " + msg.what + "/" + sux.getChildCount() + " "
					+ " " + xw + "," + xw2);
			// cvxh
			// for (int x = sux.getChildCount() - 1; x > 0; x--)
			{

				// try {
				// } catch (ClassCastException ca) {
				// }
				// LinearLayout xl = (LinearLayout) sux.getChildAt(x);
				// sr xxx
				// if (xl == null) {
				// Log.i(G, "cvboff xxx xl");
				// continue;
				// }

				// RelativeLayout.LayoutParams xh = new
				// RelativeLayout.LayoutParams( -1, -2);

				// RelativeLayout.LayoutParams xh =
				// (RelativeLayout.LayoutParams) xl
				// .getLayoutParams();
				// Log.i(G, "20110911 " + xl.getWidth() + ", "
				// + xh.leftMargin);

				// int xli = Integer.parseInt(new String((String)
				// xl.getTag())
				// .replaceFirst("mid", ""));

				// if (xli > -1)
				{
					// || (msg.what > 2 && (msg.what == xli || msg.what ==
					// xl
					// .getId()))) {
					// {

					Cursor e = SqliteWrapper.query(mCtx, getContentResolver(), Uri.parse("content://com.havenskys.elk/moment"), new String[] { "_id", "foundtitle", "sample", "cox", "coxy", "mid", "updated" }, "mid is not null and cox > 0 " + (uux.length() > 0 ? " and updated > '" + uux + "'" : ""), null, "updated desc");

					// ImageView o = new ImageView(mCtx);
					// o.setScaleType(ScaleType.FIT_CENTER);
					// LinearLayout.LayoutParams ol = new
					// LinearLayout.LayoutParams(
					// 45, 45);
					// ol.setMargins(10, -60, 0, 0);
					// o.setLayoutParams(ol);
					// o.setImageBitmap(xt);
					// o.setAlpha(95);
					// su.addView(o);

					RelativeLayout xxbe = null;
					Bitmap xt = null;
					ImageView xxb = null;
					RelativeLayout.LayoutParams xru;
					byte[] g8;
					int umid = -1;
					String tt = "";
					int mid = -1;
					int xvv = -1;
					int xub = 1;
					int nxxc = -1;

					for (int ixxb = 0; e != null && e.moveToPosition(ixxb); ixxb++) {
						if (nxxc > 734) {
							break;
						}
						xxb = null;
						xt = null;
						xxbe = null;
						mid = e.getInt(0);
						tt = e.getString(1);
						g8 = e.getBlob(2);
						umid = e.getInt(5);
						xx = e.getInt(3);
						yx = e.getInt(4);
						uux = e.getString(6);
						// LongClick
						if (umid > 0 && umidD.containsKey("g" + umid)) {
							int vx = umidD.getAsInteger("g" + umid);
							if (vx > 0) {
								xxbe = (RelativeLayout) findViewById(vx);
								// if (xxbe.getChildCount() > 50) {
								// Log.w(G, "xxxx max size");
								// continue;
								// }
							}
						}

						if (g8 != null) {

							// Log.i(G, "size xxx " + mid + ": " + tt + " -- "
							// + g8.length);
							// PNG
							try {
								xt = BitmapFactory.decodeByteArray(g8, 0,
										g8.length);
							} catch (OutOfMemoryError sf) {
								Log.w(G, "OOM xxx");
								break;
							}
						}

						if (xxbe == null) {
							nxxc++;
							RelativeLayout.LayoutParams xxh = new RelativeLayout.LayoutParams(
									-1, -2);
							// xxh.setMargins(umidD.size() * 20,
							// umidD.size() * 20, 0, 0);
							xxbe = new RelativeLayout(mCtx);
							xxbe.setId(hid++);
							xxbe.setLayoutParams(xxh);

							xxbe.setDrawingCacheEnabled(true);
							xxbe.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
							xxbe.setBackgroundColor(Color.argb(10, 8, 14, 88));

							// Animation gx = AnimationUtils.loadAnimation(mCtx,
							// R.anim.woompx);
							// gx.setStartOffset((int) (ixxb * 150 * ixxb));
							// xxbe.startAnimation(gx);

							sux.addView(xxbe);
							umidD.put("g" + umid, (int) xxbe.getId());
							cmidD.put("g" + umid, umidD.size());
						}

						if (xmidD.containsKey("g" + mid)) {
							xxb = (ImageView) findViewById(xmidD
									.getAsInteger("g" + mid));
						}
						if (xxb == null) {
							// xvv = cmidD.getAsInteger("g" + umid) * 20;
							xru = new RelativeLayout.LayoutParams(30, 30);

							xru.setMargins(getSidewide() + (xx * xw / 100) - xx
									/ 2, getTopwide() + (yx * xw2 / 100), 0, 0);
							xxb = new ImageView(mCtx);
							xxb.setId(hid++);
							xxb.setAdjustViewBounds(true);

							xxb.setLayoutParams(xru);
							xxb.setScaleType(ScaleType.FIT_START);

							/*
							 * Bundle bl = new Bundle(); bl.putInt("x", xx);
							 * bl.putInt("y", yx); bl.putBoolean("update",
							 * false); bl.putInt("mid", mid); bl.putInt("id",
							 * xxb.getId()); bl.putInt("left", getSidewide());
							 * bl.putInt("top", getTopwide()); Message ml = new
							 * Message(); ml.setData(bl);
							 * fitco.sendMessageDelayed(ml, 25 + 725 * ixxb); //
							 */
							xmidD.put("g" + mid, (int) xxb.getId());
							if (xt != null) {
								// xxb.setBackgroundDrawable();
								// xxb.setBackgroundResource(R.drawable.bx5);
								xxb.setImageResource(R.drawable.ld4);
								// xxb.setBackgroundColor(Color.argb(160, 230,
								// 230, 10));
								// xxb.setImageResource(R.drawable.ld3);
								// xxb.setPadding(1, 1, 1, 1);

								// xxb.setAlpha(5);
								// xxb.setImageBitmap(xt);
								xt = null;
							} else {
								xxb.setImageResource(R.drawable.ld5);
							}
							// le.b2
							xxbe.addView(xxb);

							Animation gx = AnimationUtils.loadAnimation(mCtx,
									R.anim.xgom4);// nb9
							gx.setStartOffset((int) (xub++ * 25 * xub / 10));
							xxb.clearAnimation();
							xxb.startAnimation(gx);

							final String ttf = tt;
							final int midf = mid;
							// xxb.setOnClickListener(new OnClickListener() {
							// public void onClick(View g) {
							// setr(ttf);
							// }
							// });

							// xxb.setOnLongClickListener(new
							// OnLongClickListener() {
							// public boolean onLongClick(View g) {
							// miesr(-1, midf, false, false);
							// return false;
							// }
							// });

						} else {

							Animation gx = AnimationUtils.loadAnimation(mCtx,
									R.anim.oh);// nb9
							gx.setStartOffset((int) (xub++ * 25 * xub));
							xxb.clearAnimation();
							xxb.startAnimation(gx);

						}

					}

					// Scan
					// / 100
					if (e != null) {
						e.close();
					}

					// "sample"

					// if (xl.getWidth() > xw / 3) {// xh.leftMargin
					// <
					// getSidewide())
					// {

					// if (msg.what > 2) {
					// Animation gxx =
					// AnimationUtils.loadAnimation(mCtx,
					// R.anim.woomp);// nb9
					// xxb.startAnimation(gxx);

					// } else {

					// }
					// break;
					// }
					// xl.setBackgroundResource(R.drawable.c2);

					// xl.setVisibility(View.VISIBLE);
					// xl.setOnClickListener();

					// RelativeLayout xv = (RelativeLayout)
					// findViewById(R.id.cvxhc);
				}

			}

		}
		// kilobytes
	};

	Handler llm = new Handler() {
		public void handleMessage(Message msg) {
			Bundle bx = msg.getData();

			LinearLayout lx = null;
			if (bx.containsKey("tid")) {
				TextView t9 = (TextView) findViewById(bx.getInt("tid"));
				lx = (LinearLayout) t9.getParent();
			} else {
				lx = (LinearLayout) findViewById(bx.getInt("id"));
			}
			int xx = bx.getInt("x", 30);

			RelativeLayout.LayoutParams llr = (RelativeLayout.LayoutParams) lx
					.getLayoutParams();

			// if (llr.leftMargin + 5 < xx) {
			{

				RelativeLayout.LayoutParams rx = new RelativeLayout.LayoutParams(
						getVwide(), llr.height);
				// if (llr.leftMargin + 10 < xx) {
				{
					Bundle bl = new Bundle();
					bl.putInt("id", lx.getId());
					bl.putInt("x", xx);
					Message ml = new Message();
					ml.setData(bl);
					// llm.sendMessageDelayed(ml, 1);
				}

				// rx.setMargins(llr.leftMargin + xx, 0, 0, 0);
				// } else {
				// rx.setMargins(xx, 0, 0, 0);
				// }
				rx.setMargins(llr.leftMargin + xx, llr.topMargin, 0,
						llr.bottomMargin);
				lx.setLayoutParams(rx);
				lx.setMinimumWidth(getVwide());

			}

		}
	};

	Handler sll0x = new Handler() {

		public void handleMessage(Message msg) {
			Bundle bx = msg.getData();

			ScrollView sx = null;
			sx = (ScrollView) findViewById(bx.getInt("sid"));
			// sx.scrollBy(50 + bx.getInt("x"), bx.getInt("x"));

			Animation bohx2 = AnimationUtils.loadAnimation(mCtx, R.anim.oh90x);
			sx.startAnimation(bohx2);

		}
	};

	Handler sllxx = new Handler() {
		int sxb = 2;

		public void handleMessage(Message msg) {
			// Bundle bx = msg.getData();

			// RelativeLayout xu2 = (RelativeLayout) findViewById(R.id.cvxhc);

			ScrollView sx = null;
			sx = (ScrollView) findViewById(R.id.cubc);
			// sx.pageScroll(msg.what);
		}
	};

	int sllxbb = 88;
	Handler sllx = new Handler() {
		int sxb = 2;
		int ssx = 55;

		public void handleMessage(Message msg) {
			Bundle bx = msg.getData();

			RelativeLayout xu2 = (RelativeLayout) findViewById(R.id.cvxhc);

			ScrollView sx = null;
			sx = (ScrollView) findViewById(bx.getInt("sid"));
			if (sx == null) {
				return;
			}
			Log.i(G,
					"sllx good xxx " + sxb + ", " + ssx + " " + sx.getScrollX());

			if (sx.getScrollX() == 0 && ssx != 0) {
				sx.scrollTo(ssx, 0);
			}

			if (bx.containsKey("x2")) {
				sx.scrollTo(bx.getInt("x2", 0), 0);
			} else if (bx.containsKey("x")) {
				int x = bx.getInt("x", 0);
				if (x != 0) {
					sx.scrollBy(x, 0);
				}

				if (sxb == 2) {
					sxb = 3;
					Animation bohx2 = AnimationUtils.loadAnimation(mCtx,
							R.anim.oh9);
					// xu2.startAnimation(bohx2);
				}

			} else if (sxb == 3) {
				sxb = 2;

				Animation bohx2 = AnimationUtils.loadAnimation(mCtx,
						R.anim.oh90x);
				// xu2.startAnimation(bohx2);

				// int x = sx.getScrollX();
				// sx.scrollBy(ssx, 0);
				Log.i(G, "sllx good xxx oh90x");

				ssx = (int) (ssx / sllxbb) * sllxbb;
				{

					Message ml = new Message();
					Bundle bl = new Bundle();
					bl.putInt("sid", R.id.cubc);
					bl.putInt("x2", ssx);
					ml.setData(bl);
					sllx.sendMessageDelayed(ml, 232);
				}

			}

			if (sx.getScrollX() != 0) {
				ssx = sx.getScrollX();
			}

		}
	};

	Handler sllm = new Handler() {
		public void handleMessage(Message msg) {
			Bundle bx = msg.getData();

			// ScrollView sx = null;
			RelativeLayout rx = null;
			// if (bx.containsKey("sid")) {
			// sx = (ScrollView) findViewById(bx.getInt("sid"));
			// rx = (RelativeLayout) sx.getChildAt(0);
			// } else if (bx.containsKey("tid")) {
			// TextView t9 = (TextView) findViewById(bx.getInt("tid"));
			// LinearLayout lx = (LinearLayout) t9.getParent();
			// rx = (RelativeLayout) lx.getParent();
			// sx = (ScrollView) rx.getParent();
			// } else {
			// sx = (ScrollView) findViewById(bx.getInt("id"));
			// rx = (RelativeLayout) findViewById(R.id.cvxhc);
			// rx = (RelativeLayout) sx.getChildAt(0);
			// }

			rx = (RelativeLayout) findViewById(R.id.cvxhc);

			int xx = bx.getInt("x", 8);
			ScrollView.LayoutParams slr = (ScrollView.LayoutParams) rx
					.getLayoutParams();
			ScrollView.LayoutParams sxl = new ScrollView.LayoutParams(
					slr.width, slr.height);

			// RelativeLayout.LayoutParams slr = (RelativeLayout.LayoutParams)
			// rx
			// .getLayoutParams();

			// RelativeLayout.LayoutParams sxl = new
			// RelativeLayout.LayoutParams(
			// slr.width, slr.height);

			// RelativeLayout.LayoutParams llr = (RelativeLayout.LayoutParams)
			// sx
			// .getLayoutParams();
			// if (llr.leftMargin + 5 < xx) {
			// RelativeLayout.LayoutParams rxl = new
			// RelativeLayout.LayoutParams(
			// llr.width, llr.height);
			// if (llr.leftMargin + 10 < xx) {
			{
				Bundle bl = new Bundle();
				// bl.putInt("id", sx.getId());
				bl.putInt("x", xx);
				Message ml = new Message();
				ml.setData(bl);
				// sllm.sendMessageDelayed(ml, 1);
			}

			// Log.i(G,
			// "sllm x(" + xx + ") l(" + llr.width + "," + llr.height
			// + ") r(" + llr.leftMargin + "," + llr.topMargin
			// + "," + llr.rightMargin + "," + llr.bottomMargin
			// + ") s(" + sx.getScrollX() + ")");
			// sx.smoothScrollTo(xx / 2 * -1, 0);

			// rxl.setMargins(getSidewide() + xx, llr.topMargin,
			// llr.rightMargin,
			// llr.bottomMargin);
			// sx.setLayoutParams(rxl);

			sxl.setMargins(getSidewide() + xx, slr.topMargin, slr.rightMargin,
					slr.bottomMargin);

			rx.setLayoutParams(sxl);

			// }

		}
	};

	Handler cvboff2 = new Handler() {
		public void handleMessage(Message msg) {
			// buccoff2
			Log.i(G, "cvboff2 good xxx");
			if (msg.what == 2) {

				ScrollView xxcub = (ScrollView) findViewById(R.id.cub);
				xxcub.setVisibility(View.GONE);
				nbi.removeAllViews();
				return;
			}

			LinearLayout xxo = (LinearLayout) findViewById(msg.what);
			// buc.setLongClickable(true);
			// buc.setVisibility(View.GONE);// buc.getVis 20110911
			if (xxo != null) {

				xxo.setVisibility(View.GONE);
				RelativeLayout xh = (RelativeLayout) xxo.getParent();
				if (xh != null) {
					xh.removeView(xxo);

					if (xh.getChildCount() == 0) {
						// buc.setVisibility(View.GONE);

						// buc.removeAllViews();
						cvboff2.sendEmptyMessageDelayed(2, 5);
					}
					// Log.i(G, "xxxa " + xh.getChildCount());
					// // Log.i(G, "doc xxxx g");

					// cvb
				}

				// id.cvb id.cub
			}

		}
	};

	Handler cvbon = new Handler() {
		public void handleMessage(Message msg) {
			// buc.getVis 20110911
			// if (buc.getVisibility() != View.VISIBLE)
			Log.i(G, "cvbon good xxx");
			// buc.setLongClickable(false);
			// buc.setClickable(false);
			// buc.setFocusable(false);

			ScrollView xxcub = (ScrollView) findViewById(R.id.cub);
			xxcub.setVisibility(View.VISIBLE);

			LinearLayout xh = (LinearLayout) findViewById(msg.what);
			if (xh != null) {
				Log.i(G, "cvbon good");
				// Animation x4 = buc.getAnimation();
				// if (x4 != null) {
				// x4.cancel();
				// }
				// buc.setVisibility(View.VISIBLE);
				xh.clearAnimation();
				Animation gx = AnimationUtils.loadAnimation(mCtx, R.anim.xgom);
				// gx.setDuration(1000);
				// gx.restrictDuration(1200);
				xh.setVisibility(View.INVISIBLE);
				gx.scaleCurrentDuration((float) 0.5);
				xh.clearAnimation();
				xh.startAnimation(gx);

				cvbon2.sendEmptyMessageDelayed(msg.what, 125);

				sizekln.sendEmptyMessageDelayed(2, 25);
				// bucx.setVisibility(View.GONE);
			}

			// bui.setVisibility(View.GONE);
			// if (msg.what == R.id.hux) {
			// bucx.setVisibility(View.VISIBLE);
			// bucb.setVisibility(View.VISIBLE);
			// bucxb.setVisibility(View.VISIBLE);
			// } else if (msg.what == R.id.hu) {
			// buc.setVisibility(View.VISIBLE);
			// bucx.setVisibility(View.GONE);
			// bucb.setVisibility(View.VISIBLE);
			// bucxb.setVisibility(View.GONE);
			// }
		}
	};
	Handler cvbon2 = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "cvbon2 good xxx");
			LinearLayout xh = (LinearLayout) findViewById(msg.what);
			if (xh != null) {

				xh.setVisibility(View.VISIBLE);
			}
		}
	};

	Handler buihide = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "buihide good xxx");
			bui.setVisibility(View.GONE);
		}
	};

	Handler cvbback = new Handler() {
		boolean u7 = false;

		public void handleMessage(Message msg) {
			Log.i(G, "cvbback good xxx " + mWidth + "," + mHeight);
			{
				Message ml = new Message();
				Bundle bl = new Bundle();
				bl.putString("text", ", ");
				bl.putInt("type", TextToSpeech.QUEUE_FLUSH);
				ml.setData(bl);

				xape.sendMessageDelayed(ml, 75);
			}
			if (xuin != null) {
				Message ml = new Message();
				Bundle bl = new Bundle();
				bl.putString("text", "audio file");
				ml.setData(bl);
				xuo.sendMessageDelayed(ml, 35);
			}

			if (u7) {
				u7 = false;
				// buc.setVisibility(View.);

			} else {
				u7 = true;
			}

			buihide.sendEmptyMessageDelayed(2, 45);

			// if (msu.getVisibility() == View.VISIBLE) {
			joexu.sendEmptyMessageDelayed(2, 25);

			// }
			// buc.getVis 20110911
			// if (//buc.getVisibility() == View.VISIBLE
			// && bucc.getVisibility() != View.VISIBLE) {
			// buccon.sendEmptyMessageDelayed(2, 25);
			// cvboff.sendEmptyMessageDelayed(2, 25);

			// } else if (buc.getVisibility() == View.VISIBLE) {
			cvboff.sendEmptyMessageDelayed(2, 25);
			// buc.setVisibility(View.GONE);
			// } else if (bucc.getVisibility() == View.VISIBLE) {

			buccoff.sendEmptyMessageDelayed(2, 25);
			// } else {

			nbi.setVisibility(View.INVISIBLE);
			sizekln.sendEmptyMessageDelayed(2, 75);
			// }

		}
	};

	public void setr(String req, boolean proceed) {
		Log.i(G, "setr() good xxx");
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putString("req", req);
		bl.putBoolean("proceed", proceed);
		ml.setData(bl);
		setr.sendMessageDelayed(ml, 5);
	}

	public void setr(String req, int dl) {
		Log.i(G, "setr() good xxx");
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putString("req", req);
		ml.setData(bl);
		setr.sendMessageDelayed(ml, dl);
	}

	public void setr(String req) {
		Log.i(G, "setr() good xxx");
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putString("req", req);
		ml.setData(bl);
		setr.sendMessageDelayed(ml, 5);
	}

	Handler setr = new Handler() {
		long setrl = -1;

		public void handleMessage(Message msg) {
			Log.i(G, "setr good xxx");
			Bundle bdl = msg.getData();
			String req = bdl.getString("req");
			if (req == null || req.length() == 0) {
				return;
			}
			boolean proek = bdl.getBoolean("proceed", false);

			if (setrl > System.currentTimeMillis() - 1880) {
				// Message ml = new Message();
				// Bundle bl = new Bundle(bdl);
				// setr.sendMessageDelayed(ml, setrl + 1880);
				// setrl += 1880;
				// return;
			}
			setrl = System.currentTimeMillis();
			bui.setText(req.trim());

			req = req.replaceAll("\n", " ").trim();// Uri.encode(req);
			bui.setVisibility(View.VISIBLE);
			// bui.setLines(1);
			bui.setTextSize((float) 14);
			buihide.removeMessages(2);
			int g7 = req.compareTo(ltxx);

			for (g7 = 0; g7 < req.length() && g7 < ltxx.length(); g7++) {
				if (req.charAt(g7) != ltxx.charAt(g7)) {
					break;
				}
			}

			while (g7 > 0 && g7 < req.length()
					&& new String(req.charAt(g7 - 1) + "").matches("[0-9]")) {
				g7--;
			}
			if (req.contentEquals(ltxx)) {
				return;
			}

			buihide.sendEmptyMessageDelayed(2, 5345);
			Log.i(G, "setr good: " + req + " -- " + ltxx + " -- " + g7);
			ltxx = req;
			if (g7 == 0) {
			} else if (g7 >= req.length()) {
			} else if (g7 > 0) {

				req = req.substring(g7).trim();
				// } else {
				// req = req.substring((g7 * -1)-1);
			}
			Log.i(G, "setr xxx " + req + " -- " + g7);

			if (mTts != null) {
				// mTts.speak(req, TextToSpeech.QUEUE_FLUSH, null);
				sr.sendEmptyMessageDelayed(2, 25);
				Message ml = new Message();
				Bundle bl = new Bundle();
				bl.putString("text", req);
				bl.putBoolean("proceed", proek);
				ml.setData(bl);
				xape.sendMessageDelayed(ml, 375);

			}

			// ble.b8
		}

		String ltxx = "";
	};

	public void xape(String text, int TextToSpeechType) {
		Log.i(G, "xape() good xxx");
		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putString("text", text);
		bl.putInt("type", TextToSpeechType);
		bl.putBoolean("proceed", false);
		ml.setData(bl);
		xape.sendMessageDelayed(ml, 5);

	}

	Handler xape = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "xape good xxx");
			Bundle bdl = msg.getData();
			final String x4f = bdl.getString("text");
			boolean proek = bdl.getBoolean("proceed");
			final int x5f = bdl.getInt("type", TextToSpeech.QUEUE_ADD);

			// Thread hx0 = new Thread() {
			// public void run() {
			String x4 = x4f;
			int x5 = x5f;

			// x4 = Uri.parse(x4 + " ").toString().replaceAll("%5[BD]", "")
			// .replaceAll("%28.*?%29", "");// .replaceAll("%3A[0-9][0-9]$");
			// x4 = Uri.decode(x4);
			// if (x4.contains("\\[\\.\\.\\.\\]")) {
			// x4 = x4.replaceFirst("\\[\\.\\.\\.\\]", "");
			// }

			if (x4.contains("www.")) {
				x4 = x4.replaceAll("www.", "... ");
			}
			x4 = x4.replaceAll(" -- ", ".. .. ");

			if (x4.startsWith("http")) {

				x4 = x4.replaceAll("http://", "... ");
				x4 = x4.replaceAll("https://", "... secure ");
				x4 = x4.replaceAll("/.*", "");

				// while (x4.matches(".*/.*?/.*")) {
				// x4 = x4.replaceAll("/.*?/", " . ");
				// }

			}

			if (x4.contains("spdblotter")) {
				x4 = x4.replaceAll("spdblotter", "SPD Blotter");
			}

			if (mTtsl.contentEquals(x4)) {
				Log.i(G, "xape xxx hush " + x4);
			} else {
				mTtsl = x4;

				if (uvccT == null) {
					loadUvccT();
				}

				float s = (float) tempo / 100;
				float p = (float) pitch / 100;
				int xns = 1;
				int xnp = 1;

				for (char b = 'a'; b < '{'; b++) {
					if (!x4.contains("" + b)) {
						continue;
					}
					if (uvccT.containsKey("s" + b)) {
						xns++;
						s += uvccT.getAsFloat("s" + b);
					}
					if (uvccT.containsKey("p" + b)) {
						xnp++;
						p += uvccT.getAsFloat("p" + b);
					}
				}

				Log.i(G, "p:" + (p / xnp) + " s:" + (s / xns));
				try {
					mTts.setPitch(p / xnp);
					mTts.setSpeechRate(s / xns);
					x4 += " ... ";

					mTts.speak(x4, x5, null);
				} catch (RuntimeException sd) {
					Log.e(G, "xxx");
					sd.printStackTrace();
				}
				// if (proek) {}
				// mTts.speak("Read this?", TextToSpeech.QUEUE_ADD, null);
				// }

			}
			// }
			// };
			// hx0.start();

			// Message ml = new Message();
			// Bundle bl = new Bundle();
			// bl.putString("text", hi);
			// bl.putInt("type", TextToSpeech.QUEUE_ADD);
			// ml.setData(bl);
			// xape.sendMessageDelayed(ml, 375);

		}
	};

	public void loadUvccT() {
		Log.i(G, "loadUvccT() good xxx");
		uvccT = new ContentValues();
		SharedPreferences mReg = getSharedPreferences("Preferences",
				MODE_WORLD_READABLE);

		for (char b = 'a'; b < '{'; b++) {
			if (mReg.contains("s" + b)) {
				uvccT.put("s" + b, mReg.getFloat("s" + b, (float) 0.93));
			}
			if (mReg.contains("p" + b)) {
				uvccT.put("p" + b, mReg.getFloat("p" + b, (float) 0.93));
			}
		}
	}

	AnimationDrawable bru;
	ImageView bv;

	Handler breath = new Handler() {

		public void handleMessage(Message msg) {
			Log.i(G, "breath good xxx");
			Bundle bdl = msg.getData();
			bru.start();

			// breath.sendEmptyMessageDelayed(2,12000);

		}

	};
	Animation nb8;
	Handler requestrun = new Handler() {

		public void handleMessage(Message msg) {
			Log.i(G, "requestrun good xxx");
			Bundle bdl = msg.getData();

			// ImageView nb4 = (ImageView) findViewById(R.id.nb4);
			// nb4.startAnimation(nb8);

		}

	};

	public void animate() {

		if (!mgl) {
			return;
		}

		if (mHome && !mAtHome && mRen.mLCameraZ == m1Cz
				&& mRen.mLCameraX == m1Cx && mRen.mLCameraY == m1Cy
				&& mRen.mAngleX == m1Ax && mRen.mAngleY == m1Ay
				&& mRen.mAngleZ == m1Az) {
			/*
			 * { Message msg = new Message(); Bundle b = new Bundle();
			 * b.putString("message", "Home"); msg.setData(b);
			 * mToastHandler.sendMessage(msg); }//
			 */
			mHome = false;
			mAtHome = true;
		}

		if ((mRen.mLCameraZ < -3250f || mRen.mLCameraZ > 3250f || mHome || mAtHome)
				&& !(mRen.mLCameraZ == m1Cz && mRen.mLCameraX == m1Cx && mRen.mLCameraY == m1Cy)) {
			mHome = true;
			mRen.mCameraZ = m1Cz;
			mRen.mCameraX = m1Cx;
			mRen.mCameraY = m1Cy;
			mRen.mAngleX = m1Ax;
			mRen.mAngleY = m1Ay;
			mRen.mAngleZ = m1Az;
		}

		/*
		 * if(mRen.mLCameraZ < 0){ if(mRen.mLAngleX < 180 ){ mRen.mAngleX =
		 * mRen.mLAngleX + 180; } }else{
		 * 
		 * if(mRen.mLAngleX > 180){ mRen.mAngleX = mRen.mLAngleX - 180; }
		 * 
		 * }//
		 */

		// orbit

		int t, j, i, k;
		float incm = 0;
		float it = 0;
		float od = 0f;
		for (t = 0; t < mPurpose.length; t++) {
			if (mPurpose[t] != null) {

				if (t == console) {
					// Log.w(G,"camera-console (x"+mPurpose[t].x+",y"+mPurpose[t].y+",z"+mPurpose[t].z+") p(x"+mWorlds[2].mPivotx+",y"+mWorlds[2].mPivoty+",z"+mWorlds[2].mPivotz+") ");
					// mWorlds[2].mTrimx = mRen.mLCameraX;
					// mWorlds[2].mTrimy = mRen.mLCameraY;
					// mWorlds[2].mTrimz = mRen.mLCameraZ-5;
					// mPurpose[console].setEt(mRen.mLCameraX-1f,
					// mRen.mLCameraY-1f, (mRen.mLCameraZ-1f));
					// mPurposeU[console] = (mRen.mAngleY-180);
					// mPurpose[console].setAngle(mRen.mAngleX-180);
				}

				if (mAnPause && t > lathing) {
					mPurposeU[t] = mPurpose[t].mAngle;
					continue;
				} else {
					if (mPurposeU[t] < 0 && mPurposeU[t] > -1) {
						mPurposeU[t] = 0;
					}
					if (mPurposeU[t] != mPurpose[t].mAngle) {

						// normalize float size to two places
						if (!mPurpose[t].mAnimating) {
							// mPurpose[t].clearAnimation();
							mPurpose[t].startAnimation();
							int h4 = (int) (mPurposeU[t] * 1000f);
							mPurposeU[t] = (float) h4 * 0.001f;
						}

						od = (mPurposeU[t] > mPurpose[t].mAngle ? (mPurposeU[t] - mPurpose[t].mAngle)
								: (mPurpose[t].mAngle - mPurposeU[t]));// (mPurpose[t].mAngle
																		// *(float)
																		// Math.PI/2f);
						incm = od * 0.005f;

						if (incm > 0.02) {
							// incm = 0.05f;
							if (od < 0.08f) {
								incm = 0.08f;
							} else if (od > 2f) {
								od = 2f;
							}
							// if(od < 0.3f){incm *= 1.1f;}
							// if(od >= 10f){incm *= 1.1f;}
							// if(od >= 100f){incm *= 1.1f;}
							// else if(od < 10f){incm *= 0.14f;}
							// else if(od < 100f){incm *= 1.2f;}
							// else if(od < 500f){incm *= 0.08f;}

							if (mPurpose[t].mAngle == mPurposeU[t]) {
								it = mPurpose[t].mAngle;
								it = mPurposeU[t];
							} else if (mPurpose[t].mAngle < mPurposeU[t]) {
								it = mPurpose[t].mAngle + incm;
							} else {
								it = mPurpose[t].mAngle - incm;
								// if(mPurposeU[t] < 0.02f){mPurposeU[t] =
								// 0.02f;}
							}
							// if(it < 0.01f && mPurposeU[t] < 0.01f){
							// it = 0f; mPurposeU[t] = 0f;
							// }
							// if(it < 0 && mPurpose[t].mAngle > 1){

							// }

							if (it >= mPurposeU[t] - incm
									&& it <= mPurposeU[t] + incm) {
								mPurposeU[t] = mPurpose[t].mAngle;

								mPurpose[t].setAngle(mA[t], mPurpose[t].mAngle);// mPurpose[t].mAngle);
								mPurpose[t].endAnimation();
								// mPurpose[t].mAnimating = false;
							} else {
								// if(t > orbitlimit){
								// Log.w(G,"animate() "+t+" od("+od+")" + incm +
								// "+" + mPurpose[t].mAngle + " in "+it+" to " +
								// mPurposeU[t]);
								// }
								// if(orbitlimit > t){
								// Log.w(G,"od("+od+") incm("+incm+") ["+mPurpose[t].mAngle+"    ^"+(mPurpose[t].mAngle>mPurposeU[t]?mPurpose[t].mAngle-mPurposeU[t]:mPurposeU[t]-mPurpose[t].mAngle)+"^       "+mPurposeU[t]+"]");
								// }
								mPurpose[t].setAngle(mA[t], it);

							}
							// InPart[] ground;for(i = 0, k=0; i <
							// mPart[t].length && mPart[t][i] != null;i++)
							// {ground = mPurpose[t].mShapes;ground[k++] =
							// mPart[t][i];}
							continue;
						}

					}
				}
				// Log.w(G,"animate() "+t+" " + mPurpose[t].mAngle + " in " +
				// mPurposeU[t]);

				mPurposeU[t] = mPurpose[t].mAngle;

			} else {
				break;
			}
		}
		updateInPurpose();

	}

	private void updateInPurpose() {

		// Log.i(G, "updateInPurpose() good xxx");
		int t, i, j, k;

		InPurpose thing;
		InPart[] shapes;
		// if(mAthing != null){
		// int ak = 0;
		// InPart[] allashapes = mPurpose[lathing].mShapes;
		// InPart[] allbshapes = mBthing.mShapes;
		for (t = 0; t < mPurpose.length && mPurpose[t] != null; t++) {
			shapes = mPurpose[t].mShapes;
			for (i = 0, k = 0; i < mPart[t].length && mPart[t][i] != null; i++) {
				shapes[k++] = mPart[t][i];
				// allbshapes[ak] = cube;
				// allashapes[ak] = cube;
				// ak++;
			}

		}
		// }
	}

	private Handler mToastHandler = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "mToastHandler good xxx");
			Bundle b = msg.getData();
			String message = b.getString("message");
			Toast.makeText(mCtx, message, Toast.LENGTH_SHORT).show();
		}
		/*
		 * Message msg = new Message(); Bundle b = new Bundle();
		 * b.putString("message", "Mode " + mode); msg.setData(b);
		 * mToastHandler.sendMessage(msg); //
		 */

	};

	private World mkGLwork() {
		Log.i(G, "mkGLwork() good xxx");
		World world = new World();
		world.mTrimx = -50f; // distance
		world.mTrimy = -10f;
		world.mTrimz = 10f;
		// world.mPivotx = 30.0f;//
		// world.mPivoty = 30.0f;//y tall
		// world.mPivotz = 30.0f;

		int t = 1;
		int j;
		for (t = 0; t < mPurpose.length; t++) {
			if (mPurpose[t] != null) {
			} else {
				mPurpose[t] = new InPurpose();

				mGLworkid = t;
				mA[t] = InPurpose.kAxisX;
				// mPurpose[t].setPivot(30f,30f,30f);
				mPart[t][0] = new InPartShape(world, -10.7f, -10.2f, -13.7f,
						14.5f, 16.7f, 14.5f);

				mPart[t][0].x = -50f;
				mPart[t][0].y = -10f;
				mPart[t][0].z = 10f;

				// mPart[t][0]
				for (j = 0; j < 6; j++) {
					if (j == 0 || j == 5) {
						mPart[t][0].setFaceColor(j, black);
						continue;
					}
					mPart[t][0].setFaceColor(j, orange);
				}
				world.addShape(mPart[t][0]);

				// t++;
				// mPurpose[t] = new InPurpose();
				// mA[t] = InPurpose.kAxisY;
				// mPart[t][0] = mPart[t-1][0];
				// lbbrtf
				// angle(y-9.400001,x-202.20007,z0.0) camera(-1.4,-13.6,-87.0)
				// world.addShape(mPart[t-1][0]);
				break;
			}
		}

		updateInPurpose();
		world.generate();
		return world;
	}

	// orbit
	private World mkConsole() {
		Log.i(G, "mkConsole() good xxx");
		World world = new World();
		world.mTrimx = 0f; // distance
		world.mTrimy = 0f;
		world.mTrimz = 0f;
		world.mPivotx = -110f;//
		world.mPivoty = -10f;// y tall
		world.mPivotz = 0f;

		int t = 1;
		for (t = 0; t < mPurpose.length; t++) {
			if (mPurpose[t] != null) {
				continue;
			} else {
				console = t;
				mPurpose[t] = new InPurpose();
				mA[t] = InPurpose.kAxisY;
				mPurpose[t].setAngle(mA[t], 0f);
				// mPurposeU[t] = 0f;
				// mPurposeU[t] = 10f;
				// mPart[t][0] = new InPartShape(world,0,90,108f,52f,125,110f);
				// mPart[t][1] = new InPartShape(world,(0f),(60),(108f), (52f),
				// (85.0f), (110f) );
				// mPart[t][2] = new InPartShape(world,(52f),(60),(108f),
				// (140f), (62.0f), (110f) );
				// mPart[t][3] = new InPartShape(world,(52f),(54),(108f),
				// (140f), (56.0f), (110f) );
				mPart[t][0] = new InPartShape(world, (0f), (0), (108f), (52f),
						(50.0f), (110f));
				// mPart[t][5] = new InPartShape(world,(0f),(-50f),(108f),
				// (52f), (25.0f), (110f) );
				int j = 1;
				for (int i = 0; i <= 0 && mPart[t] != null; i++) {
					for (j = 0; j < 6; j++) {
						if (j == 0 || j == 5) {
							mPart[t][i].setFaceColor(j, black);
							continue;
						}
						mPart[t][i].setFaceColor(j, orange);
					}
				}
				world.addShape(mPart[t][0]);
				// world.addShape(mPart[t][1]);
				// world.addShape(mPart[t][2]);
				// world.addShape(mPart[t][3]);
				// world.addShape(mPart[t][4]);
				// world.addShape(mPart[t][5]);

				break;
			}
		}

		updateInPurpose();
		world.generate();
		return world;
	}

	// orbit
	// console

	private World mkWorld() {
		Log.i(G, "mkWorld() good xxx");
		int i, j;
		int t = 1;
		int one = 0x10000;
		int half = 0x08000;

		World world = new World();
		world.mTrimx = 2f;
		// left bottom(X) back right top front
		// mPart[0][0] = new InPartShape(world, 3.5f, 1.76f, 2.0f, 3.76f, 2.0f,
		// 2.76f);
		/*
		 * mPart[0][1] = new InPartShape(world, 3.0f, 4.0f, 3.0f, 3.3f, 5.0f,
		 * 4.0f); mPart[0][2] = new InPartShape(world, 3.0f, 4.1f, 4.5f, 3.3f,
		 * 5.1f, 6.5f); mPart[0][3] = new InPartShape(world, 3.0f, 4.2f, 7.0f,
		 * 3.3f, 5.2f, 8.0f); mPart[0][4] = new InPartShape(world, 3.0f, 2.5f,
		 * 3.0f, 3.3f, 3.5f, 4.0f); mPart[0][5] = new InPartShape(world, 3.0f,
		 * 2.6f, 4.5f, 3.3f, 3.6f, 6.5f); mPart[0][6] = new InPartShape(world,
		 * 3.0f, 2.7f, 7.0f, 3.3f, 3.7f, 8.0f); // mPart[0][0] = new
		 * InPartShape(world, -5.0f, -1.5f, -1.0f, 5.5f, -0.2f, 1.1f);
		 * 
		 * mPart[4][0] = new InPartShape(world, 3.5f, 2.0f, -2.0f, 3.76f, 3.0f,
		 * -0.76f); mPart[4][1] = new InPartShape(world, 3.0f, 4.0f, 3.0f, 3.3f,
		 * 5.0f, 4.0f); mPart[4][2] = new InPartShape(world, 3.0f, 4.1f, 4.5f,
		 * 3.3f, 5.1f, 6.5f); mPart[4][3] = new InPartShape(world, 3.0f, 4.2f,
		 * 7.0f, 3.3f, 5.2f, 8.0f); mPart[4][4] = new InPartShape(world, 3.0f,
		 * 2.5f, 3.0f, 3.3f, 3.5f, 4.0f); mPart[4][5] = new InPartShape(world,
		 * 3.0f, 2.6f, 4.5f, 3.3f, 3.6f, 6.5f); mPart[4][6] = new
		 * InPartShape(world, 3.0f, 2.7f, 7.0f, 3.3f, 3.7f, 8.0f); //
		 * mPart[4][0] = new InPartShape(world, 3.5f, 2.0f, -2.0f, 3.76f, 3.0f,
		 * -0.76f); //
		 */
		// mPart[1][0] = new InPartShape(world, -11.0f, 0.32f, -1.0f, -10.32f,
		// 1.0f, -0.32f);
		// mPart[0][0] = new InPartShape(world, 2.5f, 3.5f, 9.5f, 3.5f, 6.5f,
		// 0.5f);
		// mPart[0][1] = new InPartShape(world, 3.5f, 3.76f, 9.5f, 4.5f, 2.0f,
		// 2.0f);
		// mPart[0][0] = new InPartShape(world, -0.7f, 3.5f, 9.5f, 0.7f, 6.5f,
		// 0.5f);
		// mPart[1][0] = new InPartShape(world, -0.7f, 3.5f, 9.5f, 0.7f, 6.5f,
		// 0.5f);

		// x left-right/
		mPart[0][0] = new InPartShape(world, -4.7f, -4.7f, -24.4f, 4.5f, 4.5f,
				-24.7f);
		mPart[0][1] = new InPartShape(world, -4.7f, -4.7f, -54.4f, 4.5f, 4.5f,
				-54.7f);
		mPart[0][2] = new InPartShape(world, -4.7f, -4.7f, -154.4f, 4.5f, 4.5f,
				-154.7f);

		// z in-out
		mPart[1][0] = new InPartShape(world, -4.7f, -25.3f, -3.7f, 4.5f,
				-25.0f, 4.5f);
		mPart[1][1] = new InPartShape(world, -4.7f, -55.3f, -3.7f, 4.5f,
				-55.0f, 4.5f);
		mPart[1][2] = new InPartShape(world, -4.7f, -155.3f, -3.7f, 4.5f,
				-155.0f, 4.5f);
		// y up-down
		mPart[2][0] = new InPartShape(world, -4.7f, -24.7f, -4.7f, 4.5f,
				-24.4f, 4.5f);
		mPart[2][1] = new InPartShape(world, -4.7f, -54.7f, -4.7f, 4.5f,
				-54.4f, 4.5f);
		mPart[2][2] = new InPartShape(world, -4.7f, -154.7f, -4.7f, 4.5f,
				-154.4f, 4.5f);

		// platform
		mPart[3][0] = new InPartShape(world, -62.7f, 16.2f, -64.7f, -24.5f,
				16.7f, -24.5f);
		// mPart[4][0] = new InPartShape(world, 2f, 4f, 9f, 4f, 7f, 9f);
		// mPart[5][0] = new InPartShape(world, -1f, 4f, 9f, 1f, 7f, 9f);
		// mPart[3][0] = new InPartShape(world, -5.0f, -4.0f, -5.0f, 5.0f,
		// -3.5f, 5.0f);

		// mXthing = new InPurpose(InPurpose.kAxisX);
		// mBthing = new InPurpose(InPurpose.kAxisY);
		mPurpose[0] = new InPurpose();
		mA[0] = (InPurpose.kAxisY);// spin horizontally

		mPurpose[1] = new InPurpose();
		mA[1] = (InPurpose.kAxisZ);

		mPurpose[2] = new InPurpose();
		mA[2] = (InPurpose.kAxisX);

		mPurpose[3] = new InPurpose();
		mA[3] = (InPurpose.kAxisX);
		// mPurpose[0].mAxis =
		// InPurpose.kAxisX;mPurpose[0].setAngle(155);mPurpose[0].mAxis =
		// InPurpose.kAxisY;
		// mPurpose[1].mAxis =
		// InPurpose.kAxisY;mPurpose[1].setAngle(155);mPurpose[1].mAxis =
		// InPurpose.kAxisZ;
		// mPurpose[2].mAxis =
		// InPurpose.kAxisZ;mPurpose[2].setAngle(155);mPurpose[2].mAxis =
		// InPurpose.kAxisX;

		// mPurpose[4] = new InPurpose(InPurpose.kAxisY);
		// mPurpose[5] = new InPurpose(InPurpose.kAxisZ);
		lathing = 3;

		t = lathing;

		/*
		 * for(int g=0;g<GRAPH_SIZE;g++){ t++;mPart[t][0] = new
		 * InPartShape(world
		 * ,-1.5f,4f+(g*0.15f)+(g*0.12f),7.8f+(g*g*0.015f),1.5f,
		 * 8f+(g*0.15f)+(g*0.12f),8f+(g*g*0.015f)); } t = lathing; for(int
		 * g=0;g<GRAPH_SIZE;g++){ t++;mPurpose[t] = new
		 * InPurpose(InPurpose.kAxisY); mPurposeU[t] = g*g* 0.25f;
		 * mPurpose[t].setAngle(mPurposeU[t]); }//
		 */
		// query

		for (int g = 0; g < GRAPH_SIZE; g++) {// left bottom back -- right top
												// front
			t++;

			mPart[t][0] = new InPartShape(world, -1.5f, 4f - (g * g * 0.15f)
					- (g * 1.12f), 19.2f + (g * g * 0.15f), 1.5f, 8f
					- (g * g * 0.15f) - (g * 1.12f), 20.8f + (g * g * 0.15f));
			mPurposeU[t] = g * g * 5.25f;
			mPurpose[t] = new InPurpose();
			mA[t] = InPurpose.kAxisY;
			mPurpose[t].setAngle(mA[t], mPurposeU[t]);

		}

		/*
		 * for(int g=0;g<GRAPH_SIZE;g++){//left bottom back -- right top front
		 * t++; mPart[t][0] = new
		 * InPartShape(world,-1f+(g*5.5f),-3f+(g*0.5f),22.8f-(g*0.2f),
		 * 5f+(g*5.5f),2f+(g*0.5f),23f-(g*0.2f)); mPurposeU[t] = g*g*0.25f;
		 * mPurpose[t] = new InPurpose();mA[t] = (InPurpose.kAxisY);
		 * mPurpose[t].setAngle(mA[t],mPurposeU[t]); //world.loadTexture((GL10)
		 * mPurpose[t],mCtx,R.drawable.redt); }//
		 */

		orbitlimit = t;

		// */

		// mPurpose[t].setAngle(f*0.15f);
		// mPart[t][0] = new
		// InPartShape(world,-1f,4f+g*0.1f,8.8f+g*0.1f,1f,7f+g*0.1f,9f+g*0.1f);

		// mPart[t][0].glRotate(0.45f * g, 0, 1, 0);
		// mPurpose[t].setAngle(0.15f*g);
		// for(j=0;j<6;j++){mPart[t][g].setFaceColor(j,white);}
		// world.addShape(mPart[t][g]);

		// mPurpose[2].setAngle(10);
		for (t = 0; t < mPurpose.length; t++) {
			InPurpose thing = mPurpose[t];
			if (thing != null) {

				for (i = 0; i < mPart[t].length; i++) {
					InPartShape cube = mPart[t][i];
					if (cube != null) {

						// for(j=0;j<6;j++){cube.setFaceColor(j,white);}

						if (t > 1) {
							cube.setFaceColor(5, orange);
							cube.setFaceColor(1, white);
							cube.setFaceColor(0, green);
							cube.setFaceColor(2, white);
							cube.setFaceColor(3, white);
							cube.setFaceColor(4, white);

						} else {
							cube.setFaceColor(0, green);// bottom
							cube.setFaceColor(1, black);// back
							cube.setFaceColor(2, yellow);// right
							cube.setFaceColor(3, orange);// left
							cube.setFaceColor(4, red);// front
							cube.setFaceColor(5, blue);// top

						}

						world.addShape(cube);

					}
				}

			}
		}

		// for(j=0;j<6;j++){if(j==0||j==5){mPart[2][0].setFaceColor(j,white);continue;}mPart[2][0].setFaceColor(j,blue);}
		// for(t =
		// 0;t<4;t++){for(j=0;j<6;j++){if(j==0||j==5){mPart[t][0].setFaceColor(j,black);continue;}mPart[t][0].setFaceColor(j,orange);}}
		// for(j=0;j<6;j++){if(j==0||j==5){mPart[2][2].setFaceColor(j,white);continue;}mPart[2][2].setFaceColor(j,yellow);}
		// for(j=0;j<6;j++){if(j==0||j==5){mPart[3][0].setFaceColor(j,white);continue;}mPart[3][0].setFaceColor(j,orange);}

		updateInPurpose();

		world.generate();
		return world;

	}

	private boolean mHome = true;
	private boolean mAtHome = false;
	float m1Ay = 28f;
	float m1Ax = -148f;
	float m1Az = 0;
	float m1Cy = 0f;
	float m1Cx = 0f;
	float m1Cz = -35f;
	InPurpose[] mPurpose;
	float[] mPurposeU = new float[201];
	int[] mA = new int[2001];
	int console = 1;
	int lathing = 0;
	boolean mAnPause = false;
	InPartShape[][] mPart = new InPartShape[201][201];
	float mRAngleY = 0f;
	float mRAngleX = 50f;
	float mRAngleZ = 0;
	float mRecentY = 50f;
	float mRecentX = 0f;
	float mRecentZ = -150f;
	private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	int mSelected = -1;
	GLColor black = new GLColor(110, 110, 110);
	GLColor red = new GLColor(210, 110, 0);
	GLColor green = new GLColor(0, 210, 110);
	GLColor blue = new GLColor(0, 110, 210);
	GLColor yellow = new GLColor(210, 210, 0);
	GLColor orange = new GLColor(210, 110, 110);
	GLColor white = new GLColor(210, 210, 210);
	int GRAPH_SIZE = 12;
	int mGLworkid = -1;

}
