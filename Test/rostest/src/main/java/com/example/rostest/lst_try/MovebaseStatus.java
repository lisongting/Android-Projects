package com.example.rostest.lst_try;

/**
 * Created by lisongting on 2017/6/4.
 */

public class MovebaseStatus {
    //当前所处的位置区域
    private int locationId;

    //当前底盘是否正在移动[暂时保留，后续也可能删除]
    private boolean isMoving;

    public MovebaseStatus(int locationId, boolean isMoving) {
        this.locationId = locationId;
        this.isMoving = isMoving;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public String toString() {
        return "[" + locationId + "," + isMoving + "]";
    }
}
