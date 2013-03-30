package com.havenskys.elk.ast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

import com.havenskys.elk.DataProvider;
import com.havenskys.elk.R;
import com.havenskys.elk.SqliteWrapper;
import com.havenskys.elk.Motion;

public class Scan extends Handler {

	String G = "ElkScan";

	Context mCtx;
	Uri mCp;
	int hid = 21304;
	Handler mSetr = null;
	LinearLayout mL;
	// ScrollView mS;
	// ListView mV;
	Handler mLmt;
	// Handler mGetxi;
	boolean mFlt;
	Looper mxL;
	Handler mNew;
	Handler mAppend;
	Handler mUpdate;
	Handler mPack;
	static int MULTIP = 3;
	boolean intv = false;
	// boolean mAddx = false;
	int mMid = -1;
	int mP = 0;

	public Scan(Looper hh) {
		super(hh);
	}

	// ListView hv,
	public void set(Context context, Uri contentpath, Handler setr,
			LinearLayout hl, Handler lmt, boolean filter, int mid, Handler nx,
			Handler mx, Handler ux, Handler px) {

		Log.i(G, "Scan good xxx");

		// mAddx = addx;
		mMid = mid;
		mCp = contentpath;
		mCtx = context;
		mNew = nx;
		mUpdate = ux;
		mAppend = mx;
		mPack = px;
		if (setr != null) {
			mSetr = setr;
		}

		mFlt = filter;
		hid = (int) SystemClock.uptimeMillis() - 4568 * 234;

		if (hl != null) {
			// mS = hs;
			mL = hl;
		} else {
			intv = true;
			// mS = new ScrollView(mCtx);// new RelativeLayout.LayoutParams(-1,
			// -1)
			// mS.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
			// mS.setId(hid++);
			mL = new LinearLayout(mCtx);// new ScrollView.LayoutParams(-1, -2)
			mL.setLayoutParams(new ScrollView.LayoutParams(-1, -2));
			mL.setId(hid++);
			mL.setOrientation(LinearLayout.VERTICAL);

		}

		// mV = hv;
		mLmt = lmt;
		// mGetxi = getxi;

		// TextView u9 = new TextView(mCtx);
		// u9.setLayoutParams(new LinearLayout.LayoutParams(-1, 200));
		// u9.setId(hid++);
		// u9.setTextSize((float) 24);
		// u9.setText("Play");
		// mL.addView(u9);

		HandlerThread t8 = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
		t8.start();
		s01 = new s01(t8.getLooper());

		HandlerThread tx = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
		tx.start();
		miesr2 = new miesr2(tx.getLooper());
		HandlerThread tx4 = new HandlerThread(G,
				Process.THREAD_PRIORITY_DEFAULT);
		tx4.start();
		miesr4 = new miesr4(tx4.getLooper());
		HandlerThread tx5 = new HandlerThread(G,
				Process.THREAD_PRIORITY_DEFAULT);
		tx5.start();
		miesr5 = new miesr5(tx5.getLooper());

	}

	public void updateM(String title, int mid) {
		Log.i(G, "updateM(,) good xxx");

		{

			ContentValues v8 = new ContentValues();
			// v8.put("status", status);
			v8.put("title", title);
			SqliteWrapper.update(mCtx, mCtx.getContentResolver(),
					Uri.parse("content://com.havenskys.elk/moment"), v8, "_id="
							+ mid + "", null);
		}

	}

	public void updateM(int status, String title, int mid) {
		Log.i(G, "updateM() good xxx");

		{
			ContentValues v8 = new ContentValues();
			v8.put("status", status);
			v8.put("title", title);
			SqliteWrapper.update(mCtx, mCtx.getContentResolver(),
					Uri.parse("content://com.havenskys.elk/moment"), v8, "_id="
							+ mid + "", null);
		}

	}

	boolean on = false;

	Handler s01;

	public void go(String content) {
		Log.i(G, "go() good xxx");

		if (on) {
			Log.e(G, "go xxxxxxx already run");
			return;
		}
		on = true;

		Bundle bl = new Bundle();
		Message ml = new Message();
		bl.putString("content", content);
		ml.setData(bl);
		s01.sendMessageDelayed(ml, 25);

	}

	public void go(int w) {

		if (on) {
			Log.e(G, "go good xxxxxxx already run");
			return;
		}
		on = true;

		Bundle bdl = new Bundle();
		Message ml = new Message();
		ml.setData(bdl);
		s01.sendMessageDelayed(ml, 25);

		Log.i(G, "go good xxxxxxxxxxxxxxx " + w + " " + mFlt);

	}

	public boolean OVERRIDE = true;

	private class s01 extends Handler {
		public s01(Looper xu) {
			super(xu);
		}

		public void handleMessage(Message msg) {
			Log.i(G, "s01 good xxx");

			Bundle bdl = msg.getData();

			String rcontent = "";
			if (bdl.containsKey("content")) {
				rcontent = bdl.getString("content");
			}
			// int mid = bdl.getInt("mid");
			// int huid = bdl.getInt("huid");
			// int scub = bdl.getInt("scub");
			// boolean xec = bdl.getBoolean("xec");

			// Log.i(G, "s01 " + mid + " " + huid + " " + scub + " " + xec);

			// boolean gs = false;

			//
			Cursor n8 = null;
			if (rcontent.length() == 0) {
				Log.i(G, "s01 xxxx");
				{
					n8 = SqliteWrapper.query(mCtx, mCtx.getContentResolver(),
							Uri.parse("content://com.havenskys.elk/moment"),
							new String[] { "filtered" }, "_id=" + mMid, null,
							null);

					if (n8 != null) {
						if (n8.moveToFirst()) {
							rcontent = n8.getString(0);
							if (rcontent == null) {
								rcontent = "";
							} else if (rcontent.length() > 0) {
								// updateM(11, "Filtered", mMid);
							}
						}
						n8.close();
					}
				}
			}

			//
			if (rcontent.length() == 0) {

				// updateM(11, "Filtering", mMid);
				// Toast.makeText(mCtx, "Filtering", 5643).show();

				// bfilter = new Bundle();
				// bfilter.putBoolean("xec", mAddx);
				// bfilter.putInt("mid", mMid);
				// bfilter.putInt("huid", mL.getId());
				// bfilter.putInt("scub", mS.getId());

				{
					n8 = SqliteWrapper.query(mCtx, mCtx.getContentResolver(),
							Uri.parse("content://com.havenskys.elk/moment"),
							new String[] { "content" }, "_id=" + mMid, null,
							null);

					if (n8 != null) {
						if (n8.moveToFirst()) {
							rcontent = n8.getString(0);
						}
						n8.close();
					}
				}

				mFlt = true;
				rfilter = rcontent;

				miesr2.sendEmptyMessageDelayed(2, 75);

			} else {

				mie(rcontent, mMid, mL.getId(), false);

			}

		}
	}

