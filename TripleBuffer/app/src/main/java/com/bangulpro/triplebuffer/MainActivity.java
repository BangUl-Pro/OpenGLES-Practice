package com.bangulpro.triplebuffer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback2 {

    private MyThread myThread;
    private int displayWidth, displayHiehgt;        // 디바이스 너비와 높이
    private int bufferNum;                          // 버퍼 개수
    private float x, y, spdX = 10, spdY = 10;       // 점 좌표와 스피드
    private Paint p = new Paint();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().takeSurface(this);
        myThread = new MyThread();
        myThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        synchronized (myThread) {
            myThread.isRun = false;
            myThread.notify();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        synchronized (myThread) {
            myThread.isRun = true;
            myThread.notify();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (myThread) {
            myThread.isQuit = true;
            myThread.notify();
        }
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (myThread) {
            myThread.surface = holder;
            myThread.notify();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        displayWidth = width;
        displayHiehgt = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (myThread) {
            myThread.surface = holder;
            myThread.notify();
            while (myThread.isActivate) {
                try {
                    myThread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class MyThread extends Thread {

        SurfaceHolder surface;
        boolean isRun, isActivate, isQuit;
        Canvas c;

        @Override
        public void run() {
            // 점 색, 크기 세팅
            p.setColor(Color.RED);
            p.setStrokeWidth(20);

            while (true) {
                synchronized (this) {
                    // 스레드 탈출 조건
                    while (surface == null || !isRun) {
                        if (isActivate) {
                            isActivate = false;
                            notify();
                        }

                        if (isQuit)
                            return;
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!isActivate) {
                        isActivate = true;
                        notify();
                    }

                    c = surface.lockCanvas();           // 다른 스레드나 OS에서 해당 캔버스를 건드리지 못하게 잠금
                    if (c == null)
                        continue;

                    if (bufferNum < 2) {
                        c.drawColor(Color.WHITE);
                        bufferNum++;
                    }
                    x += spdX;                          // x의 위치를 spdX 만큼 이동
                    y += spdY;                          // y의 위치를 spdY 만큼 이동
                    if (x < 0 || x > displayWidth)      // 벽에 부딪혔을 때 튕겨져 나오기
                        spdX = -spdX;
                    if (y < 0 || y > displayHiehgt)
                        spdY = -spdY;
                    c.drawPoint(x,y,p);

                    surface.unlockCanvasAndPost(c);     // canvas 잠금 해제 및 화면에 띄우기
                }
            }
        }
    }
}
