package com.pro.bangul.grid;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Bangul-Pro on 2017. 1. 31..
 */

public class MyGLUtils {



    public static FloatBuffer loadPointer(float[] array) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        return (FloatBuffer) buffer.put(array).position(0);
    }
}
