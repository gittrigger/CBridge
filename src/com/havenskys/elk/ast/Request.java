package com.havenskys.elk.ast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;
import java.util.logging.LogRecord;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.RedirectHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;

import com.havenskys.elk.DataProvider;
import com.havenskys.elk.SqliteWrapper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Request {

	static String G = "Elk Request";
	Context mCtx;
	Uri mCp;

	public Request(Context context, Uri contentpath) {
		Log.i(G, "Request() good xxx");

		mCtx = context;
		mCp = contentpath;

		Count();
		if (length > -1) {
			Log.i(G, "Request startup succeed. (" + length + ")");
		}

	}

	public Uri add(String url, String summary, String author, String foundtitle) {
		// insert into moment (_id,title,content,published,author) values (NULL,
		// 'Request', 'New installation of data set.','"+datetime()+"','Haven');
		Log.i(G, "add() good xxx");

		ContentValues v1 = new ContentValues();
		v1.put("url", url);
		v1.put("summary", summary);
		v1.put("title", "Update");
		v1.put("foundtitle", foundtitle);
		v1.put("author", author);
		v1.put("updatemin", 30);
		v1.put("published", datetime(System.currentTimeMillis()));

		Uri u1 = SqliteWrapper.insert(mCtx, mCtx.getContentResolver(),
				Uri.withAppendedPath(mCp, "/moment"), v1);

		{
			ContentValues v4 = new ContentValues();
			v4.put("mid", Integer.parseInt(u1.getLastPathSegment()));
			v4.put("status", 5986);
			v4.put("title", "Settings");
			v4.put("content",
					"<html><title>Settings</title><form>size</form></html>");
			Uri u4 = SqliteWrapper.insert(mCtx, mCtx.getContentResolver(),
					Uri.withAppendedPath(mCp, "/moment"), v4);

		}

		{
			ContentValues v4 = new ContentValues();
			v4.put("mid", Integer.parseInt(u1.getLastPathSegment()));
			v4.put("status", 5986);
			v4.put("title", "Off");
			v4.put("content",
					"<html><title>Off</title>Control<form>off</form></html>");
			Uri u4 = SqliteWrapper.insert(mCtx, mCtx.getContentResolver(),
					Uri.withAppendedPath(mCp, "/moment"), v4);

		}

		return (u1);
	}

	public String datetime(long xe) {
		Log.i(G, "datetime() good xxx");

		String g = "";
		Date d = new Date(xe);
		g = (d.getYear() + 1900) + "-" + ((d.getMonth() < 9) ? "0" : "")
				+ ((d.getMonth() + 1)) + "-" + ((d.getDate() < 10) ? "0" : "")
				+ d.getDate() + " " + ((d.getHours() < 10) ? "0" : "")
				+ d.getHours() + ":" + ((d.getMinutes() < 10) ? "0" : "")
				+ d.getMinutes() + ":" + ((d.getSeconds() < 10) ? "0" : "")
				+ d.getSeconds();
		// Log.i(TAG,"generated date "+g);
		return g;
	}

	public int length = -1;

	static int DOWNLOAD_LIMIT = 1024 * 1024 * 1024 * 1024 * 1024 * 7;

	public void scan() {

		Log.i(G, "scan() good xxx Update");
		ContentValues cx = new ContentValues();
		cx.put("status", 1);
		cx.put("title", "Update");
		SqliteWrapper
				.update(mCtx,
						mCtx.getContentResolver(),
						Uri.parse("content://com.havenskys.elk/moment"),
						cx,
						"status > 0 and updatemin is not null and url is not null and updatemin > 0 and updated < '"
								+ datetime(System.currentTimeMillis()
										- (300 * 60 * 1000)) + "'", null);

	}

	public void Count() {
		Log.i(G, "Count() good xxx");

		Cursor cx = SqliteWrapper.query(mCtx, mCtx.getContentResolver(),
				Uri.withAppendedPath(mCp, "/moment"),
				new String[] { "count(*)" }, "title = 'Update' AND status = 1",
				null, null);

		if (cx != null) {
			if (cx.moveToFirst()) {

				try {
					length = cx.getInt(0);
					Log.i(G, "Count() " + length);
				} catch (IllegalStateException ei) {
					ei.printStackTrace();
				}
				// String here =
				// cpxpac.getString(cpxpac.getColumnIndex("content"));
				// String bh[] = cpxpac.getColumnNames();
				// Log.i(G,"here("+here+") ");

			}
			cx.close();
		}

	}

	public void updateM(int status, String title, int mid) {
		Log.i(G, "updateM() good xxx");

		{

			ContentValues v8 = new ContentValues();
			v8.put("status", status);
			v8.put("title", title);
			v8.put("updated", datetime(System.currentTimeMillis()));
			SqliteWrapper.update(mCtx, mCtx.getContentResolver(),
					Uri.parse("content://com.havenskys.elk/moment"), v8, "_id="
							+ mid + "", null);
		}

	}

	public void breader() {

		Log.i(G, "breader() good xxx");
		Thread gb = new Thread() {
			@Override
			public void run() {
				Log.i(G, "breader() thread");

				Cursor cx = SqliteWrapper.query(mCtx,
						mCtx.getContentResolver(),
						Uri.withAppendedPath(mCp, "/moment"),
						new String[] { "*" },
						"title = 'Update' AND status = 1", null,
						"created asc limit 1");

				String url = "";
				int mid = -1;
				if (cx != null) {
					if (cx.moveToFirst()) {
						url = cx.getString(cx.getColumnIndex("url"));
						mid = cx.getInt(cx.getColumnIndex("_id"));
					}
					cx.close();
				}

				if (url == null || url.length() == 0) {
					Log.e(G, "no url");
					return;
				}

				// updateM(2, "Socket", mid);
				Log.i(G, "Update " + url);

				DefaultHttpClient mHC = null;
				mHC = new DefaultHttpClient();

				// search retrospect for cookies from last interaction?
				String cookies = "";

				if (cookies != null && cookies.length() > 0) {
					CookieStore cs = (mHC != null) ? mHC.getCookieStore()
							: new DefaultHttpClient().getCookieStore();
					// SharedPreferences mReg =
					// mCtx.getSharedPreferences("Preferences",
					// MODE_WORLD_WRITEABLE);Editor mEdt = mReg.edit();
					String[] clist = cookies.split("\n");
					ContentValues cg = new ContentValues();
					for (int h = 0; h < clist.length; h++) {
						String[] c = clist[h].split(" ", 2);
						if (c.length == 2 && c[0].length() > 3) {
							if (cg.containsKey(c[0]) == false) {
								// cg.put(c[0], c[1]);
								Cookie logonCookie = new BasicClientCookie(
										c[0], c[1].replaceAll("; expires=null",
												""));
								// Log.w(G,"Carry Cookie mGet2 " + c[0] +
								// ":"+c[1] +
								// " expires("+logonCookie.getExpiryDate()+")" +
								// " path("+logonCookie.getPath()+") domain("+logonCookie.getDomain()+")");
								cs.addCookie(logonCookie);// TODO
							}
						}
					}
					mHC.setCookieStore(cs);
				}

				String rurl = url;

				Socket socket = null;
				SSLSocket sslsocket = null;
				BufferedReader br = null;
				BufferedWriter bw = null;
				int loopcnt = 0;
				try {
					while (rurl.length() > 0) {

						rurl = Uri.decode(rurl);

						loopcnt++;
						if (loopcnt > 19) {
							updateM(8, "Drat too deep", mid);
							Log.e(G, "getPage() Looped " + loopcnt
									+ " times, really?! this many forwards?");
							break;
						}

						boolean secure = rurl.startsWith("https:")
								|| rurl.contains(":443/") ? true : false;
						String rhostname = rurl.replaceFirst(".*?://", "")
								.replaceFirst("/.*", "");

						int rport = secure ? 443 : 80;
						int rid = -1;
						if (rhostname.contains(":")) {
							String[] p = rhostname.split(":");
							rhostname = p[0];
							rport = Integer.parseInt(p[1]);
						}

						String rdocpath = rurl.replaceFirst(".*?://", "")
								.replaceFirst(".*?/", "/");

						if (!rdocpath.contains("/")
								&& rdocpath.contentEquals(rhostname)) {
							rdocpath = "/";
						}

						{
							ContentValues ins = new ContentValues();
							ins.put("hostname", rhostname);
							ins.put("docpath", rdocpath);
							ins.put("url", url);
							ins.put("rurl", rurl);
							ins.put("port", rport);
							Uri ruri = SqliteWrapper
									.insert(mCtx,
											mCtx.getContentResolver(),
											Uri.parse("content://com.havenskys.elk/retrospect"),
											ins);
							rid = ruri.getLastPathSegment().matches("[0-9]+") ? Integer
									.parseInt(ruri.getLastPathSegment()) : -1;
							Log.i(G, "Created " + rid + " " + ruri.toString());
							// String sourceheader = "";
							// String sourcebody = "";
						}

						final int frid = rid;
						mHC.setRedirectHandler(new RedirectHandler() {
							public URI getLocationURI(HttpResponse arg0,
									HttpContext arg1) throws ProtocolException {

								if (arg0.containsHeader("Location")) {

									String burl = arg0.getFirstHeader(
											"Location").getValue();
									URI uri = URI.create(burl);

									ContentValues v3 = new ContentValues();
									v3.put("rurl", Uri.decode(burl));
									SqliteWrapper.update(
											mCtx,
											mCtx.getContentResolver(),
											Uri.parse("content://com.havenskys.elk/retrospect"),
											v3, "_id=" + frid, null);

									return uri;
								} else {
									return null;
								}
							}

							public boolean isRedirectRequested(
									HttpResponse arg0, HttpContext arg1) {
								if (arg0.containsHeader("Location")) {
									String burl = arg0.getFirstHeader(
											"Location").getValue();

									{
										ContentValues v3 = new ContentValues();
										v3.put("rurl", burl);
										SqliteWrapper.update(
												mCtx,
												mCtx.getContentResolver(),
												Uri.parse("content://com.havenskys.elk/retrospect"),
												v3, "_id=" + frid, null);
									}
									return true;
								}
								return false;
							}

						});

						Log.i(G, "getPage() " + rid + " hostname(" + rhostname
								+ ") path(" + rdocpath + ") gourl(" + rurl
								+ ")");// file("+f.exists()+","+f.getAbsolutePath()+")");
						//
						//
						//
						//
						//

						long rrr = System.currentTimeMillis();
						long rrs = 2;
						if (!secure) {
							sslsocket = null;
							Log.w(G, "getPage() Connecting to hostname("
									+ rhostname + ") port(" + rport + ")");
							socket = new Socket(rhostname, rport);

							// socket = new SecureSocket();
							// SecureSocket s = null;

							if (socket.isConnected()) {
								updateM(2, "Open", mid);

								InetAddress iix = socket.getInetAddress();
								Log.i(G, "getPage() Connecting to hostname("
										+ rhostname + ") CONNECTED");

								if (rrs == 2) {
									rrs = System.currentTimeMillis();
									Log.w(G, "Took " + (rrs - rrr)
											+ "ms ready to connect.");// mEdt.putLong(loc+"_respondedms",(rrs-rrr));mEdt.putLong(loc+"_connect",System.currentTimeMillis());mEdt.commit();

									/*
									 * ContentValues v9 = new ContentValues();
									 * v9.put("srview", "Link Established");
									 * v9.put("srdate", datetime());
									 * SqliteWrapper.update( mCtx,
									 * mCtx.getContentResolver(), Uri.parse(
									 * "content://com.havenskys.elk/filter"),
									 * v9, "_id=" + fid, null);
									 */
									/*
									 * { ContentValues v8 = new ContentValues();
									 * v8.put("responsems", (rrs - rrr)); if
									 * (iix != null) { v8.put("ipaddress",
									 * iix.getHostAddress()); } v8.put("port",
									 * socket.getPort()); SqliteWrapper
									 * .update(mCtx, mCtx.getContentResolver(),
									 * Uri
									 * .parse("content://com.havenskys.elk/filter"
									 * ), v8, "_id=" + rid, null); }//
									 */

									/*
									 * { ContentValues v8 = new ContentValues();
									 * if (iix != null) { v8.put("ipaddress",
									 * iix.getHostAddress()); } v8.put("port",
									 * socket.getPort()); SqliteWrapper
									 * .update(mCtx, getContentResolver(),
									 * Uri.parse(getString(R.string.cp) +
									 * "/filter"), v8, "_id=" + fid, null); }//
									 */

								}

							} else {
								int loopcnt2 = 0;
								while (!socket.isConnected()) {
									Log.e(G,
											"getPage() Not connected to hostname("
													+ rhostname + ")");
									loopcnt2++;
									if (loopcnt2 > 10) {

										/*
										 * ContentValues v9 = new
										 * ContentValues(); v9.put("srview",
										 * "Timeout"); v9.put("srdate",
										 * datetime()); SqliteWrapper
										 * .update(mCtx,
										 * mCtx.getContentResolver(), Uri.parse(
										 * "content://com.havenskys.elk/filter"
										 * ), v9, "_id=" + fid, null);
										 * 
										 * //
										 */

										updateM(8, "Dang no anwser", mid);

										Log.e(G,
												"getPage() Not connected to hostname("
														+ rhostname
														+ ") TIMEOUT REACHED");
										break;
									}
									SystemClock.sleep(300);
								}
							}

							// w(TAG,"getPage() Creating Writable to hostname("+hostname+") port("+port+")");
							bw = new BufferedWriter(new OutputStreamWriter(
									socket.getOutputStream()));
							// w(TAG,"getPage() Creating Readable to hostname("+hostname+") port("+port+")");
							br = new BufferedReader(new InputStreamReader(
									socket.getInputStream()));
						} else {
							socket = null;
							Log.w(G,
									"getPage() Connecting Securely to hostname("
											+ rhostname + ") port(" + rport
											+ ")");

							SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory
									.getDefault();
							sslsocket = (SSLSocket) factory.createSocket(
									rhostname, 443);
							SSLSession session = sslsocket.getSession();
							X509Certificate cert;
							try {
								cert = (X509Certificate) session
										.getPeerCertificates()[0];
							} catch (SSLPeerUnverifiedException e) {
								updateM(8, "Security warning", mid);
								Log.e(G,
										"getPage() Connecting to hostname("
												+ rhostname
												+ ") port(443) failed CERTIFICATE UNVERIFIED");
								break;
							}

							if (sslsocket.isConnected()) {
								Log.i(G, "getPage() Connecting to hostname("
										+ rhostname + ") CONNECTED");
								updateM(2, "Secure", mid);
								InetAddress iix = sslsocket.getInetAddress();

								if (rrs == 2) {
									rrs = System.currentTimeMillis();
									Log.w(G, "Took " + (rrs - rrr)
											+ "ms ready to connect.");// mEdt.putLong(loc+"_respondedms",(rrs-rrr));mEdt.putLong(loc+"_connect",System.currentTimeMillis());mEdt.commit();

									/*
									 * ContentValues v9 = new ContentValues();
									 * v9.put("srview", "Link Secure");
									 * v9.put("srdate", datetime());
									 * SqliteWrapper.update( mCtx,
									 * mCtx.getContentResolver(),
									 * Uri.parse(getString(R.string.cp) +
									 * "/filter"), v9, "_id=" + fid, null); //
									 */

									{

										ContentValues v8 = new ContentValues();
										if (iix != null) {
											v8.put("ipaddress",
													iix.getHostAddress());
										}
										v8.put("port", sslsocket.getPort());
										v8.put("responsems", (rrs - rrr));
										SqliteWrapper
												.update(mCtx,
														mCtx.getContentResolver(),
														Uri.parse("content://com.havenskys.elk/retrospect"),
														v8, "_id=" + rid, null);

									}

									/*
									 * {
									 * 
									 * ContentValues v8 = new ContentValues();
									 * if (iix != null) { v8.put("ipaddress",
									 * iix.getHostAddress()); } v8.put("port",
									 * sslsocket.getPort()); SqliteWrapper
									 * .update(mCtx, mCtx.getContentResolver(),
									 * Uri
									 * .parse("content://com.havenskys.elk/filter"
									 * ),
									 * 
									 * v8, "_id=" + fid, null);
									 * 
									 * }//
									 */

								}

								// if(iix !=
								// null){mEdt.putString(loc+"_ipaddress",iix.getHostAddress());}
								// mEdt.putInt(loc+"_ipport",sslsocket.getPort());mEdt.commit();

							} else {
								int loopcnt2 = 0;
								while (!sslsocket.isConnected()) {
									Log.e(G,
											"getPage() Not connected to hostname("
													+ rhostname + ")");
									loopcnt2++;
									if (loopcnt2 > 20) {

										/*
										 * ContentValues v9 = new
										 * ContentValues(); v9.put("srview",
										 * "Timeout"); v9.put("srdate",
										 * datetime()); SqliteWrapper
										 * .update(mCtx, getContentResolver(),
										 * Uri.parse(getString(R.string.cp) +
										 * "/filter"), v9, "_id=" + fid, null);
										 * 
										 * //
										 */

										updateM(8, "Dang holdup", mid);
										Log.e(G,
												"getPage() Not connected to hostname("
														+ rhostname
														+ ") TIMEOUT REACHED");
										break;
									}
									SystemClock.sleep(300);
								}
							}

							// w(TAG,"getPage() Creating Writable to hostname("+hostname+") port("+port+")");
							bw = new BufferedWriter(new OutputStreamWriter(
									sslsocket.getOutputStream()));
							// w(TAG,"getPage() Creating Readable to hostname("+hostname+") port("+port+")");
							br = new BufferedReader(new InputStreamReader(
									sslsocket.getInputStream()));
						}
						//
						//
						//
						//
						//

						rrs = 2;//
						// mEdt.putLong(loc+"_connected",System.currentTimeMillis());
						// mEdt.putLong("lastfeedactive",
						// System.currentTimeMillis());mEdt.commit();
						Log.w(G, "getPage() Requesting document(" + rdocpath
								+ ") hostname(" + rhostname + ") port(" + rport
								+ ") limit(" + DOWNLOAD_LIMIT + ")");

						bw.write("GET " + rdocpath + " HTTP/1.0\r\n");

						bw.write("Host: " + rhostname + "\r\n");
						bw.write("User-Agent: Android\r\n");
						if (DOWNLOAD_LIMIT > 0) {
							bw.write("Range: bytes=0-"
									+ (1024 * DOWNLOAD_LIMIT) + "\r\n");
						}
						// + "\r\n");
						// bw.write("TE: deflate\r\n");
						bw.write("\r\n");

						bw.flush();
						// http://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html#sec5
						/*
						 * {
						 * 
						 * ContentValues v9 = new ContentValues();
						 * v9.put("srview", secure ?
						 * "Request securely delivered." :
						 * "Request delivered."); v9.put("srdate", datetime());
						 * SqliteWrapper.update(mCtx, getContentResolver(),
						 * Uri.parse(getString(R.string.cp) + "/filter"), v9,
						 * "_id=" + fid, null);
						 * 
						 * }//
						 */

						//
						//
						//
						//
						//

						// updateM(3, "Connected", mid);
						String line = "";
						String sourcebody = "";
						int httpPageSize = 0;

						try {
							/*
							 * if( !secure ){ if( br.ready() ){
							 * w(TAG,"getPage() Ready to be read"); }else{ int
							 * loopcnt2 = 0;long sv =
							 * SystemClock.uptimeMillis(); while( !br.ready() ){
							 * e(TAG,"getPage() NOT Ready to be read");
							 * loopcnt2++; if( SystemClock.uptimeMillis() - sv >
							 * 30000 ){ e(TAG,
							 * "getPage() NOT Ready to be read TIMEOUT REACHED WAITING"
							 * ); //line = br.readLine(); //e(TAG,
							 * "getPage() NOT Ready to be read TIMEOUT REACHED WAITING line("
							 * +line+")"); break; } SystemClock.sleep(300); } }
							 * }else{ // br.ready() doesn't work from the
							 * sslsocket source }//
							 */
							Log.i(G,
									"####################Ready for Reply#############");

							int linecnt = 0;

							// updateM(4, "Handshaking", mid);

							String sourceheader = "";
							ContentValues h3 = new ContentValues();

							for (line = br.readLine(); line != null; line = br
									.readLine()) {
								if (line.length() == 0) {
									if (rrs == 2) {
										rrs = System.currentTimeMillis();
										Log.w(G, "Took " + (rrs - rrr)
												+ "ms ready to respond.");// mEdt.putLong(loc+"_respondedms",(rrs-rrr));mEdt.putLong(loc+"_responded",System.currentTimeMillis());mEdt.commit();
										h3.put("protostatus", line);

									}
									Log.w(G, "getPage() End of header Reached");
									break;
								}
								linecnt++;

								Log.i(G, "Header " + rhostname + ":" + line
										+ ":;");
								sourceheader += line;

								if (line.regionMatches(true, 0, "Location:", 0,
										9)) {
									String burl = line.replaceFirst(".*?:", "")
											.trim();

									h3.put("rurl", burl);

									Log.i(G,
											"getPage() location("
													+ Uri.decode(burl) + ") ");
								} else if (line.regionMatches(true, 0,
										"Content-Length:", 0, 15)
										&& line.replaceAll(".*?:", "").trim()
												.matches("[0-9]+")) {
									int css = Integer.parseInt(line.replaceAll(
											".*?:", "").trim());
									// mEdt.putLong(loc+"_length",css);

									h3.put("contentlength", css);

									Log.i(G, "getPage() contentlength(" + css
											+ ")");
								} else if (line.regionMatches(true, 0,
										"Content-Type:", 0, 13)) {
									String ct = line.replaceAll(".*?:", "")
											.replaceAll(";.*", "").trim();

									h3.put("contenttype", ct);

									// mEdt.putString(loc+"_type",ct);
									Log.i(G, "getPage() contenttype(" + ct
											+ ")");
									if (line.toLowerCase().matches(
											".*;.*?charset.*?=.*")) {
										String ch = line
												.replaceAll(
														".*;.*?[cC][hH][aA][rR][sS][eE][tT].*?=",
														"").trim();

										h3.put("charset", ch.toUpperCase());

										// mEdt.putString(loc+"_charset",ch.toUpperCase());
										Log.i(G, "getPage() charset(" + ch
												+ ")");
									}
								}

							}

							int w = 0;
							if (h3.containsKey("contentlength")) {
								w = h3.getAsInteger("contentlength");
							}
							{
								h3.put("header", sourceheader);
								SqliteWrapper
										.update(mCtx,
												mCtx.getContentResolver(),
												Uri.parse("content://com.havenskys.elk/retrospect"),
												h3, "_id=" + rid, null);
							}

							{
								String nr = "";
								Cursor b;
								b = SqliteWrapper
										.query(mCtx,
												mCtx.getContentResolver(),
												Uri.parse("content://com.havenskys.elk/retrospect"),
												new String[] { "rurl" }, "_id="
														+ rid, null,
												"created desc limit 1");
								if (b != null) {
									if (b.moveToFirst()) {
										nr = b.getString(0);
									}
									b.close();
								}
								if (nr.length() > 0 && !nr.contentEquals(rurl)) {
									rurl = nr;
								} else {
									rurl = "";
								}

							}

							if (rurl.length() > 0) {

								updateM(7, "Interconnection " + loopcnt, mid);
								Log.w(G, "getPage() forward url: " + rurl);
								continue;
							}
							if (line == null) {
								Log.w(G, "getPage() End of read");
							}
							// if( linecnt > 0 ){
							// mEdt.putLong("lastfeedactive",
							// System.currentTimeMillis());
							// mEdt.putLong("lowmemory", 0);
							// }
							// mEdt.commit();

							// updateM(5, "Located", mid);
							int spout = 0;

							if (line != null) {
								// int zerocnt = 0;
								char[] u7 = new char[1024 * 32];

								// String pline = "";

								// String ls2 = "";
								{
									Message ml = new Message();
									Bundle bl = new Bundle();
									bl.putInt("mid", mid);
									ml.setData(bl);
									prcheck.sendMessageDelayed(ml, 896);
								}

								int cs = -1;
								prx7 = SystemClock.uptimeMillis();
								// String u7 = "";
								// for (u7 = br.readLine(); u7 != null; u7 = br
								// .readLine()) {

								for (cs = br.read(u7); cs > -1; cs = br
										.read(u7)) {
									// cs = u7.length();

									// String b = "";
									// int cs = 1;
									// for (int u7 = br.read(); u7 > -1; u7 = br
									// .read()) {
									// b = String.valueOf(u7);
									// line += b;
									// if (!b.contains("\n")) {
									// continue;
									// }

									// Security Alert
									line = Uri.encode(new String(u7),
											"[\n \t<>\"'=/]");

									u7 = new char[1024 * 32];
									// line = line.replaceAll("%00", "");
									// "[A-Za-z0-9.-/,<>;:=+_\n \t]");
									// if (cs < 324) {

									// if (line.length() > 0
									// && pline.compareTo(line) == 0) {
									// Log.i(G, "skip dup input " + cs);

									// continue;
									// }
									// pline = line;
									// Log.i(G, "" + rhostname + ":" + line);
									// }

									// ls2 = line;

									line = line
											.replaceAll(
													"%([8-9A-F1][0-9A-F]|7F|0[01-8BCDEF])",
													"");

									Log.i(G, "" + rhostname + "=" + line);

									// line = new String(u7);
									// if (ls2.contentEquals(line) ){
									// + ":;");
									line = Uri.decode(line);

									// line =
									// line.replaceAll("\t+"," ").replaceAll("\n+","\n").replaceAll(" +"," ").replaceAll(" +<","<").replaceAll("> +",">");
									// line =
									// line.replaceAll("\n+<","<").replaceAll(">\n+",">");

									// line =
									// line.replaceAll(">",">\n").replaceAll("<","\n<");
									// line = line.replaceAll("\n+</","</");
									// linecnt++;

									// fs.write(line.getBytes());
									// line = line.trim();

									if (line.length() > 0) {// fs != null &&
										// fs.write(line.getBytes());}
										httpPageSize += cs;
										spout += cs;
										sourcebody += line;

										int prx = (int) (SystemClock
												.uptimeMillis() - prx7);

										if (spout > 1024 * 3 && prx > 3850) {

											String ds = "";
											if (h3.containsKey("contentlength")) {

												ds = (int) ((float) httpPageSize
														/ (float) w * (float) 100)
														+ "%";

											} else {
												ds = "live";
											}

											updateM(5, "Transmission " + ds,
													mid);

											Log.i(G,
													"getPage() being sent "
															+ sourcebody
																	.length()
															+ " bytes secure("
															+ secure
															+ ") encoded("
															+ line.length()
															+ ") " + prx
															+ "ms "
															+ (spout / prx)
															* 60 + "b/s" + " "
															+ ds);
											prx7 = SystemClock.uptimeMillis();

											spout = 0;

											// httpPage += Uri.decode(line);

											if (DOWNLOAD_LIMIT > 0
													&& httpPageSize > 1024 * DOWNLOAD_LIMIT) {
												Log.w(G,
														"getPage() downloaded "
																+ httpPageSize
																+ " >"
																+ (1024 * DOWNLOAD_LIMIT)
																+ "K from the site, moving on.");

												updateM(8, "Drat overload", mid);
												break;
											}//

										}

										// line = "";

										// if (cs == -1) {
										// break;
										// }

									}

								}
							}
							// kilo
							{

								String ds = "";
								if (httpPageSize / 1024 > 212) {
									ds += " (megs) "
											+ (float) (((httpPageSize * 10) / 1024) / 10);
									// + " megs";
								} else if ((httpPageSize > 1024)) {

									ds += " (kilobytes) "
											+ (int) (httpPageSize / 1024);
									// + " kilobytes";
								} else {
									ds += " (bytes) " + httpPageSize;
									// + " bytes";
								}
								prx7 = -1;
								// updateM(6, "Exchange 100%", mid);
								Log.i(G, "getPage() download concluded");
							}

						} catch (OutOfMemoryError e2) {
							updateM(8,
									"Dang hassling how much "
											+ e2.getLocalizedMessage(), mid);

							Log.e(G, "getPage() OutOfMemoryError e2");
							e2.printStackTrace();

						} catch (IOException e1) {
							String msg = null;
							msg = e1.getLocalizedMessage() != null ? e1
									.getLocalizedMessage() : e1.getMessage();
							if (msg == null) {
								msg = e1.getCause().getLocalizedMessage();
								if (msg == null) {
									msg = "undeclared";
								}
							}

							updateM(8, "Dang " + msg, mid);
							Log.e(G,
									"getPage() IOException while reading from web server "
											+ msg);
							e1.printStackTrace();
						}// */

						if (!secure) {
							socket.close();
						} else {
							sslsocket.close();
						}

						{
							CookieStore cs2 = mHC.getCookieStore();
							List<Cookie> cl2 = cs2.getCookies();

							Bundle co = new Bundle();
							String cshort2 = "";
							for (int i = cl2.size() - 1; i >= 0; i--) {
								Cookie c3 = cl2.get(i);
								if (co.containsKey(c3.getName())) {
									continue;
								}
								co.putInt(c3.getName(), 1);
								cshort2 += c3.getName()
										+ " "
										+ c3.getValue()
										+ (c3.getExpiryDate() != null ? "; expires="
												+ c3.getExpiryDate()
												: "")
										+ (c3.getPath() != null ? "; path="
												+ c3.getPath() : "")
										+ (c3.getDomain() != null ? "; domain="
												+ c3.getDomain() : "") + "\n";
							}
							if (cshort2.length() > 0) {
								ContentValues c = new ContentValues();
								c.put("cookies", cshort2);
								SqliteWrapper
										.update(mCtx,
												mCtx.getContentResolver(),
												Uri.parse("content://com.havenskys.elk/retrospect"),
												c, "_id=" + rid, null);
								/*
								 * SqliteWrapper.update( mCtx,
								 * mCtx.getContentResolver(),
								 * Uri.parse("content://com.havenskys.elk/filter"
								 * ), c, "_id=" + fid, null);//
								 */
							}
						}

						// fs.flush();
						// fs.close();

						// FileInputStream h = new FileInputStream(f);
						// byte[] bx = new byte[(int)f.length()];
						// h.read(bx);
						// httpPage = new String(bx);

						// Log.i(G,"############## httpPage download " +
						// httpPageSize );

						/*
						 * { ContentValues v4 = new ContentValues();
						 * v4.put("srview", "Saved " + httpPageSize + " bytes");
						 * v4.put("srdate", datetime()); SqliteWrapper.update(
						 * mCtx, getContentResolver(),
						 * Uri.parse("content://com.havenskys.elk/filter" ), v4,
						 * "_id=" + fid, null); }//
						 */
						{
							ContentValues v4 = new ContentValues();
							v4.put("body", sourcebody);
							SqliteWrapper
									.update(mCtx,
											mCtx.getContentResolver(),
											Uri.parse("content://com.havenskys.elk/retrospect"),
											v4, "_id=" + rid, null);
							// Log.i(G, "Recorded retrospect");
						}

						{
							ContentValues v4 = new ContentValues();
							v4.put("content", sourcebody);
							v4.put("filtered", "");
							SqliteWrapper
									.update(mCtx,
											mCtx.getContentResolver(),
											Uri.parse("content://com.havenskys.elk/moment"),
											v4, "_id=" + mid + "", null);

							// Log.i(G, "Recorded moment");

						}

						// updateM(6, "Transmission complete", mid);

						if (true) {

							sguff.sendEmptyMessageDelayed(mid, 75);

						}

					}

				} catch (ConnectException e2) {
					updateM(8, "Dang " + e2.getLocalizedMessage(), mid);
					Log.e(G, "connectException");
					e2.printStackTrace();
				} catch (UnknownHostException e1) {
					updateM(8, "Dang name unknown", mid);
					Log.e(G, "unknownHostException " + e1.getLocalizedMessage());
					// e1.printStackTrace();
				} catch (IOException e1) {
					updateM(8, "Dang " + e1.getLocalizedMessage(), mid);
					Log.e(G, "getPage() IOException");
					e1.printStackTrace();

				} finally {
					prx7 = -1;
				}

			}
		};
		gb.start();

	}

	Handler sguff = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "sguff good xxx");

			int mid = msg.what;
			HandlerThread yx = new HandlerThread(G,
					Process.THREAD_PRIORITY_DEFAULT);
			yx.start();
			Scan gs = new Scan(yx.getLooper());

			gs.set(mCtx, DataProvider.CONTENT_URI, null, null, null, true, mid,
					null, null, null, null);

			gs.go(5);
		}
	};

	long prx7 = -1;
	Handler prcheck = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(G, "prcheck good xxx");

			Bundle bdl = msg.getData();
			int mid = bdl.getInt("mid");
			if (prx7 > -1) {
				int sx = (int) (SystemClock.uptimeMillis() - prx7) / 2000;

				if (sx > 2) {
					String so = "S";
					for (int o = 0; o < sx && o < 32; o++) {

						if (o < 10) {
							so += "l";
						} else if (o < 10) {
							so += "o";
						} else if (o < 20) {
							so += "w";
						}

					}
					if (sx > 30) {
						updateM(8, "Dang connection was noisy. ", mid);
						return;
					}
					updateM(8, so + "!", mid);
				}

			} else {
				return;
			}
			{
				Message ml = new Message();
				Bundle bl = new Bundle();
				bl.putInt("mid", mid);
				ml.setData(bl);
				prcheck.sendMessageDelayed(ml, 880);
			}
		}
	};

}
