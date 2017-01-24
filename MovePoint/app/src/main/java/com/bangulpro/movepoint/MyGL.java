package com.bangulpro.movepoint;

import android.content.Context;
import android.opengl.GLES10;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by IronFactory on 2017. 1. 24..
 */

public class MyGL extends GLES10 {

    Context context;
    int displayWidth, displayHeight;
    boolean isInit;

    float x, y, speedX = .5f, speedY = .1f;

    FloatBuffer pointVertexPointer, pointColorPointer;


    public MyGL(Context context) {
        this.context = context;
    }


    FloatBuffer loadPointer(float[] array) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        return (FloatBuffer) buffer.put(array).position(0);
    }


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
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        glViewport(0,0, displayWidth, displayHeight);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrthof(-10, 10, -15, 15, 0, 1);
    }


    public void main() {
        glClear(GL_COLOR_BUFFER_BIT);

        x += speedX;
        y += speedY;
        if (x < -10 || x > 10)
            speedX = -speedX;
        if (y < -15 || y > 15)
            speedY = -speedY;

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glTranslatef(x,y,0);

        glVertexPointer(2, GL_FLOAT, 0, pointVertexPointer);
        glColorPointer(4, GL_FLOAT, 0, pointColorPointer);
        glPointSize(30);
        
        glDrawArrays(GL_POINTS, 0, 1);
    }
}
