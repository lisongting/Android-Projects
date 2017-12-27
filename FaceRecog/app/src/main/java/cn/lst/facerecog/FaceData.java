package cn.lst.facerecog;

import android.graphics.PointF;

/**
 * Created by lisongting on 2017/12/14.
 */

public class FaceData {
    private PointF midEye;
    private float eyeDist;
    private float confidence;
    private int id;
    private long time;

    public FaceData() {
        id = 0;
        midEye = new PointF(0.0f, 0.0f);
        eyeDist = 0.0f;
        confidence = 0.4f;
        time = System.currentTimeMillis();
    }


    public void setFace(int id, PointF midEye, float eyeDist, float confidence, long time) {
        set(id, midEye, eyeDist, confidence, time);
    }

    public void clear() {
        set(0, new PointF(0.0f, 0.0f), 0.0f, 0.4f, System.currentTimeMillis());
    }

    public synchronized void set(int id, PointF midEye, float eyeDist, float confidence, long time) {
        this.id = id;
        this.midEye.set(midEye);
        this.eyeDist = eyeDist;
        this.confidence = confidence;
        this.time = time;
    }

    public float eyesDistance() {
        return eyeDist;
    }

    public void setEyeDist(float eyeDist) {
        this.eyeDist = eyeDist;
    }

    public PointF getMidEye() {
        return midEye;
    }

    public void setMidEye(PointF midEye) {
        this.midEye = midEye;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "FaceResult{" +
                "midEye=" + midEye +
                ", eyeDist=" + eyeDist +
                ", confidence=" + confidence +
                ", id=" + id +
                ", time=" + time +
                '}';
    }
}
