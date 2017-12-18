package cn.iscas.xlab.fragmentlifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lisongting on 2017/11/20.
 */

public class SimpleFragment2 extends Fragment {
    private String str;
    private int num=2;

    public SimpleFragment2() {

    }

    public static SimpleFragment2 getInstance(String s) {
        SimpleFragment2 fragment2 = new SimpleFragment2();
        Bundle b = new Bundle();
        b.putString("string", s);
        fragment2.setArguments(b);
        return fragment2;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("onAttach");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        log("onCreate");
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log("onCreateView");
        str = getArguments().getString("string");
        View v = inflater.inflate(R.layout.fragment_simple, container,false);
        TextView text = (TextView) v.findViewById(R.id.textview);
        text.setText(str);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        log("onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        log("onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        log("onResume");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("key", "test");
        super.onSaveInstanceState(outState);
        log("onSaveInstanceState");
    }

    @Override
    public void onPause() {
        super.onPause();
        log("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        log("onStop");
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        log("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        log("onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("onDetach");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        log("isHidden:" + hidden);
    }

    private void log(String s) {
        Log.v("tag","Fragment"+num +" -- " + s);
    }
}
