package com.lst.airhockeyimprovemallet.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLUtils.texImage2D;

/**
 * Created by lisongting on 2018/2/27.
 */

public class TextureHelper {
    public static int loadTexture(Context context, int resourceId) {
        final int[] textureId = new int[1];
        glGenTextures(1, textureId, 0);
        if (textureId[0] == 0) {
            log("cannot generate texture object");
            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        if (bitmap == null) {
            log("decode bitmap failed");
            glDeleteTextures(1, textureId, 0);
            return 0;
        }
        //将纹理绑定到OpenGL
        glBindTexture(GL_TEXTURE_2D,textureId[0]);

        //设置纹理过滤参数
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        //告诉OpenGL读入bitmap定义的位图数据，并把它复制到当前绑定的纹理对象
        texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        //生成Mip贴图
        glGenerateMipmap(GL_TEXTURE_2D);
        bitmap.recycle();

        //解除绑定
        glBindTexture(GL_TEXTURE_2D,0);
        //返回纹理ID
        return textureId[0];
    }

    private static void log(String s) {
        Log.i("TextureHelper", s);
    }
}
