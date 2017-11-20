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

public class SimpleFragment1 extends Fragment {

    private String str;
    private int num=1;

    public SimpleFragment1() {

    }

    public static SimpleFragment1 getInstance(String s) {

        SimpleFragment1 fragment = new SimpleFragment1();
        Bundle b = new Bundle();
        b.putString("string", s);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        log("onAttach()");
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        log("onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        log("onCreateView()");
        str = getArguments().getString("string");
        View v = inflater.inflate(R.layout.fragment_simple, container,false);
        TextView text = (TextView) v.findViewById(R.id.textview);
        text.setText(str);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        log("onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        log("onStart()");
        super.onStart();
    }


    @Override
    public void onResume() {
        log("onResume()");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        log("onSaveInstanceState()");
        outState.putString("key", "test");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        log("onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        log("onStop()");
        super.onStop();
    }



    @Override
    public void onDestroyView() {
        log("onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        log("onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        log("onDetach()");
        super.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        log("isHidden:" + hidden);
        super.onHiddenChanged(hidden);
    }

    private void log(String s) {
        Log.i("tag","Fragment"+num +" -- " + s);
    }


}
