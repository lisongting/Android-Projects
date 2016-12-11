package cn.ssdut.lst.contactsreadertest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/8.
 */
public class MyIntentService extends IntentService {

    private static String SERV_ADDR = "192.168.17.17";
    private static int PORT = 30000;
    private List<Map<String,String>> contactsList;
    private JSONArray jsonArray;
    public MyIntentService(){
        super("MyIntentService");
    }
    public void onHandleIntent(Intent intent) {
        Log.d("tag","------MyIntentService----onHandleIntent()");
        contactsList = (List<Map<String,String>>)intent.getSerializableExtra("list");
        try{
            Socket socket = new Socket(SERV_ADDR,PORT);
            sendJsonWrapContacts(socket);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sendJsonWrapContacts(Socket socket){
        jsonArray = new JSONArray();
        int count= contactsList.size();
        //这个for循环，将contactList中的所有联系人和电话封装到JSONArray里面
        Log.d("tag","count"+count);
        String name,phoneNumber;
        for(int i=0;i<count;i++) {
            Map<String,String> tmpMap ;
            tmpMap = contactsList.get(i);
            name = tmpMap.get("name");
            phoneNumber = tmpMap.get("phone");
            JSONObject jsonObj = new JSONObject();
            try{
                jsonObj.put("name",name);
                jsonObj.put("phone",phoneNumber);
                jsonArray.put(jsonObj);
            }catch(Exception e){
                e.printStackTrace();
            }
            //构造jsonArray对象：由许多的jsonObject组成
        }
        String jsonString = jsonArray.toString();
        Log.d("tag",""+jsonString);
        try{
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            Log.d("tag","发送的jsonString长度为："+jsonString.length());
            PrintWriter writer = new PrintWriter(outputStream);
            writer.write(jsonString);
            writer.flush();
            Log.d("tag","发送完毕");
            socket.shutdownOutput();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
