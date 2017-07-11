package com.example.dialogfragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dialogfragment.loadingview.CircleRotateView;

/**
 * Created by lisongting on 2017/7/4.
 */

public class LoadingDialogFragment extends DialogFragment {
    Button btStartAnim,btStopAnim;
    CircleRotateView circleRotateView ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loading_fragment_layout, container, false);
        btStartAnim = (Button) view.findViewById(R.id.startLoading);
        btStopAnim = (Button) view.findViewById(R.id.stopLoading);

        circleRotateView = (CircleRotateView) view.findViewById(R.id.loading_view);
        btStartAnim.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view1) {
                circleRotateView.startAnimation();
            }
        });

        btStopAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleRotateView.endAnimation();
            }
        });

        return view;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

}
