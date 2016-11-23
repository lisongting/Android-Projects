package com.ssdut.lst.namemaker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.wanpu.pay.PayConnect;
import com.wanpu.pay.PayResultListener;

import java.util.List;


/**
 * Created by Administrator on 2016/10/26.
 */
public class PayClass {

    Context context;
    // 应用或游戏商自定义的支付订单(每条支付订单数据不可相同)
    String orderId = "";
    // 用户标识
    String userId = "";
    // 支付商品名称
    String goodsName = "英文名推荐";
    // 支付金额
    float price = 1.0f;
    // 支付时间
    String time = "";
    // 支付描述
    String goodsDesc = "根据您的个性喜好，生成一个好听而有意义的英文名。";
    boolean PAY_SUCCESS = false;
    // 应用或游戏商服务器端回调接口（无服务器可不填写）
    String notifyUrl = "";
    Maker maker;
    String sex;
    int[] array;
    SQLiteDatabase db;
    String input_name;
    public PayClass(Context c,Maker m,String se,int[] arr,SQLiteDatabase sqd,String input)
    {
        this.context=c;maker=m;sex = se;
        array = arr; db = sqd;input_name=input;
    }
    public boolean run(){
        // 初始化统计器(必须调用)
        PayConnect.getInstance("2d928fda202a40fe1ac34ea311e80874", "WAPS", context);

        userId = PayConnect.getInstance(context).getDeviceId(
                context);
        try {
            // // 游戏商自定义支付订单号，保证该订单号的唯一性，建议在执行支付操作时才进行该订单号的生成
            orderId = System.currentTimeMillis() + "";

            PayConnect.getInstance(context).pay(context, orderId, userId, price,
                    goodsName, goodsDesc, notifyUrl,new MyPayResultListener());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return PAY_SUCCESS;
    }



    private class MyPayResultListener implements PayResultListener {

        @Override
        public void onPayFinish(Context payViewContext, String orderId,
                                int resultCode, String resultString, int payType, float amount,
                                String goodsName) {
            // 可根据resultCode自行判断
            if (resultCode == 0) {
                Toast.makeText(context,
                        resultString + "：" + amount + "元", Toast.LENGTH_LONG)
                        .show();
                // 支付成功时关闭当前支付界面
                PayConnect.getInstance(context).closePayView(payViewContext);
                PAY_SUCCESS=true;//并把PAY_SUCCESS置为true
                // TODO 在客户端处理支付成功的操作
                show();
                Toast.makeText(context,"支付成功",Toast.LENGTH_SHORT).show();
                // 未指定notifyUrl的情况下，交易成功后，必须发送回执
                PayConnect.getInstance(context).confirm(orderId,payType);
            } else {
                Toast.makeText(context, resultString,Toast.LENGTH_LONG).show();
            }
        }
    }
    public  void show(){
        List<Integer> result = null;
        int dest_name_position = 0 ;
        result = maker.findName(sex,array);//根据array数组去调用findName方法
        //Toast.makeText(MainActivity.this,"result大小"+result.size(),Toast.LENGTH_SHORT).show();
        dest_name_position = result.get(0);//结果集的大小对拼音长度取余
        //以上步骤获取到的信息:用户名字拼音的长度,喜好数组,
        //接下来进行查询
        String sql = "select *from names where gender = ?and key = ?";
        Cursor cursor = db.rawQuery(sql,new String[]{sex, String.valueOf(dest_name_position)});
        String str_Cname = input_name;
        String str_Ename="";
        String str_detail="";
        while(cursor.moveToNext()){
            str_Ename= cursor.getString(2);
            str_detail= cursor.getString(3);
            if(str_Ename!=""){
                break;
            }
        }
        //Toast.makeText(MainActivity.this,"英文名位置"+dest_name_position,Toast.LENGTH_SHORT).show();
        // Toast.makeText(MainActivity.this,"str数组"+str[599][2],Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("找到了一个适合您的英文名：")
                .setMessage("根据您的中文名："+str_Cname+"\n为您推荐英文名："+str_Ename+"\n释义："+str_detail)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this,"确定",Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }

}
