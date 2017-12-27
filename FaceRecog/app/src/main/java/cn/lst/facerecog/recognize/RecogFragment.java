package cn.lst.facerecog.recognize;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.FaceDetector;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import cn.lst.facerecog.FaceData;
import cn.lst.facerecog.FaceOverlayView;
import cn.lst.facerecog.ImageUtils;
import cn.lst.facerecog.R;
import cn.lst.facerecog.Util;


/**
 * Created by lisongting on 2017/12/14.
 */

public class RecogFragment extends Fragment implements RecogContract.View{

    private static final String TAG = "RecogFragment";
    private TextureView textureView;
    private SurfaceTexture surfaceTexture;
    //与拍摄相关的一些类
    private String cameraId;
    private CameraDevice cameraDevice;
    private CameraManager cameraManager;
    private CaptureRequest.Builder captureBuilder;
    private CameraCaptureSession cameraCaptureSession;

    //关于摄像头相关的一些监听器
    private CameraCaptureSession.CaptureCallback captureCallback;
    private CameraCaptureSession.StateCallback captureSessionCallback;
    private TextureView.SurfaceTextureListener surfaceTextureListener;
    private CameraDevice.StateCallback cameraCallback;

    private HandlerThread backgroundThread;
    private Handler backgroundHandler;

    //与人脸检测相关的
    private double mScale = 0.2;
    private int MAX_FACE_COUNT = 1;
    private volatile boolean isDetecting = false ;
    private Timer detectTimer;
    private TimerTask detectFaceTask;
    private FaceDetector faceDetector;
    private SparseIntArray facesCountMap;
    private FaceDetector.Face[] detectedFaces;
    private FaceData[] faces;
    private FaceData[] previousFaces;
    private Bitmap faceBitmap;
    private RecogContract.Presenter presenter;
    private int cameraFacingMode;
    private FaceOverlayView faceOverlayView;
    private AlertDialog alertDialog;

    public RecogFragment() {
        cameraFacingMode = CameraCharacteristics.LENS_FACING_BACK;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recognize, container, false);
        textureView = v.findViewById(R.id.texture_view);
        faceOverlayView = v.findViewById(R.id.face_overlay_view);