	Handler miesr2;

	class miesr2 extends Handler {
		public miesr2(Looper xh) {
			super(xh);
		}

		public void handleMessage(Message msg) {

			if (rfilter == null) {
				rfilter = ":)\n<message>Non-content</message>";
			} else {
				if (rfilter.length() == 0) {
					rfilter = ":)\n<message>No Content</message>";
				}
			}
			Log.i(G, "miesr2 good xxxx");
			// Log.i(G, ":" + rfilter.replaceAll("[\n \t]", ""));

			try {
				rfilter = rfilter.replaceAll(">", ">\n");
				rfilter = rfilter.replaceAll("-->", "\n-->");
			} catch (OutOfMemoryError ui) {
				if (msg.what < 8) {
					updateM(14, "Grind 2." + msg.what, mMid);
					miesr2.sendEmptyMessageDelayed((msg.what + 1), 3500);
				}
				Log.w(G, "miesr2 " + msg.what);
				ui.printStackTrace();
				return;
			}

			HandlerThread tx = new HandlerThread(G,
					Process.THREAD_PRIORITY_DEFAULT);
			tx.start();
			miesr3 = new miesr3(tx.getLooper());
			miesr3.sendEmptyMessageDelayed(2, 15);
			// Thread x4 = new Thread(){public void run(){
			// }};x4.start();

		}
	};

	Handler miesr3;

	class miesr3 extends Handler {
		public miesr3(Looper ln) {
			super(ln);
		}

		public void handleMessage(Message msg) {

			Log.i(G, "miesr3 good xxxx");
			try {
				rfilter = rfilter.replaceAll("<", "\n<");
				rfilter = rfilter.replaceAll("&gt;", "&gt;\n");
			} catch (OutOfMemoryError ui) {
				if (msg.what < 8) {
					updateM(14, "Grind 3." + msg.what, mMid);
					miesr3.sendEmptyMessageDelayed((msg.what + 1), 13500);
				}
				Log.w(G, "miesr3 " + msg.what);
				ui.printStackTrace();
				return;
			}

			miesr4.sendEmptyMessageDelayed(2, 15);
		}
	};

	Handler miesr4;

	class miesr4 extends Handler {
		public miesr4(Looper ln) {
			super(ln);
		}

		public void handleMessage(Message msg) {
			Log.i(G, "miesr4 good xxxx");

			try {
				rfilter = rfilter.replaceAll("&lt;", "\n&lt;");
				// rfilter = rfilter.replaceAll("<[\\/]p>", "");
			} catch (OutOfMemoryError ui) {
				if (msg.what < 8) {
					updateM(14, "Grind 4." + msg.what, mMid);
					miesr4.sendEmptyMessageDelayed((msg.what + 1), 3500);
				}
				Log.w(G, "miesr4 " + msg.what);
				ui.printStackTrace();
				return;
			}
			miesr5.sendEmptyMessageDelayed(2, 15);

		}
	};

	Handler miesr5;

	class miesr5 extends Handler {
		public miesr5(Looper ln) {
			super(ln);
		}

		public void handleMessage(Message msg) {
			Log.i(G, "miesr5 good xxx");

			try {
				rfilter = rfilter.replaceAll("\n[\n]+", "\n").trim();
				mie(rfilter, mMid, mL.getId(), true);

			} catch (OutOfMemoryError ui) {
				if (msg.what < 8) {
					updateM(14, "Grind 5." + msg.what, mMid);
					miesr5.sendEmptyMessageDelayed((msg.what + 1),
							(3500 + (msg.what + 1) * (msg.what + 1)));
				}
				Log.w(G, "miesr5 " + msg.what);
				ui.printStackTrace();
				return;
			}

			// Bundle bg = msg.getData();
			// rfilter = rfilter.replaceAll("<br.*?>", "");
			// int mid = bfilter.getInt("mid");
			// int huid = bfilter.getInt("huid");
			// int scub = bfilter.getInt("scub");
			// boolean xec = bfilter.getBoolean("xec");
			Log.i(G, "miesr5 xxxx");

		}
	};

	class Press extends Handler {
		public Press(Looper hx) {
			super(hx);
		}

		public void handleMessage(Message msg) {
			{
				Log.i(G, "Press good xxx");

				TextView b = null;
				TextView t9 = null;

				for (int x4 = 0; x4 < mL.getChildCount(); x4++) {
					String t = "";
					String n = "";
					b = (TextView) mL.getChildAt(x4);
					if (b == null) {
						continue;
					}
					t = (String) b.getTag();
					n = b.getText().toString();
					if (t == null) {
						t = "";
					}
					t = t.trim();
					n = n.trim();

					if (t.length() > 0
							&& n.matches("</" + t.replaceAll(" .*", "")
									+ ".*?>")) {
						if (mUpdate == null || OVERRIDE) {
							b.setText("");
							b.setVisibility(View.GONE);
						} else {
							Message ml = new Message();
							Bundle bl = new Bundle();
							bl.putInt("id", b.getId());
							bl.putString("text", "");
							ml.setData(bl);
							mUpdate.sendMessageDelayed(ml, 75);
						}
					} else

					if (t.length() > 0
							&& !n.contains("</" + t.replaceAll(" .*", ""))) {

						t9 = (TextView) mL.getChildAt(x4 + 1);
						if (t9 != null) {
							String n5 = t9.getText().toString();
							if (n5.contains("</" + t.replaceAll(" .*", ""))) {
								n5 = n5.replaceFirst(
										"</" + t.replaceAll(" .*", "") + ".*?>",
										"").trim();

								if (mUpdate == null || OVERRIDE) {
									t9.setText(n5);
									if (n5.length() == 0) {
										t9.setVisibility(View.GONE);
									}
								} else {

									Message ml = new Message();
									Bundle bl = new Bundle();
									bl.putInt("id", t9.getId());
									bl.putString("text", n5);
									ml.setData(bl);
									mUpdate.sendMessageDelayed(ml, 75);
								}
							}
						}

					}
				}

			}
		}
	}

