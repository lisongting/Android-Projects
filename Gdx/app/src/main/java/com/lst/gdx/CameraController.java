package com.lst.gdx;


import android.graphics.Point;
import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by lisongting on 2018/3/9.
 */

public class CameraController extends GestureDetector {
    /** The button for rotating the camera. */
    public int rotateButton = Input.Buttons.LEFT;
    /** The angle to rotate when moved the full width or height of the screen. */
    public float rotateAngle = 360f;
    /** The button for translating the camera along the up/right plane */
    public int translateButton = Input.Buttons.RIGHT;
    /** The units to translate the camera when moved the full width or height of the screen. */
    public float translateUnits = 10f; // FIXME auto calculate this based on the target
    /** The button for translating the camera along the direction axis */
    public int forwardButton = Input.Buttons.MIDDLE;
    /** The key which must be pressed to activate rotate, translate and forward or 0 to always activate. */
    public int activateKey = 0;
    /** Indicates if the activateKey is currently being pressed. */
    protected boolean activatePressed;
    /** Whether scrolling requires the activeKey to be pressed (false) or always allow scrolling (true). */
    public boolean alwaysScroll = true;
    /** The weight for each scrolled amount. */
    public float scrollFactor = -0.1f;
    /** World units per screen size */
    public float pinchZoomFactor = 10f;
    /** Whether to update the camera after it has been changed. */
    public boolean autoUpdate = true;
    /** The target to rotate around. */
    public Vector3 target = new Vector3();
    /** Whether to update the target on translation */
    public boolean translateTarget = true;
    /** Whether to update the target on forward */
    public boolean forwardTarget = true;
    /** Whether to update the target on scroll */
    public boolean scrollTarget = false;
    public int forwardKey = Input.Keys.W;
    protected boolean forwardPressed;
    public int backwardKey = Input.Keys.S;
    protected boolean backwardPressed;
    public int rotateRightKey = Input.Keys.A;
    protected boolean rotateRightPressed;
    public int rotateLeftKey = Input.Keys.D;
    protected boolean rotateLeftPressed;
    /** The camera. */
    public Camera camera;
    /** The current (first) button being pressed. */
    protected int button = -1;

    private float startX, startY;
    private final Vector3 tmpV1 = new Vector3();
    private final Vector3 tmpV2 = new Vector3();
    private Vector3 cameraLook,cameraPos;

    private Point touchPoint0,touchPoint1;

    protected static class CameraGestureListener extends GestureAdapter {
        public CameraController controller;
        private float previousZoom;

        @Override
        public boolean touchDown (float x, float y, int pointer, int button) {
            previousZoom = 0;
            return false;
        }

        @Override
        public boolean tap (float x, float y, int count, int button) {
            return false;
        }

        @Override
        public boolean longPress (float x, float y) {
            return false;
        }

        @Override
        public boolean fling (float velocityX, float velocityY, int button) {
            return false;
        }

        @Override
        public boolean pan (float x, float y, float deltaX, float deltaY) {
            return false;
        }

        @Override
        public boolean zoom (float initialDistance, float distance) {
            int width = Gdx.graphics.getWidth();
            if (Math.abs(initialDistance - distance) < width/35) {
                //当双指的距离d，起始d与终止d相差不多时，
                //也会触发pinch() 此时看做是平移而不是缩放
                return false;
            }
//            log("zoom -- " + initialDistance + ",distance:" + distance);
            float newZoom = distance - initialDistance;
            float amount = newZoom - previousZoom;
            previousZoom = newZoom;
            float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
            return controller.pinchZoom(amount / ((w > h) ? h : w));

        }

        @Override
        public boolean pinch (Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
            int width = Gdx.graphics.getWidth();
            double distance = Math.abs(getDistanceBetweenPoints(pointer1, pointer2) -
                    getDistanceBetweenPoints(initialPointer1, initialPointer2));
            if (distance>width/8) {
                //当双指的距离d，起始d与终止d相差较大时，
                //也会触发zoom() 此时看做是缩放而不是平移
                return false;
            }
            return controller.translate(initialPointer1, initialPointer2, pointer1, pointer2);

        }
    };

    protected final CameraController.CameraGestureListener gestureListener;

    protected CameraController (final CameraController.CameraGestureListener gestureListener, final Camera camera) {
        super(gestureListener);
        this.gestureListener = gestureListener;
        this.gestureListener.controller = this;
        this.camera = camera;
    }

    public CameraController (final Camera camera) {
        this(new CameraController.CameraGestureListener(), camera);
        touchPoint0 = new Point(0, 0);
        touchPoint1 = new Point(0, 0);
    }

    public void update () {
        if (rotateRightPressed || rotateLeftPressed || forwardPressed || backwardPressed) {
            final float delta = Gdx.graphics.getDeltaTime();
            if (rotateRightPressed) {
                camera.rotate(camera.up, -delta * rotateAngle);
            }
            if (rotateLeftPressed) {
                camera.rotate(camera.up, delta * rotateAngle);
            }
            if (forwardPressed) {
                camera.translate(tmpV1.set(camera.direction).scl(delta * translateUnits));
                if (forwardTarget) target.add(tmpV1);
            }
            if (backwardPressed) {
                camera.translate(tmpV1.set(camera.direction).scl(-delta * translateUnits));
                if (forwardTarget) target.add(tmpV1);
            }
            if (autoUpdate) camera.update();
        }
    }

    private int touched;
    private boolean multiTouch;

