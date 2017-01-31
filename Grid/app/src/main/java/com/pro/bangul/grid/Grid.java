package com.pro.bangul.grid;

import android.opengl.GLES10;

import java.nio.FloatBuffer;

/**
 * Created by Bangul-Pro on 2017. 1. 31..
 */

public class Grid {
    private static final float GRAY = .5f;
    FloatBuffer vertexPointer, colorPointer;

    public Grid() {
        float[] vertexArray = {
                -10,0,0,        10,0,0,
                0,0,-10,        0,0,10,
        };
        vertexPointer = MyGLUtils.loadPointer(vertexArray);

        float[] colorArray = {
                GRAY, GRAY, GRAY, GRAY,     GRAY, GRAY, GRAY, GRAY,
                GRAY, GRAY, GRAY, GRAY,     GRAY, GRAY, GRAY, GRAY,
        };
        colorPointer = MyGLUtils.loadPointer(colorArray);
    }


    public void draw(GLES10 gl) {
        gl.glVertexPointer(3, GLES10.GL_FLOAT, 0, vertexPointer);
        gl.glColorPointer(4, GLES10.GL_FLOAT, 0, colorPointer);
        gl.glLineWidth(1);

        for (int i = -10; i <= 10; i++) {
            gl.glLoadIdentity();
            gl.glTranslatef(0,0,i);
            gl.glDrawArrays(GLES10.GL_LINES, 0, 2);

            gl.glLoadIdentity();
            gl.glTranslatef(i,0,0);
            gl.glDrawArrays(GLES10.GL_LINES, 2, 2);
        }
    }
}
