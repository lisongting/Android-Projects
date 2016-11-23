package cn.ssdut.lst.telephonystatus;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView showView;
    String[] statusName;//声明代表状态名的数组
    ArrayList<String> statusValues = new ArrayList<>();//声明代表手机状态的集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showView =(ListView)findViewById(R.id.listView);
        //获取系统的TelephonyManager对象
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);//
        //获取各种状态名称的数组
        statusName = getResources().getStringArray(R.array.statusNames);
        String[] simState = getResources().getStringArray(R.array.simState);
        //获取代表电话网络类型的数组
        String[] phoneType = getResources().getStringArray(R.array.phoneType);
        //获取一个String类型的设备编号
        statusValues.add(tManager.getDeviceId());
        //获取系统平台的版本
        statusValues.add(tManager.getDeviceSoftwareVersion()!=null?tManager.getDeviceSoftwareVersion():"未知");
        //获取网络运营商代号
        statusValues.add(tManager.getNetworkOperator());
        //获取网络运营商名称
        statusValues.add(tManager.getNetworkOperatorName());
        //获取手机网络类型
        statusValues.add(phoneType[tManager.getPhoneType()]);
        //获取设备所在位置
        statusValues.add(tManager.getCellLocation() != null ? tManager.getCellLocation().toString() : "未知");
        //获取sim卡国别
        statusValues.add(tManager.getSimCountryIso());
        //获取sim卡序列号
        statusValues.add(tManager.getSimSerialNumber());
        //获取sim卡状态
        statusValues.add(simState[tManager.getSimState()]);
        ArrayList<Map<String,String>> items = new ArrayList<>();
        for(int i=0;i<statusValues.size();i++) {
            Map<String, String> item = new HashMap<>();
            item.put("name",statusName[i]);
            item.put("value",statusValues.get(i));
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this,items,R.layout.line,new String[]{"name","value"},new int[]{R.id.name,R.id.value});
        showView.setAdapter(adapter);
    }
}
