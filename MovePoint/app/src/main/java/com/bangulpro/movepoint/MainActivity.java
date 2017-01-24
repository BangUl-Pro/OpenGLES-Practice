package com.bangulpro.movepoint;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity implements GLSurfaceView.Renderer {

    private GLSurfaceView glSurfaceView;
    private MyGL myGL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(this);
        setContentView(glSurfaceView);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        myGL = new MyGL(this);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (!myGL.isInit) {
            myGL.init(width, height);
            myGL.isInit = true;
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (myGL.isInit)
            myGL.main();
    }
}
