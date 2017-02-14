package com.pro.bangul.plane;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLU;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Bangul-Pro on 2017. 2. 3..
 */

public class MyGL extends GLES10 {
    Context context;
    int displayWidth, displayHeight;
    boolean isInit;
    GL10 gl;

    Axis axis;
    Grid grid;


    float angle;
    FloatBuffer planeVertexPointer, planeColorPointer;


    public MyGL(Context context) {
        this.context = context;
    }

    public void init(int width, int height) {
        displayWidth = width;
        displayHeight = height;

        axis = new Axis();
        grid = new Grid();



        /////////////////////////////////// Plane ///////////////////////////////////
        float[] planeVertexArray = {
                // STRIP 방식
                -1,-1,0,
                1,-1,0,
                -1,1,0,
                1,1,0,

                // FAN 방식
                -1,-1,0,
                1,-1,0,
                1,1,0,
                -1,1,0,

                // TRIANGLES 방식
                -1,-1,0,
                1,-1,0,
                -1,1,0,
                -1,1,0,
                1,-1,0,
                1,1,0,
        };
        planeVertexPointer = Utils.loadPointer(planeVertexArray);

        float[] planeColorArray = {
                1,0,0,1,
                1,0,0,1,
                1,0,0,1,
                1,0,0,1,

                0,1,0,1,
                0,1,0,1,
                0,1,0,1,
                0,1,0,1,

                0,0,1,1,
                0,0,1,1,
                0,0,1,1,
                0,0,1,1,
                0,0,1,1,
                0,0,1,1,
        };
        planeColorPointer = Utils.loadPointer(planeColorArray);



        glClearColor(1,1,1,1);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glViewport(0, 0, displayWidth, displayHeight);
        GLU.gluPerspective(gl, 60, (float) displayWidth / displayHeight, 1, 100);
        GLU.gluLookAt(gl, 6,6,6,    0,0,0,  0,1,0);
    }

    public void main() {
        glClear(GL_COLOR_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);

        glLoadIdentity();
        axis.draw(this);
        grid.draw(this);

        // STRIP
        glLoadIdentity();
        glRotatef(angle += 2, 0,1,0);

        glVertexPointer(3, GL_FLOAT, 0, planeVertexPointer);
        glColorPointer(4, GL_FLOAT, 0, planeColorPointer);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);


        // FAN
        glLoadIdentity();
        glTranslatef(2, 0, 0);
        glRotatef(angle, 0,1,0);

        glColorPointer(4, GL_FLOAT, 0, planeColorPointer);
        glDrawArrays(GL_TRIANGLE_FAN, 4, 4);



        // TRIANGLE
        glLoadIdentity();
        glTranslatef(0,0,2);
        glRotatef(angle, 0,1,0);

        glColorPointer(4, GL_FLOAT, 0, planeColorPointer);
        glDrawArrays(GL_TRIANGLES, 8, 6);
    }
}
