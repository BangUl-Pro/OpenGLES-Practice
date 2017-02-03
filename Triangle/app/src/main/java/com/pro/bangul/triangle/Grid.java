package com.pro.bangul.triangle;

import android.opengl.GLES10;

import java.nio.FloatBuffer;

/**
 * Created by Bangul-Pro on 2017. 2. 2..
 */

public class Grid {

    FloatBuffer vertexPointer, colorPointer;
    private static final float GRAY = .5f;

    public Grid() {
        float[] vertexArray = {
                -10,0,0,    10,0,0,
                0,0,-10,    0,0,10,
        };
        vertexPointer = Utils.loadPointer(vertexArray);

        float[] colorArray = {
                GRAY, GRAY, GRAY, GRAY,     GRAY, GRAY, GRAY, GRAY,
                GRAY, GRAY, GRAY, GRAY,     GRAY, GRAY, GRAY, GRAY,
        };
        colorPointer = Utils.loadPointer(colorArray);
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
