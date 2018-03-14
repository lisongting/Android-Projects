package com.lst.gdx;

import android.util.Log;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

/**
 * Created by lisongting on 2018/3/5.
 */

public class Loader implements ApplicationListener {
    //透视相机
    private PerspectiveCamera camera;
    private Model model;
    private ModelInstance instanceXbot,instanceMuseum;
    //使用ModelBatch来渲染图像
    private ModelBatch batch;
    private Environment environment;
    private CameraController controller;
    private AssetManager assets;
    private Array<ModelInstance> instances = new Array<>();
    private Label label;
    private StringBuilder stringBuilder;
    private Stage stage;
    private boolean loading;
    private final String MODEL_XBOT = "base_link.g3dj";
    private final String MODEL_MUSEUM = "ISCAS_museum.g3dj";
    private Vector3 minVector,maxVector;

    float i =0;

    @Override
    public void create() {
        batch = new ModelBatch();
        environment = new Environment();
        stringBuilder = new StringBuilder();
        stage = new Stage();
        label = new Label(" ", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        stage.addActor(label);
        //构建环境光0.4,0.4,0.4
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.4f,0.4f,0.4f,1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        //设置为67度视角
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 20f, -30f);
        camera.lookAt(0, 0, 0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();
        controller = new CameraController(camera);
        controller.setCameraData(new Vector3(0f,20f,-30f),new Vector3(0f, 0f, 0f));
        Gdx.input.setInputProcessor(controller);

        assets = new AssetManager();
        assets.load(MODEL_XBOT, Model.class);
        assets.load(MODEL_MUSEUM, Model.class);
        loading = true;

//        ModelLoader loader = new G3dModelLoader(new JsonReader());
//        ModelData modelData = loader.loadModelData(Gdx.files.internal(MODEL_MUSEUM));
//        model = new Model(modelData, new TextureProvider.FileTextureProvider());
//        doneLoading();

//        ModelLoader loader = new MyObjLoader();
//        ModelData modelData = loader.loadModelData(Gdx.files.internal(MODEL_MUSEUM));
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

        BoundingBox boundingBox = new BoundingBox();
        instanceMuseum.calculateBoundingBox(boundingBox);
        maxVector = new Vector3();
        minVector = new Vector3();
        boundingBox.getMax(maxVector);
        boundingBox.getMin(minVector);
//        Log.i("tag", "maxVector:"+maxVector.toString());
//        Log.i("tag", "maxVector:"+minVector.toString());

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        if (loading && assets.update()) {
            doneLoading();
        }
//        Log.i("tag", "rendering..");
        controller.update();
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        batch.begin(camera);

        batch.render(instances, environment);
        if (instances.size > 1 && i<=maxVector.x*0.8f) {
//            Vector3 pos = new Vector3();
//            instances.get(1).transform.getTranslation(pos);
//            Log.i("tag",pos.toString());
            instances.get(1).transform.setToTranslation(i, 0f, 0f);
            i+=0.01;
        }
        batch.end();



        stringBuilder.setLength(0);
        stringBuilder.append("FPS: ").append(Gdx.graphics.getFramesPerSecond());
        label.setText(stringBuilder.toString());
        label.setPosition(20,20);
        label.setFontScale(3);
        stage.draw();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        Log.i("tag", "resume()");

    }

    @Override
    public void dispose() {
        batch.dispose();
        instances.clear();
        assets.dispose();
        stage.dispose();
    }
}
