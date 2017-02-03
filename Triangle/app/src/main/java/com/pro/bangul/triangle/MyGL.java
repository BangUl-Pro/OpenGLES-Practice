package com.pro.bangul.triangle;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLU;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Bangul-Pro on 2017. 2. 2..
 */

public class MyGL extends GLES10 {

    Context context;
    int displayWidth, displayHeight;
    boolean isInit;
    GL10 gl;

    Axis axis;
    Grid grid;

    float angle;
    FloatBuffer triangleVertexPointer, triangleColorPointer;



    public MyGL(Context context) {
        this.context = context;
    }


    public void init(int width, int height) {
        displayWidth = width;
        displayHeight = height;

        axis = new Axis();
        grid = new Grid();

        //////////////////// Triangle //////////////////////
        float[] triangleVertexArray = {
                -1,0,0,
                1,0,0,
                0,2,0
        };
        triangleVertexPointer = Utils.loadPointer(triangleVertexArray);

        float[] triangleColorArray = {
                1,0,0,1,
                0,1,0,1,
                0,0,1,1,
        };
        triangleColorPointer = Utils.loadPointer(triangleColorArray);




        glClearColor(1,1,1,1);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(gl, 60, (float) displayWidth / displayHeight, 1, 100);
        GLU.gluLookAt(gl, 6,6,6,    0,0,0,  0,1,0);
    }


    public void main() {
        glClear(GL_COLOR_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        axis.draw(this);

        glLoadIdentity();
        grid.draw(this);


        ////////////////////////// Triangle //////////////////////////
        glLoadIdentity();
        glRotatef(angle += 2, 0,1,0);

        glVertexPointer(3, GL_FLOAT, 0, triangleVertexPointer);
        glColorPointer(4, GL_FLOAT, 0, triangleColorPointer);
        glDrawArrays(GL_TRIANGLES, 0, 3);
    }
}
