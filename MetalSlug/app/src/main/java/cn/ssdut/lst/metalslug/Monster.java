package cn.ssdut.lst.metalslug;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

import static cn.ssdut.lst.metalslug.GameView.player;
import static cn.ssdut.lst.metalslug.Graphics.TRANS_NONE;

/**
 * Created by Administrator on 2017/2/28.
 */

public class Monster {
    //定义怪物类型
    public static final int TYPE_BOMB = 1;
    public static final int TYPE_FLY  = 2;
    public static final int TYPE_MAN  = 3;
    private int type = TYPE_BOMB;
    private int x = 0;
    private int y = 0;
    private boolean isDie = false;
    //左上角的x和y坐标
    private int startX = 0;
    private int startY = 0;
    //右下角的x和y坐标
    private int endX = 0;
    private int endY = 0;
    //动画的刷新速度
    int drawCount = 0;
    //标记动画刷新到第几帧
    private int drawIndex = 0;
    //每当怪物死亡时，该变量会被初始化为死亡动画的总帧数，
    //当动画播放完时，该变量减为0
    private int dieMaxDrawCount = Integer.MAX_VALUE;
    private List<Bullet> bulletList = new ArrayList<>();

    public Monster(int type) {
        this.type = type;
        //如果怪物是炸弹或者敌人，则y坐标与人物坐标一致
        if(type == TYPE_BOMB || type == TYPE_MAN){
            y = Player.Y_DEFAULT;
            //如果怪物是飞机，则随机生成其y坐标
        }else if(type == TYPE_FLY){
            y = ViewManager.SCREEN_HEIGHT *50/100 - Util.rand((int)ViewManager.scale * 100);
        }
        //随机计算x坐标
        x = ViewManager.SCREEN_WIDTH +Util.rand(ViewManager.SCREEN_WIDTH/2) - (ViewManager.SCREEN_HEIGHT/2);
    }

    //绘制怪物的方法
    public void draw(Canvas canvas){
        if (canvas == null) {
            return;
        }
        switch(type){
            case TYPE_BOMB:
                //死亡的怪物用死亡的图片
                drawAni(canvas, isDie ? ViewManager.bomb2Image : ViewManager.bombImage);
                break;
            case TYPE_FLY:
                drawAni(canvas, isDie ? ViewManager.flyDieImage : ViewManager.flyImage);
                break;
            case TYPE_MAN:
                drawAni(canvas, isDie ? ViewManager.manDieImage : ViewManager.manImage);
                break;
            default:
                break;
        }
    }

    //根据怪物的动画帧图片来绘制怪物动画
    public void drawAni(Canvas canvas, Bitmap[] bitmapArr) {
        if (canvas == null || bitmapArr == null) {
            return ;
        }
        if (isDie && dieMaxDrawCount == Integer.MAX_VALUE) {
            //将dieMaxDrawCount设置为与死亡动画的总帧数相同
            dieMaxDrawCount = bitmapArr.length;
        }
        drawIndex = drawIndex % bitmapArr.length;
        //获取当前绘制的动画帧对应的位图
        Bitmap bitmap = bitmapArr[drawIndex];
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        int drawX = x;
        //对绘制怪物动画帧位图的x坐标进行微调
        if (isDie) {
            if (type == TYPE_BOMB) {
                drawX = x - (int) (ViewManager.scale * 50);
            } else if (type == TYPE_MAN) {
                drawX = x + (int) (ViewManager.scale * 50);
            }
        }
        //对绘制怪物动画帧位图的y坐标进行微调
        int drawY = y - bitmap.getHeight();
        //绘制怪物动画帧的位图
        Graphics.drawMatrixImage(canvas,bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),
                TRANS_NONE,drawX,drawY,0,Graphics.TIMES_SCALE);
        startX = drawX;
        startY = drawY;
        endX = startX + bitmap.getWidth();
        endY = startY + bitmap.getHeight();
        drawCount++;

