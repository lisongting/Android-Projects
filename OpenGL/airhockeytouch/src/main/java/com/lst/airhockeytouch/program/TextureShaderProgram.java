package com.lst.airhockeytouch.program;

import android.content.Context;

import com.lst.airhockeytouch.R;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * Created by lisongting on 2018/2/27.
 * 纹理着色器程序
 */

public class TextureShaderProgram extends ShaderProgram {

    //Uniform 位置
    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    //attribute 位置
    private final int aPositionLocation;
    private final int aTextureCoordinatesLocation;

    public TextureShaderProgram(Context context) {
        super(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);

        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocation = glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniforms(float[] matrix, int textureId) {
        //pass the matrix into the shader program
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

        //set the active texture unit to texture unit 0
        glActiveTexture(GL_TEXTURE0);

        //bind the texture to the unit
        glBindTexture(GL_TEXTURE_2D, textureId);

        //tell the texture uniform sampler to use this texture in the shader by telling it to read form texture unit 0
        glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordinatesLocation(){
        return aTextureCoordinatesLocation;
    }






}
