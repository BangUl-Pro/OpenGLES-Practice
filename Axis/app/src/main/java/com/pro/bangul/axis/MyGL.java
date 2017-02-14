package com.pro.bangul.axis;

import android.content.Context;
import android.opengl.GLES10;
import android.opengl.GLU;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Bangul-Pro on 2017. 1. 31..
 */

public class MyGL extends GLES10 {

    Context context;
    int displayWidth, displayHeight;
    GL10 gl;
    boolean isInit;

    final float HALF = .5f;

    FloatBuffer axisVertexPointer;      // axis의 정점 정보를 담는 배열
    FloatBuffer axisColorPointer;       // axis의 색상 정보를 담는 배열

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

        float axisVertexArray[] = {
                4,0,0,      0,0,0,      // x축 기준선
                0,4,0,      0,0,0,      // y축 기준선
                0,0,4,      0,0,0       // z축 기준선
        };
        axisVertexPointer = loadPointer(axisVertexArray);

        float axisColorArray[] = {
                1,0,0,1,      1,0,0,1,      // x축 기준선 색
                0,1,0,1,      0,1,0,1,      // y축 기준선 색
                0,0,1,1,      0,0,1,1,      // z축 기준선 색
        };
        axisColorPointer = loadPointer(axisColorArray);

        glClearColor(1,1,1,1);

        // vertex와 color 정보를 사용하겠다.
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        // Matrix 0으로 초기화
        glMatrixMode(GL_PROJECTION);

        // 단위 Matrix 준비
        glLoadIdentity();

        // 투영법 설정
        GLU.gluPerspective(gl, 60, (float) displayWidth / displayHeight, 1, 100);
        GLU.gluLookAt(gl, 10,10,10, 0,0,0, 0, 1,0);
    }

    public void main() {
        // 컬러 버퍼 초기화
        glClear(GL_COLOR_BUFFER_BIT);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glVertexPointer(3, GL_FLOAT, 0, axisVertexPointer);
        glColorPointer(4, GL_FLOAT, 0, axisColorPointer);
        glLineWidth(3);
        glDrawArrays(GL_LINES, 0, 6);
    }
}
