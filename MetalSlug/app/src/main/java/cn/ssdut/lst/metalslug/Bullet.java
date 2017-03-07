package cn.ssdut.lst.metalslug;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/2/28.
 */

public class Bullet {
    public static final int BULLET_TYPE_1 = 1;
    public static final int BULLET_TYPE_2 = 2;
    public static final int BULLET_TYPE_3 = 3;
    public static final int BULLET_TYPE_4 = 4;
    private int type;
    private int x,y;
    private int dir;//射击方向
    private int yAccelate = 0;//y方向的加速度
    private boolean isEffect = true;//子弹是否有效

    public Bullet(int type, int x, int y, int dir) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    //根据子弹类型返回子弹的图片
    public Bitmap getBitmap() {
        switch (type) {
            case BULLET_TYPE_1:
                return ViewManager.bulletImage[0];
            case BULLET_TYPE_2:
                return ViewManager.bulletImage[1];
            case BULLET_TYPE_3:
                return ViewManager.bulletImage[2];
            case BULLET_TYPE_4:
                return ViewManager.bulletImage[3];
            default:
                return null;
        }
    }
    //根据子弹类型返回子弹的x速度
    public int getSpeedX(){
        //根据玩家的方向来计算子弹的方向和移动方向
        int sign = dir == Player.DIR_RIGHT ? 1:-1;
        switch(type){
            case BULLET_TYPE_1:
                return (int)(ViewManager.scale * 12)*sign;
            case BULLET_TYPE_2:
                return (int)(ViewManager.scale * 8)*sign;
            case BULLET_TYPE_3:
                return (int)(ViewManager.scale * 8)*sign;
            case BULLET_TYPE_4:
                return (int)(ViewManager.scale * 8)*sign;
            default:
                return (int)(ViewManager.scale * 8)*sign;
        }
    }

    //获取子弹在y方向的速度
    public int getSpeedY() {
        //如果yAccelate不为0，则以yAccelate作为y方向的速度
        if(yAccelate!=0){
            return yAccelate;
        }
        //只有第三种子弹(飞机子弹)才会有y方向的速度
        switch (type) {
            case BULLET_TYPE_1:
                return 0;
            case BULLET_TYPE_2:
                return 0;
            case BULLET_TYPE_3:
                return (int)(ViewManager.scale*6);
            case BULLET_TYPE_4:
                return 0;
            default:
                return 0;
        }
    }

    //定义控制子弹移动的方法
    public void move() {
        x+=getSpeedX();
        y+=getSpeedY();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getyAccelate() {
        return yAccelate;
    }

    public void setyAccelate(int yAccelate) {
        this.yAccelate = yAccelate;
    }

    public int getDir() {
        return dir;
    }

    public void setDir(int dir) {
        this.dir = dir;
    }

    public boolean isEffect() {
        return isEffect;
    }

    public void setEffect(boolean effect) {
        isEffect = effect;
    }
}

