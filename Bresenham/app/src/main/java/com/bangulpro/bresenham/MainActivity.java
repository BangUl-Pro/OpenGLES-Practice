package com.bangulpro.bresenham;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback2 {

    private int displayWidth, displayHeight;
    private MyThread myThread;
    private Paint p = new Paint();
    private int x, y, targetX, targetY, ep;
    private float dx, dy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().takeSurface(this);
        myThread = new MyThread();
        myThread.start();
    }


    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        synchronized (myThread) {
            myThread.holder = holder;
            myThread.notify();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        displayWidth = width;
        displayHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        synchronized (myThread) {
            myThread.holder = holder;
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

    class MyThread extends Thread {
        SurfaceHolder holder;
        boolean isRun, isActivate, isQuit;
        Canvas c;
        int direction;

        @Override
        public void run() {
            p.setColor(Color.RED);
            p.setStrokeWidth(20);

            while (true) {
                synchronized (this) {
                    if (holder == null || !isRun) {
                        if (isActivate) {
                            isActivate = false;
                            notify();
                        }

                        if (isQuit)
                            return;
                        try {
                            wait();
                            continue;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!isActivate) {
                        isActivate = true;
                        notify();
                    }

                    c = holder.lockCanvas();
                    if (c == null)
                        continue;

                    Log.d("x", x + "");
                    Log.d("y", y + "");
                    Log.d("targetX", targetX + "");
                    Log.d("targetY", targetY + "");

                    if (x == targetX && y == targetY) {
                        Random random = new Random();
                        targetX = random.nextInt(displayWidth);         // 목표점 지정
                        targetY = random.nextInt(displayHeight);
                        calculateDirection();                           // 방향 찾기
                    }

                    movePoint();                        // 점의 다음 위치 탐색
                    c.drawPoint(x, y, p);               // 점 찍기

                    holder.unlockCanvasAndPost(c);
                }
            }
        }

        private float abs(float num) {
            return (num < 0 ? -num: num);
        }

        private void calculateDirection() {
            dx = targetX - x;
            dy = targetY - y;

            if (dx >= 0 && dy <= 0) {         // 우상단
                if (abs(dx) < abs(dy)) {
                    direction = 0;
                } else {
                    direction = 1;
                }
            } else if (dx >= 0 && dy >= 0) {  // 우하단
                if (abs(dx) > abs(dy))
                    direction = 2;
                else
                    direction = 3;
            } else if (dx <= 0 && dy >= 0) {    // 좌하단
                if (abs(dx) < abs(dy))
                    direction = 4;
                else
                    direction = 5;
            } else {                            // 좌상단
                if (abs(dx) > abs(dy))
                    direction = 6;
                else
                    direction = 7;
            }
            dx = abs(dx);
            dy = abs(dy);
        }

        private void movePoint() {
            switch (direction) {
                case 0:
                    y--;
                    ep += dx;
                    if (ep > dy / 2) {
                        x++;
                        ep -= dy;
                    }
                    break;

                case 1:
                    x++;
                    ep += dy;
                    if (ep > dx / 2) {
                        y--;
                        ep -= dx;
                    }
                    break;

                case 2:
                    x++;
                    ep += dy;
                    if (ep > dx / 2) {
                        y++;
                        ep -= dx;
                    }
                    break;

                case 3:
                    y++;
                    ep += dx;
                    if (ep > dy / 2) {
                        x++;
                        ep -= dy;
                    }
                    break;

                case 4:
                    y++;
                    ep += dx;
                    if (ep > dy / 2) {
                        x--;
                        ep -= dy;
                    }
                    break;

                case 5:
                    x--;
                    ep += dy;
                    if (ep > dx / 2) {
                        y++;
                        ep -= dx;
                    }
                    break;

                case 6:
                    x--;
                    ep += dy;
                    if (ep > dx / 2) {
                        y--;
                        ep -= dx;
                    }
                    break;

                case 7:
                    y--;
                    ep += dx;
                    if (ep > dy / 2) {
                        x--;
                        ep -= dy;
                    }
                    break;
            }
        }
    }
}
