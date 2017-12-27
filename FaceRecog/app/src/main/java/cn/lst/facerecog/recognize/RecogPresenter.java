package cn.lst.facerecog.recognize;

import android.util.Log;

import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;

/**
 * Created by lisongting on 2017/12/14.
 */

public class RecogPresenter implements RecogContract.Presenter {

    private static final String TAG = "RecogPresenter";
    private RecogContract.View view ;
//    private RecognitionRetrofit api;
    private Retrofit retrofit;
    private Disposable disposable;

    public RecogPresenter(RecogContract.View view) {
        this.view = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void recognize(String strBitmap) {

//        HashMap<String, String> map = new HashMap<>();
//        map.put("Image", strBitmap);
//        Gson gson = new Gson();
//        String str = gson.toJson(map);
//
//        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), str);
//        api.recognizeFace(body)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<FaceRecogResult>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        disposable = d;
//                    }
//
//                    @Override
//                    public void onNext(FaceRecogResult faceRecogResult) {
//                        log("recognize Result:" + faceRecogResult);
//                        if (faceRecogResult.getRet() == 0&&
//                                faceRecogResult.getConfidence()>0.3) {
//                            view.showRecognitionSuccess(Util.hexStringToString(faceRecogResult.getId()));
//                        } else if (faceRecogResult.getRet() == 1000) {
//                            view.showRecognitionSuccess("未注册用户");
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        view.showError("请检查网络和服务器配置");
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


    }

    @Override
    public void start() {
//        StringBuilder baseUrl = new StringBuilder("http://");
//        baseUrl.append(Config.ROS_SERVER_IP).append(":").append(Config.RECOGNITION_SERVER_PORT).append("/");
//        retrofit = new Retrofit.Builder()
//                .baseUrl(baseUrl.toString())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        api = retrofit.create(RecognitionRetrofit.class);
    }

    public void destroy(){
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private void log(String s) {
        Log.i(TAG, s);
    }
}
