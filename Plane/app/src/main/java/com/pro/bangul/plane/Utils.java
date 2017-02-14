package com.pro.bangul.plane;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Bangul-Pro on 2017. 2. 3..
 */

public class Utils {

    public static FloatBuffer loadPointer(float[] array) {
        FloatBuffer buffer = ByteBuffer.allocateDirect(array.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        return (FloatBuffer) buffer.put(array).position(0);
    }
}
