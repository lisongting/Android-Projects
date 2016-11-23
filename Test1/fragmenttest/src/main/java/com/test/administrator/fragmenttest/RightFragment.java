package com.test.administrator.fragmenttest;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/9/12.
 */
public class RightFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.right_fragment,container,false);
        return view;
    }
    public static final String Tag = "RightFragment";
    public void onAttach(Activity activity){
        super.onAttach(activity);
        Log.d(Tag,"onAttach");
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(Tag,"onCreate");
    }
    public void onDetach(){
        super.onDetach();
        Log.d(Tag,"onDetach");
    }
}
