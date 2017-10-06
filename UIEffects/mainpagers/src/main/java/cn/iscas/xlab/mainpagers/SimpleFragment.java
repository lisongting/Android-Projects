package cn.iscas.xlab.mainpagers;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lisongting on 2017/10/6.
 */

public class SimpleFragment extends Fragment {

    private TextView textView;
    private String text = "";

    public SimpleFragment(){}

    public static SimpleFragment getInstance(String str) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle b = new Bundle();
        b.putString("text",str);
        fragment.setArguments(b);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        text = savedInstanceState.getString("text");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.layout_simple_fragment,container,false);
        textView = (TextView) v.findViewById(R.id.textview);
        textView.setText(text);
        return v;
    }
}
