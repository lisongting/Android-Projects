package cn.lst.facerecog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.lst.facerecog.recognize.RecogActivity;
import cn.lst.facerecog.register.RegisterFragment;

public class MainActivity extends AppCompatActivity {

    private Button b1, b2;
    private RegisterFragment registerFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerFragment = new RegisterFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(registerFragment, "registerFragment")
                        .commit();
            }
        });

        findViewById(R.id.recognize).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecogActivity.class));
            }
        });
    }
}
