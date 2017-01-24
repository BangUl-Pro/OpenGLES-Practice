package com.bangulpro.timer;

/**
 * Created by IronFactory on 2017. 1. 24..
 */

public class MyTimer {
    long start, delay;
    boolean flag;

    public MyTimer(long delay) {
        this.delay = delay;
        start = System.currentTimeMillis();
    }

    void run(long now) {
        long t = now - start;
        if (t > delay) {
            flag = true;
            start = now - (t - delay);
        } else
            flag = false;
    }
}
