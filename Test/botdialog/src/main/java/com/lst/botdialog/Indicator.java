package com.lst.botdialog;

/**
 * Created by lisongting on 2018/4/6.
 */

public class Indicator  {
    private float x;
    private float y;
    private float theta;

    public Indicator(){}

    public Indicator(float x, float y) {
        this.x = x;
        this.y = y;
        this.theta = 0F;
    }
    public Indicator(float x, float y, float radius) {
        this.x = x;
        this.y = y;
        this.theta = radius;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTheta() {
        return theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "x=" + x +
                ", y=" + y +
                ", theta=" + theta +
                '}';
    }
}
