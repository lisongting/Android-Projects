package com.lst.gdx;

import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;

/**
 * Created by lisongting on 2018/3/5.
 */

public class ModelLoaderTest implements ApplicationListener {
    //透视相机
    private PerspectiveCamera camera;
    private Model model;
    private ModelInstance instanceXbot,instanceMuseum;
    //使用ModelBatch来渲染图像
    private ModelBatch batch;
    private Environment environment;
    private CameraInputController controller;
    private AssetManager assets;
    private Array<ModelInstance> instances = new Array<>();
    private boolean loading;
    private final String MODEL_XBOT = "base_link.g3dj";
    private final String MODEL_MUSEUM = "ISCAS_museum.g3dj";
    float i =0;

    @Override
    public void create() {
        batch = new ModelBatch();
        environment = new Environment();
        //构建环境光0.4,0.4,0.4
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.4f,0.4f,0.4f,1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        //设置为67度视角
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
        controller = new CameraInputController(camera);
        Gdx.input.setInputProcessor(controller);

        assets = new AssetManager();
        assets.load(MODEL_XBOT, Model.class);
        assets.load(MODEL_MUSEUM, Model.class);
        loading = true;

//        ModelLoader loader = new G3dModelLoader(new JsonReader());
//        ModelData modelData = loader.loadModelData(Gdx.files.internal(MODEL_XBOT));
//        model = new Model(modelData, new TextureProvider.FileTextureProvider());
//        doneLoading();

    }


    private void doneLoading() {
        Model xbot = assets.get(MODEL_XBOT, Model.class);
        instanceXbot = new ModelInstance(xbot);

        Model museum = assets.get(MODEL_MUSEUM, Model.class);
        instanceMuseum = new ModelInstance(museum);

        loading = false;
        instances.add(instanceMuseum, instanceXbot);
//        instance = new ModelInstance(model);
//        instances.add(instance);
//        loading = false;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        if (loading && assets.update()) {
            doneLoading();
        }
        Log.i("tag", "rendering..");
        controller.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin(camera);

        batch.render(instances, environment);

        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                i+=0.1;
                if (instanceXbot.transform != null) {
                    instances.get(1).transform.setToTranslation(i, 0, 0);
                    render();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("tag", "transform is null");
                }
            }
        }).start();
    }

    @Override
    public void dispose() {
        batch.dispose();
        instances.clear();
        assets.dispose();
    }
}
