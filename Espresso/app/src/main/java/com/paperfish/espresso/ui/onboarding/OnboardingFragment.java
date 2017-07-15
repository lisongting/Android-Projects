package com.paperfish.espresso.ui.onboarding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paperfish.espresso.R;

/**
 * Created by lisongting on 2017/7/14.
 */

public class OnboardingFragment extends Fragment {

    private AppCompatTextView sectionLabel;
    private AppCompatTextView sectionIntro;
    private ImageView sectionImg;

    private int page;

    //代表是第几个fragment的参数
    private static final String ARG_SECTION_NUMBER = "section_number";
    public void OnboardingFragment(){}

    public static OnboardingFragment newInstance(int pos) {
        OnboardingFragment fragment = new OnboardingFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_SECTION_NUMBER, pos);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt(ARG_SECTION_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);
        initViews(view);

        switch (page) {
            case 0:
                sectionImg.setBackgroundResource(R.drawable.ic_beenhere_black_24dp);
                sectionLabel.setText(R.string.onboarding_section_1);
                sectionIntro.setText(R.string.onboarding_intro_1);
                break;
            case 1:
                sectionImg.setBackgroundResource(R.drawable.ic_camera_black_24dp);
                sectionLabel.setText(R.string.onboarding_section_2);
                sectionIntro.setText(R.string.onboarding_intro_2);
                break;
            case 2:
                sectionImg.setBackgroundResource(R.drawable.ic_notifications_black_24dp);
                sectionLabel.setText(R.string.onboarding_section_3);
                sectionIntro.setText(R.string.onboarding_intro_3);
                break;
            default:
                break;
        }

        return view;
    }

    private void initViews(View view) {
        sectionLabel = (AppCompatTextView) view.findViewById(R.id.section_label);
        sectionIntro = (AppCompatTextView) view.findViewById(R.id.section_intro);
        sectionImg = (ImageView) view.findViewById(R.id.section_img);
    }

}
