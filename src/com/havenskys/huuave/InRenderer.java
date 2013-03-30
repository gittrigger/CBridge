package com.havenskys.huuave;

import android.opengl.GLSurfaceView;
import android.util.Log;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Date;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.egl.EGL10;

import com.havenskys.elk.R;
import com.havenskys.huuave.InRenderer.AnimationCallback;
//import javax.microedition.khronos.egl.EGL11;
//import javax.microedition.khronos.egl.EGLConfig;
//import javax.microedition.khronos.egl.EGLContext;
//import javax.microedition.khronos.egl.EGLDisplay;
//import javax.microedition.khronos.egl.EGLSurface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.os.SystemClock;

/**
 * Example of how to use OpenGL|ES in a custom view
 * 
 */
public class InRenderer implements GLSurfaceView.Renderer {

	/**
	 * initiate
	 * 
	 * 
	 * @param context
	 *            Application Context
	 * @param worlds
	 *            World class array
	 */

	public InRenderer(Context context, World[] worlds) {
		mContext = context;
		mWorlds = worlds;
		mCallback = (AnimationCallback) context;

		mProjector = new Projector();
		// mTri = new Triangle();

		// mTextureLoader = new RobotTextureLoader();

		mLabelPaint = new Paint();
		mLabelPaint.setTextSize(26);
		mLabelPaint.setAntiAlias(true);
		mLabelPaint.setARGB(0xff, 0x2F, 0x4f, 0xff);

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		gl.glDisable(GL10.GL_DITHER);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

		gl.glClearColor(0, 0, 0, gxf < .6f ? 0 : gxf - .6f);
		// gl.glClearColor(1f,1f,1f,0.8f);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glEnable(GL10.GL_TEXTURE0);

		// mTextureLoader.load(gl);

		if (mLabels != null) {
			mLabels.shutdown(gl);
		} else {
			mLabels = new LabelMaker(true, 256, 64);
		}

		mLabels.initialize(gl);
		mLabels.beginAdding(gl);
		// mLabelA = mLabels.add(gl, "A", mLabelPaint);
		// mLabelB = mLabels.add(gl, "B", mLabelPaint);
		// mLabelC = mLabels.add(gl, "C", mLabelPaint);
		mLabelCo = mLabels.add(gl, "G", mLabelPaint);
		mLabeltfr = mLabels.add(gl, "", mLabelPaint);
		mLabelMsPF = mLabels.add(gl, "ms/f", mLabelPaint);
		mLabels.endAdding(gl);

		if (mNumericSprite != null) {
			mNumericSprite.shutdown(gl);
		} else {
			mNumericSprite = new NumericSprite();
		}
		mNumericSprite.initialize(gl, mLabelPaint);

		Paint csp = new Paint();
		csp.setTextSize(18);
		csp.setAntiAlias(true);
		csp.setARGB(0xff, 0x2F, 0x9f, 0xff);
		if (mCSprite != null) {
			mCSprite.shutdown(gl);
		} else {
			mCSprite = new NumericSprite();
		}
		mCSprite.initialize(gl, csp);

	}

	public void onSurfaceChanged(GL10 gl, int width, int height) {
		mWidth = width;
		mHeight = height;
		gl.glViewport(0, 0, width, height);
		// , show right
		mProjector.setCurrentView(0, 0, width, height);
		float ratio = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 2560);

