package cn.lst.facerecog;

import cn.lst.facerecog.entity.AddFaceResponse;
import cn.lst.facerecog.entity.DetectFaceResponse;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lisongting on 2017/12/29.
 * 旷视Face++ 人脸识别API
 */

public interface FacePlusApi {


    @FormUrlEncoded
    @POST("detect")
    Observable<DetectFaceResponse> detectFace(@Field("api_key") String apiKey,
                                    @Field("api_secret")String apiSecret,
                                    @Field("image_base64")String base64);

    @POST("faceset/create")
    Observable<ResponseBody> createFaceSet(@Body RequestBody requestBody);

    @FormUrlEncoded
    @POST("faceset/addface")
    Observable<AddFaceResponse> addFace(@Field("api_key") String apiKey,
                                        @Field("api_secret")String apiSecret,
                                        @Field("faceset_token")String facesetToken,
                                        @Field("face_tokens")String faceToken);

    @FormUrlEncoded
    @POST("search")
    Observable<ResponseBody> searchFace(@Field("api_key") String apiKey,
                                        @Field("api_secret")String apiSecret,
                                        @Field("faceset_token")String facesetToken,
                                        @Field("image_base64")String base64);


}
