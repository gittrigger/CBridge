/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.havenskys.elk;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * A vertex shaded cube.
 */
class Cube
{
    public Cube()
    {

    	float one = 1.0f;//0x10000;
        float vertices[] = {
                -one, -one, -one,
                one, -one, -one,
                one,  one, -one,
                -one,  one, -one,
                -one, -one,  one,
                one, -one,  one,
                one,  one,  one,
                -one,  one,  one,
        };

        
        //*

        int on = 0x10000;
        int colors[] = {
                0,    0,    0,  on,
                on,    0,    0,  on,
                on,  on,    0,  on,
                0,  on,    0,  on,
                0,    0,  on,  on,
                on,    0,  on,  on,
                on,  on,  on,  on,
                0,  on,  on,  on,
        };//*/

        byte indices[] = {
                0, 4, 5,    0, 5, 1,
                1, 5, 6,    1, 6, 2,
                2, 6, 7,    2, 7, 3,
                3, 7, 4,    3, 4, 0,
                4, 7, 6,    4, 6, 5,
                3, 0, 1,    3, 1, 2
        };

        // Buffers to be passed to gl*Pointer() functions
        // must be direct, i.e., they must be placed on the
        // native heap where the garbage collector cannot
        // move them.
        //
        // Buffers with multi-byte datatypes (e.g., short, int, float)
        // must have their byte order set to native order

        ByteBuffer vbb = ByteBuffer.allocateDirect((vertices.length/3)*3*4);
        vbb.order(ByteOrder.nativeOrder());
        //mVertexBuffer = vbb.asIntBuffer();
        //mVertexBuffer.put(vertices);
        mFVertexBuffer = vbb.asFloatBuffer();

        for (int i = 0; i < vertices.length/3; i++) {
            for(int j = 0; j < 3; j++) {
                mFVertexBuffer.put(vertices[i*3+j]);// * 1);
            }
        }      
//        mVertexBuffer.position(0);

        
        mFVertexBuffer.position(0);
        
        
        
        ByteBuffer tbb = ByteBuffer.allocateDirect((vertices.length/3)*2*4);
        tbb.order(ByteOrder.nativeOrder());
        mTexBuffer = tbb.asFloatBuffer();
        //mTexBuffer = tbb.asIntBuffer();
        //mTexBuffer.put(vertices);
        for (int i = 0; i < vertices.length/3; i++) {
            for(int j = 0; j < 2; j++) {
                mTexBuffer.put((float)vertices[i*3+j]);// + 0.5f);// * 1.0f + 0.5f);
            }
        }        
        mTexBuffer.position(0);
        
        
        
        
        //*/
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asIntBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
        //*/
        
        
        
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
    }

    public void draw(GL10 gl)
    {
        gl.glFrontFace(gl.GL_CW);
//        gl.glVertexPointer(3, gl.GL_FIXED, 0, mVertexBuffer);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
        
        gl.glColorPointer(4, gl.GL_FIXED, 0, mColorBuffer);

        gl.glEnable(GL10.GL_TEXTURE0);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTexBuffer);
        //gl.glTexCoordPointer(2, gl.GL_FIXED, 0, mTexBuffer);
        
        
        gl.glDrawElements(gl.GL_TRIANGLES, 36, gl.GL_UNSIGNED_BYTE, mIndexBuffer);
    }

    private FloatBuffer mFVertexBuffer;
    //private IntBuffer mVertexBuffer;
    //private IntBuffer mTexBuffer;
    private FloatBuffer mTexBuffer;
    private IntBuffer   mColorBuffer;
    private ByteBuffer  mIndexBuffer;

    
}
