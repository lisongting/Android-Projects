package com.lst.airhockeyimprovemallet;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.lst.airhockeyimprovemallet.object.Mallet;
import com.lst.airhockeyimprovemallet.object.Puck;
import com.lst.airhockeyimprovemallet.object.Table;
import com.lst.airhockeyimprovemallet.program.ColorShaderProgram;
import com.lst.airhockeyimprovemallet.program.TextureShaderProgram;
import com.lst.airhockeyimprovemallet.util.MatrixHelper;
import com.lst.airhockeyimprovemallet.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;

/**
 * Created by lisongting on 2018/2/24.
 * 绘制3D冰球和木槌
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    //投影矩阵
    private final float[] projectionMatrix = new float[16];
    //模型矩阵
    private final float[] modelMatrix = new float[16];
    //视图矩阵
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];
    private Table table;
    private Mallet mallet;
    private Puck puck;
    private TextureShaderProgram textureShaderProgram;
    private ColorShaderProgram colorShaderProgram;
    private int texture;
    private int i =0;

    public AirHockeyRenderer(Context context) {
        this.context = context;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0f, 0f, 0f, 0f);
        table = new Table();
        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);

        textureShaderProgram = new TextureShaderProgram(context);
        colorShaderProgram = new ColorShaderProgram(context);
        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / height, 1f, 10f);
        setLookAtM(viewMatrix, 0, 0f, 1.2f, 2.2f, 0f, 0f, 0f, 0f, 1f, 0f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        //将视图矩阵和投影矩阵相乘,结果放在viwProjectionMatrix里面
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        //绘制桌面
        positionTableInScene();
        textureShaderProgram.useProgram();
        textureShaderProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureShaderProgram);
        table.draw();

        //绘制木槌1
        positionObjectInScene(0f, mallet.height / 2f, -0.4f);
        colorShaderProgram.useProgram();
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mallet.bindData(colorShaderProgram);
        mallet.draw();

        //绘制木槌2
        positionObjectInScene(0f, mallet.height / 2f, 0.4f);
        colorShaderProgram.useProgram();
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        //这里不需要再次bindData了，绑定一次就可以了
        mallet.draw();

        //绘制冰球
        positionObjectInScene(0f, puck.height / 2f, 0f);
        colorShaderProgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 0.8f);
        puck.bindData(colorShaderProgram);
        puck.draw();
    }

    private void positionTableInScene(){
        //设置为单位矩阵
        setIdentityM(modelMatrix, 0);

        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);

    }

    private void positionObjectInScene(float x, float y, float z) {
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, x, y, z);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }
    private void log(String s) {
        Log.i("AirHockeyRenderer", s);
    }
}
