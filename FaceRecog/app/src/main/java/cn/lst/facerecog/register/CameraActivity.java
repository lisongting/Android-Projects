package cn.lst.facerecog.register;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.Arrays;

import cn.lst.facerecog.ImageUtils;
import cn.lst.facerecog.R;
import cn.lst.facerecog.Util;

import static android.view.View.GONE;

/**
 * Created by lisongting on 2017/12/14.
 */

public class CameraActivity extends AppCompatActivity implements RegisterContract.View{

    private TextureView mTextureView;
    private SurfaceTexture mSurfaceTexture;
    private ImageView ivShow,btCapture,btBack,btSwithCam;
    private CameraManager mCameraManager;//摄像头管理器
    private String mCameraID;//摄像头Id 0 为后  1 为前
    private ImageReader mImageReader;
    private CameraCaptureSession mCameraCaptureSession;
    private CameraDevice mCameraDevice;
    private Button bt_ok,bt_reCapture,bt_home;
    private LinearLayout linearLayout;

    public static final String TAG = "CameraActivity";
//    private Bitmap faceBitmap;
    private SoftReference<Bitmap> bitmapSoftReference ;
    private Size mPreviewSize;
    private CaptureRequest.Builder requestBuilder;
    private RegisterContract.Presenter presenter;
    private boolean isPreviewing = true;
    private int cameraFacingMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_take_head_photo);

        linearLayout = (LinearLayout) findViewById(R.id.bottom_linear_layout);
        bt_ok = (Button) findViewById(R.id.id_bt_ok);
        bt_reCapture = (Button) findViewById(R.id.id_bt_again);
        bt_home = (Button) findViewById(R.id.id_bt_home);
        ivShow = (ImageView) findViewById(R.id.id_iv_show_picture);
        btBack = findViewById(R.id.bt_back);
        mTextureView = (TextureView) findViewById(R.id.id_texture_view);
        btCapture = findViewById(R.id.capture);
        btSwithCam = findViewById(R.id.bt_switch);

        //LENS_FACING_FRONT 后置摄像头， LENS_FACING_BACK  前置摄像头
        cameraFacingMode = CameraCharacteristics.LENS_FACING_BACK;

        initView();

        initCamera();

        initOnClickListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter = new RegisterPresenter(this);
    }

    public void initView() {
        View status_bar = findViewById(R.id.status_bar_view);
        ViewGroup.LayoutParams params = status_bar.getLayoutParams();
        params.height = Util.getStatusBarHeight(this);
        status_bar.setLayoutParams(params);
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public void initOnClickListener() {
        //为SurfaceView设置监听器
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                log("TextureView.SurfaceTextureListener -- onSurfaceTextureAvailable()");
                mSurfaceTexture = surface;
                takePreview();
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                log( "TextureView.SurfaceTextureListener -- onSurfaceTextureSizeChanged()");
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                log( "TextureView.SurfaceTextureListener -- onSurfaceTextureDestroyed()");
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });

        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = getIntent().getStringExtra("userName");
                presenter.register(Util.makeUserNameToHex(userName),
                        ImageUtils.encodeBitmapToBase64(bitmapSoftReference.get(), Bitmap.CompressFormat.JPEG, 100));


            }
        });

        bt_reCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reCapture();
                isPreviewing = true;
            }
        });

        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        
        btCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
                isPreviewing = false;
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btSwithCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPreviewing) {
                    return;
                }
                if (mCameraDevice != null) {
                    mCameraDevice.close();
                }

                if (mCameraCaptureSession != null) {
                    mCameraCaptureSession.close();
                }

                if (cameraFacingMode == CameraCharacteristics.LENS_FACING_BACK) {
                    cameraFacingMode =  CameraCharacteristics.LENS_FACING_FRONT;
                } else {
                    cameraFacingMode =  CameraCharacteristics.LENS_FACING_BACK;
                }
                if (mTextureView.isAvailable()) {
                    initCamera();

                }
            }
        });

    }

    private CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            log( "CameraDevice.StateCallback -- onOpened()");
            mCameraDevice = camera;
            //在切换前后摄像头时，会触发onOpen方法，如果这里textureview可用，则开启预览
            if (mTextureView.isAvailable()) {
                takePreview();
            }
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            log( "CameraDevice.StateCallback -- onDisconnected()");
        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            log( "CameraDevice.StateCallback -- onError()");
        }
    };

    //初始化摄像头
    private void initCamera() {
        log("initCamera()");
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        log( "DisplayMetrics width:" + width + ",height:" + height);
        mImageReader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
        log( "ImageReader width:" + mImageReader.getWidth() + ",height:" + mImageReader.getHeight());

        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                mTextureView.setVisibility(GONE);
                btCapture.setVisibility(GONE);
                ivShow.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);

                Image image = reader.acquireLatestImage();
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                Bitmap tmpBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                if (tmpBitmap != null) {
                    log("Bitmap info: [width:"+tmpBitmap.getWidth() +",height:"+ tmpBitmap.getHeight()+"]");

                    //调节bitmap的尺寸大小
                    int width = tmpBitmap.getWidth();
                    int height = tmpBitmap.getHeight();
                    Matrix matrix = new Matrix();
                    Matrix matrix1 = new Matrix();

                    //如果是前置摄像头，则拍出来的照片要进行镜像水平翻转
                    if (cameraFacingMode == CameraCharacteristics.LENS_FACING_BACK) {
                        if (width >= 1500 || height >= 1500) {
                            matrix.postScale(-0.3F, 0.3F);
                        } else {
                            matrix.postScale(-0.5F, 0.5F);
                        }

                        matrix1.postScale(-1, 1);
                    } else {
                        if (width >= 1500 || height >= 1500) {
                            matrix.postScale(0.3F, 0.3F);
                        } else {
                            matrix.postScale(0.5F, 0.5F);
                        }
                        matrix1.postScale(1, 1);
                    }
                    bitmapSoftReference = new SoftReference<Bitmap>(Bitmap.createBitmap(tmpBitmap, 0, 0, width, height, matrix, true));

                    WeakReference<Bitmap> bitmapWeakReference =
                            new WeakReference<Bitmap>(Bitmap.createBitmap(tmpBitmap, 0, 0, width, height, matrix1, true));
                    ivShow.setImageBitmap(bitmapWeakReference.get());
                    image.close();
                }
            }
        },null);

        mCameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        openCamera();
    }

    @TargetApi(23)
    public void openCamera() {
        log("openCamera:" + cameraFacingMode);
        mCameraID = ""+ cameraFacingMode;
        try{
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!=
                    PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},1);
            }
            //打开摄像头
            mCameraManager.openCamera(mCameraID,stateCallback,null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void takePreview() {
        log("takePreview()");
        try{
            CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(mCameraID);
            StreamConfigurationMap configMap = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

            int width = mTextureView.getWidth();
            int height = mTextureView.getHeight();
            //获得最合适的预览尺寸
            mPreviewSize = Util.getPreferredPreviewSize(configMap.getOutputSizes(ImageFormat.JPEG), width, height);
//            mPreviewSize = Util.getPreferredPreviewSize(configMap.getOutputSizes(SurfaceTexture.class), width, height);
            mSurfaceTexture.setDefaultBufferSize(mPreviewSize.getWidth(),mPreviewSize.getHeight());
            log("mPreviewSize:" + mPreviewSize.getWidth() + "x" + mPreviewSize.getHeight());

            final Surface surface = new Surface(mSurfaceTexture);
            //创建预览需要的CaptureRequest.Builder
            requestBuilder =
                    mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);

            if (surface.isValid()) {
                requestBuilder.addTarget(surface);
            }
            log( "mTextureView info:" + mTextureView.getWidth() + "x" + mTextureView.getHeight());

            //创建CameraSession,该对象负责管理处理预览请求和拍照请求
            mCameraDevice.createCaptureSession(Arrays.asList(surface,mImageReader.getSurface()),
                    new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    if (mCameraDevice == null) {
                        return;
                    }
                    mCameraCaptureSession = session;

                    //设置自动对焦点
                    requestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
                    //打开自动曝光
                    requestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
                    //显示预览
                    CaptureRequest previewRequest = requestBuilder.build();

                    try {
                        mCameraCaptureSession.setRepeatingRequest(previewRequest, null, null);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
                   log("onConfigureFailed");

                }
            },null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

    }


    private void takePicture() {
        if (mCameraDevice == null) {
            return;
        }
        log("takePicture()");
        //创建Request.Builder()
        final CaptureRequest.Builder requestBuilder ;
        try{
            requestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);

            //将ImageReader的surface作为CaptureRequest.Builder的目标
            requestBuilder.addTarget(mImageReader.getSurface());
            // 自动对焦
            requestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
            // 自动曝光
            requestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
            if (cameraFacingMode == CameraCharacteristics.LENS_FACING_BACK) {
                requestBuilder.set(CaptureRequest.JPEG_ORIENTATION, 270);
            } else {
                requestBuilder.set(CaptureRequest.JPEG_ORIENTATION, 90);
            }

            //拍照
            CaptureRequest mCaptureRequest = requestBuilder.build();
            mCameraCaptureSession.capture(mCaptureRequest, null, null);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void reCapture() {
        ivShow.setVisibility(GONE);
        linearLayout.setVisibility(GONE);
        mTextureView.setVisibility(View.VISIBLE);
        btCapture.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        log("CameraActivity--onDestroy--");
        if (bitmapSoftReference != null) {
            bitmapSoftReference.clear();
        }
        if (mSurfaceTexture != null) {
            mSurfaceTexture.release();
        }
        if (mCameraCaptureSession != null) {
            try {
                mCameraCaptureSession.abortCaptures();
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            mCameraCaptureSession.close();
        }
        mImageReader.getSurface().release();
        mCameraDevice.close();
    }

    @Override
    public void showInfo(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSuccess() {
        Toast.makeText(CameraActivity.this, "注册成功", Toast.LENGTH_LONG).show();
        bt_ok.setVisibility(GONE);
        bt_reCapture.setVisibility(GONE);
        bt_home.setVisibility(View.VISIBLE);
    }

    private void log(String s) {
        Log.i(TAG, s);
    }
}
