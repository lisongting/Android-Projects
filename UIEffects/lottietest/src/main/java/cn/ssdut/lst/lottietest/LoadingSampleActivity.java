package cn.ssdut.lst.lottietest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

/**
 * Created by lisongting on 2017/4/25.
 */

public class LoadingSampleActivity extends Activity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.loading_layout);

        final LottieAnimationView animationView = (LottieAnimationView) findViewById(R.id.pinJump);

        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationView.isAnimating()) {
                    animationView.cancelAnimation();
                } else {
                    animationView.playAnimation();
                }
            }
        });
    }


}
