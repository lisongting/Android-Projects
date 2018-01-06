package cn.lst.facerecog.recognize;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.lst.facerecog.Constant;
import cn.lst.facerecog.FacePlusApi;
import cn.lst.facerecog.Util;
import cn.lst.facerecog.YouTuApi;
import cn.lst.facerecog.entity.ResponseRecogFace;
import cn.lst.facerecog.entity.SearchFaceResponse;
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
 * Created by lisongting on 2017/12/14.
 */

public class RecogPresenter implements RecogContract.Presenter {

    private static final String TAG = "RecogPresenter";
    private RecogContract.View view ;
//    private RecognitionRetrofit api;
    private Retrofit retrofit;
    private Disposable disposable;
    private YouTuApi api;
    private FacePlusApi facePlusApi;
    private StringBuffer authorizationBuffer;
    private String MEDIA_TYPE = "application/json;charset=UTF-8";

    public RecogPresenter(RecogContract.View view) {
        this.view = view;
        view.setPresenter(this);
        start();
    }

    @Override
    public void youtuRecognize(String strBitmap) {
        JSONObject json = new JSONObject();
        String jsonStr ;
        try {
            json.put("app_id", Constant.YOUTU_APP_ID);
//            JSONArray array = new JSONArray();
            json.put("group_id", "XbotUserGroup");
//            array.put(strBitmap);
            json.put("image", strBitmap);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonStr = json.toString();
        RequestBody requestBody = RequestBody.create(MediaType.parse(MEDIA_TYPE), jsonStr);
        api.recognizeFace(jsonStr.getBytes().length, authorizationBuffer.toString(), requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseRecogFace>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseRecogFace responseRecogFace) {
                        log(responseRecogFace.toString());
                        if (responseRecogFace.getErrorcode() == 0) {
                            String personId = responseRecogFace.getCandidates().get(0).getPerson_id();
                            view.showRecognitionSuccess("识别成功："+Util.hexStringToString(personId));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showError("识别失败："+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public  void facePlusRecognize(String strBitmap) {
        //Face++的人脸识别实际上是在一个组内进行人脸查找，找到最相似的
        String faceSetToken = "8c2414aa04e213319c605be206f273d9";
        facePlusApi.searchFace(Constant.FACEPLUS_API_KEY,
                Constant.FACEPLUS_API_SECRET,
                faceSetToken,
                strBitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchFaceResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SearchFaceResponse responseBody) {
                        log(responseBody.toString());
                        view.showRecognitionSuccess("识别成功\nToken:" + responseBody.getResults().get(0).getFace_token());
                    }

                    @Override
                    public void onError(Throwable e) {
                        log(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void start() {
//        retrofit = new Retrofit.Builder()
//                .baseUrl(Constant.YOUTU_BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        api = retrofit.create(YouTuApi.class);
//        authorizationBuffer = new StringBuffer();
//        YoutuSign.appSign(Constant.YOUTU_APP_ID,
//                Constant.YOUTU_SECRET_ID, Constant.YOUTU_SECRET_KEY,
//                System.currentTimeMillis()/1000 + Constant.YOUTU_EXPIRED_SECONDS,
//                "", authorizationBuffer);

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.FACEPLUS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        facePlusApi = retrofit.create(FacePlusApi.class);
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
