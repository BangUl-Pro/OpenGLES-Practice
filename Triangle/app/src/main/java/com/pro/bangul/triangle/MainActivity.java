package com.pro.bangul.triangle;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity implements GLSurfaceView.Renderer {

    private GLSurfaceView surfaceView;
    private MyGL myGL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        surfaceView = new GLSurfaceView(this);
        surfaceView.setRenderer(this);
        setContentView(surfaceView);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        myGL = new MyGL(this);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        myGL.gl = gl;
        if (!myGL.isInit) {
            myGL.isInit = true;
            myGL.init(width, height);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (myGL.isInit)
            myGL.main();
    }
}
