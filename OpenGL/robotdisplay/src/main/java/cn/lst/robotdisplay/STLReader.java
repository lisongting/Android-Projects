package cn.lst.robotdisplay;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lisongting on 2017/12/19.
 */

public class STLReader {
    private listener listener;

    interface listener{
        void onStart();

        void onLoading(int cur,int total);

        void onFinished();

        void onFailure(Exception e);
    }

    public Model ParseBinStlInAssets(Context context, String fileName) throws IOException {
        InputStream is = context.getAssets().open(fileName);
        Model model = new Model();

        if (listener != null) {
            listener.onStart();
        }
        //前80字节是文件头
        is.skip(80);
        byte[] bytes = new byte[4];
        //读取三角片的个数
        is.read(bytes);
        int faceCount = Util.byte4ToInt(bytes, 0);
        model.setFacetCount(faceCount);
        if (faceCount == 0) {
            is.close();
            return model;
        }

        //每个三角片占用50字节
        byte[] facetBytes = new byte[50 * faceCount];
        is.read(facetBytes);
        is.close();

        parseModel(model, facetBytes);
        if (listener != null) {
            listener.onFinished();
        }
        return model;
    }

    //解析模型数据，包括顶点数据，法向量数据，所占空间范围等
    private void parseModel(Model model, byte[] facetBytes) {
        int facetCount = model.getFacetCount();
        /**
         *  每个三角面片占用固定的50个字节,50字节当中：
         *  三角片的法向量：（1个向量相当于一个点）*（3维/点）*（4字节浮点数/维）=12字节
         *  三角片的三个点坐标：（3个点）*（3维/点）*（4字节浮点数/维）=36字节
         *  最后2个字节用来描述三角面片的属性信息
         * **/
        // 保存所有顶点坐标信息,一个三角形3个顶点，一个顶点3个坐标轴
        float[] verts = new float[facetCount * 3 * 3];
        // 保存所有三角面对应的法向量位置，
        // 一个三角面对应一个法向量，一个法向量有3个点
        // 而绘制模型时，是针对需要每个顶点对应的法向量，因此存储长度需要*3
        // 又同一个三角面的三个顶点的法向量是相同的，
        // 因此后面写入法向量数据的时候，只需连续写入3个相同的法向量即可
        float[] vnorms = new float[facetCount * 3 * 3];
        //保存所有三角面的属性信息
        short[] remarks = new short[facetCount];

        int stlOffset = 0;
        try {
            for (int i = 0; i < facetCount; i++) {
                if (listener != null) {
                    listener.onLoading(i, facetCount);
                }
                for (int j = 0; j < 4; j++) {
                    float x = Util.byte4ToFloat(facetBytes, stlOffset);
                    float y = Util.byte4ToFloat(facetBytes, stlOffset + 4);
                    float z = Util.byte4ToFloat(facetBytes, stlOffset + 8);
                    stlOffset += 12;

                    if (j == 0) {//法向量
                        vnorms[i * 9] = x;
                        vnorms[i * 9 + 1] = y;
                        vnorms[i * 9 + 2] = z;
                        vnorms[i * 9 + 3] = x;
                        vnorms[i * 9 + 4] = y;
                        vnorms[i * 9 + 5] = z;
                        vnorms[i * 9 + 6] = x;
                        vnorms[i * 9 + 7] = y;
                        vnorms[i * 9 + 8] = z;
                    } else {//三个顶点
                        verts[i * 9 + (j - 1) * 3] = x;
                        verts[i * 9 + (j - 1) * 3 + 1] = y;
                        verts[i * 9 + (j - 1) * 3 + 2] = z;

                        //记录模型中三个坐标轴方向的最大最小值
                        if (i == 0 && j == 1) {
                            model.minX = model.maxX = x;
                            model.minY = model.maxY = y;
                            model.minZ = model.maxZ = z;
                        } else {
                            model.minX = Math.min(model.minX, x);
                            model.minY = Math.min(model.minY, y);
                            model.minZ = Math.min(model.minZ, z);
                            model.maxX = Math.max(model.maxX, x);
                            model.maxY = Math.max(model.maxY, y);
                            model.maxZ = Math.max(model.maxZ, z);
                        }
                    }
                }
                short r = Util.byte2ToShort(facetBytes, stlOffset);
                stlOffset = stlOffset + 2;
                remarks[i] = r;
            }
        } catch (Exception e) {
            if (listener != null) {
                listener.onFailure(e);
            } else {
                e.printStackTrace();
            }
        }
        //将读取的数据设置到Model对象中
        model.setVerts(verts);
        model.setVnorms(vnorms);
        model.setRemarks(remarks);
        
    }


}
