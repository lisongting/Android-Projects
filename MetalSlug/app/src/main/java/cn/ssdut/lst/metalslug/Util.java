package cn.ssdut.lst.metalslug;

import java.util.Random;

/**
 * Created by Administrator on 2017/2/28.
 */
//用来生成随机数
public class Util {
    public static Random random = new Random();
    public static int rand(int range){
        if(range == 0){
            return 0;
        }
        return Math.abs(random.nextInt() % range);
    }
}
