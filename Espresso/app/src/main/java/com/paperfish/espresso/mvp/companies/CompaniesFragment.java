package com.paperfish.espresso.mvp.companies;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.paperfish.espresso.data.Company;

import java.util.List;

/**
 * Created by lisongting on 2017/8/11.
 */

public class CompaniesFragment extends Fragment implements CompaniesContract.View{

    private RecyclerView recyclerView;

    private CompaniesAdapter adapter;

    private CompaniesContract.Presenter presenter;

    public CompaniesFragment(){}

    public static CompaniesFragment getInstance() {
        return new CompaniesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate()
    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void setPresenter(CompaniesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showGetCompaniesError() {

    }

    @Override
    public void showCompanies(List<Company> list) {

    }
}
