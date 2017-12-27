package cn.lst.facerecog.register;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.lst.facerecog.Constant;
import cn.lst.facerecog.Util;
import cn.lst.facerecog.YouTuApi;
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
    private YoutuSign youtuSign;
    private StringBuffer authorizationBuffer;
    private String MEDIA_TYPE = "application/json;charset=UTF-8";

    public RegisterPresenter(RegisterContract.View v){
        this.view = v;
        v.setPresenter(this);
        start();
    }

    @Override
    public void start() {
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


    }

    @Override
    public void register(final String userId, final String strBitmap) {
        JSONObject json = new JSONObject();
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

        //先创建用户，然后再添加人脸


//        api.registerFace(requestBody)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<UserRegisterResult>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                    }
//
//                    @Override
//                    public void onNext(UserRegisterResult userRegisterResult) {
//                        int ret = userRegisterResult.getRet();
//                        if (ret == 0) {
//                            view.showSuccess();
//                        } else if (ret == 11) {
//                            view.showInfo("注册失败:图片尺寸过大");
//                        } else if (ret == 9) {
//                            view.showInfo("注册失败:未检测到人脸");
//                        }else {
//                            view.showInfo("注册失败，错误码：" + ret);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.showInfo("注册失败，请检查网络和服务器配置");
//                    }
//
//                    @Override
//                    public void onComplete() {
//                    }
//                });
    }

    private void log(String s) {
        Log.i(TAG, s);
    }

}
