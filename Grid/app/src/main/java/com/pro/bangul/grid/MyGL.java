package com.pro.bangul.grid;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLU;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Bangul-Pro on 2017. 1. 31..
 */

public class MyGL extends GLES10 {

    Context context;
    int displayWidth, displayHeight;
    boolean isInit;
    GL10 gl;

    Axis axis;
    Grid grid;

    public MyGL(Context context) {
        this.context = context;
    }

    public void init(int width, int height) {
        displayWidth = width;
        displayHeight = height;

        axis = new Axis();
        grid = new Grid();

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        glClearColor(1,1,1,1);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(gl, 60, (float) displayWidth / displayHeight, 1, 100);
        GLU.gluLookAt(gl, 8,8,8,    0,0,0,  0,1,0);
    }

    public void main() {
        glClear(GL_COLOR_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
        axis.draw(this);
        grid.draw(this);
    }
}