        initView();
        return v;
    }

    @Override
    public void initView() {
        faces = new FaceData[MAX_FACE_COUNT];
        previousFaces = new FaceData[MAX_FACE_COUNT];
        detectedFaces = new FaceDetector.Face[MAX_FACE_COUNT];
        for (int i = 0; i < MAX_FACE_COUNT; i++) {
            faces[i] = new FaceData();
            previousFaces[i] = new FaceData();
        }

        facesCountMap = new SparseIntArray();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        alertDialog = builder
                .setCancelable(false)
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                })
                .create();
    }

    @Override
    public void onResume() {
        super.onResume();
        backgroundThread = new HandlerThread("handlerThread");
        backgroundThread.start();
        backgroundHandler = new Handler(backgroundThread.getLooper());
        initCallbackAndListeners();
        initCamera();
        Toast.makeText(getContext(), "正在进行人脸识别", Toast.LENGTH_SHORT).show();
        isDetecting = true;
    }

    private void initCallbackAndListeners() {

        cameraCallback = new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice camera) {
                log("CameraStateCallback -- onOpened()");
                cameraDevice = camera;
                if (textureView.isAvailable()) {
                    startPreview();
                }
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice camera) {
                log("CameraStateCallback -- onDisconnected()");
                if (cameraDevice != null) {
                    cameraDevice.close();
                }

            }

            @Override
            public void onError(@NonNull CameraDevice camera,int error) {
                log("CameraStateCallback -- onOpened()");
            }
        };

        surfaceTextureListener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                log("TextureView.SurfaceTextureListener -- onSurfaceTextureAvailable()");
                surfaceTexture = surface;
                startPreview();
                startTimerTask();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                log("TextureView.SurfaceTextureListener -- onSurfaceTextureSizeChanged()");
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                log("TextureView.SurfaceTextureListener -- onSurfaceTextureDestroyed()");
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                Log.v(TAG,"TextureView.SurfaceTextureListener -- onSurfaceTextureUpdated()");
            }
        };

        captureCallback = new CameraCaptureSession.CaptureCallback() {
            @Override
            public void onCaptureStarted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, long timestamp, long frameNumber) {
                //Log.v(TAG,"CameraCaptureSession.CaptureCallback -- onCaptureStarted()");
                super.onCaptureStarted(session, request, timestamp, frameNumber);
            }

            @Override
            public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                //Log.v(TAG,"CameraCaptureSession.CaptureCallback -- onCaptureCompleted()");
                super.onCaptureCompleted(session, request, result);
            }

        };

        captureSessionCallback = new CameraCaptureSession.StateCallback() {
            @Override
            public void onConfigured(@NonNull CameraCaptureSession session) {
                log("CameraCaptureSession.StateCallback -- onConfigured");
                if (cameraDevice == null) {
                    return;
                }
                cameraCaptureSession = session;

                captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);

                CaptureRequest request = captureBuilder.build();
                try {
                    cameraCaptureSession.setRepeatingRequest(request, captureCallback, backgroundHandler);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                log("CameraCaptureSession.StateCallback -- onConfigureFailed");
            }
        };

        textureView.setSurfaceTextureListener(surfaceTextureListener);
    }

    private void initCamera() {
        cameraId = "" + cameraFacingMode;
        cameraManager = (CameraManager) getContext().getSystemService(Context.CAMERA_SERVICE);

        try {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},1);
            }
            //打开摄像头
            cameraManager.openCamera(cameraId, cameraCallback, backgroundHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }

    public void switchCamera() {
        closeCamera();
        if (cameraFacingMode == CameraCharacteristics.LENS_FACING_BACK) {
            cameraFacingMode =  CameraCharacteristics.LENS_FACING_FRONT;
        } else {
            cameraFacingMode =  CameraCharacteristics.LENS_FACING_BACK;
        }
        if (textureView.isAvailable()) {
            initCamera();

        }
    }

    private void startPreview() {
        try {
            CameraCharacteristics characteristics = cameraManager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap configMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            Size previewSize = Util.getPreferredPreviewSize(
                    configMap.getOutputSizes(ImageFormat.JPEG),textureView.getWidth(), textureView.getHeight());

            surfaceTexture.setDefaultBufferSize(previewSize.getWidth(),previewSize.getHeight());
            Surface surface = new Surface(surfaceTexture);
            captureBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureBuilder.addTarget(surface);

            cameraDevice.createCaptureSession(Arrays.asList(surface),captureSessionCallback,backgroundHandler);

            WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(displayMetrics);

            faceOverlayView.setPreviewWidth(textureView.getWidth());
            faceOverlayView.setPreviewHeight(textureView.getHeight());
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void startTimerTask(){
        detectTimer = new Timer();
        //在这个定时任务中，不断的检测界面中的人脸
        detectFaceTask = new TimerTask() {
            @Override
            public void run() {
                Bitmap face = textureView.getBitmap();
                if (face != null&&isDetecting) {
                    //原先的bitmap格式是ARGB_8888，以下的步骤是把格式转换为RGB_565
                    ByteArrayOutputStream bout = new ByteArrayOutputStream();
                    face.compress(Bitmap.CompressFormat.JPEG, 100, bout);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    Bitmap RGBFace = BitmapFactory.decodeStream(new ByteArrayInputStream(bout.toByteArray()), null, options);

                    Bitmap smallRGBFace = Bitmap.createScaledBitmap(RGBFace, (int)(RGBFace.getWidth()*mScale)
                            , (int)(RGBFace.getHeight()*mScale), false);
                    if (faceDetector == null) {
                        faceDetector = new FaceDetector(smallRGBFace.getWidth(), smallRGBFace.getHeight(), 1);
                    }
                    //findFaces中传入的bitmap格式必须为RGB_565
                    int found = faceDetector.findFaces(smallRGBFace, detectedFaces);
                    Log.v(TAG, "found:" + found+" face(s)");


                    if (detectedFaces[0] == null) {
                        faces[0].clear();
                    } else {
                        PointF mid = new PointF();
                        detectedFaces[0].getMidPoint(mid);

                        //前面为了方便检测人脸将图片缩小，现在按比例还原
                        mid.x *= 1.0/mScale;
                        mid.y *= 1.0/mScale;
                        float eyeDistance = detectedFaces[0].eyesDistance()*(float)(1.0/mScale);
                        float confidence = detectedFaces[0].confidence();
                        //mPersonId一开始是0
                        int personId = 0;

                        //预先创建一个人脸矩形区域
                        RectF rectF = ImageUtils.getPreviewFaceRectF(mid, eyeDistance);

                        //如果人脸矩形区域大于一定面积，才采集图像
                        if (rectF.width() * rectF.height() > 15 * 20) {
                            //获取之前的Faces数据
                            float eyesDisPre = previousFaces[0].eyesDistance();
                            PointF midPre ;
                            midPre = previousFaces[0].getMidEye();

                            //在一定区域内检查人脸是否移动过大，超出这个区域。
                            RectF rectCheck = ImageUtils.getCheckFaceRectF(midPre, eyesDisPre);

                            //如果没有当前人脸没有超过这个检查区域，说明该ID对应的人脸晃动程度小，则适合采集
                            if (rectCheck.contains(mid.x, mid.y) &&
                                    (System.currentTimeMillis() - previousFaces[0].getTime()) < 1000) {
                                personId = previousFaces[0].getId();
                            }

                            faces[0].setFace(personId, mid, eyeDistance, confidence, System.currentTimeMillis());
                            previousFaces[0].set(faces[0].getId(), faces[0].getMidEye(),
                                    faces[0].eyesDistance(), faces[0].getConfidence(), faces[0].getTime());

                            //将采集到的人脸的帧数以key-value的形式放在一个map中
                            int tmpFrameCount = facesCountMap.get(personId) + 1;
                            if (tmpFrameCount < 3) {
                                facesCountMap.put(personId, tmpFrameCount);
                            }else if (tmpFrameCount == 3) {
                                faceBitmap = ImageUtils.cropFace(faces[0], RGBFace);
                                if (!alertDialog.isShowing()) {
                                    presenter.recognize(
                                            ImageUtils.encodeBitmapToBase64(faceBitmap, Bitmap.CompressFormat.JPEG,100));
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            faceOverlayView.setFaces(faces);
                                        }
                                    });
                                }

                            }
                        }
                    }

                    try {
                        bout.close();
                        face.recycle();
                        RGBFace.recycle();
                        smallRGBFace.recycle();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        detectTimer.schedule(detectFaceTask, 0, 1000);
    }

    public void closeCamera() {
        if (cameraCaptureSession != null) {
            try {
                cameraCaptureSession.abortCaptures();
                cameraCaptureSession.stopRepeating();
                cameraCaptureSession.close();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }
        if (cameraDevice != null) {
            cameraDevice.close();
        }
        isDetecting = false;
        detectTimer.cancel();
    }

    @Override
    public synchronized void showRecognitionSuccess(String userName) {
        if (getContext() == null ) {
            return;
        }
        if (alertDialog != null) {
            if( alertDialog.isShowing()){
                return;
            }
        }
        alertDialog.setTitle("识别结果：" + userName);
        alertDialog.setMessage("是否继续识别？");
        alertDialog.show();
//        Toast.makeText(getContext(), "识别结果："+userName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public synchronized void showError(String s) {
        if (getContext() == null) {
            return;
        }
        if (alertDialog != null) {
            if( alertDialog.isShowing()){
                return;
            }
        }
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        closeCamera();
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void setPresenter(RecogContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void log(String s) {
        Log.i(TAG, s);
    }
}
