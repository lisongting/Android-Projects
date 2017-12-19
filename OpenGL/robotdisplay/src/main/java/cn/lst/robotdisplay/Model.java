package cn.lst.robotdisplay;


import java.nio.FloatBuffer;

/**
 * Created by lisongting on 2017/12/19.
 */

public class Model {

    private int facetCount;
    private float[] verts;
    private float[] vnorms;
    private short[] remarks;

    //顶点数组转换而来的buffer
    private FloatBuffer vertBuffer;
    //每个顶点对应的法向量转换而来的buffer
    private FloatBuffer vnormBuffer;

    public float maxX,minX,maxY,minY,maxZ, minZ;

    //返回模型的中心点
    public Point getCenterPoint(){
        float cx = (minX + maxX) / 2;
        float cy = (minY + maxY) / 2;
        float cz = (minZ + maxZ) / 2;
        return new Point(cx, cy, cz);
    }

    //包裹模型的最大半径
    public float getR() {
        float dx = maxX - minX;
        float dy = maxY - minY;
        float dz = maxZ - minZ;
        float max = dx;
        max = max > dy ? max : dy;
        max = max > dz ? max : dz;
        return max;
    }

    //设置顶点数组的同时，设置对应的Buffer
    public void setVerts(float[] verts) {
        this.verts = verts;
        vertBuffer = Util.floatArrayToBuffer(verts);
    }

    //设置顶点数组法向量的同时，设置对应的Buffer
    public void setVnorms(float[] vnorms) {
        this.vnorms = vnorms;
        vnormBuffer = Util.floatArrayToBuffer(vnorms);
    }

    public int getFacetCount() {
        return facetCount;
    }

    public void setFacetCount(int facetCount) {
        this.facetCount = facetCount;
    }

    public float[] getVerts() {
        return verts;
    }

    public float[] getVnorms() {
        return vnorms;
    }

    public short[] getRemarks() {
        return remarks;
    }

    public void setRemarks(short[] remarks) {
        this.remarks = remarks;
    }

    public FloatBuffer getVertBuffer() {
        return vertBuffer;
    }

    public void setVertBuffer(FloatBuffer vertBuffer) {
        this.vertBuffer = vertBuffer;
    }

    public FloatBuffer getVnormBuffer() {
        return vnormBuffer;
    }

    public void setVnormBuffer(FloatBuffer vnormBuffer) {
        this.vnormBuffer = vnormBuffer;
    }

}
