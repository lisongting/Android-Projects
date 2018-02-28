package com.lst.airhockeyimprovemallet.program;

import android.content.Context;

import com.lst.airhockeyimprovemallet.util.ShaderHelper;
import com.lst.airhockeyimprovemallet.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by lisongting on 2018/2/27.
 */

public abstract class ShaderProgram {
    //uniform 常量
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_COLOR = "u_Color";

    //属性常量
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));

    }

    public void useProgram() {
        glUseProgram(program);
    }

}