		mProjector.getCurrentProjection(gl);
		// gl.glDisable(GL10.GL_DITHER);
		// gl.glActiveTexture(GL10.GL_TEXTURE_2D);
	}

	public void onDrawFrame(GL10 gl) {
		if (mCallback != null) {
			mCallback.animate();
		}

		gl.glDisable(GL10.GL_DITHER);
		//
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		// gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

		gl.glActiveTexture(GL10.GL_TEXTURE0);

		gl.glColor4f(1, 0, 0, 0);// traslucent colored background
		gl.glClearColor(0, 0, 0, 1);
		// gl.glClearColor(0, .05f, .1f, gxf < .6f ? 0 : gxf - .6f);// red green
		// blue
		// alpha
		// if (gxf < 1.59f) {
		// gxf += .024f;
		// }
		// gl.glTexEnvx(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
		// GL10.GL_MODULATE);

		float matAmbient[] = new float[] { 0.5f, 0.0f, 0.1f, .5f };
		float matDiffuse[] = new float[] { 0.5f, 0.1f, 1.0f, .2f };

		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, matAmbient,
				0);
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDiffuse, 0);

//		gl.glEnable(GL10.GL_CULL_FACE);
		gl.glEnable(GL10.GL_BLEND);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);

		// gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glEnable(GL10.GL_DEPTH_TEST);

		// gl.glClearColor(1f,1f,1f,0f);

		/*
		 * Now we're ready to draw some 3D object
		 */

		// GLU.gluLookAt(gl, 0, 0, -5, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
		/*
		 * int dd = 1; if(mCameraZ > 100){dd = 10;}else if(mCameraZ > 30){dd =
		 * 1;} // orbit
		 * 
		 * mAngleY = (float) ((int) mAngleY/dd)*dd; mAngleZ = (float) ((int)
		 * mAngleZ/dd)*dd; mAngleX = (float) ((int) mAngleX/dd)*dd;
		 * 
		 * mCameraY = (float) ((int) mCameraY/dd)*dd; mCameraZ = (float) ((int)
		 * mCameraZ/dd)*dd; mCameraX = (float) ((int) mCameraX/dd)*dd; //
		 */
		// if(mCameraZ > 13 ){mCameraZ = 140;}
		// if(mCameraZ < 10 && mCameraZ > 3){mCameraZ = 10;}
		// if(mCameraZ < 0 && mCameraZ > -3){mCameraZ = -10;}
		// if(mCameraZ < -13 ){mCameraZ = -140;}

		float gx = 0.084f;
		float d = 0;

		if (mLCameraY == mCameraY) {
		} else if (mLCameraY > mCameraY) {
			d = mLCameraY - mCameraY;
			mLCameraY -= d * gx;
			if (mLCameraY < mCameraY || d < gx) {
				mLCameraY = mCameraY;
			}
		} else {
			d = mCameraY - mLCameraY;
			mLCameraY += d * gx;
			if (mLCameraY > mCameraY || d < gx) {
				mLCameraY = mCameraY;
			}
		}
		if (mLCameraZ == mCameraZ) {
		} else if (mLCameraZ > mCameraZ) {
			d = mLCameraZ - mCameraZ;
			mLCameraZ -= d * gx;
			if (mLCameraZ < mCameraZ || d < gx) {
				mLCameraZ = mCameraZ;
			}
		} else {
			d = mCameraZ - mLCameraZ;
			mLCameraZ += d * gx;
			if (mLCameraZ < mCameraZ || d < gx) {
				mLCameraZ = mCameraZ;
			}
		}
		if (mLCameraX == mCameraX) {
		} else if (mLCameraX > mCameraX) {
			d = mLCameraX - mCameraX;
			mLCameraX -= d * gx;
			if (mLCameraX < mCameraX || d < gx) {
				mLCameraX = mCameraX;
			}
		} else {
			d = mCameraX - mLCameraX;
			mLCameraX += d * gx;
			if (mLCameraX < mCameraX || d < gx) {
				mLCameraX = mCameraX;
			}
		}

		// if(mLCameraZ < 5){mLCameraZ = 5f;mCameraZ = 7f;}else if(mLCameraZ >
		// 60){mCameraZ = 60;mCameraZ = 50;}
		// Log.w(G,"GL View("+mLCameraY+"("+mCameraY+"),"+mLCameraX+"("+mCameraX+"),"+mLCameraZ+"("+mCameraZ+"))");
		gl.glTranslatef(mLCameraY, mLCameraX, mLCameraZ);// left-right up-down
															// in-out

		gl.glScalef(0.6f, 0.6f, 0.6f);
		// gl.glRotatef(3f, 0, 1, 0);//side
		// gl.glRotatef(16f, 1, 0, 0);//top

		if (mLAngleY == mAngleY) {
		} else if (mLAngleY > mAngleY) {
			d = mLAngleY - mAngleY;
			mLAngleY -= d * gx;
			if (mLAngleY < mAngleY || d < gx) {
				mLAngleY = mAngleY;
			}
		} else {
			d = mAngleY - mLAngleY;
			mLAngleY += d * gx;
			if (mLAngleY > mAngleY || d < gx) {
				mLAngleY = mAngleY;
			}
		}
		if (mLAngleZ == mAngleZ) {
		} else if (mLAngleZ > mAngleZ) {
			d = mLAngleZ - mAngleZ;
			mLAngleZ -= d * gx;
			if (mLAngleZ < mAngleZ || d < gx) {
				mLAngleZ = mAngleZ;
			}
		} else {
			d = mAngleZ - mLAngleZ;
			mLAngleZ += d * gx;
			if (mLAngleZ < mAngleZ || d < gx) {
				mLAngleZ = mAngleZ;
			}
		}
		if (mLAngleX == mAngleX) {
		} else if (mLAngleX > mAngleX) {
			d = mLAngleX - mAngleX;
			mLAngleX -= d * gx;
			if (mLAngleX < mAngleX || d < gx) {
				mLAngleX = mAngleX;
			}
		} else {
			d = mAngleX - mLAngleX;
			mLAngleX += d * gx;
			if (mLAngleX < mAngleX || d < gx) {
				mLAngleX = mAngleX;
			}
		}

		gl.glRotatef(mLAngleX, 0, 1, 0);
		gl.glRotatef(mLAngleY, 1, 0, 0);
		gl.glRotatef(mLAngleZ, 0, 0, 1);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_REPEAT);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);

		// mTextureLoader.load(gl);

		gl.glEnable(GL10.GL_LIGHTING);
		gl.glEnable(GL10.GL_LIGHT0);
		gl.glEnable(GL10.GL_LIGHT1);
		gl.glEnable(GL10.GL_LIGHT2);
		gl.glEnable(GL10.GL_LIGHT3);

		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, new float[] { 0, 1, 0,
				1.1f, 1 }, 0);
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, new float[] { 0f, 0f,
				1f, .8f }, 0);
		gl.glLightf(GL10.GL_LIGHT0, GL10.GL_SPOT_EXPONENT, 220f);

		gl.glEnable(GL10.GL_LIGHT1);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_POSITION, new float[] { 10, 3, 0,
				1f, 1 }, 0);
		gl.glLightfv(GL10.GL_LIGHT1, GL10.GL_AMBIENT, new float[] { 0f, 0,
				.9f, 1f }, 0);// red,green,blue,alpha
		gl.glLightf(GL10.GL_LIGHT1, GL10.GL_SPOT_EXPONENT, 210.0f);

		gl.glEnable(GL10.GL_LIGHT2);
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_POSITION, new float[] { 140, 3,
				1f, 0.5f, 1 }, 0);
		gl.glLightfv(GL10.GL_LIGHT2, GL10.GL_AMBIENT, new float[] { 0f, 0, .8f,
				.8f }, 0);// red,green,blue,alpha
		gl.glLightf(GL10.GL_LIGHT2, GL10.GL_SPOT_EXPONENT, 210.0f);

		gl.glEnable(GL10.GL_LIGHT3);
		gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_POSITION, new float[] { 0, 0,
				-50f, 1 }, 0);
		gl.glLightfv(GL10.GL_LIGHT3, GL10.GL_AMBIENT, new float[] { 0f, .9f,
				.1f, .8f }, 0);// red,green,blue,alpha
		gl.glLightf(GL10.GL_LIGHT3, GL10.GL_SPOT_EXPONENT, 220.0f);

		for (int w = 0; w < mWorlds.length && mWorlds[w] != null; w++) {

			mWorlds[w].draw(gl);
		}

		// mTri.draw(gl);

		mProjector.getCurrentModelView(gl);

		try {

			mLabels.beginAdding(gl);

			if (mLabels == null) {
				Log.e(G, "onDrawFrame fatal?");
				return;
			}

			// mLabels.initialize(gl);
			// mLabelA = mLabels.add(gl, "A"+mTri.getX(0), mLabelPaint);
			// mLabelB = mLabels.add(gl, "B"+mTri.getY(0), mLabelPaint);
			// mLabelC = mLabels.add(gl, "C"+mTri.getZ(0), mLabelPaint);

			// Log.w(G," draw " + mLCameraZ + " " + mWorlds[2].mPivotx);
			int ghy = 26;// (int) (mLCameraZ - mWorlds[2].mPivotx)*2;
			// if(ghy *-1 > 0){ghy *= -1;}if(ghy < 12){ghy = 12;} if(ghy >
			// 18){ghy =
			// 18;}
			mLabelPaint.setTextSize((float) ghy);
			mLabelPaint.setAntiAlias(false);
			mLabelPaint.setARGB(0xff, 0x1F, 0x5f, 0x9f);// alpha red green blue
			mLabelCo = mLabels.add(gl, "Good Health", mLabelPaint);
			// mLabelCo = mLabels.add(gl,
			// "Good Health "+SystemClock.uptimeMillis(),
			// mLabelPaint);
			mLabelMsPF = mLabels.add(gl, "ms/f", mLabelPaint);
			mLabeltfr = mLabels.add(gl, datetime(), mLabelPaint);
			mLabels.endAdding(gl);

			mLabels.beginDrawing(gl, mWidth, mHeight);
			// drawLabel(gl, 0, mLabelA);
			// drawLabel(gl, 1, mLabelB);
			// drawLabel(gl, 2, mLabelC);

			drawCode(gl, 0, 1, mLabelCo);
			drawCode(gl, 0, 10, mLabelCo);
			drawCode(gl, 0, 20, mLabelCo);
			// drawCode(gl, 2, 0, mLabelCo);
			drawCode(gl, 2, 1, mLabelCo);
			// drawCode(gl, 2, 3, mLabelCo);

			int bbase = 50;
			mLabels.draw(gl, (mWidth - mLabels.getWidth(mLabeltfr) - 1),
					mLabels.getHeight(mLabelMsPF) + 1 + bbase, mLabeltfr);

			float msPFX = mWidth - mLabels.getWidth(mLabelMsPF) - 1;
			mLabels.draw(gl, msPFX, 0 + bbase, mLabelMsPF);

			// mLabels.draw(gl, msPFX, 0, mLabelCo);

			mLabels.endDrawing(gl);

			mCSprite.setValue(datetime());

			mCSprite.draw(gl, 0, 0 + bbase, mWidth, mHeight);

			drawMsPF(gl, msPFX);

			// drawTFPS(gl, (mWidth - mLabels.getWidth(mLabeltfr) - 1 ));

			// mWorld.loadTexture(gl,mContext,R.drawable.redt);

			// orbit

			// gl.glEnable(GL10.GL_FOG);
			// gl.glFogfv(GL10.GL_FOG, new float[]{20,0,0,1}, 0);
			// gl.glEnable(GL10.GL_FOG_DENSITY);

		} catch (NullPointerException ep) {
			ep.printStackTrace();

		} catch (IllegalArgumentException ae) {

			// ae.printStackTrace();
		} catch (OutOfMemoryError em) {
			em.printStackTrace();
		}

	}

	private void drawCode(GL10 gl, int wid, int vertex, int labelId) {

		float z = mWorlds[wid].getZ(vertex);

		float x = mWorlds[wid].getX(vertex);
		float y = mWorlds[wid].getY(vertex);
		mScratch[0] = x;
		mScratch[1] = y;
		mScratch[2] = z;
		mScratch[3] = 1.0f;
		mProjector.project(mScratch, 0, mScratch, 4);
		float sx = mScratch[4];
		float sy = mScratch[5];
		float height = mLabels.getHeight(labelId);
		float width = mLabels.getWidth(labelId);
		float tx = sx;// - width * 0.2f;
		float ty = sy;// - height * 0.4f;
		mLabels.draw(gl, tx, ty, labelId);
		// orbit
	}

	public interface TextureLoader {
		void load(GL10 gl);
	}

	public interface AnimationCallback {
		void animate();
	}

	private class RobotTextureLoader implements TextureLoader {
		public void load(GL10 gl) {

			InputStream is = mContext.getResources().openRawResource(mImg);
			Bitmap bitmap;
			try {
				bitmap = BitmapFactory.decodeStream(is);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (OutOfMemoryError e2) {
					Log.e(G, "oom");
					e2.printStackTrace();
				}
			}
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
			bitmap.recycle();

			int[] textures = new int[1];
			gl.glGenTextures(1, textures, 0);

			mTextureId = textures[0];
			gl.glBindTexture(GL10.GL_TEXTURE_2D, mTextureId);

			// gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, width,
			// height, 0, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb);
			// gl.glTexParameterx(GL10.GL_TEXTURE_2D,
			// GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
			// gl.glTexParameterx(GL10.GL_TEXTURE_2D,
			// GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
					GL10.GL_NEAREST);
			gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
					GL10.GL_LINEAR);
			// gl.glTexParameterf(GL10.GL_TEXTURE_2D,
			// GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
			// gl.glTexParameterf(GL10.GL_TEXTURE_2D,
			// GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
			gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
					GL10.GL_REPEAT);
			gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
					GL10.GL_REPEAT);

			gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
					GL10.GL_REPLACE);

			loadTexture(gl, mContext, mImg);

		}
	}

	public static void loadTexture(GL10 gl, Context context, int resource) {

		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
				resource);

		ByteBuffer bb = extract(bmp);

		loadt(gl, bb, bmp.getWidth(), bmp.getHeight());

	}

	private static void loadt(GL10 gl, ByteBuffer bb, int width, int height) {
		int[] tmp_tex = new int[1];
		gl.glGenTextures(1, tmp_tex, 0);
		int tex = tmp_tex[0];
		// mTextureId = tex;

		gl.glBindTexture(GL10.GL_TEXTURE_2D, tex);
		gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_RGBA, width, height, 0,
				GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb);
		// gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
		// GL10.GL_LINEAR);
		// gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
		// GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		// gl.glTexParameterf(GL10.GL_TEXTURE_2D,
		// GL10.GL_TEXTURE_WRAP_S,GL10.GL_CLAMP_TO_EDGE);
		// gl.glTexParameterf(GL10.GL_TEXTURE_2D,
		// GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_REPEAT);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);

		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
				GL10.GL_REPLACE);

	}

	// orbit
	private static ByteBuffer extract(Bitmap bmp) {
		ByteBuffer bb = ByteBuffer.allocateDirect(bmp.getHeight()
				* bmp.getWidth() * 4);
		bb.order(ByteOrder.BIG_ENDIAN);
		IntBuffer ib = bb.asIntBuffer();

		// Conversion: ARGB - RGBA
		for (int y = bmp.getHeight() - 1; y > -1; y--) {
			for (int x = bmp.getWidth() - 1; x > -1; x--) {
				int pix = bmp.getPixel(x, bmp.getHeight() - y - 1);
				int red = ((pix >> 16) & 0xFF);
				int green = ((pix >> 8) & 0xFF);
				int blue = ((pix) & 0xFF);

				ib.put(red << 24 | green << 16 | blue << 8
						| ((red + blue + green) / 3));
			}
		}
		bb.position(0);
		return bb;
	}

	public String datetime() {
		String g = "";
		Date d = new Date();
		g = (d.getYear() + 1900) + "-" + ((d.getMonth() < 9) ? "0" : "")
				+ ((d.getMonth() + 1)) + "-" + ((d.getDate() < 10) ? "0" : "")
				+ d.getDate() + " " + ((d.getHours() < 10) ? "0" : "")
				+ d.getHours() + ":" + ((d.getMinutes() < 10) ? "0" : "")
				+ d.getMinutes() + ":" + ((d.getSeconds() < 10) ? "0" : "")
				+ d.getSeconds();
		// {Message mx = new Message(); Bundle bx = new
		// Bundle();bx.putString("text","generated date "+g);bx.putInt("l",1);mx.setData(bx);logoly.sendMessageDelayed(mx,pRate);}
		return g;
	}

	private void drawMsPF(GL10 gl, float rightMargin) {
		long time = SystemClock.uptimeMillis();
		if (mStartTime == 0) {
			mStartTime = time;
		}
		if (mFrames++ == SAMPLE_PERIOD_FRAMES) {
			mFrames = 0;
			long delta = time - mStartTime;
			mStartTime = time;
			mMsPerFrame = (int) (delta * SAMPLE_FACTOR);
		}
		if (mMsPerFrame > 0) {
			mNumericSprite.setValue(mMsPerFrame);
			float numWidth = mNumericSprite.width();
			float x = rightMargin - numWidth;
			mNumericSprite.draw(gl, x, 50, mWidth, mHeight);
		}
	}

	private void drawLabel(GL10 gl, int triangleVertex, int labelId) {

		float x = mTri.getX(triangleVertex);
		float y = mTri.getY(triangleVertex);
		mScratch[0] = x;
		mScratch[1] = y;
		mScratch[2] = 0.0f;
		mScratch[3] = 1.0f;
		mProjector.project(mScratch, 0, mScratch, 4);
		float sx = mScratch[4];
		float sy = mScratch[5];
		float height = mLabels.getHeight(labelId);
		float width = mLabels.getWidth(labelId);
		float tx = sx - width * 0.5f;
		float ty = sy - height * 0.5f;
		mLabels.draw(gl, tx, ty, labelId);
	}

	public static String G = "InRenderer";
	public int mImg = R.drawable.b5;

	private TextureLoader mTextureLoader;
	int mTextureId;
	Context mContext;
	World[] mWorlds = new World[2001];
	private AnimationCallback mCallback;

	private LabelMaker mLabels;
	private Paint mLabelPaint;
	// private int mLabelA;
	// private int mLabelB;
	// private int mLabelC;
	private int mLabelCo;
	private int mLabelMsPF;
	private int mLabeltfr;
	private NumericSprite mNumericSprite;
	private NumericSprite mCSprite;

	public float mAngleY = 0;
	public float mAngleX = 0;
	public float mAngleZ = 0;
	public float mLAngleY = 0;
	public float mLAngleX = 0;
	public float mLAngleZ = 0;
	public float mLCameraZ = -40;
	public float mLCameraX = 0;
	public float mLCameraY = 0;
	public float mCameraZ = 0;
	public float mCameraX = 0;//
	public float mCameraY = 0;// -left +right
	// orbit
	float gxf = .0001f;

	private final static int SAMPLE_PERIOD_FRAMES = 12;
	private long mStartTime;
	private int mFrames;
	private int mMsPerFrame;
	private final static float SAMPLE_FACTOR = 1.0f / SAMPLE_PERIOD_FRAMES;
	private int mWidth;
	private int mHeight;

	private Triangle mTri;
	private Projector mProjector;
	private float[] mScratch = new float[8];

}
