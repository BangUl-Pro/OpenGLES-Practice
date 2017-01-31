package com.bangulpro.timer;

import android.content.Context;
import android.opengl.GLES10;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Random;

/**
 * Created by IronFactory on 2017. 1. 24..
 */

public class MyGL extends GLES10 {

    Context context;
    int displayWidth, displayHeight;
    boolean isInit;
    float x, y, spdX = .1f, spdY = .1f;

    public MyGL(Context context) {
        this.context = context;
    }


    FloatBuffer loadPointer(float[] array) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        return (FloatBuffer) buffer.put(array).position(0);
    }


    FloatBuffer pointVertexPointer, pointColorPointer;


    public void init(int width, int height) {
        displayWidth = width;
        displayHeight = height;

        float[] pointVertexArray = {
                0,0
        };
        pointVertexPointer = loadPointer(pointVertexArray);

        float[] pointColorArray = {
                1,0,0,1
        };
        pointColorPointer = loadPointer(pointColorArray);

        glClearColor(1,1,1,1);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnableClientState(GL_VERTEX_ARRAY);

        glViewport(0,0,displayWidth,displayHeight);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrthof(-10,10,-15,15,0,1);
    }

    MyTimer timer = new MyTimer(2000);

    public void main() {
        timer.run(System.currentTimeMillis());
        glClear(GL_COLOR_BUFFER_BIT);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        if (!timer.flag) {
            x += spdX;
            y += spdY;

            if (x < -10 || x > 10)
                spdX = -spdX;
            if (y < -15 || y > 15)
                spdY = -spdY;
        }

        glTranslatef(x, y, 0);
        glPointSize(30);
        glVertexPointer(2, GL_FLOAT, 0, pointVertexPointer);
        glColorPointer(4, GL_FLOAT, 0, pointColorPointer);
        glDrawArrays(GL_POINTS, 0, 1);
    }
}
