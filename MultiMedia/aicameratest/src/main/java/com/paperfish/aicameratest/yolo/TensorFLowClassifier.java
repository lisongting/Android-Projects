package com.paperfish.aicameratest.yolo;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

/**
 * Created by lisongting on 2017/8/23.
 */

public class TensorFLowClassifier implements Classifier {

    private static final String TAG = "TensorFlowClassifier";
    @Override
    public List<Recognition> recognizeImage(Bitmap bitmap) {
        return null;
    }


    private void log(String string) {
        Log.i(TAG, string);
    }
}
