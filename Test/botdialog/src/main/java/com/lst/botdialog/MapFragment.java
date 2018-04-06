package com.lst.botdialog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lisongting on 2018/4/6.
 */

public class MapFragment extends Fragment {

    private MapView mapView;

    public MapFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, parent,false);
        mapView = view.findViewById(R.id.map_view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getContext(), "mapView size:"+mapView.getWidth()+"x"+mapView.getHeight(), Toast.LENGTH_SHORT).show();
    }
}
