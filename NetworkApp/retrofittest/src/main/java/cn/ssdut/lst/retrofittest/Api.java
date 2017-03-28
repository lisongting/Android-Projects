package cn.ssdut.lst.retrofittest;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/3/27.
 */

public interface Api {
    @GET("cc/json/mobile_tel_segment.htm?tel=13188886666")
    Call<PhoneInfo> getPhoneInfo();
}