	class miesrx extends Handler {
		public miesrx(Looper ln) {
			super(ln);
		}

		public void handleMessage(Message msg) {
			Log.i(G, "miesrx good xxx");

			Bundle bdl = msg.getData();
			int mid = bdl.getInt("mid");
			int huid = bdl.getInt("huid", -1);
			if (huid == -1) {
				Log.e(G, "miesrx xxx");
				return;
			}

			int cgu = 1;
			String rfilter = "";
			LinearLayout x = mL;// (LinearLayout) findViewById(huid);
			for (int x4 = 0; x4 < x.getChildCount(); x4++) {

				String t = "";
				String n = "";
				TextView b = (TextView) x.getChildAt(x4);
				if (b == null) {
					Log.e(G, "miesrx xxxxx b " + x4);
					continue;
				}
				if (b.getVisibility() == View.GONE) {
					continue;
				}
				t = (String) b.getTag();
				n = b.getText().toString();
				if (t == null) {
					t = "";
				}

				if (t.length() > 0) {
					if (!n.contains("<" + t.replaceAll(" .*", ""))) {
						rfilter += "<" + t + ">\n";
					}
					rfilter += n + "\n";
					if (!n.contains("</" + t.replaceAll(" .*", ""))) {
						rfilter += "</" + t.replaceAll(" .*", "") + ">\n";
					}
				} else {
					rfilter += n + "\n";
				}

			}

			ContentValues hx = new ContentValues();
			// hx.put("title", "Here");
			hx.put("filtered", rfilter);
			SqliteWrapper.update(mCtx, mCtx.getContentResolver(),
					Uri.parse("content://com.havenskys.elk/moment"), hx, "_id="
							+ mid + "", null);

		}
	};

	public void mieb(int xid, int mid, int huid, int scub) {
		Log.i(G, "mieb() good xxx");

		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putInt("xid", xid);
		bl.putInt("mid", mid);
		bl.putInt("huid", huid);
		bl.putInt("scub", scub);
		ml.setData(bl);

		HandlerThread tx = new HandlerThread(G, Process.THREAD_PRIORITY_DEFAULT);
		tx.start();
		Handler mieb = new mieb(tx.getLooper());

		mieb.sendMessageDelayed(ml, 75);
		// cvbon.sendEmptyMessageDelayed(huid, 75);

	}

	class mieb extends Handler {
		public mieb(Looper ln) {
			super(ln);
		}

		public void handleMessage(Message msg) {
			Log.i(G, "mieb good xxx");

			Bundle bdl = msg.getData();
			int xid = bdl.getInt("xid");
			int mid = bdl.getInt("mid");
			int huid = bdl.getInt("huid");
			// int scub = bdl.getInt("scub");

			// cvbon.sendEmptyMessageDelayed(huid, 75);

			TextView tid = null; // tid = (TextView) findViewById(xid);

			String rcontent = "";

			if (tid != null) {
				rcontent = tid.getText().toString();

			} else {
				rcontent = "<message>No source content</message>";
			}
			// LinearLayout hu = (LinearLayout) findViewById(huid);
			// hu.removeAllViews();
			// hu.setVisibility(View.VISIBLE);
			// hu.setDrawingCacheEnabled(true);
			// hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

			mie(rcontent, mid, huid, false);

		}

	};

	public void setr(String req) {
		Log.i(G, "setr() good xxx");

		Message ml = new Message();
		Bundle bl = new Bundle();
		bl.putString("req", req);
		ml.setData(bl);
		if (mSetr != null) {

			// HandlerThread tx = new HandlerThread(G, 10);
			// tx.start();
			// Handler miesr2 = new miesr2(tx.getLooper());

			mSetr.sendMessageDelayed(ml, 75);
		}

	}

	Request ElkRequest;
	// Bundle bfilter;
	String rfilter = "";

	int cntn = -1;
	int cntg = cntn;
	int cri = -1;
	String[] cr;

