package cn.lst.facerecog.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import cn.lst.facerecog.R;


/**
 * Created by lisongting on 2017/12/13.
 * 用来提示用户输入姓名
 */

public class RegisterFragment extends DialogFragment {

    private Window window;
    public RegisterFragment(){}
    private Button btCapture,btCancel;
    private TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        btCapture = v.findViewById(R.id.id_bt_camera);
        btCancel = v.findViewById(R.id.id_bt_cancel);
        textView = v.findViewById(R.id.id_textView);
        window = getDialog().getWindow();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (window != null) {
            window.setBackgroundDrawableResource(android.R.color.transparent);

        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        if (metrics.heightPixels < 2000) {
            window.setLayout((int) (metrics.widthPixels * 0.7), (int) (metrics.heightPixels * 0.5));
        } else {
            window.setLayout((int) (metrics.widthPixels * 0.5), (int) (metrics.heightPixels * 0.4));
        }

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = textView.getEditableText().toString();
//                if (RegexCheckUtil.isRightPersonName(userName)) {
                    dismiss();
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra("userName", userName);
                    startActivity(intent);
//                } else {
//                    Toast.makeText(getContext(), "请输入正确的姓名", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }


}
