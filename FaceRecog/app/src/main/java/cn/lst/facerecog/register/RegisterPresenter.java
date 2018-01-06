package cn.lst.facerecog.register;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.lst.facerecog.Constant;
import cn.lst.facerecog.FacePlusApi;
import cn.lst.facerecog.Util;
import cn.lst.facerecog.YouTuApi;
import cn.lst.facerecog.entity.AddFaceResponse;
import cn.lst.facerecog.entity.DetectFaceResponse;
import cn.lst.facerecog.entity.ResponseAddFace;
import cn.lst.facerecog.entity.ResponseCreatePerson;
import cn.lst.facerecog.sign.YoutuSign;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lisongting on 2017/12/13.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private static final String TAG = "RegisterPresenter";
    private Retrofit retrofit;
//    private RecognitionRetrofit api;
    private RegisterContract.View view;
    private YouTuApi api;
    private FacePlusApi facePlusApi;
    private StringBuffer authorizationBuffer;
    private String MEDIA_TYPE = "application/json;charset=UTF-8";

    public RegisterPresenter(RegisterContract.View v){
        this.view = v;
        v.setPresenter(this);
        start();
    }

    @Override
    public void start() {
        //这是优图的
        authorizationBuffer = new StringBuffer();
        int ret = YoutuSign.appSign(Constant.YOUTU_APP_ID,
                Constant.YOUTU_SECRET_ID, Constant.YOUTU_SECRET_KEY,
                System.currentTimeMillis()/1000 + Constant.YOUTU_EXPIRED_SECONDS,
                "", authorizationBuffer);
        if (ret == 0) {
            log("generate Youtu authorization string success :"+authorizationBuffer.toString());
        } else {
            Log.e(TAG, "generate Youtu authorization string error");
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.YOUTU_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(YouTuApi.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.FACEPLUS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        facePlusApi = retrofit.create(FacePlusApi.class);
    }

    @Override
    public void youtuRegister(final String userId, final String strBitmap) {
        final JSONObject json = new JSONObject();
        try {
            json.put("app_id", Constant.YOUTU_APP_ID);
            JSONArray a = new JSONArray();
            a.put("XbotUserGroup");
            json.put("person_id", userId);
            json.put("person_name", Util.hexStringToString(userId));
            json.put("group_ids", a);
            json.put("image", strBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String strBody = json.toString();
        final RequestBody requestBody = RequestBody.create(MediaType.parse(MEDIA_TYPE), strBody);

        api.createPerson(strBody.getBytes().length, authorizationBuffer.toString(), requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseCreatePerson>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseCreatePerson responseCreatePerson) {
                        log(responseCreatePerson.toString());
                        int code = responseCreatePerson.getErrorcode();
                        if (code  == 0) {
                            view.showInfo("注册成功");
                        } else if (code == -1302) {
                            //个体已存在，则调用addFace，添加人脸
                            JSONObject obj = new JSONObject();
                            try {
                                obj.put("app_id", Constant.YOUTU_APP_ID);
                                obj.put("person_id", userId);
                                JSONArray array = new JSONArray();
                                array.put(strBitmap);
                                obj.put("images", array);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String str = obj.toString();
                            final RequestBody request = RequestBody.create(MediaType.parse(MEDIA_TYPE), str);
                            api.addFace(str.getBytes().length, authorizationBuffer.toString(), request)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<ResponseAddFace>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {

                                        }

                                        @Override
                                        public void onNext(ResponseAddFace responseAddFace) {
                                            log(responseAddFace.toString());
                                            if (responseAddFace.getErrorcode() == 0) {
                                                view.showInfo("注册成功");
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            view.showInfo("注册失败：" + e.getMessage());
                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        } else {
                            view.showInfo("注册失败，错误码：" + code);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showInfo("注册失败 " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    @Override
    public void facePlusRegister(final String userId, final String strBitmap) {
        //创建人脸集合,创建好会得到一个facesettoken
        //如facsettoken : 7afefd59b5afe54fb0f79f0f7487f4eb

        //床脚好人脸集合后，检测人脸
        //检测人脸会得到一个FaceToken
        try {
            facePlusApi.detectFace(Constant.FACEPLUS_API_KEY, Constant.FACEPLUS_API_SECRET,strBitmap)
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<DetectFaceResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(DetectFaceResponse detectFaceResponse) {
                            log(detectFaceResponse.toString());
                            if (detectFaceResponse.getFaces().size() != 0) {
                                try {
                                    final String faceToken = detectFaceResponse.getFaces().get(0).getFace_token();
                                    //这一步添加人脸得到face_token，类似于这样的：a4b0f89e3f48c0e021d75095ac21dc81
                                    facePlusApi.addFace(Constant.FACEPLUS_API_KEY,
                                            Constant.FACEPLUS_API_SECRET,"8c2414aa04e213319c605be206f273d9",
                                            faceToken)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Observer<AddFaceResponse>() {
                                                @Override
                                                public void onSubscribe(Disposable d) {

                                                }

                                                @Override
                                                public void onNext(AddFaceResponse addFaceResponse) {
                                                    log(addFaceResponse.toString());
                                                    if (addFaceResponse.getFace_count() == 1) {
//                                                        view.showSuccess();
                                                        view.showInfo("注册成功。人脸Token:"+faceToken);
                                                    }

                                                }

                                                @Override
                                                public void onError(Throwable e) {
                                                    log("onError:" + e.getMessage());

                                                }

                                                @Override
                                                public void onComplete() {

                                                }
                                            });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            log("onError:" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

//        facePlusApi.detectFace(requestBody)
//                .subscribeOn(Schedulers.io())
//                .flatMap(new Function<DetectFaceResponse, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(DetectFaceResponse detectFaceResponse) throws Exception {
//                        return null;
//                    }
//                })
//                .subscribe(new Consumer<DetectFaceResponse>() {
//                    @Override
//                    public void accept(DetectFaceResponse detectFaceResponse) throws Exception {
//                        if (detectFaceResponse.getFaces().size() != 0) {
//
//                        }
//
//                    }
//                });


    }


    private void log(String s) {
        Log.i(TAG, s);
    }

}
