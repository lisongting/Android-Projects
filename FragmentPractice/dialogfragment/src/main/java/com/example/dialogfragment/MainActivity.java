package com.example.dialogfragment;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dialogfragment.loadingview.SmileView;

public class MainActivity extends AppCompatActivity {


    Button loadingButton;

    Button promptButton;
    Button start,end;
    SmileView smileView;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smileView = (SmileView) findViewById(R.id.smile_view);
        loadingButton = (Button) findViewById(R.id.button);
        loadingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialogFragment ldf = new LoadingDialogFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(ldf,"loadingFragment")
                        .commit();
            }
        });


        promptButton = (Button) findViewById(R.id.button2);
        promptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PromptDialogFragment adf = PromptDialogFragment.newInstance("this is AlertDialogFragment");
                adf.show(getFragmentManager(), "tag");

            }
        });
        start = (Button) findViewById(R.id.bt_start_anim);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smileView.startAnimation();
            }
        });
        end = (Button) findViewById(R.id.bt_end_anim);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smileView.endAnimation();
            }
        });

    }

    public void onDialogDone(String tag, boolean cancelled, CharSequence charSequence) {
        String s = tag + " response with" + charSequence;
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
