package cn.ssdut.lst.matrix;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 测试图片使用Matrix变换
 * 测试Bitmap以位图形式操作和手势缩放，旋转
 * 测试Base64解码图片
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private boolean isScale = false;//缩放还是旋转
    private float sx = 0.0f;//设置倾斜度
    //缩放比例
    private float scale = 1.0f;
    private MyView myview;
    LinearLayout linearLayout;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt1,bt2,bt3,bt4,bt5,bt6;
        bt1 = (Button)findViewById(R.id.leftSkew);
        bt2 = (Button)findViewById(R.id.rightSkew);
        bt3 = (Button)findViewById(R.id.zoomIn)  ;
        bt4 = (Button)findViewById(R.id.zoomOut);
        bt5 = (Button)findViewById(R.id.openTextureview);
        bt6 = (Button) findViewById(R.id.base64);
        imageView = (ImageView) findViewById(R.id.imageView);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        myview = new MyView(this);
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
                    scale -=.02;
                myview.invalidate();
                break;
            case R.id.openTextureview:
                startActivity(new Intent(this, MapViewTestActivity.class));
                break;
            case R.id.base64:
                startActivity(new Intent(this, DecodeActivity.class));
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = ImageUtils.decodeBase64ToBitmap(base64PNG);
                Log.i("tag", "Base64 string decoded Bitmap:" + bitmap.getWidth() + "X" + bitmap.getHeight());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }).start();
    }

    @TargetApi(21)
    public class MyView extends View {
        private Bitmap bitmap;
        private Matrix matrix = new Matrix();
        private int width,height;
        public MyView(Context context) {
            super(context);
            //获取drawable中的图片文件
            bitmap = ((BitmapDrawable)context.getResources()
                    .getDrawable(R.drawable.pic,null)).getBitmap();
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

    //测试base64编码。解码图片
    //这个是png格式的，最后解出来为496*496
    public static String base64PNG = "iVBORw0KGgoAAAANSUhEUgAAA+AAAAPgCAIAAAA9RYEqAAAbtUlEQVR4AezBC5LkVBIEwIj7HzrX\n" +
            "FgODge7p+kglPcndm2QCAACcQvOHCQAAcLzmLxMAAOBgzT9MAACAIzW/mgAAAIdp/mMCAAAco/nK\n" +
            "BAAAOEDzjQkAAPBpzfcmAADARzW/NQEAAD6n+ckEAAD4kOYBEwAA4BOax0wAAIDdNQ+bAAAA+2qe\n" +
            "MQEAAHbUPGkCAADspXneBAAA2EXzkgkAALC95lUTAABgY80bJgAAwJaa90wAAIDNNG+bAAAA22i2\n" +
            "MAEAADbQbGQCAAC8q9nOBAAAeEuzqQkAAPC6ZmsTAADgRc0OJgAAwCuafUwAAICnNbuZAAAAz2n2\n" +
            "NAEAAJ7Q7GwCAAA8qtnfBAAAeEjzERMAAOBnzadMAACAHzQfNAEAAH6n+awJAADwrebjJgAAwNea\n" +
            "I0wAAIAvNAeZAAAA/9YcZwIAAPyiOdQEAAD4W3O0CQAA8KfmBCYAAMD/NecwAQAA0pzGBAAA7q45\n" +
            "kwkAANxaczITAAC4r+Z8JgAAcFPNKU0AAOCOmrOaAADA7TQnNgEAgHtpzm0CAAA30pzeBAAA7qJZ\n" +
            "wQQAAG6hWcQEAACur1nHBAAALq5ZygQAAK6sWc0EAAAuq1nQBAAArqlZ0wQAAC6oWdYEAACuplnZ\n" +
            "BAAALqVZ3AQAAK6jWd8EAAAuormECQAAXEFzFRMAAFhecyETAABYW3MtEwAAWFhzORMAAFhVc0UT\n" +
            "AABYUnNREwAAWE9zXRMAAFhMc2kTAABYSXN1EwAAWEZzAxMAAFhDcw8TAABYQHMbEwAAOLvmTiYA\n" +
            "AHBqzc1MAADgvJr7mQAAwEk1tzQBAIAzau5qAgAAp9Pc2AQAAM6lubcJAACcSHN7EwAAOIuGZAIA\n" +
            "AKfQ8IcJAAAcr+EvEwAAOFjDP0wAAOBIDb+aAADAYRr+YwIAAMdo+MoEAAAO0PCNCQAAfFrD9yYA\n" +
            "APBRDb81AQCAz2n4yQQAAD6k4QETAAD4hIbHTAAAYHcND5sAAMC+Gp4xAQCAHTU8aQIAAHtpeN4E\n" +
            "AAB20fCSCQAAbK/hVRMAANhYwxsmAACwpYb3TAAAYDMNb5sAAMA2GrYwAQCADTRsZAIAAO9q2M4E\n" +
            "AADe0rCpCQAAvK5haxMAAHhRww4mAADwioZ9TAAA4GkNu5kAAMBzGvY0AQCAJzTsbAIAAI9q2N8E\n" +
            "AAAe0vAREwAA+FnDp0wAAOAHDR80AQCA32n4rAkAAHyr4eMmAADwtYYjTAAA4AsNB5kAAMC/NRxn\n" +
            "AgAAv2g41AQAAP7WcLQJAAD8qeEEJgAA8H8N5zABAIA0nMYEAIC7aziTCQAAt9ZwMhMAAO6r4Xwm\n" +
            "AADcVMMpTQAAuKOGs5oAAHA7DSc2AQDgXhrObQIAwI00nN4EAIC7aFjBBACAW2hYxAQAgOtrWMcE\n" +
            "AICLa1jKBACAK2tYzQQAgMtqWNAEAIBraljTBACAC2pY1gQAgKtpWNkEAIBLaVjcBACA62hY3wQA\n" +
            "gItouIQJAABX0HAVEwAAltdwIRMAANbWcC0TAAAW1nA5EwAAVtVwRRMAAJbUcFETAADW03BdEwAA\n" +
            "FtNwaRMAAFbScHUTAACW0XADEwAA1tBwDxMAABbQcBsTAADOruFOJgAAnFrDzUwAADivhvuZAABw\n" +
            "Ug23NAEA4Iwa7moCAMDpNNzYBACAc2m4twkAACfScHsTAADOooFkAgDAKTTwhwkAAMdr4C8TAAAO\n" +
            "1sA/TAAAOFIDv5oAAHCYBv5jAgDAMRr4ygQAgAM08I0JAACf1sD3JgAAfFQDvzUBAOBzGvjJBACA\n" +
            "D2ngARMAAD6hgcdMAADYXQMPmwAAsK8GnjEBAGBHDTxpAgDAXhp43gQAgF008JIJAADba+BVEwAA\n" +
            "NtbAGyYAAGypgfdMAADYTANvmwAAsI0GtjABAGADDWxkAgDAuxrYzgQAgLc0sKkJAACva2BrEwAA\n" +
            "XtTADiYAALyigX1MAAB4WgO7mQAA8JwG9jQBAOAJDexsAgDAoxrY3wQAgIc08BETAAB+1sCnTAAA\n" +
            "+EEDHzQBAOB3GvisCQAA32rg4yYAAHytgSNMAAD4QgMHmQAA8G8NHGcCAMAvGjjUBACAvzVwtAkA\n" +
            "AH9q4AQmAAD8XwPnMAEAIA2cxgQA4O4aOJMJAMCtNXAyEwCA+2rgfCYAADfVwClNAADuqIGzmgAA\n" +
            "3E4DJzYBALiXBs5tAgBwIw2c3gQA4C4aWMEEAOAWGljEBADg+hpYxwQA4OIaWMoEAODKGljNBADg\n" +
            "shpY0AQA4JoaWNMEAOCCGljWBADgahpY2QQA4FIaWNwEAOA6GljfBADgIhq4hAkAwBU0cBUTAIDl\n" +
            "NXAhEwCAtTVwLRMAgIU1cDkTAIBVNXBFEwCAJTVwURMAgPU0cF0TAIDFNHBpEwCAlTRwdRMAgGU0\n" +
            "cAMTAIA1NHAPEwCABTRwGxMAgLNr4E4mAACn1sDNTAAAzquB+5kAAJxUA7c0AQA4owbuagIAcDoN\n" +
            "3NgEAOBcGri3CQDAiTRwexMAgLNogGQCAHAKDfCHCQDA8RrgLxMAgIM1wD9MAACO1AC/mgAAHKYB\n" +
            "/mMCAHCMBvjKBADgAA3wjQkAwKc1wPcmAAAf1QC/NQEA+JwG+MkEAOBDGuABEwCAT2iAx0wAAHbX\n" +
            "AA+bAADsqwGeMQEA2FEDPGkCALCXBnjeBABgFw3wkgkAwPYa4FUTAICNNcAbJgAAW2qA90wAADbT\n" +
            "AG+bAABsowG2MAEA2EADbGQCAPCuBtjOBADgLQ2wqQkAwOsaYGsTAIAXNcAOJgAAr2iAfUwAAJ7W\n" +
            "ALuZAAA8pwH2NAEAeEID7GwCAPCoBtjfBADgIQ3wERMAgJ81wKdMAAB+0AAfNAEA+J0G+KwJAMC3\n" +
            "GuDjJgAAX2uAI0wAAL7QAAeZAAD8WwMcZwIA8IsGONQEAOBvDXC0CQDAnxrgBCYAAP/XAOcwAQBI\n" +
            "A5zGBAC4uwY4kwkAcGsNcDITAOC+GuB8JgDATTXAKU0AgDtqgLOaAAC30wAnNgEA7qUBzm0CANxI\n" +
            "A5zeBAC4iwZYwQQAuIUGWMQEALi+BljHBAC4uAZYygQAuLIGWM0EALisBljQBAC4pgZY0wQAuKAG\n" +
            "WNYEALiaBljZBAC4lAZY3AQAuI4GWN8EALiIBriECQBwBQ1wFRMAYHkNcCETAGBtDXAtEwBgYQ1w\n" +
            "ORMAYFUNcEUTAGBJDXBREwBgPQ1wXRMAYDENcGkTAGAlDXB1EwBgGQ1wAxMAYA0NcA8TAGABDXAb\n" +
            "EwDg7BrgTiYAwKk1wM1MAIDzaoD7mQAAJ9UAtzQBAM6oAe5qAgCcTgPc2AQAOJcGuLcJAHAiDXB7\n" +
            "EwDgLBqAZAIAnEID8IcJAHC8BuAvEwDgYA3AP0wAgCM1AL+aAACHaQD+YwIAHKMB+MoEADhAA/CN\n" +
            "CQDwaQ3A9yYAwEc1AL81AQA+pwH4yQQA+JAG4AETAOATGoDHTACA3TUAD5sAAPtqAJ4xAQB21AA8\n" +
            "aQIA7KUBeN4EANhFA/CSCQCwvQbgVRMAYGMNwBsmAMCWGoD3TACAzTQAb5sAANtoALYwAQA20ABs\n" +
            "ZAIAvKsB2M4EAHhLA7CpCQDwugZgaxMA4EUNwA4mAMArGoB9TACApzUAu5kAAM9pAPY0AQCe0ADs\n" +
            "bAIAPKoB2N8EAHhIA/AREwDgZw3Ap0wAgB80AB80AQB+pwH4rAkA8K0G4OMmAMDXGoAjTACALzQA\n" +
            "B5kAAP/WABxnAgD8ogE41AQA+FsDcLQJAPCnBuAEJgDA/zUA5zABANIAnMYEAO6uATiTCQDcWgNw\n" +
            "MhMAuK8G4HwmAHBTDcApTQDgjhqAs5oAwO00ACc2AYB7aQDObQIAN9IAnN4EAO6iAVjBBABuoQFY\n" +
            "xAQArq8BWMcEAC6uAVjKBACurAFYzQQALqsBWNAEAK6pAVjTBAAuqAFY1gQArqYBWNkEAC6lAVjc\n" +
            "BACuowFY3wQALqIBuIQJAFxBA3AVEwBYXgNwIRMAWFsDcC0TAFhYA3A5EwBYVQNwRRMAWFIDcFET\n" +
            "AFhPA3BdEwBYTANwaRMAWEkDcHUTAFhGA3ADEwBYQwNwDxMAWEADcBsTADi7BuBOJgBwag3AzUwA\n" +
            "4LwagPuZAMBJNQC3NAGAM2oA7moCAKfTANzYBADOpQG4twkAnEgDcHsTADiLBoBkAgCn0ADwhwkA\n" +
            "HK8B4C8TADhYA8A/TADgSA0Av5oAwGEaAP5jAgDHaAD4ygQADtAA8I0JAHxaA8D3JgDwUQ0AvzUB\n" +
            "gM9pAPjJBAA+pAHgARMA+IQGgMdMAGB3DQAPmwDAvhoAnjEBgB01ADxpAgB7aQB43gQAdtEA8JIJ\n" +
            "AGyvAeBVEwDYWAPAGyYAsKUGgPdMAGAzDQBvmwDANhoAtjABgA00AGxkAgDvagDYzgQA3tIAsKkJ\n" +
            "ALyuAWBrEwB4UQPADiYA8IoGgH1MAOBpDQC7mQDAcxoA9jQBgCc0AOxsAgCPagDY3wQAHtIA8BET\n" +
            "APhZA8CnTADgBw0AHzQBgN9pAPisCQB8qwHg4yYA8LUGgCNMAOALDQAHmQDAvzUAHGcCAL9oADjU\n" +
            "BAD+1gBwtAkA/KkB4AQmAPB/DQDnMAGANACcxgSAu2sAOJMJALfWAHAyEwDuqwHgfCYA3FQDwClN\n" +
            "ALijBoCzmgBwOw0AJzYB4F4aAM5tAsCNNACc3gSAu2gAWMEEgFtoAFjEBIDrawBYxwSAi2sAWMoE\n" +
            "gCtrAFjNBIDLagBY0ASAa2oAWNMEgAtqAFjWBICraQBY2QSAS2kAWNwEgOtoAFjfBICLaAC4hAkA\n" +
            "V9AAcBUTAJbXAHAhEwDW1gBwLRMAFtYAcDkTAFbVAHBFEwCW1ABwURMA1tMAcF0TABbTAHBpEwBW\n" +
            "0gBwdRMAltEAcAMTANbQAHAPEwAW0ABwGxMAzq4B4E4mAJxaA8DNTAA4rwaA+5kAcFINALc0AeCM\n" +
            "GgDuagLA6TQA3NgEgHNpALi3CQAn0gBwexMAzqIBgGQCwCk0APCHCQDHawDgLxMADtYAwD9MADhS\n" +
            "AwC/mgBwmAYA/mMCwDEaAPjKBIADNADwjQkAn9YAwPcmAHxUAwC/NQHgcxoA+MkEgA9pAOABEwA+\n" +
            "oQGAx0wA2F0DAA+bALCvBgCeMQFgRw0APGkCwF4aAHjeBIBdNADwkgkA22sA4FUTADbWAMAbJgBs\n" +
            "qQGA90wA2EwDAG+bALCNBgC2MAFgAw0AbGQCwLsaANjOBIC3NACwqQkAr2sAYGsTAF7UAMAOJgC8\n" +
            "ogGAfUwAeFoDALuZAPCcBgD2NAHgCQ0A7GwCwKMaANjfBICHNADwERMAftYAwKdMAPhBAwAfNAHg\n" +
            "dxoA+KwJAN9qAODjJgB8rQGAI0wA+EIDAAeZAPBvDQAcZwLALxoAONQEgL81AHC0CQB/agDgBCYA\n" +
            "/F8DAOcwASANAJzGBODuGgA4kwnArTUAcDITgPtqAOB8JgA31QDAKU0A7qgBgLOaANxOAwAnNgG4\n" +
            "lwYAzm0CcCMNAJzeBOAuGgBYwQTgFhoAWMQE4PoaAFjHBODiGgBYygTgyhoAWM0E4LIaAFjQBOCa\n" +
            "GgBY0wTgghoAWNYE4GoaAFjZBOBSGgBY3ATgOhoAWN8E4CIaALiECcAVNABwFROA5TUAcCETgLU1\n" +
            "AHAtE4CFNQBwOROAVTUAcEUTgCU1AHBRE4D1NABwXROAxTQAcGkTgJU0AHB1E4BlNABwAxOANTQA\n" +
            "cA8TgAU0AHAbE4CzawDgTiYAp9YAwM1MAM6rAYD7mQCcVAMAtzQBOKMGAO5qAnA6DQDc2ATgXBoA\n" +
            "uLcJwIk0AHB7E4CzaACAZAJwCg0A8IcJwPEaAOAvE4CDNQDAP0wAjtQAAL+aABymAQD+YwJwjAYA\n" +
            "+MoE4AANAPCNCcCnNQDA9yYAH9UAAL81AficBgD4yQTgQxoA4AETgE9oAIDHTAB21wAAD5sA7KsB\n" +
            "AJ4xAdhRAwA8aQKwlwYAeN4EYBcNAPCSCcD2GgDgVROAjTUAwBsmAFtqAID3TAA20wAAb5sAbKMB\n" +
            "ALYwAdhAAwBsZALwrgYA2M4E4C0NALCpCcDrGgBgaxOAFzUAwA4mAK9oAIB9TACe1gAAu5kAPKcB\n" +
            "APY0AXhCAwDsbALwqAYA2N8E4CENAPARE4CfNQDAp0wAftAAAB80AfidBgD4rAnAtxoA4OMmAF9r\n" +
            "AIAjTAC+0AAAB5kA/FsDABxnAvCLBgA41ATgbw0AcLQJwJ8aAOAEJgD/1wAA5zABSAMAnMYEuLsG\n" +
            "ADiTCXBrDQBwMhPgvhoA4HwmwE01AMApTYA7agCAs5oAt9MAACc2Ae6lAQDObQLcSAMAnN4EuIsG\n" +
            "AFjBBLiFBgBYxAS4vgYAWMcEuLgGAFjKBLiyBgBYzQS4rAYAWNAEuKYGAFjTBLigBgBY1gS4mgYA\n" +
            "WNkEuJQGAFjcBLiOBgBY3wS4iAYAuIQJcAUNAHAVE2B5DQBwIRNgbQ0AcC0TYGENAHA5E2BVDQBw\n" +
            "RRNgSQ0AcFETYD0NAHBdE2AxDQBwaRNgJQ0AcHUTYBkNAHADE2ANDQBwDxNgAQ0AcBsT4OwaAOBO\n" +
            "JsCpNQDAzUyA82oAgPuZACfVAAC3NAHOqAEA7moCnE4DANzYBDiXBgC4twlwIg0AcHsT4CwaAIBk\n" +
            "ApxCAwDwhwlwvAYA4C8T4GANAMA/TIAjNQAAv5oAh2kAAP5jAhyjAQD4ygQ4QAMA8I0J8GkNAMD3\n" +
            "JsBHNQAAvzUBPqcBAPjJBPiQBgDgARPgExoAgMdMgN01AAAPmwD7agAAnjEBdtQAADxpAuylAQB4\n" +
            "3gTYRQMA8JIJsL0GAOBVE2BjDQDAGybAlhoAgPdMgM00AABvmwDbaAAAtjABNtAAAGxkAryrAQDY\n" +
            "zgR4SwMAsKkJ8LoGAGBrE+BFDQDADibAKxoAgH1MgKc1AAC7mQDPaQAA9jQBntAAAOxsAjyqAQDY\n" +
            "3wR4SAMA8BET4GcNAMCnTIAfNAAAHzQBfqcBAPisCfCtBgDg4ybA1xoAgCNMgC80AAAHmQD/1gAA\n" +
            "HGcC/KIBADjUBPhbAwBwtAnwpwYA4AQmwP81AADnMAHSAACcxgTurgEAOJMJ3FoDAHAyE7ivBgDg\n" +
            "fCZwUw0AwClN4I4aAICzmsDtNAAAJzaBe2kAAM5tAjfSAACc3gTuogEAWMEEbqEBAFjEBK6vAQBY\n" +
            "xwQurgEAWMoErqwBAFjNBC6rAQBY0ASuqQEAWNMELqgBAFjWBK6mAQBY2QQupQEAWNwErqMBAFjf\n" +
            "BC6iAQC4hAlcQQMAcBUTWF4DAHAhE1hbAwBwLRNYWAMAcDkTWFUDAHBFE1hSAwBwURNYTwMAcF0T\n" +
            "WEwDAHBpE1hJAwBwdRNYRgMAcAMTWEMDAHAPE1hAAwBwGxM4uwYA4E4mcGoNAMDNTOC8GgCA+5nA\n" +
            "STUAALc0gTNqAADuagKn0wAA3NgEzqUBALi3CZxIAwBwexM4iwYAgGQCp9AAAPCHCRyvAQDgLxM4\n" +
            "WAMAwD9M4EgNAAC/msBhGgAA/mMCx2gAAPjKBA7QAADwjQl8WgMAwPcm8FENAAC/NYHPaQAA+MkE\n" +
            "PqQBAOABE/iEBgCAx0xgdw0AAA+bwL4aAACeMYEdNQAAPGkCe2kAAHjeBHbRAADwkglsrwEA4FUT\n" +
            "2FgDAMAbJrClBgCA90xgMw0AAG+bwDYaAAC2MIENNAAAbGQC72oAANjOBN7SAACwqQm8rgEAYGsT\n" +
            "eFEDAMAOJvCKBgCAfUzgaQ0AALuZwHMaAAD2NIEnNAAA7GwCj2oAANjfBB7SAADwERP4WQMAwKdM\n" +
            "4AcNAAAfNIHfaQAA+KwJfKsBAODjJvC1BgCAI0zgCw0AAAeZwL81AAAcZwK/aAAAONQE/tYAAHC0\n" +
            "CfypAQDgBCbwfw0AAOcwgTQAAJzGhLtrAAA4kwm31gAAcDIT7qsBAOB8JtxUAwDAKU24owYAgLOa\n" +
            "cDsNAAAnNuFeGgAAzm3CjTQAAJzehLtoAABYwYRbaAAAWMSE62sAAFjHhItrAABYyoQrawAAWM2E\n" +
            "y2oAAFjQhGtqAABY04QLagAAWNaEq2kAAFjZhEtpAABY3ITraAAAWN+Ei2gAALiECVfQAABwFROW\n" +
            "1wAAcCET1tYAAHAtExbWAABwORNW1QAAcEUTltQAAHBRE9bTAABwXRMW0wAAcGkTVtIAAHB1E5bR\n" +
            "AABwAxPW0AAAcA8TFtAAAHAbE86uAQDgTiacWgMAwM1MOK8GAID7mXBSDQAAtzThjBoAAO5qwuk0\n" +
            "AADc2IRzaQAAuLcJJ9IAAHB7E86iAQCAZMIpNAAA8IcJx2sAAOAvEw7WAADAP0w4UgMAAL+acJgG\n" +
            "AAD+Y8IxGgAA+MqEAzQAAPCNCZ/WAADA9yZ8VAMAAL814XMaAAD4yYQPaQAA4AETPqEBAIDHTNhd\n" +
            "AwAAD5uwrwYAAJ4xYUcNAAA8acJeGgAAeN6EXTQAAPCSCdtrAADgVRM21gAAwBsmbKkBAID3TNhM\n" +
            "AwAAb5uwjQYAALYwYQMNAABsZMK7GgAA2M6EtzQAALCpCa9rAABgaxNe1AAAwA4mvKIBAIB9THha\n" +
            "AwAAu5nwnAYAAPY04QkNAADsbMKjGgAA2N+EhzQAAPARE37WAADAp0z4QQMAAB804XcaAAD4rAnf\n" +
            "agAA4OMmfK0BAIAjTPhCAwAAB5nwbw0AABxnwi8aAAA41IS/NQAAcLQJf2oAAOAEJvxfAwAA5zAh\n" +
            "DQAAnMbk7hoAADiTya01AABwMpP7agAA4HwmN9UAAMApTe6oAQCAs5rcTgMAACc2uZcGAADObXIj\n" +
            "DQAAnN7kLhoAAFjB5BYaAABYxOT6GgAAWMfk4hoAAFjK5MoaAABYzeSyGgAAWNDkmhoAAFjT5IIa\n" +
            "AABY1uRqGgAAWNnkUhoAAFjc5DoaAABY3+QiGgAAuITJFTQAAHAVk+U1AABwIZO1NQAAcC2ThTUA\n" +
            "AHA5k1U1AABwRZMlNQAAcFGT9TQAAHBdk8U0AABwaZOVNAAAcHWTZTQAAHADkzU0AABwD5MFNAAA\n" +
            "cBuTs2sAAOBOJqfWAADAzUzOqwEAgPuZnFQDAAC3NDmjBgAA7mpyOg0AANzY5FwaAAC4t8mJNAAA\n" +
            "cHuTs2gAAIBkcgoNAADwh8nxGgAA4C+TgzUAAMA/TI7UAAAAv5ocpgEAAP5jcowGAAD4yuQADQAA\n" +
            "8I3JpzUAAMD3Jh/VAAAAvzX5nAYAAPjJ5EMaAADgAZNPaAAAgMdMdtcAAAAPm+yrAQAAnjHZUQMA\n" +
            "ADxpspcGAAB43mQXDQAA8JLJ9hoAAOBVk401AADAGyZbagAAgPdMNtMAAABvm2yjAQAAtjDZQAMA\n" +
            "AGxk8q4GAADYzuQtDQAAsKnJ6xoAAGBrkxc1AADADiavaAAAgH1MntYAAAC7mTynAQAA9jR5QgMA\n" +
            "AOxs8qgGAADY3+QhDQAA8BGTnzUAAMCnTH7QAAAAHzT5nQYAAPisybcaAADg4yZfawAAgCNMvtAA\n" +
            "AAAHmfxbAwAAHGfyiwYAADjU5G8NAABwtMmfGgDgf+3BQREAAAgDoNk/tJ4t9gAACmzeBAAA6LDJ\n" +
            "BAAAqHHNJd8vafxHjAAAAABJRU5ErkJggg==";
}
