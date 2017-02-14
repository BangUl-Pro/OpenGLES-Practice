package com.pro.bangul.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES10;
import android.opengl.GLUtils;

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


    // 텍스쳐를 openGL에게 넘겨주는 메소드
    public static void loadTexture(Context context, GLES10 gl, int texId, int resId) {
        gl.glBindTexture(GLES10.GL_TEXTURE_2D, texId);
        gl.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MIN_FILTER, GLES10.GL_NEAREST);
        gl.glTexParameterf(GLES10.GL_TEXTURE_2D, GLES10.GL_TEXTURE_MAG_FILTER, GLES10.GL_LINEAR);
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), resId);
        GLUtils.texImage2D(GLES10.GL_TEXTURE_2D, 0, bmp, 0);
        bmp.recycle();
    }
}