	public void mie(String rcontent, final int mid, final int huid,
			final boolean filter) {

		Log.i(G, "mie good xxx (" + rcontent.replaceAll("\n", ")(") + ")");

		if (rcontent.startsWith("<![")) {
			rcontent = rcontent.replaceFirst("<!\\[CDATA\\[", "").replaceFirst(
					"\\]\\]>", "");
		}

		if (rcontent.startsWith("<item")) {
			rcontent = rcontent.replaceFirst("<item.*?>", "");
			rcontent = rcontent.replaceFirst("</item.*?>", "");
			Log.i(G, "mie found <item xxxxxxxxxxxxxxx");
		}
		if (!rcontent.contains("\n")) {
			// rcontent = ":)" + "\n" + rcontent;
		}

		cntg = 0;
		cntn = 0;
		cri = 0;
		cr = rcontent.split("\n");

		// LinearLayout hu = mL;// (LinearLayout) findViewById(huid);
		if (mL == null) {
			Log.e(G, "hu(" + huid + ") gone");
			return;
		}

		{

			Message ml = new Message();
			Bundle bl = new Bundle();
			ml.setData(bl);
			HandlerThread tx = new HandlerThread(G,
					Process.THREAD_PRIORITY_DEFAULT);
			tx.start();
			Handler crclimb = new crclimb(tx.getLooper());

			crclimb.sendMessageDelayed(ml, 25);

		}

		// bl.putInt("mid", mid);
		// bl.putInt("huid", huid);
		// bl.putInt("scub", scub);
		// bl.putBoolean("addx", addx);
		// bl.putBoolean("filter", filter);

		// if (mL.getChildCount() > 0) {
		// mL.removeAllViews();
		// }
		// hu.setVisibility(View.VISIBLE);
		// hu.requestFocus();

		// ScrollView cubx = mS;// (ScrollView) findViewById(scub);
		// mS.scrollTo(0, 0);
		// mS.setDrawingCacheEnabled(true);
		// mS.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
		// mS.postInvalidateDelayed(15);

		// RelativeLayout.LayoutParams c98 = new RelativeLayout.LayoutParams(-1,
		// -1);// (RelativeLayout.LayoutParams)
		// cubx
		// .getLayoutParams();
		// if (mWidth > getListView().getWidth()) {
		// c98.setMargins(89, 55, 0, 0);
		// } else {
		// c98.setMargins(0, 0, 0, 0);
		// }
		// cubx.setLayoutParams(c98);

		// getListView().setLayoutParams
		// if (mV != null) {

		// RelativeLayout.LayoutParams hn = new RelativeLayout.LayoutParams(
		// 88, -1);
		// mV.setLayoutParams(hn);
		// }

		// hn.setMargins(88, 88, (int) (mWidth / 3 > 240 ? 240 : mWidth /
		// 3),
		// (int) ((mHeight - 100) / 3));

		// huxb.sendEmptyMessageDelayed(2, 75);
		// if (huid != R.id.hux) {
		// bucxb.setVisibility(View.GONE);
		// }

		// o1show.sendEmptyMessageDelayed(2, 275);
		// cvbon.sendEmptyMessageDelayed(huid, 345);

		// if (addx) {
		// addx(rcontent, mid, scub);
		// }

	}

	int cdi = 0;

	boolean x = false;
	boolean xc54 = x;
	boolean xu = x;
	boolean xj = x;
	// boolean xc2 = x;
	boolean xc43 = x;
	boolean xc45 = x;
	boolean xc48 = x;
	// boolean xc24 = x;
	boolean xc4 = x;
	int appcp = -1;
	boolean hx4 = false;
	String ix = "";
	String xpack = "";
	int tbg = -1;
	int lastt = -1;
	String texttage = "";
	String texttag = "";
	int tbgc = 1;
	int clm = -1;
	int xot = 0;
	int cnti = 1;

	class crclimb extends Handler {
		public crclimb(Looper ln) {
			super(ln);
		}

