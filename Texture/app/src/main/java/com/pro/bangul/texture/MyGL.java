package com.pro.bangul.texture;

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
    FloatBuffer planeVertexPointer, planeTexturePointer;    // plane 정점, 텍스쳐 위치 정보
    int tex[] = new int[1];                                 // 텍스터 저장 배열


    public MyGL(Context context) {
        this.context = context;
    }

    public void init(int width, int height) {
        displayWidth = width;
        displayHeight = height;

        axis = new Axis();
        grid = new Grid();



        float[] planeVertexArray = {
                -1,-1,0,
                1,-1,0,
                -1,1,0,
                1,1,0,
        };
        planeVertexPointer = Utils.loadPointer(planeVertexArray);

        // 텍스쳐 위치 정보 저장
        float[] planeTextureArray = {
                0,1,    1,1,    0,0,    1,0
        };
        planeTexturePointer = Utils.loadPointer(planeTextureArray);

        glGenTextures(1, tex, 0);
        Utils.loadTexture(context, this, tex[0], R.drawable.imag);

        glClearColor(1,1,1,1);
        glEnableClientState(GL_VERTEX_ARRAY);

        glViewport(0,0, displayWidth, displayHeight);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(gl, 60, (float) displayWidth / displayHeight, 1, 100);
        GLU.gluLookAt(gl, 6,6,6,    0,0,0,  0,1,0);
    }

    public void main() {
        glClear(GL_COLOR_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);

        glEnableClientState(GL_COLOR_ARRAY);
        glLoadIdentity();
        axis.draw(this);
        grid.draw(this);
        glDisableClientState(GL_COLOR_ARRAY);

        glEnable(GL_TEXTURE_2D);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        // STRIPE
        glLoadIdentity();
        glRotatef(angle += 2, 0,1,0);

        glVertexPointer(3, GL_FLOAT, 0, planeVertexPointer);
        glTexCoordPointer(2, GL_FLOAT, 0, planeTexturePointer);
        glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisable(GL_TEXTURE_2D);
    }
}
