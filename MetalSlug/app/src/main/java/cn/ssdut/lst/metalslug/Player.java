package cn.ssdut.lst.metalslug;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class Player {
    //最高生命值
    public static final int MAX_HP = 50;
    public static final int ACTION_STAND_RIGHT = 1;
    public static final int ACTION_STAND_LEFT = 2;
    public static final int ACTION_RUN_RIGHT = 3;
    public static final int ACTION_RUN_LEFT = 4;
    public static final int ACTION_JUMP_RIGHT = 5;
    public static final int ACTION_JUMP_LEFT = 6;
    //角色向右移动的常量
    public static final int DIR_RIGHT = 1;
    //向左移动的常量
    public static final int DIR_LEFT = 2;
    //角色的默认坐标
    public static int X_DEFAULT = 0;
    public static int Y_DEFAULT = 0;
    public static int Y_JUMP_MAX = 0;
    //定义控制角色移动的常量
    public static final int MOVE_STAND = 0;
    public static final int MOVE_RIGHT = 1;
    public static final int MOVE_LEFT = 2;
    private String name;
    private int hp;
    private int gun;
    //当前的动作,默认向右站立
    private int action = ACTION_STAND_RIGHT;
    private int x = -1;
    private int y = -1;
    //保存角色射出的所有子弹
    private final List<Bullet> bulletList = new ArrayList<>();
    //保存角色移动方式的变量
    private int move = MOVE_STAND;
    public static final int MAX_LEFT_SHOOT_TIME = 6;
    // 控制射击状态的保留计数器
    // 每当用户发射一枪时，leftShootTime会被设为MAX_LEFT_SHOOT_TIME。
    // 只有当leftShootTime变为0时，用户才能发射下一枪
    private int leftShootTime = 0;
    // 保存角色是否跳动的成员变量
    public boolean isJump = false;
    // 保存角色是否跳到最高处的成员变量
    public boolean isJumpMax = false;
    // 控制跳到最高处的停留时间
    public int jumpStopCount = 0;
    // 当前正在绘制角色脚部动画的第几帧
    private int indexLeg = 0;
    // 当前正在绘制角色头部动画的第几帧
    private int indexHead = 0;
    // 当前绘制头部图片的X坐标
    private int currentHeadDrawX = 0;
    // 当前绘制头部图片的Y坐标
    private int currentHeadDrawY = 0;
    // 当前正在画的脚部动画帧的图片
    private Bitmap currentLegBitmap = null;
    // 当前正在画的头部动画帧的图片
    private Bitmap currentHeadBitmap = null;
    // 该变量控制用于控制动画刷新的速度
    private int drawCount = 0;

    public Player(String name,int hp){
        this.name = name;
        this.hp = hp;
    }

    public void initPosition() {
        x = ViewManager.SCREEN_WIDTH * 15/100;
        y = ViewManager.SCREEN_HEIGHT * 75/100;
        X_DEFAULT = x;
        Y_DEFAULT = y;
        Y_JUMP_MAX = ViewManager.SCREEN_HEIGHT*50/100;
    }

    //获取当前角色的方向，为奇数则向右
    public int getDir() {
        if(action % 2==1){
            return DIR_RIGHT;
        }else{
            return DIR_LEFT;
        }
    }
    //获取角色在屏幕上的位移
    public int getShift() {
        if (x <= 0 || y <= 0) {
            initPosition();
        }
        return X_DEFAULT - x;
    }

    public boolean isDied(){
        return hp<=0;
    }
    public List<Bullet> getBulletList(){
        return bulletList;
    }
    //画角色的方法
    public void draw(Canvas canvas){
        if(canvas == null){
            return ;
        }
        switch (action) {
            case ACTION_STAND_RIGHT:
                drawAni(canvas,ViewManager.legStandImage,ViewManager.headStandImage,DIR_RIGHT);
                break;
            case ACTION_STAND_LEFT:
                drawAni(canvas, ViewManager.legStandImage, ViewManager.headStandImage, DIR_LEFT);
                break;
            case ACTION_RUN_RIGHT:
                drawAni(canvas, ViewManager.legRunImage, ViewManager.headRunImage, DIR_RIGHT);
                break;
            case ACTION_RUN_LEFT:
                drawAni(canvas, ViewManager.legRunImage, ViewManager.headRunImage, DIR_LEFT);
                break;
            case ACTION_JUMP_RIGHT:
                drawAni(canvas, ViewManager.legJumpImage, ViewManager.headJumpImage, DIR_RIGHT);
                break;
            case ACTION_JUMP_LEFT:
                drawAni(canvas, ViewManager.legJumpImage, ViewManager.headJumpImage, DIR_LEFT);
                break;
            default:
                break;
        }
    }

    public void drawAni(Canvas canvas, Bitmap[] legArr, Bitmap[] headArr, int dir) {
        if (canvas == null || legArr == null|| headArr == null) {
            return;
        }
        //射击状态停留次数每次减少一
        if(leftShootTime > 0){
            headArr = ViewManager.headShootImage;
            leftShootTime--;
        }
        indexLeg = indexLeg % legArr.length;
        indexHead = indexHead % headArr.length;
        //是否需要翻转图片
        int trans = dir == DIR_RIGHT ?Graphics.TRANS_MIRROR:Graphics.TRANS_NONE;
        Bitmap bitmap = legArr[indexLeg];
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }

        //先画脚
        int drawX = X_DEFAULT;
        int drawY = y - bitmap.getHeight();
        Graphics.drawMatrixImage(canvas, bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), trans, drawX, drawY, 0, Graphics.TIMES_SCALE);
        currentLegBitmap = bitmap;

        //画头
        Bitmap bitmap2 = headArr[indexHead];
        if (bitmap2 == null || bitmap2.isRecycled()) {
            return;
        }
        drawX = drawX - ((bitmap2.getWidth() - bitmap.getWidth())/2);
        if (action == ACTION_STAND_LEFT) {
            drawX += (int)(6 * ViewManager.scale);
        }
        drawY = drawY - bitmap2.getHeight() - (int) (ViewManager.scale * 10);
        Graphics.drawMatrixImage(canvas, bitmap2, 0, 0, bitmap2.getWidth(),
                bitmap2.getHeight(), trans, drawX, drawY, 0, Graphics.TIMES_SCALE);
        currentHeadDrawX = drawX;
        currentHeadDrawY = drawY;
        currentHeadBitmap = bitmap2;

        //drawCount控制该方法每调用4次才会切换到下一帧位图
        drawCount++;
        if (drawCount >= 4) {
            drawCount = 0;
            indexLeg  ++;
            indexHead ++;
        }
        //画子弹和头
        drawBullet(canvas);
        drawHead(canvas);
    }

    //绘制左上角的角色，名字，生命值
    public void drawHead(Canvas canvas) {
        if (ViewManager.head == null) {
            return;
        }
        // 画头像
        Graphics.drawMatrixImage(canvas, ViewManager.head, 0, 0,
                ViewManager.head.getWidth(),ViewManager.head.getHeight(),
                Graphics.TRANS_MIRROR, 0, 0, 0, Graphics.TIMES_SCALE);
        Paint p = new Paint();
        p.setTextSize(30);
        // 画名字
        Graphics.drawBorderString(canvas, 0xa33e11, 0xffde00, name,
                ViewManager.head.getWidth(), (int) (ViewManager.scale * 20), 3, p);
        // 画生命值
        Graphics.drawBorderString(canvas, 0x066a14, 0x91ff1d, "HP: " + hp,
                ViewManager.head.getWidth(), (int) (ViewManager.scale * 40), 3, p);
    }

    //判断该角色是否被子弹打中
    public boolean isHurt(int startX,int endX,int startY,int endY){
        if (currentHeadBitmap == null || currentLegBitmap == null) {
            return false;
        }
        int playerStartX = currentHeadDrawX;
        int playerEndX = playerStartX + currentHeadBitmap.getWidth();
        int playerStartY = currentHeadDrawY;
        int playerEndY = playerStartY + currentHeadBitmap.getHeight();
        return ((startX >= playerStartX && startX <= playerEndX) ||
                (endX >= playerStartX && endX <= playerEndX))
                && ((startY >= playerStartY && startY <= playerEndY) ||
                (endY >= playerStartY && endY <= playerEndY));
    }

    //画子弹
    public void drawBullet(Canvas canvas) {
        List<Bullet> deleteList = new ArrayList<>();
        Bullet bullet;
        for(int i=0;i<bulletList.size();i++) {
            bullet = bulletList.get(i);
            if (bullet == null) {
                continue;
            }
            //将越界的子弹收集到deleteList中
            if (bullet.getX() < 0 || bullet.getX() > ViewManager.SCREEN_WIDTH) {
                deleteList.add(bullet);
            }
        }
        //清除越界的子弹
        Bitmap bitmap;
        bulletList.removeAll(deleteList);
        //遍历角色发射的所有子弹
        for(int i=0;i<bulletList.size();i++) {
            bullet = bulletList.get(i);
            if (bullet == null)
            {
                continue;
            }
            // 获取子弹对应的位图
            bitmap = bullet.getBitmap();
            if (bitmap == null)
            {
                continue;
            }
            // 子弹移动
            bullet.move();
            // 画子弹
            Graphics.drawMatrixImage(canvas, bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), bullet.getDir() == Player.DIR_LEFT ?
                            Graphics.TRANS_MIRROR : Graphics.TRANS_NONE,
                    bullet.getX(), bullet.getY(), 0, Graphics.TIMES_SCALE);
        }
    }

    //发射子弹的方法
    public void addBullet() {
        // 计算子弹的初始X坐标
        int bulletX = getDir() == DIR_RIGHT ? X_DEFAULT +(int)
                (ViewManager.scale * 50) : X_DEFAULT - (int)(ViewManager.scale * 50);
        // 创建子弹对象
        Bullet bullet = new Bullet(Bullet.BULLET_TYPE_1, bulletX,
                y - (int) (ViewManager.scale * 60), getDir());
        // 将子弹添加到用户发射的子弹集合中
        bulletList.add(bullet);
        // 发射子弹时，将leftShootTime设置为射击状态最大值
        leftShootTime = MAX_LEFT_SHOOT_TIME;
        // 播放射击音效
        ViewManager.soundPool.play(ViewManager.soundMap.get(1), 1, 1, 0, 0, 1);
    }
    //处理角色移动的方法
    public void move(){
        if (move == MOVE_RIGHT) {
            //更新怪物的位置
            MonsterManager.updatePosition((int) (6 * ViewManager.scale));
            setX(getX() + (int) (6 * ViewManager.scale));
            if (!isJump()) {
                //不跳的时候，设置动作
                setAction(ACTION_RUN_RIGHT);
            }

        } else if (move == MOVE_LEFT) {
            if (getX() - (int) (6 * ViewManager.scale) < Player.X_DEFAULT) {
                MonsterManager.updatePosition(-(getX() - Player.X_DEFAULT));
            }else{
                MonsterManager.updatePosition(-(int) (6 * ViewManager.scale));
            }
            //更新角色位置
            setX(getX() - (int) (6 * ViewManager.scale));
            if (!isJump()) {
                setAction(Player.ACTION_RUN_LEFT);
            }
        } else if (getAction() != Player.ACTION_JUMP_RIGHT &&
                getAction()!= Player.ACTION_JUMP_LEFT) {
            if (!isJump()) {
                setAction(Player.ACTION_STAND_RIGHT);
            }
        }
    }
    //处理移动与跳的逻辑关系
    public void logic(){
        if (!isJump())
        {
            move();
            return;
        }

        // 如果还没有跳到最高点
        if (!isJumpMax)
        {
            setAction(getDir() == Player.DIR_RIGHT ?
                    Player.ACTION_JUMP_RIGHT : Player.ACTION_JUMP_LEFT);
            // 更新Y坐标
            setY(getY() - (int) (8 * ViewManager.scale));
            // 设置子弹在Y方向上具有向上的加速度
            setBulletYAccelate(-(int) (2 * ViewManager.scale));
            // 已经达到最高点
            if (getY() <= Player.Y_JUMP_MAX)
            {
                isJumpMax = true;
            }
        }
        else
        {
            jumpStopCount--;
            // 如果在最高点停留次数已经使用完
            if (jumpStopCount <= 0)
            {
                // 更新Y坐标
                setY(getY() + (int) (8 * ViewManager.scale));
                // 设置子弹在Y方向上具有向下的加速度
                setBulletYAccelate((int) (2 * ViewManager.scale));
                // 已经掉落到最低点
                if (getY() >= Player.Y_DEFAULT)
                {
                    // 恢复Y坐标
                    setY(Player.Y_DEFAULT);
                    isJump = false;
                    isJumpMax = false;
                    setAction(Player.ACTION_STAND_RIGHT);
                }
                else
                {
                    // 未掉落到最低点，继续使用跳的动作
                    setAction(getDir() == Player.DIR_RIGHT ?
                            Player.ACTION_JUMP_RIGHT : Player.ACTION_JUMP_LEFT);
                }
            }
        }
        // 控制角色移动
        move();
    }

    //更新子弹的位置,子弹的位置同样会受到角色的位移影响
    public void updateBulletShift(int shift) {
        for (Bullet bullet : bulletList) {
            if (bullet == null) {
                continue;
            }
            bullet.setX(bullet.getX() - shift);
        }
    }

    //给子弹设置垂直方向的加速度
    //当角色跳动时，子弹会具有垂直方向的加速度
    public void setBulletYAccelate(int accelate) {
        for (Bullet bullet : bulletList) {
            if (bullet == null || bullet.getyAccelate() != 0) {
                continue;
            }
            bullet.setyAccelate(accelate);
        }
    }
    public int getLeftShootTime()
    {
        return leftShootTime;
    }
    public boolean isDie() {
        return hp <= 0;
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getX() {
        if (x <= 0 || y <= 0) {
            initPosition();
        }
        return x;
    }

    public void setX(int x) {
        this.x = x % (ViewManager.map.getWidth() + X_DEFAULT);
        if (this.x < X_DEFAULT) {
            this.x = X_DEFAULT;
        }
    }

    public int getY() {
        if (x <= 0 || y <= 0) {
            initPosition();
        }
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public boolean isJump() {
        return isJump;
    }

    public void setJump(boolean jump) {
        isJump = jump;
    }
}
