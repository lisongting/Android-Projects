package cn.ssdut.lst.okhttptest;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lisongting on 2017/9/3.
 *
 *
 */

public class ReadSourceSnippet {

    //Get请求
   public String get(String url) throws IOException {

       //新建OKHttpClient
       OkHttpClient client = new OkHttpClient();

       Request request = new Request.Builder()
               .url(url)
               .get()
               .build();

       Response response = client.newCall(request).execute();

       if (response.isSuccessful()) {
           return response.body().string();
       }else{
           throw new IOException("unexpected Code:" + response.code());
       }
   }
}
