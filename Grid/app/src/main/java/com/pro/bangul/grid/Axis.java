package com.pro.bangul.grid;

import android.opengl.GLES10;

import java.nio.FloatBuffer;

/**
 * Created by Bangul-Pro on 2017. 1. 31..
 */

public class Axis {
    FloatBuffer vertexPointer, colorPointer;

    public Axis() {
        float[] vertexArray = {
                4,0,0,      0,0,0,
                0,4,0,      0,0,0,
                0,0,4,      0,0,0,
        };
        vertexPointer = MyGLUtils.loadPointer(vertexArray);

        float[] colorArray = {
                1,0,0,1,    1,0,0,1,
                0,1,0,1,    0,1,0,1,
                0,0,1,1,    0,0,1,1,
        };
        colorPointer = MyGLUtils.loadPointer(colorArray);
    }


    public void draw(GLES10 gl) {
        gl.glLoadIdentity();
        gl.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexPointer);
        gl.glColorPointer(4, GLES10.GL_FLOAT, 0, colorPointer);
        gl.glLineWidth(3);
        gl.glDrawArrays(GLES10.GL_LINES, 0, 6);
    }
}
