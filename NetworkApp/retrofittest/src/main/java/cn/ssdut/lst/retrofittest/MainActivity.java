package cn.ssdut.lst.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Api api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tcc.taobao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);
    }

    public void requestAPI(View view) {
        api.getPhoneInfo().enqueue(new Callback<PhoneInfo>(){
            public void onResponse(Call<PhoneInfo> call, Response<PhoneInfo> response){
                Toast.makeText(MainActivity.this, "PhoneInfo:"+response.body().getProvince(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "catName:"+response.body().getCatName(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, "Carrier:"+response.body().getCarrier(), Toast.LENGTH_SHORT).show();

            }
            public void onFailure(Call<PhoneInfo> call,Throwable t){

            }
        });
    }
}
