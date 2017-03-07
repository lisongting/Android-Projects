package cn.ssdut.lst.metalslug;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */

public class MonsterManager {
    public static final List<Monster> dieMonsterList = new ArrayList<>();
    public static final List<Monster> monsterList = new ArrayList<>();

    //随机生成并添加怪物的方法
    public static void generateMonster() {
        if (monsterList.size() < 3 + Util.rand(3)) {
            Monster monster = new Monster(1 + Util.rand(3));
            monsterList.add(monster);
        }
    }

    //更新怪物与子弹位置的方法
    public static void updatePosition(int shift) {
        Monster monster = null;
        List<Monster> delList = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            monster = monsterList.get(i);
            if (monster == null) {
                continue;
            }
            monster.updateShift(shift);
            if (monster.getX() < 0) {
                delList.add(monster);
            }
        }
        monsterList.removeAll(delList);
        delList.clear();
        //遍历所有已死亡的怪物
        for (int i = 0; i < dieMonsterList.size(); i++) {
            monster = dieMonsterList.get(i);
            if (monster == null) {
                continue;
            }
            monster.updateShift(shift);
            if (monster.getX() < 0) {
                delList.add(monster);
            }
            dieMonsterList.removeAll(delList);
            //更新玩家控制的角色的子弹坐标
            GameView.player.updateBulletShift(shift);
        }
    }

    //检查怪物是否将要死亡的方法
    public static void checkMonster() {
        List<Bullet> bulletList = GameView.player.getBulletList();
        if (bulletList == null) {
            bulletList = new ArrayList<>();
        }
        Monster monster = null;
        //保存将要死亡的怪物
        List<Monster> delList = new ArrayList<>();
        //保存所有将要被删除的子弹
        List<Bullet> delBulletList = new ArrayList<>();
        for (int i = 0; i < monsterList.size(); i++) {
            monster = monsterList.get(i);
            if (monster == null) {
                continue;
            }
            //如果怪物是炸弹
            if (monster.getType() == Monster.TYPE_BOMB) {
                if (GameView.player.isHurt(monster.getStartX(), monster.getEndX(),
                        monster.getStartY(), monster.getEndY())) {
                    monster.setDie(true);
                    ViewManager.soundPool.play(ViewManager.soundMap.get(2), 1, 1, 0, 0, 1);
                    delList.add(monster);
                    GameView.player.setHp(GameView.player.getHp() - 10);
                }
                continue;
            }
            //对于其他类型的怪物，则需要遍历角色发射的所有子弹
            //只要任何一个子弹打中怪物，则可判断怪物死亡
            for (Bullet b : bulletList) {
                if (b == null || !b.isEffect()) {
                    continue;
                }
                if (monster.isHurt(b.getX(), b.getY())) {
                    b.setEffect(false);
                    monster.setDie(true);
                    if (monster.getType() == Monster.TYPE_FLY) {
                        ViewManager.soundPool.play(ViewManager.soundMap.get(2), 1, 1, 0, 0, 1);
                    }
                    if (monster.getType() == Monster.TYPE_MAN) {
                        ViewManager.soundPool.play(ViewManager.soundMap.get(3), 1, 1, 0, 0, 1);
                    }
                    delList.add(monster);
                    delBulletList.add(b);
                }
            }
            bulletList.removeAll(delBulletList);
            monster.checkBullet();
        }
        //将死亡的怪物（保存在delList集合中）添加到dieMonsterList集合中
        dieMonsterList.addAll(delList);
        //将已死亡的怪物（保存在delList集合中）从monsterlist中删除
        monsterList.removeAll(delList);
    }

    //绘制所有怪物的方法
    public static void drawMonster(Canvas canvas) {
        Monster monster = null;
        for(int i=0;i<monsterList.size();i++) {
            monster = monsterList.get(i);
            if (monster == null) {
                continue;
            }
            monster.draw(canvas);
        }
        List<Monster> delList = new ArrayList<>();
        //遍历所有已死亡的怪物，遍历所有已死亡的怪物
        for(int i=0;i<dieMonsterList.size();i++) {
            monster = dieMonsterList.get(i);
            if (monster == null) {
                continue;
            }
            monster.draw(canvas);
            //当getDieMaxDrawCount等于0时，表示怪物已经死亡
            if (monster.getDieMaxDrawCount() <= 0) {
                delList.add(monster);
            }
        }
        dieMonsterList.removeAll(delList);
    }


}
