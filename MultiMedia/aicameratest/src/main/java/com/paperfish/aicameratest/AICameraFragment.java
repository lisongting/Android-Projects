package com.paperfish.aicameratest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by lisongting on 2017/8/16.
 */

public class AICameraFragment extends Fragment {
    public static final String TAG = "tag";

    private static final int NUM_CLASSES = 1470;
    private static final int INPUT_SIZE = 448;
    private static final int IMAGE_MEAN = 128;
    private static final float IMAGE_STD = 128;
    private static final String INPUT_NAME = "Placeholder";
    private static final String OUTPUT_NAME = "19_fc";

    private static final String MODEL_FILE = "file:///android_asset/android_graph.pb";
    private static final String LABEL_FILE =
            "file:///android_asset/label_strings.txt";
    //yolo能识别的物体
    private final String[] class_labels =  {"aeroplane", "bicycle", "bird", "boat", "bottle", "bus", "car",
            "cat", "chair", "cow", "diningtable", "dog", "horse", "motorbike", "person",
            "pottedplant", "sheep", "sofa", "train","tvmonitor"};
    private TextureView textureView;
    private TextView textView;
    private SurfaceTexture surfaceTexture;

    private String cameraId;
    private CameraDevice cameraDevice;
    private CameraCaptureSession captureSession;
    private CaptureRequest.Builder captureRequestBuilder;
    private CaptureRequest captureRequest;

    private Size imageDimension;
    private Size previewSize;

    private Handler mUIHandler ;
    private Handler backgroundHandler;
    private HandlerThread backgroundThread;

    private String predictedClass = "none";



    private TextureView.SurfaceTextureListener surfaceTextureListener;
    private CameraDevice.StateCallback cameraStateCallback;
    private ImageReader.OnImageAvailableListener imageListener;
    private CameraCaptureSession.StateCallback captureSessionCallback;

    private AssetManager mgr;
    private boolean processing = false;
    private int imageCount = 0;
    private ByteBuffer Ybuffer;
    private ByteBuffer Ubuffer;
    private ByteBuffer Vbuffer;
    private byte[] Y;
    private byte[] U;
    private byte[] V;
    static{
//        //caffe2
        System.loadLibrary("native-lib");

        //tensorflow
        //System.loadLibrary("tensorflow_demo");
    }

    public native String classificationFromCaffe2(int h, int w, byte[] Y, byte[] U, byte[] V,
                                                  int rowStride, int pixelStride, boolean r_hwc);

    public native void initCaffe2(AssetManager mgr);

//    public native int initializeTensorFlow(
//            AssetManager assetManager,
//            String model,
//            String labels,
//            int numClasses,
//            int inputSize,
//            int imageMean,
//            float imageStd,
//            String inputName,
//            String outputName);
//
//    private native String classifyImageBmp(Bitmap bitmap);
//

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

        mgr = getResources().getAssets();
        mUIHandler = new Handler(Looper.getMainLooper());


        new Thread(new Runnable() {
            @Override
            public void run() {
                initCaffe2(mgr);

//                int initCode = initializeTensorFlow(mgr, MODEL_FILE, LABEL_FILE, NUM_CLASSES, INPUT_SIZE, IMAGE_MEAN, IMAGE_STD, INPUT_NAME, OUTPUT_NAME);
//                log("TensorFlow initCode:" + initCode);
            }
        }).start();

        backgroundThread = new HandlerThread("Background Thread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstaceState) {
        log("onCreateView");
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        textureView = (TextureView) view.findViewById(R.id.texture_view);
        textView = (TextView) view.findViewById(R.id.text);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        log("onResume");
        initListeners();
        textureView.setSurfaceTextureListener(surfaceTextureListener);
    }

    private void initListeners() {
        surfaceTextureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                surfaceTexture = surface;
                //当SurfaceTexture可用的时候，打开照相头
                openCamera();
                log("onSurfaceTextureAvailable");
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                log("onSurfaceTextureSizeChanged");
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                log("onSurfaceTextureDestroyed");
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        };

        cameraStateCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                cameraDevice = camera;
                //开始预览
                startPreview();
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {
                cameraDevice.close();
            }

            @Override
            public void onError(@NonNull CameraDevice camera, int error) {
                cameraDevice.close();
                cameraDevice = null;
            }
        };

        imageListener= new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                Image image = reader.acquireNextImage();
