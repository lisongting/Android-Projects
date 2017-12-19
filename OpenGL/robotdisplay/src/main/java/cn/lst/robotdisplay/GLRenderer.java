package cn.lst.robotdisplay;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lisongting on 2017/12/19.
 */

public class GLRenderer implements GLSurfaceView.Renderer{

    private Model model;
    private Point mCenterPoint;
    private Point eye ;
    private Point up ;
    private Point center;
    private float mScalef = 1;
    private float mDegree = 0;

    private float angleX,angleY;


    public GLRenderer(Context context) {
        try {
            model = new STLReader().ParseBinStlInAssets(context, "base_link.STL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        eye = new Point(0, 0, -3);
        up = new Point(0, 1, 0);
        center = new Point(0, 0, 0);
    }

    public void rotate(float degree) {
        mDegree = degree;
    }

    public float getAngleX() {
        return angleX;
    }
    public float getAngleY() {
        return angleY;
    }

    public void setAngle(float angleX,float angleY) {
        this.angleX = angleX;
        this.angleY = angleY;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        log("onSurfaceCreated");
        //启用深度缓存
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //设置深度缓存值
//        gl.glClearDepthf(1.0f);
        //设置深度缓存比较函数
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glShadeModel(GL10.GL_SMOOTH);
//        gl.glRotatef(45, 0, 0 , 1);
        float r = model.getR();
        mScalef = 0.5f / r;
        mCenterPoint = model.getCenterPoint();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        log("onSurfaceChanged");
        //设置openGL场景大小，(0,0)表示窗口内部视图的左下角,width和height指定了宽高
        gl.glViewport(0, 0, width, height);

        //设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //设置矩阵为单位矩阵，相当于重置
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, ((float) width) / height, 1f, 100f);// 设置透视范围

        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        log("onDrawFrame");
//        float[] scrach = new float[16];
//        float[] mRotationMatrix = new float[100];
//        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1F);
//        Matrix.multiplyMM(scrach,0,);

        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();// 重置当前的模型观察矩阵

        //眼睛对着原点看
        GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x,
                center.y, center.z, up.x, up.y, up.z);

        //为了能有立体感觉，通过改变mDegree值，让模型不断旋转
//        gl.glRotatef(mDegree, 0, 1, 0);

//        gl.glRotatef(mDegree,3,0,1);
        gl.glRotatef(angleY, 1, 0 , 0);
        gl.glRotatef(angleX, 0, 1, 0);

        //将模型放缩到View刚好装下
        gl.glScalef(mScalef, mScalef, mScalef);
        //把模型移动到原点
        gl.glTranslatef(-mCenterPoint.x, -mCenterPoint.y,
                -mCenterPoint.z);


        //允许给每个顶点设置法向量
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        // 允许设置顶点
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 允许设置颜色

        //设置法向量数据源
        gl.glNormalPointer(GL10.GL_FLOAT, 0, model.getVnormBuffer());
        // 设置三角形顶点数据源
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model.getVertBuffer());

        // 绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model.getFacetCount() * 3);

        // 取消顶点设置
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        //取消法向量设置
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);


    }

    private void log(String s) {
        Log.i("tag", s);
    }
}
