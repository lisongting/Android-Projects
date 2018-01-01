package cn.lst.facerecog;

import cn.lst.facerecog.entity.ResponseAddFace;
import cn.lst.facerecog.entity.ResponseCreatePerson;
import cn.lst.facerecog.entity.ResponseDeleteFace;
import cn.lst.facerecog.entity.ResponseDeletePerson;
import cn.lst.facerecog.entity.ResponseQueryPersonInfo;
import cn.lst.facerecog.entity.ResponseRecogFace;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by lisongting on 2017/12/27.
 * 优图在线人脸识别API
 */

public interface YouTuApi {

    @POST("newperson")
    @Headers({"Host: api.youtu.qq.com",
            "Content-Type: text/json"})
    Observable<ResponseCreatePerson> createPerson(@Header("Content-Length") int length,
                                                  @Header("Authorization") String authorization,
                                                  @Body RequestBody requestBody);
    //RequestBody.create(MediaType.parse("Content-Type,application/json",json);


    @POST("addface")
    @Headers({"Host: api.youtu.qq.com",
            "Content-Type: text/json"})
    Observable<ResponseAddFace> addFace(@Header("Content-Length") int length,
                                        @Header("Authorization") String authorization,
                                        @Body RequestBody requestBody);

    @POST("delperson")
    @Headers({"Host: api.youtu.qq.com",
            "Content-Type: text/json"})
    Observable<ResponseDeletePerson> deletePerson(@Header("Content-Length") int length,
                                                  @Header("Authorization") String authorization,
                                                  @Body RequestBody requestBody);

    @POST("delface")
    @Headers({"Host: api.youtu.qq.com",
            "Content-Type: text/json"})
    Observable<ResponseDeleteFace> deleteFace(@Header("Content-Length") int length,
                                              @Header("Authorization") String authorization,
                                              @Body RequestBody requestBody);

    @POST("faceidentify")
    @Headers({"Host: api.youtu.qq.com",
            "Content-Type: text/json"})
    Observable<ResponseRecogFace> recognizeFace(@Header("Content-Length") int length,
                                             @Header("Authorization") String authorization,
                                             @Body RequestBody requestBody);

    @POST("getinfo")
    @Headers({"Host: api.youtu.qq.com",
            "Content-Type: text/json"})
    Observable<ResponseQueryPersonInfo> queryPersonInfo(@Header("Content-Length") int length,
                                                        @Header("Authorization") String authorization,
                                                        @Body RequestBody requestBody);
}