//                log("Image read Size:" + image.getWidth() + "X" + image.getHeight());
                imageCount++;
                if (!processing && imageCount % 30 == 0) {
                    processing = true;
                    int w = image.getWidth();
                    int h = image.getHeight();
                    //获取Y,U,V的ByteBuffer
                    Ybuffer = image.getPlanes()[0].getBuffer();
                    Ubuffer = image.getPlanes()[1].getBuffer();
                    Vbuffer = image.getPlanes()[2].getBuffer();

                    int rowStride = image.getPlanes()[1].getRowStride();
                    int pixelStride = image.getPlanes()[1].getPixelStride();
                    if (Y == null && U == null && V == null) {
                        Y = new byte[Ybuffer.capacity()];
                        U = new byte[Ubuffer.capacity()];
                        V = new byte[Vbuffer.capacity()];
                    }

                    Ybuffer.get(Y);
                    Ubuffer.get(U);
                    Vbuffer.get(V);

                    predictedClass = classificationFromCaffe2(h, w, Y, U, V, rowStride, pixelStride, false);
                    Log.i("tag", "底层代码识别结果：" + predictedClass);
                    mUIHandler.post(new Runnable() {
                        @Override
                        public void run() {
//                        textView.setText(predictedClass);
                            String[] things = getProbablyThings(predictedClass);
                            textView.setText("1."+things[0]+"\n2."+things[1]);
                            processing = false;
                        }
                    });
                }else{
                }

                image.close();

            }
        };


        captureSessionCallback = new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session) {
                if (null == cameraDevice) {
                    return;
                }
                captureSession = session;
                updatePreview();
            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                Toast.makeText(getContext(), "CaptureSession Request Failed", Toast.LENGTH_SHORT).show();
            }
        };
        
    }

    private void updatePreview() {
        //打开自动曝光
        captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

        captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

        captureRequest = captureRequestBuilder.build();

        try {
            captureSession.setRepeatingRequest(captureRequest, null, backgroundHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }


    private void openCamera() {
        CameraManager manager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraId = "" + CameraCharacteristics.LENS_FACING_FRONT;
//            cameraId = "" + CameraCharacteristics.LENS_FACING_BACK;

            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap configurationMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            previewSize = Util.getPreferredPreviewSize(configurationMap.getOutputSizes(ImageFormat.YUV_420_888), textureView.getWidth(), textureView.getHeight());
            //获取所有可支持的图像输出尺寸，这里是获取了第0个
            imageDimension = configurationMap.getOutputSizes(SurfaceTexture.class)[0];
            log("output Image Dimension:" + imageDimension.getWidth() + "X" + imageDimension.getHeight());

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                manager.openCamera(cameraId, cameraStateCallback, null);
            }

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    private void startPreview() {

        surfaceTexture.setDefaultBufferSize(textureView.getWidth(), textureView.getHeight());
        Surface surface = new Surface(surfaceTexture);

        int width = textureView.getWidth()/6;
        int height = textureView.getHeight()/6;
        ImageReader imageReader = ImageReader.newInstance(width, height, ImageFormat.YUV_420_888, 4);
        log("imageReader size:" + imageReader.getWidth() + "X" + imageReader.getHeight());
        //让ImageAvailable事件发生在后台线程
        imageReader.setOnImageAvailableListener(imageListener, backgroundHandler);

//        surfaceTexture.setDefaultBufferSize(imageDimension.getWidth(),imageDimension.getHeight());
        surfaceTexture.setDefaultBufferSize(previewSize.getWidth(),previewSize.getHeight());
        try {
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            
            //让照相机的图像数据同时传给两个目标，一个是TextureView用于展示
            // 另一个是ImageReader，用于进行后台的物体识别
            captureRequestBuilder.addTarget(surface);
            captureRequestBuilder.addTarget(imageReader.getSurface());


            cameraDevice.createCaptureSession(Arrays.asList(surface, imageReader.getSurface()), captureSessionCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }


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

    }

    @Override
    public void onDestroy() {
        log("onDestroy");
        super.onDestroy();
        closeCamera();
        backgroundThread.quitSafely();
    }


    private void closeCamera() {
        if (null != cameraDevice) {
            captureSession.close();
            cameraDevice.close();
            cameraDevice = null;
            captureSession = null;
        }
    }
    private void log(String string) {
        Log.i(TAG, "AiCameraFragment -- "+string);
    }

    private String[] getProbablyThings(String string) {
//        log(string);
        StringBuilder sb = new StringBuilder(string);
        String[] things = new String[2];
        int first = sb.indexOf("0:");
        int second = sb.indexOf("1:");
        int end = sb.indexOf("-");
        things[0] = sb.substring(first+3, end);
        things[1] = sb.substring(second+3, sb.indexOf("-", second));
        return things;
    }
}