    private boolean isTwoFinger =false;
    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        touched |= (1 << pointer);
        multiTouch = !MathUtils.isPowerOfTwo(touched);
        if (multiTouch) {
            this.button = -1;
        } else {
            if (this.button < 0 && (activateKey == 0 || activatePressed)) {
                startX = screenX;
                startY = screenY;
                this.button = button;
            }
        }
        return super.touchDown(screenX, screenY, pointer, button) || (activateKey == 0 || activatePressed);
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        touched &= -1 ^ (1 << pointer);
        multiTouch = !MathUtils.isPowerOfTwo(touched);
        if (button == this.button) this.button = -1;
        //两个手指，第一个先抬起来
        if(touched>0){
            touchPoint1.set(screenX, screenY);
            isTwoFinger = true;
        } else if (isTwoFinger) {
            //最后一个手指抬起来，pointer为0
            touchPoint0.set(screenX, screenY);
            isTwoFinger = false;
            //更新旋转中心
//            updateCenter();
        }
//        log("touchup -- pointer:" + pointer + ",pos:" + screenX + "," + screenY + ",touched:" + touched + ",isTwoFinger:" + isTwoFinger);

        return super.touchUp(screenX, screenY, pointer, button) || activatePressed;
    }

    protected boolean process (float deltaX, float deltaY, int button) {
        if (button == rotateButton) {
            tmpV1.set(camera.direction).crs(camera.up).y = 0f;
            camera.rotateAround(target, tmpV1.nor(), deltaY * rotateAngle);
            cameraLook.rotate(tmpV1.nor(), deltaY * rotateAngle);
            camera.rotateAround(target, Vector3.Y, deltaX * -rotateAngle);
            cameraLook.rotate(Vector3.Y, deltaX * -rotateAngle);

        } else if (button == translateButton) {
            camera.translate(tmpV1.set(camera.direction).crs(camera.up).nor().scl(-deltaX * translateUnits));
            camera.translate(tmpV2.set(camera.up).scl(-deltaY * translateUnits));
            if (translateTarget) target.add(tmpV1).add(tmpV2);
        } else if (button == forwardButton) {
            camera.translate(tmpV1.set(camera.direction).scl(deltaY * translateUnits));
            if (forwardTarget) target.add(tmpV1);
        }
        if (autoUpdate) camera.update();
        return true;
    }

    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        boolean result = super.touchDragged(screenX, screenY, pointer);

        if (result || this.button < 0) return result;
        final float deltaX = (screenX - startX) / Gdx.graphics.getWidth();
        final float deltaY = (startY - screenY) / Gdx.graphics.getHeight();
        startX = screenX;
        startY = screenY;
        return process(deltaX, deltaY, button);
    }

    @Override
    public boolean scrolled (int amount) {
        return zoom(amount * scrollFactor * translateUnits);
    }

    public boolean zoom (float amount) {
        if (!alwaysScroll && activateKey != 0 && !activatePressed) return false;
        camera.translate(tmpV1.set(camera.direction).scl(amount));
        if (scrollTarget) target.add(tmpV1);
        if (autoUpdate) camera.update();
        return true;
    }

    protected boolean pinchZoom (float amount) {
        return zoom(pinchZoomFactor * amount);
    }

    //平移镜头
    public boolean translate(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        //将屏幕的坐标转换为三维世界的坐标
        Vector3 initP1 = camera.unproject(new Vector3(initialPointer1, 0));
        Vector3 initP2 = camera.unproject(new Vector3(initialPointer2, 0));
        Vector3 endP1 = camera.unproject(new Vector3(pointer1, 0));
        Vector3 endP2 = camera.unproject(new Vector3(pointer2, 0));
        Vector3 initP = initP1.add(initP2).scl(0.5f);
        Vector3 endP = endP1.add(endP2).scl(0.5f);
        Vector3 realTranslateVector = initP.sub(endP);

        cameraLook.add(realTranslateVector);
        camera.translate(realTranslateVector);
        camera.lookAt(cameraLook);
        camera.update();

//        target = cameraLook;

        return true;
    }

    @Override
    public boolean keyDown (int keycode) {
        if (keycode == activateKey) activatePressed = true;
        if (keycode == forwardKey)
            forwardPressed = true;
        else if (keycode == backwardKey)
            backwardPressed = true;
        else if (keycode == rotateRightKey)
            rotateRightPressed = true;
        else if (keycode == rotateLeftKey) rotateLeftPressed = true;
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
        if (keycode == activateKey) {
            activatePressed = false;
            button = -1;
        }
        if (keycode == forwardKey)
            forwardPressed = false;
        else if (keycode == backwardKey)
            backwardPressed = false;
        else if (keycode == rotateRightKey)
            rotateRightPressed = false;
        else if (keycode == rotateLeftKey) rotateLeftPressed = false;
        return false;
    }

    public void setCameraData(Vector3 cameraPos,Vector3 cameraLook) {
        this.cameraLook = cameraLook;
        this.cameraPos = cameraPos;
    }

    //更新旋转中心
    public  void updateCenter(){
        Vector3 realTouchPoint0 = camera.unproject(new Vector3(touchPoint0.x, touchPoint0.y, 0));
        Vector3 realTouchPoint1 = camera.unproject(new Vector3(touchPoint1.x, touchPoint1.y, 0));
        target = realTouchPoint0.add(realTouchPoint1).scl(0.5f);
//        log("center:" + target.toString());
    }

    private static double getDistanceBetweenPoints(Vector2 p1, Vector2 p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    private static void log(String s) {
        Log.i("CameraController", s);
    }

}
