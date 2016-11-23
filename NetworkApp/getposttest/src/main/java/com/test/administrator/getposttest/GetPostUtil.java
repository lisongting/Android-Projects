package com.test.administrator.getposttest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/10/19.
 */
public class GetPostUtil {
    public static String sendGet(String url,String params){
        String result ="";
        BufferedReader in = null;
        try{
            //String urlName = url+"?"+params;
            //Log.d("tag",urlName);
            URL realUrl = new URL(url);
            Log.d("tag",url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; SV1)");
            conn.connect();//建立实际的连接
            Map<String,List<String>> map = conn.getHeaderFields();//获取所有的相应头字段
            for(String key:map.keySet()){
                Log.d("tag","key---->"+map.get(key));
            }
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line=in.readLine())!=null){
                result+="\n"+line;
            }
        }catch(Exception e){
            Log.d("tag","get请求出现异常"+e);
            e.printStackTrace();
        }finally{
            try{
               if(in!=null)
                   in.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url,String params){
        PrintWriter out =null;
        BufferedReader in =null;
        String result = "";
        try{
            String urlName = url+"?"+params;
            //String urlName = url;
            URL realUrl = new URL(urlName);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 10.0; SV1)");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //获取URL对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            out.print(params);
            //刷新输出流
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = in.readLine())!=null){
                result+="\n"+line;
            }
        }catch(Exception e){
            e.printStackTrace();
            Log.d("tag","post请求出现异常"+e);
        }finally{
            try{
                if(out!=null)
                    out.close();
                if(in!=null)
                    in.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }
}