        //4和6用于控制人、飞机发射子弹的速度
        if(drawCount >= (type == TYPE_MAN ?6:4)){
            //如果怪物是人，则在第三帧才发射子弹
            if(type == TYPE_MAN && drawIndex == 2){
                addBullet();
            }
            //如果怪物是飞机，则在最后一帧发射子弹
            if(type == TYPE_FLY && drawIndex == bitmapArr.length -1){
                addBullet();
            }
            drawIndex ++;
            drawCount = 0;
        }
        //每播放死亡动画的一帧，dieMaxDrawCount减一
        //当其等于0时，表明死亡动画播放完成
        if (isDie) {
            dieMaxDrawCount --;
        }
        drawBullet(canvas);
    }

    //判断怪物是否被子弹打中的方法
    public boolean isHurt(int x,int y){
        return x >= startX && x < endX
                && y > startY &&y<endY;
    }
    //根据怪物类型获取子弹类型
    public int getBulletType(){
        switch (type) {
            case TYPE_BOMB:
                return 0;
            case TYPE_FLY:
                return Bullet.BULLET_TYPE_3;
            case TYPE_MAN:
                return Bullet.BULLET_TYPE_2;
            default:
                return 0;
        }
    }
    //定义发射子弹的方法
    public void addBullet(){
        int bulletType = getBulletType();
        //如果没有子弹
        if(bulletType <= 0){
            return;
        }
        int drawX = x;
        int drawY = y - (int) (ViewManager.scale * 60);
        //如果是飞机，重写计算飞机发射子弹的y坐标
        if (type == TYPE_FLY) {
            drawY = y - (int) (ViewManager.scale * 60);
        }
        Bullet bullet = new Bullet(bulletType, drawX, drawY, Player.DIR_LEFT);
        bulletList.add(bullet);
    }

    //更新所有子弹的位置，将所有子弹的x坐标减少shift距离：子弹左移
    public void updateShift(int shift){
        x -= shift;
        for (Bullet b : bulletList) {
            if (b == null) {
                continue;
            }
            b.setX(b.getX() - shift);
        }
    }

    //绘制子弹的方法
    public void drawBullet(Canvas canvas){
        //该集合保存所有需要删除的子弹
        List<Bullet>deleteList = new ArrayList<>();
        Bullet bullet = null;
        for(int i=0;i<bulletList.size();i++) {
            bullet = bulletList.get(i);
            if (bullet == null) {
                continue;
            }
            //如果子弹越过屏幕
            if (bullet.getX() < 0 || bullet.getX() > ViewManager.SCREEN_WIDTH) {
                deleteList.add(bullet);
            }
        }
        //把所有越过屏幕的子弹删除
        bulletList.removeAll(deleteList);
        Bitmap bitmap;
        for(int i=0;i<bulletList.size();i++) {
            bullet = bulletList.get(i);
            if (bullet == null) {
                continue;
            }
            //获取子弹对应的位图
            bitmap = bullet.getBitmap();
            if (bitmap == null) {
                continue;
            }
            bullet.move();
            Graphics.drawMatrixImage(canvas,bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),
                    bullet.getDir() == player.DIR_RIGHT ? Graphics.TRANS_MIRROR : Graphics.TRANS_NONE,
                    bullet.getX(),bullet.getY() , 0, Graphics.TIMES_SCALE);

        }
    }
    public void checkBullet(){
        List<Bullet> delBulletList = new ArrayList<>();
        for (Bullet b : bulletList) {
            if (b == null || !b.isEffect()) {
                continue;
            }
            //如果玩家控制的角色被子弹打中
            if(player.isHurt(b.getX(), b.getX(),b.getY(),b.getY())){
                b.setEffect(false);
                //生命值减少5
                player.setHp(player.getHp() - 5);
                delBulletList.add(b);
            }
        }
    }
    //下面是set和get方法
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

    public boolean isDie() {
        return isDie;
    }

    public void setDie(boolean die) {
        isDie = die;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getDieMaxDrawCount() {
        return dieMaxDrawCount;
    }

    public void setDieMaxDrawCount(int dieMaxDrawCount) {
        this.dieMaxDrawCount = dieMaxDrawCount;
    }
}
