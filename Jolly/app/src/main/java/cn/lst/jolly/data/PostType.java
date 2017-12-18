package cn.lst.jolly.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by lisongting on 2017/12/18.
 */
@Retention(SOURCE)
@IntDef({PostType.TYPE_ZHIHU,PostType.TYPE_DOUBAN,PostType.TYPE_GUOKR})
public @interface PostType {
    int TYPE_ZHIHU = 0;

    int TYPE_DOUBAN = 1;

    int TYPE_GUOKR = 2;
}