		public void handleMessage(Message msg) {
			Log.i(G, "crclimb good xxx");

			Bundle bdl = msg.getData();
			int mid = mMid;
			int huid = mL.getId();
			// final int scub = bdl.getInt("scub");
			// final boolean addx = bdl.getBoolean("addx");
			// boolean filter = bdl.getBoolean("filter");

			// Log.i(G, "climb " + cntn + " " + cri + "/" + cr.length + " huid("
			// + huid + ")");
			// if (cri == 0) {
			// return;
			// }

			String packtext = "";
			xu = true;
			int xi98 = 599;
			int xln = 1;
			Log.i(G, "good climb");
			int pxi = 0;
			long pxxi = SystemClock.uptimeMillis();

			for (int ih = cri; ih < cr.length; cri++, ih++) {

				ix = cr[ih].trim();
				if (ix.length() == 0) {
					continue;
				}

				pxi = (int) (SystemClock.uptimeMillis() - pxxi);

				xi98++;
				if (mFlt && xi98 > (cr.length / 10) && xi98 > 350 && pxi > 3850) {
					int px = (int) (((float) (cri + 1) / (float) cr.length) * (float) 100);
					// mFlt = mFlt;

					if (px > 0) {
						xi98 = 0;
						pxxi = SystemClock.uptimeMillis();
						updateM(11, "Filtering " + px + "%", mid);
					}
				}

				// Log.i(G, ":>" + ix);

				if (xu) {
					lastt = -1;
					xu = false;
					x = false;
					// xj = false;

					if (texttag.length() > 0) {
						// texttage = texttag;
						texttag = "";
						clm++;
					}

					if (!xc4 && !xc48 && !xc43 && !xc45 && !xc54) {

						xln++;
						if (clm > 8) {

							// for (;; cri--) {
							// if (cr[ih].trim().length() > 0) {
							// break;
							// }
							// }

							cri -= 1;

							xot++;
							clm = 0;
							{
								// cri++;
								Message ml = new Message();
								Bundle bl = new Bundle();
								// bl.putInt("mid", mid);
								// bl.putInt("huid", huid);
								// bl.putInt("scub", scub);
								// bl.putBoolean("addx", addx);
								// bl.putBoolean("filter", mFlt);
								ml.setData(bl);

								// HandlerThread tx = new HandlerThread(G, 10);
								// tx.start();
								// Handler crclimb = new
								// crclimb(tx.getLooper());

								HandlerThread tx = new HandlerThread(G,
										Process.THREAD_PRIORITY_DEFAULT);
								tx.start();
								Handler crclimb = new crclimb(tx.getLooper());

								crclimb.sendMessageDelayed(ml, 75);
							}

							return;
							// } else {
							// continue;
						}

					}
				}

				if (xc45) {
					// xu = true;
					packtext += "\n" + ix;
					if (!ix.endsWith("]]>")) {
						continue;
					}
					packtext = packtext.replaceFirst("\\]\\]>", "");
					xu = true;
					ix = packtext.trim();
					packtext = "";
					texttag = "";
				} else if (xc43) {
					// xu = true;
					packtext += "\n" + ix;
					if (!ix.regionMatches(true, 0, "</style", 0, 7)) {
						continue;
					}
					ix = packtext.trim();
					xu = true;
					texttag = "";
					packtext = "";
				} else if (xc48) {
					// xu = true;
					packtext += "\n" + ix;
					if (!ix.regionMatches(true, 0, "</script", 0, 8)) {
						continue;
					}
					ix = packtext.trim();
					xu = true;
					texttag = "";
					packtext = "";
				} else if (xc4) {
					// xu = true;
					packtext += "\n" + ix;
					if (!ix.regionMatches(true, 0, "</item", 0, 6)) {
						continue;
					}

					ix = packtext.trim();
					xu = true;
					packtext = "";
					texttag = "";// + cnti++;

					// Log.i(G, "climb pack[(" + ix.replaceAll("\n", ")(") +
					// ")]");

				} else if (xc54) {
					packtext += "\n" + ix;
					if (!ix.endsWith("-->")) {
						continue;
					}

					ix = packtext.trim();
					packtext = "";
					xu = true;
					// texttag = "comment";

				} else {

					if (ix.startsWith("<!--")) {
						xu = true;
						// texttag = "comment";
						xc54 = true;
						if (ix.endsWith("-->")) {
							lastt = -1;
						} else {
							packtext = ix;
							continue;
						}
					}

					if (ix.startsWith("<![")) {
						ix = ix.replaceFirst("<!\\[CDATA\\[", "");
						cri--;
						xu = true;
						// xc45 = true;
						// texttag = getN();
						// packtext = ix;
						if (!ix.endsWith("]]>")) {
							continue;
						}
					}
					if (ix.endsWith("]]>")) {
						ix = ix.replaceFirst("\\]\\]>", "");
					}

					if (ix.regionMatches(true, 0, "<item", 0, 5)) {
						xc4 = true;
						packtext = ix;
						xu = true;
						continue;
					}

					if (ix.regionMatches(true, 0, "<script", 0, 7)) {
						xu = true;
						xc48 = true;
						packtext = ix;
						continue;
					}

					if (ix.regionMatches(true, 0, "<style", 0, 6)) {
						xu = true;
						xc43 = true;
						packtext = ix;
						continue;
					}

					if (ix.startsWith("<")) {

						if (ix.matches("<(br|p|i|b|h[0-9])[> \\/].*")) {
							continue;
						}
						if (ix.matches("<\\/(br|p|i|b|h[0-9])[> \\/].*")) {
							continue;
						}

						// markup content
						x = true;
						if (!ix.endsWith(">")) {
							packtext = ix;
							// xu = true;
							xj = true;
							continue;
						}

					}

					{
						if (!xj && !ix.startsWith("<")) {// TEXT passes here
							lastt = -1;
							x = false;
						} else if (xj && ix.endsWith(">")) {
							xj = false;
							ix = packtext.trim();
							packtext = "";
							// xu = true;
						} else if (xj) {
							packtext += "\n" + ix;
							continue;
						}

					}
					if (ix.regionMatches(true, 0, "<a", 0, 2)) {
						lastt = -1;
						xu = true;
					}

					if (ix.regionMatches(true, 0, "<img", 0, 4)) {
						lastt = -1;
						xu = true;
					}

					// if (texttag.length() > 0 && ix.startsWith("</" +
					// texttag)) {
					// texttage = texttag;
					// texttag = "";
					// xu = true;
					// continue;
					// }

					// if (texttage.length() > 0 && ix.startsWith("</" +
					// texttage)) {
					// texttage = "";
					// xu = true;
					// continue;
					// }

					// if (!xc2) {
					// lastt = -1;
					// }

					// if (ix.startsWith("<![")) {

					// cr[ih] = ix;
					// ih--;
					// lastt = -1;
					// continue;
					// } // else if (ix.startsWith("<![") && scub == R.id.cubx)
					// {
					// lastt = -1;
					//
					// hx4 = true;
					// xu = true;
					// }

					// xc24 &&
					// if (ix.endsWith("]]>")) {
					// ix = ix.replaceFirst("\\]\\]>", "");
					// xu = true;
					// xc24 = false;
					//
					// cr[ih] = ix;
					// ih--;
					// continue;
					// }
					// if (hx4 && ix.endsWith("]]>")) {
					//
					// hx4 = false;
					// }

				}

				// Method of expanding Countable
				/*
				 * if (ix.regionMatches(true, 0, "</item", 0, 6)) { xu = true;
				 * hu = (LinearLayout) findViewById(R.id.hu);
				 * hu.setDrawingCacheEnabled(true);
				 * hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
				 * 
				 * continue; } if (ix.regionMatches(true, 0, "<item", 0, 5)) {
				 * tbg = R.drawable.b3; lastt = -1; }
				 */

				/*
				 * / if (tbg != -1) {
				 * 
				 * LinearLayout.LayoutParams t1r = new
				 * LinearLayout.LayoutParams( -1, -2); TextView ti = new
				 * TextView(mCtx); ti.setLayoutParams(t1r);
				 * ti.setTextColor(Color.argb(255, 210, 215, 240));
				 * ti.setGravity(Gravity.CENTER); ti.setTextSize(27);
				 * ti.setText("" + tbgc++); ti.setBackgroundResource(tbg); tbg =
				 * -1; LinearLayout mhu = (LinearLayout) findViewById(hu.getId()
				 * == R.id.hux ? R.id.hux : R.id.hu); mhu.addView(ti);
				 * 
				 * // LinearLayout.LayoutParams x1 = new //
				 * LinearLayout.LayoutParams( // -1, -2); LayoutParams xhuj =
				 * new LinearLayout.LayoutParams(-1, -2); LinearLayout xhu = new
				 * LinearLayout(mCtx); xhu.setLayoutParams(xhuj);
				 * xhu.setOrientation(LinearLayout.VERTICAL); xhu.setId(hid++);
				 * xhu.setVisibility(View.GONE); mhu.addView(xhu);
				 * 
				 * hu = xhu; hu.setDrawingCacheEnabled(true);
				 * hu.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
				 * 
				 * final int xid = xhu.getId();
				 * 
				 * ti.setOnClickListener(new OnClickListener() { boolean gu =
				 * false;
				 * 
				 * public void onClick(View gb) { View gv = findViewById(xid);
				 * if (!gu) { gv.setVisibility(View.VISIBLE); gu = true; } else
				 * { gu = false; gv.setVisibility(View.GONE); } } });
				 * 
				 * xu = true; continue; }//
				 */

				// TextView t1;

				// : (ix.contains("&gt;")
				// && ix
				// .contains("&lt;"));

				boolean x6 = xc4 ? xc4 : false;

				// < <!-- <script <style <![...]> <item <...>
				if (x || xc54 || xc48 || xc43 || xc45 || xc4 || xj) {

					if (lastt == -1) {

						cntg++;
						lastt = hid++;

						/*
						 * 
						 * 
						 * mNew
						 */

						if (mNew == null || OVERRIDE) {
							TextView ti = new TextView(mCtx);
							ti.setLayoutParams(new LinearLayout.LayoutParams(
									-1, -2));
							ti.setId(lastt);
							ti.setText(ix);
							ti.setTextSize((float) 14);
							ti.setTag(texttag);
							texttag = "";
							mL.addView(ti);

						} else {
							Message ml = new Message();
							Bundle bl = new Bundle();
							bl.putInt("id", lastt);
							bl.putString("text", ix);
							bl.putInt("gravity", Gravity.LEFT);
							bl.putInt("lid", huid);
							bl.putFloat("size", 14);
							bl.putInt("mid", mid);
							bl.putBoolean("x6", x6);
							bl.putString("tag", texttag);

							// if (x6) {
							// bl.putInt("bgr", R.drawable.b7);
							// bl.putInt("color",
							// Color.argb(250, 195, 179, 138));
							// } else

							if (ix.matches("<img")) {
								String jux = texttag;
								texttag = ix.replaceFirst("<", "")
										.replaceFirst(">", "");
								ix = jux;
								if (ix.length() == 0) {
									ix = "Image";
								}

							}

							if (xc4) {

								bl.putInt("bgr", R.drawable.b7);
								bl.putInt("color", Color.argb(250, 108, 91, 10));
							} else if (texttag.matches("(img) .*")
									|| ix.matches("http.*(.png|.jpg|.gif)")) {
								bl.putInt("color", Color.argb(250, 108, 91, 10));
								bl.putInt("bgr", R.drawable.img);
							} else if (texttag.matches("(a|link) .*")
									|| ix.matches("http.*")) {
								bl.putInt("color", Color.argb(250, 108, 91, 10));
								bl.putInt("bgr", R.drawable.unvis);
							} else {
								bl.putInt("bgr", R.drawable.b602);
								bl.putInt("color",
										Color.argb(250, 127, 146, 175));
							}

							bl.putInt("order", mP++);

							ml.setData(bl);
							// Log.i(G, "mNew(" + bl.getInt("order") + ") in "
							// + (75 + (MULTIP * clm) + (MULTIP * xot))
							// + " ms <" + texttag + ">");
							texttag = "";

							mNew.sendMessageDelayed(ml, 75);// + (MULTIP * clm)
							// + (MULTIP * xot));

						}

						appcp = 0;
					} else {

						appcp++;

						/*
						 * 
						 * 
						 * mAppend
						 */

						if (mAppend == null || OVERRIDE) {
							TextView t1 = (TextView) mL.getChildAt(mL
									.getChildCount() - 1);

							if (t1.getId() != lastt) {
								Log.w(G, "append inner " + t1.getId() + " != "
										+ lastt);
							}
							if (t1 != null) {
								t1.append("\n" + ix);
							}

						} else {

							Message ml = new Message();
							Bundle bl = new Bundle();
							bl.putInt("mid", mid);
							bl.putInt("id", lastt);
							bl.putString("text", ix);
							// bl.putInt("lid", mL.getId());
							ml.setData(bl);

							// Log.i(G,
							// "mAppend in "
							// + (75 + (MULTIP * appcp)
							// + (MULTIP * clm) + (MULTIP * xot))
							// + " ms");
							mAppend.sendMessageDelayed(ml, 75);
							// + (appcp * MULTIP) + (MULTIP * clm)
							// + (MULTIP * xot));

						}

					}

					if (xc4) {
						xc4 = false;
					}
					if (xc48) {
						xc48 = false;
					}
					if (xc54) {
						xc54 = false;
					}
					if (xc43) {
						xc43 = false;
					}
					if (xc45) {
						xc45 = false;
					}

				} else

				{

					if (mFlt) {
						try {

							Charset charset = Charset.forName("ISO-8859-1");
							CharsetDecoder decoder = charset.newDecoder();
							ByteBuffer b3 = ByteBuffer.wrap(ix.getBytes());
							CharBuffer c3;
							c3 = decoder.decode(b3);
							ix = c3.toString();
							ix = ix.replaceAll("&amp;", "&").replaceAll(
									"&nbsp;", " ");
							ix = ix.replaceAll("&quot;", "\"")
									.replaceAll("&apos;", "'")
									.replaceAll("&copy;", "")
									.replaceAll("&ndash;", "-")
									.replaceAll("&ntilde;", "~")
									// .replaceAll("&rsquo;", "'")
									// .replaceAll("&[a-z]+;", " ")
									// .replaceAll("&raquo;", "'")
									// .replaceAll("&#39;", "'")
									// .replaceAll("&#[0-9]+;", " ")
									.replaceAll("&lt;", "<")
									.replaceAll("&gt;", ">");

							if (ix.matches(".*?%[0-9A-F][0-9A-F].*")) {
								ix = Uri.decode(ix);
							}

							if (ix.matches(".*(&|&#)[0-9a-z]+;.*")) {
								Log.i(G, "ix xxx contains &?+; " + ix);
								ix = ix.replaceAll("(&|&#)[0-9a-z]+;", " ");
							}

						} catch (CharacterCodingException e) {
							e.printStackTrace();
						}
					}

					lastt = hid++;
					// Log.i(G, ">" + ix);

					/*
					 * 
					 * 
					 * mNew
					 */

					if (mNew == null || OVERRIDE) {

						cntn++;
						TextView ti = new TextView(mCtx);
						ti.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
						ti.setId(lastt);
						ti.setText(ix);
						texttag = getN();
						ti.setTag(texttag);
						mL.addView(ti);

						Log.i(G, "climb xxx service " + lastt + ": " + ix
								+ " <" + texttag + "> " + cntn + "("
								+ ti.getTag().toString() + ")");

					} else {
						// Log.i(G, "climb xxx to mNew " + lastt + ": " + ix
						// + " <" + texttag + "> " + cntn);

						Message ml = new Message();
						Bundle bl = new Bundle();
						bl.putInt("mid", mid);
						bl.putInt("lid", huid);
						bl.putInt("id", lastt);
						bl.putBoolean("x6", x6);
						bl.putString("text", ix);

						// if (x6)
						// bl.putInt("bgr", R.drawable.b7);
						// bl.putInt("gravity", Gravity.LEFT);
						// bl.putFloat("size", 14);

						// } else {

						cntn++;

						if (cntn == 1) {

							// if (mFlt) {

							// }
							bl.putInt("bgr", R.drawable.b802);
							bl.putInt("gravity", Gravity.CENTER);
							bl.putFloat("size", 21);
							bl.putInt("margintop", 0);
							bl.putInt("color", Color.argb(255, 0, 128, 255));
							// bl.putInt("minheight", 88 + 3);

						} else {

							bl.putInt("bgr", R.drawable.b502);
							bl.putInt("gravity", Gravity.LEFT);
							bl.putInt("color", Color.argb(255, 73, 137, 200));
							bl.putFloat("size", 16);

						}
						bl.putInt("order", mP++);
						ml.setData(bl);

						// Log.i(G, "mNew(" + bl.getInt("order") + ") in "
						// + (75 + (MULTIP * clm) + (MULTIP * xot))
						// + " ms");
						mNew.sendMessageDelayed(ml, 75);// + (MULTIP * clm)
						// + (MULTIP * xot));
					}

					/*
					 * 
					 * 
					 * mLmt
					 */

					if (cntn == 1 && mLmt != null) {
						Message mlh = new Message();
						Bundle blh = new Bundle();
						blh.putInt("id", lastt);
						// blh.putInt("scub", mS.getId());
						// blh.putInt("plus", -6);
						// blh.putInt("minheight", 55);
						mlh.setData(blh);

						Log.i(G, "mLmt in "
								+ (75 + (MULTIP * clm) + (MULTIP * xot))
								+ " ms");
						mLmt.sendMessageDelayed(mlh, 975);// + (clm * MULTIP)
						// + (xot * MULTIP));
					}

					if (cntn == 1 || texttag.startsWith("title")) {

						if (!ftitle && !ix.contentEquals(":)")) {
							ftitle = true;
							ContentValues e = new ContentValues();
							e.put("foundtitle", ix);
							Log.i(G, "foundtitle xxxx " + ix);
							SqliteWrapper
									.update(mCtx,
											mCtx.getContentResolver(),
											Uri.parse("content://com.havenskys.elk/moment"),
											e, "_id=" + mid, null);
						}
					}

					if (texttag.startsWith("language")) {

						if (!flang) {
							flang = true;
							ContentValues e = new ContentValues();
							e.put("language", ix);
							Log.i(G, "language xxxx " + ix);
							SqliteWrapper
									.update(mCtx,
											mCtx.getContentResolver(),
											Uri.parse("content://com.havenskys.elk/moment"),
											e, "_id=" + mid, null);
						}
					}

					lastt = -1;
					xu = true;

				}

				//
				//
				//
				// texttag

				// t1.setPadding(7, 13, 7, 13);

			}

			// Press
			HandlerThread tu92 = new HandlerThread(G,
					Process.THREAD_PRIORITY_DEFAULT);
			tu92.start();

			Handler ctgxx = new Press(tu92.getLooper());
			ctgxx.sendEmptyMessageDelayed(2, 754);// + (10 * clm) + (10 *
													// xot));

			// CompressPack (xot=group clm=ingroup)
			{

				HandlerThread tu9 = new HandlerThread(G,
						Process.THREAD_PRIORITY_DEFAULT);
				tu9.start();
				Handler cpxpac = new CompressPack(tu9.getLooper());

				Message ml = new Message();
				Bundle bl = new Bundle();
				bl.putInt("mid", mid);
				ml.setData(bl);
				cpxpac.sendMessageDelayed(ml, 750 + clm + xot);// , 2754 +
																// (MULTIP *
																// clm)+ (MULTIP
																// * xot));

			}

			// Curvebutt (reocurring)
			HandlerThread tu95 = new HandlerThread(G,
					Process.THREAD_PRIORITY_DEFAULT);
			tu95.start();
			crvxx = new Curvebutt(tu95.getLooper());
			crvxx.sendEmptyMessageDelayed(2, 1275);

		}
	};

