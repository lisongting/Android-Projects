package cn.ssdut.lst.buttonflyout;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private int[] imagesID = new int[]{R.id.id_iv_folder,R.id.id_iv_1,R.id.id_iv_2
            ,R.id.id_iv_3,R.id.id_iv_4,R.id.id_iv_5,R.id.id_iv_6};
    private List<ImageButton> imageButtons = new ArrayList<>();
    private boolean isOut = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for(int i=0;i<imagesID.length;i++){
            ImageButton btn = (ImageButton)findViewById(imagesID[i]);
            btn.setOnClickListener(this);
            imageButtons.add(btn);
        }
    }
    public void onClick(View v){
        switch(v.getId()){
            case R.id.id_iv_folder:
                if(isOut){
                    closeAnim();
                }else{
                    startAnim();
                }
                break;

            default:
                Toast.makeText(MainActivity.this,"点击了按钮的ID："+v.getId(),Toast.LENGTH_SHORT).show();
                break;

        }
    }
    private void closeAnim(){
        for(int i=1;i<imagesID.length;i++){
            ImageButton bt = imageButtons.get(i);
            ObjectAnimator anim = ObjectAnimator.ofFloat(bt,"translationY",(7-i)*150,0);
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(bt,"translationX",(i-1)*150,0);
            ObjectAnimator anim3 = ObjectAnimator.ofFloat(bt,"rotation",0,360);
            AnimatorSet set = new AnimatorSet();

            set.play(anim).with(anim2).with(anim3);
            set.setDuration(i*200);
            set.start();
        }
        isOut = false;
    }
    private void startAnim(){
        for(int i=1;i<imagesID.length;i++){
            ImageButton bt = imageButtons.get(i);
            ObjectAnimator anim = ObjectAnimator.ofFloat(bt,"translationY",0,(7-i)*150);
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(bt,"translationX",0,(i-1)*150);
            ObjectAnimator anim3 = ObjectAnimator.ofFloat(bt,"rotationY",0,360);
            AnimatorSet set = new AnimatorSet();
            set.setInterpolator(new AccelerateDecelerateInterpolator());
            set.play(anim).with(anim2).with(anim3);
            set.setDuration(i*200);
            set.start();
        }
        isOut = true;
    }
}
