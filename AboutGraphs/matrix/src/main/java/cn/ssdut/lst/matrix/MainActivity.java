package cn.ssdut.lst.matrix;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isScale = false;//缩放还是旋转
    private float sx = 0.0f;//设置倾斜度
    //缩放比例
    private float scale = 1.0f;
    private MyView myview;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myview = new MyView(this);
        setContentView(R.layout.activity_main);
        Button bt1,bt2,bt3,bt4;
        bt1 = (Button)findViewById(R.id.leftSkew);
        bt2 = (Button)findViewById(R.id.rightSkew);
        bt3 = (Button)findViewById(R.id.zoomIn)  ;
        bt4 = (Button)findViewById(R.id.zoomOut);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.addView(myview);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.leftSkew:
                isScale =false;
                sx +=0.1;
                myview.invalidate();
                break;
            case R.id.rightSkew:
                isScale = false;
                sx-=0.1;
                myview.invalidate();
                break;
            case R.id.zoomIn:
                isScale = true;
                if(scale<2.0)//最大只能放大到2
                    scale+=0.1;
                myview.invalidate();
                break;
            case R.id.zoomOut:
                isScale = true;
                if(scale>0.5)//最小只能缩小到0.5
                    scale -=.01;
                myview.invalidate();
                break;
        }
    }
    @TargetApi(19)
    public class MyView extends View {
        private Bitmap bitmap;
        private Matrix matrix = new Matrix();
        private int width,height;
        public MyView(Context context) {
            super(context);
            bitmap = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.pic)).getBitmap();//获取drawable中的图片文件
            //bitmap.setWidth(bitmap.getWidth());
            width = bitmap.getWidth();//获取位图宽度
            height = bitmap.getHeight();//获取位图高度
            //令当前视图获得焦点
            this.setFocusable(true);
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            matrix.reset();//重置matrix
            if (!isScale) {
                matrix.setSkew(sx, 0);//倾斜
            }else{//缩放
                matrix.setScale(scale,scale);
            }
            //根据原始位图和matrix创建新的图片
            Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            canvas.drawBitmap(bitmap2, matrix, null);//绘制新的图片
        }

    }
}
