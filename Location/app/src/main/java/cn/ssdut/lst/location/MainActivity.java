package cn.ssdut.lst.location;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
    Button bt_gps,bt_network;
    TextView show;
    @Override
    @TargetApi(23)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_gps = (Button) findViewById(R.id.bt_gps);
        bt_network= (Button) findViewById(R.id.bt_network);
        show = (TextView) findViewById(R.id.tv_show);
        bt_gps.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                LocationManager locManager =
                        (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                LocationListener locListener ;
                locListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        Log.d("tag","位置变化了");
                        //位置信息变化时触发
                        StringBuilder sb = new StringBuilder();
                        sb.append("位置信息来源："+location.getProvider()+"\n");
                        sb.append("经度：" + location.getLatitude()+"\n");
                        sb.append("纬度：" + location.getLongitude()+"\n");
                        sb.append("海拔：" + location.getAltitude()+"\n");
                        sb.append("精度(米)：" + location.getAccuracy()+"\n");
                        show.setText(sb.toString());
                    }
                    public void onStatusChanged(String provider,
                                                int status, Bundle extras) {
                        //gps状态变化时触发
                        Log.d("tag","GPS状态变化了");
                    }
                    public void onProviderEnabled(String provider) {
                        //GPS启用时触发
                        Log.d("tag", "GPS已启用");
                    }
                    public void onProviderDisabled(String provider) {
                        //GPS被禁用时触发
                        Log.d("tag", "GPS已禁用");
                    }
                };
                if(PackageManager.PERMISSION_GRANTED==
                        checkSelfPermission("android.permission.ACCESS_FINE_LOCATION")){
                    /**
                     * 给LocationManager绑定监听
                     * 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种，
                     *      前者是GPS,后者是GPRS以及WIFI定位
                     * 参数2，位置信息更新周期.单位是毫秒
                     * 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
                     * 参数4，LocationListener监听器
                     * 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过
                     * 时间来定时更新；两者为0，则随时刷新
                     */
                    locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            2000,0,locListener);
                }
            }
        });
        bt_network.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                LocationManager locManager =
                        (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                LocationListener locListener ;
                locListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //位置信息变化时触发
                        StringBuilder sb = new StringBuilder();
                        sb.append("位置信息来源："+location.getProvider()+"\n");
                        sb.append("经度：" + location.getLatitude()+"\n");
                        sb.append("纬度：" + location.getLongitude()+"\n");
                        sb.append("海拔：" + location.getAltitude()+"\n");
                        sb.append("精度(米)：" + location.getAccuracy()+"\n");
                        show.setText(sb.toString());
                    }
                    public void onStatusChanged(String provider,
                                                int status, Bundle extras) {
                        //gps状态变化时触发
                        Log.d("tag","GPS状态变化");
                    }
                    public void onProviderEnabled(String provider) {
                        //GPS启用时触发
                        Log.d("tag", "GPS已启用");
                    }
                    public void onProviderDisabled(String provider) {
                        //GPS被禁用时触发
                        Log.d("tag", "GPS已禁用");
                    }
                };
                if(PackageManager.PERMISSION_GRANTED==
                        checkSelfPermission("android.permission.INTERNET"))
                locManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,2000,0,locListener);
            }
        });
    }
}
