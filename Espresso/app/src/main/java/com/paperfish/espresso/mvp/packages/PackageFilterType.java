package com.paperfish.espresso.mvp.packages;

/**
 * Created by lisongting on 2017/7/15.
 */

public enum  PackageFilterType {
    /**
     * Do not filter the packages.
     */
    ALL_PACKAGES,

    /**
     * Filters only the on the way (not complete or delivered) packages.
     * 在运输路上的包裹
     */
    ON_THE_WAY_PACKAGES,

    /**
     * Filters only the delivered packages.
     * 已派送的包裹
     */
    DELIVERED_PACKAGES

}