	boolean ftitle = false;
	boolean flang = false;
	Handler crvxx;

	public String getN() {
		Log.i(G, "getN() good xxx");

		String texttag = "";

		try {
			TextView uk = (TextView) mL.getChildAt(mL.getChildCount() - 1);// mL.getChildCount()
			// -
			// 1
			// findViewById(hid
			// -
			// 2);
			if (uk != null) {

				String ut = uk.getText().toString();
				String[] ut8 = ut.split("\n");
				String ut7 = "";
				for (int b = 0; b < ut8.length - 1; b++) {
					ut7 += ut8[b] + "\n";
				}

				String ut2 = ut8[ut8.length - 1];
				if (ut2.startsWith("<") && !ut2.startsWith("<![")
						&& !ut2.startsWith("</")) {
					ut7 = ut7.trim();
				} else {
					ut7 += ut2;
					ut2 = "";
				}
				uk.setText(ut7);

				ut2 = ut2.replaceFirst("<", "");
				ut2 = ut2.replaceFirst(">.*", "");

				texttag = ut2;
				if (ut7.length() == 0) {
					uk.setVisibility(View.GONE);
				}

			}

		} catch (ClassCastException ey) {
			ey.printStackTrace();
		}

		return texttag;

	}

	class CompressPack extends Handler {
		public CompressPack(Looper hx) {
			super(hx);
		}

