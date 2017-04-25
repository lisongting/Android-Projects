package cn.ssdut.lst.lottietest;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView lottie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lottie = (LottieAnimationView) findViewById(R.id.animation_view1);

        lottie.setAnimation("test2.json");
        lottie.loop(true);
        lottie.playAnimation();
        lottie.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i("tag", "animation start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("tag", "animation end");
                //Toast.makeText(MainActivity.this, "finish", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.i("tag", "animation repeat");
            }
        });
    }

    public void onClick(View v) {
        startActivity(new Intent(this, LoadingSampleActivity.class));
    }

    @Override
    protected void onPause() {
        lottie.pauseAnimation();
        super.onPause();
    }

    @Override
    protected void onResume() {
        lottie.playAnimation();
        super.onResume();
    }
}
