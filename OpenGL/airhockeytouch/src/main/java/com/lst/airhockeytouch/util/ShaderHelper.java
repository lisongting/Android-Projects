package com.lst.airhockeytouch.util;

import android.util.Log;

import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glValidateProgram;

/**
 * Created by lisongting on 2018/2/24.
 */

public class ShaderHelper {
    private static final String TAG = "ShaderHelper";

    //编译顶点着色器，返回顶点着色器对象的ID
    public static int compileVertexShader(String shaderCode) {
        final int shaderId = glCreateShader(GL_VERTEX_SHADER);
        if (shaderId == 0) {
            log("Fail to create new shader");
            return 0;
        }
        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        //得到编译结果
        final int[] compileResult = new int[1];
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileResult, 0);
        log("compileResult:" + compileResult[0] + ",vertexShaderId:" + shaderId);
        if (compileResult[0] == 0) {
            glDeleteShader(shaderId);
            log("compile failed");
            return 0;
        }
        return shaderId;
    }

    public static int compileFragmentShader(String shaderCode) {
        final int shaderId = glCreateShader(GL_FRAGMENT_SHADER);
        if (shaderId == 0) {
            log("Fail to create new shader");
            return 0;
        }
        glShaderSource(shaderId, shaderCode);
        glCompileShader(shaderId);

        //得到编译结果
        final int[] compileResult = new int[1];
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileResult, 0);
        log("compileResult:" + compileResult[0] + ",vertexShaderId:" + shaderId);
        if (compileResult[0] == 0) {
            glDeleteShader(shaderId);
            log("compile failed");
            return 0;
        }
        return shaderId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programId = glCreateProgram();
        if (programId == 0) {
            log("create program failed");
            return 0;
        }

        //连接program与顶点着色器
        glAttachShader(programId,vertexShaderId);
        //连接program与片元着色器
        glAttachShader(programId, fragmentShaderId);
        glLinkProgram(programId);

        //获取链接结果
        final int[] linkResult = new int[1];
        glGetProgramiv(programId, GL_LINK_STATUS, linkResult, 0);
        log("Result of linking program:" + linkResult[0]+",info:"+glGetProgramInfoLog(programId));

        if (linkResult[0] == 0) {
            glDeleteProgram(programId);
            log("linking program failed");
            return 0;
        }
        return programId;

    }

    public static boolean validateProgram(int programId) {
        glValidateProgram(programId);
        final int[] result = new int[1];
        glGetProgramiv(programId, GL_VALIDATE_STATUS, result, 0);
        log("result of validating program:" + result[0]);
        return result[0] != 0;
    }

    //将顶点着色器和片元着色器的代码进行编译成程序
    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int program;
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        program = linkProgram(vertexShader, fragmentShader);

        validateProgram(program);

        return program;
    }

    private static void log(String s) {
        Log.i(TAG, s);
    }
}