		public void handleMessage(Message msg) {
			{
				Bundle bdl = msg.getData();
				int mid = bdl.getInt("mid");
				Log.i(G, "compresspack good xxx " + mL.getChildCount());
				int nc = 0;
				for (int u = 1; u < mL.getChildCount() - 1; u++) {
					// for (int u = mL.getChildCount() - 1; u > 0; u--) {
					TextView u8 = (TextView) mL.getChildAt(u);

					if (u8 != null) {
						// if (u8.length() > ) {
						String s9 = u8.getText().toString();
						if (s9.contains("<item") && !s9.contains("content:")) {
							nc++;

							if (mPack == null || OVERRIDE) {
								contentize(u, mid);
							} else {

								Message ml = new Message();
								Bundle bl = new Bundle();
								bl.putInt("lid", mL.getId());
								bl.putInt("u", u);
								bl.putInt("mid", mid);
								ml.setData(bl);
								mPack.sendMessageDelayed(ml, 53 + u);

								// Log.i(G,
								// "mPack in "
								// + (153 + (MULTIP * clm)
								// + (MULTIP * xot) + (MULTIP * u))
								// + " ms");
							}

						}
					}
				}

				if (nc > 0) {

					updateM(12, nc + " found", mMid);
					mFlt = true;
					// } else if (mFlt) {
					// updateM(12, "Filtering 100%", mMid);
				}

				if (mFlt) {

					{
						Message ml = new Message();
						Bundle bl = new Bundle();
						bl.putInt("mid", mid);
						bl.putInt("huid", mL.getId());
						ml.setData(bl);
						HandlerThread tx5 = new HandlerThread(G,
								Process.THREAD_PRIORITY_DEFAULT);
						tx5.start();
						Handler miesrx = new miesrx(tx5.getLooper());

						miesrx.sendMessageDelayed(ml, 2375);// + (20 * clm)
						// + (20 * xot));
					}

				}

			}
		}
	}

