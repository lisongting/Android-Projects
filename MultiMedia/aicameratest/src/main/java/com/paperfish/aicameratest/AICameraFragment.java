package com.paperfish.aicameratest;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lisongting on 2017/8/16.
 */

public class AICameraFragment extends Fragment {
    public static final String TAG = "CameraFragment";
    private TextureView textureView;
    private TextView textView;
    private SurfaceTexture surfaceTexture;

    private String cameraId;
    private CameraDevice cemeraDevice;
    private CameraCaptureSession captureSession;
    private CaptureRequest.Builder requestBuilder;
    private Size imageDimension;
    private Handler mBackgroundHandler;

    private String predictedClass = "none";

    private AssetManager assetManaget;
    private boolean processing = false;
    private Image image = null;
    private boolean run_HWC = false;


    static{
        System.loadLibrary("native-lib");

    }

    public native String classificationFromCaffe2(int h, int w, byte[] Y, byte[] U, byte[] V,
                                                  int rowStride, int pixelStride, boolean r_hwc);

    public native void initCaffe2(AssetManager mgr);

    public AICameraFragment() {

    }

    public static AICameraFragment getInstance() {
        return new AICameraFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");

    }

    @Override
    public void onStart() {
        super.onStart();
        log("onStart");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        log("onCreateView");
        View view = inflater.inflate(R.layout.fragment_camera, container, false);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        log("onResume");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("onAttach");
    }

    //这个方法在onDestroy之后调用
    @Override
    public void onDetach() {
        super.onDetach();
        log("onDetach");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop");
    }

    @Override
    public void onDestroy() {
        log("onDestroy");
        super.onDestroy();
    }


    private void log(String string) {
        Log.i(TAG, string);
    }
}
