package cn.lst.robotdisplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.andresoviedo.app.model3D.services.SceneLoader;
import org.andresoviedo.app.model3D.view.ModelHelper;
import org.andresoviedo.app.model3D.view.ModelSurfaceView;

/**
 * Created by lisongting on 2017/12/21.
 */

public class RobotActivity extends AppCompatActivity implements ModelHelper.AdapterInterface{

    private static final String TAG = "TestActivity";
    //中间类，作为适配器
    private ModelHelper model;

    private SceneLoader sceneLoader;
    private ModelSurfaceView gLView;

    private String assetDir = "model";
//    private String assetFilename = "xbot_model.obj";
//    private String assetFilename = "ISCAS_museum.dae";
    private String assetFilename = "cowboy.dae";
    private float[] backgroundColor = new float[]{0,0,0,1};


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new ModelHelper(this, assetDir, assetFilename, null, backgroundColor, this);

        //创建ModelSurfaceView
        gLView = new ModelSurfaceView(model);
        setContentView(gLView);


        //创建3D场景加载器
        sceneLoader = new SceneLoader(model);
        sceneLoader.init();


    }


    @Override
    public void requestRender() {
        if (gLView != null) {
            gLView.requestRender();
        }else {
            Log.e(TAG, "gLView is null");
        }
    }

    @Override
    public SceneLoader getSceneLoader() {
        if (sceneLoader != null) {
            return sceneLoader;
        }
        Log.e(TAG, "SceneLoader is null");
        return null;
    }
}