	class Curvebutt extends Handler {
		public Curvebutt(Looper hx) {
			super(hx);
		}

		int e2 = -1;
		long et2 = -1;

		public void handleMessage(Message msg) {

			Log.i(G, "curvebutt good xxx");
			for (int h9 = mL.getChildCount() - 1; h9 >= 0; h9--) {
				// mL = (LinearLayout) findViewById(huid);

				TextView u8 = (TextView) mL.getChildAt(h9);

				if (u8 != null) {
					if (u8.getVisibility() != View.VISIBLE) {
						continue;
					}
					if (e2 > -1 && e2 != h9) {
						TextView l1 = (TextView) mL.getChildAt(e2);

						/*
						 * 
						 * 
						 * mUpdate
						 */
						if (mUpdate == null || OVERRIDE) {
							// l1.setBackgroundResource(R.drawable.b5);
						} else {
							Message ml = new Message();
							Bundle bl = new Bundle();
							bl.putInt("id", l1.getId());
							bl.putInt("bkg", R.drawable.b502);
							ml.setData(bl);
							mUpdate.sendMessageDelayed(ml, 75);
						}
					}

					if (e2 != h9) {
						et2 = System.currentTimeMillis();
						e2 = h9;

						/*
						 * 
						 * 
						 * mUpdate
						 */

						if (mUpdate == null || OVERRIDE) {
							// u8.setBackgroundResource(R.drawable.b9);
						} else {
							Message ml = new Message();
							Bundle bl = new Bundle();
							bl.putInt("id", u8.getId());
							bl.putInt("bkg", R.drawable.b902);
							ml.setData(bl);
							mUpdate.sendMessageDelayed(ml, 75);
						}

					}
				}

				break;
			}

			if ((System.currentTimeMillis()) < et2 + 5000) {
				// HandlerThread tu95 = new HandlerThread(G,
				// Process.THREAD_PRIORITY_DEFAULT);
				// tu95.start();
				// Handler crvxx = new Curvebutt(tu95.getLooper());
				crvxx.sendEmptyMessageDelayed(2, 1875);

			}

		}
	}

	int xhu = 0;

	public Uri contentize(int id, int mid) {
		Log.i(G, "contentize() good xxx");

		TextView t7;
		t7 = (TextView) mL.getChildAt(id);

		if (t7 != null) {
			String oh = t7.getText().toString();
			if (oh.contains("content:")) {
				return Uri.parse(oh.replaceAll("\n", "")
						.replaceAll(".*content:", "content:")
						.replaceAll("<.*", ""));
			}
			Cursor u92 = null;
			u92 = SqliteWrapper.query(mCtx, mCtx.getContentResolver(),
					Uri.parse("content://com.havenskys.elk/moment"),
					new String[] { "_id", "language" }, "_id = " + mid + "",
					null, null);

			String xl = "";
			if (u92 != null && u92.moveToFirst()) {
				xl = u92.getString(1);
			}
			String xid = Uri.encode(oh.replaceAll("<.*?>", ""), " ")
					.replaceAll("%..", "").replaceAll("[-()' +]", "").trim();
			ContentValues i9 = new ContentValues();
			i9.put("content", oh);
			i9.put("itext", xid);
			i9.put("mid", mid);
			i9.put("status", 2);
			i9.put("language", xl);
			// new String[] { xid }
			Log.i(G, "contentize " + xid.length());
			Cursor u9 = null;
			u9 = SqliteWrapper.query(mCtx, mCtx.getContentResolver(),
					Uri.parse("content://com.havenskys.elk/moment"),
					new String[] { "_id" }, "itext = \"" + xid + "\"", null,
					null);

			Uri h4 = null;
			boolean n = true;
			if (u9 != null) {
				if (u9.moveToFirst()) {
					h4 = Uri.parse("content://com.havenskys.elk/moment/"
							+ u9.getInt(0));
					n = false;
				}
				u9.close();
			}

			if (n) {
				try {
					h4 = SqliteWrapper
							.insert(mCtx,
									mCtx.getContentResolver(),
									Uri.parse("content://com.havenskys.elk/moment"),
									i9);
				} catch (SQLiteException xb) {
					xb.printStackTrace();
				}
				t7.setTextSize(21);
			}

			String tg = (String) t7.getTag();
			if (tg == null || tg.length() == 0) {
				tg = "item";
			}

			if (h4 != null) {
				// t7.setText(h4.toString());
				t7.setText("<" + tg + ">\n" + h4.toString() + "\n</"
						+ tg.replaceAll(" .*", "") + ">");

				return h4;
			}
		}

		return null;

	}
}
