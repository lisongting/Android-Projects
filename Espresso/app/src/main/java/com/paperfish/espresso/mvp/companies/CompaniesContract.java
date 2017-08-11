package com.paperfish.espresso.mvp.companies;

import com.paperfish.espresso.data.Company;
import com.paperfish.espresso.mvp.BasePresenter;
import com.paperfish.espresso.mvp.BaseView;

import java.util.List;

/**
 * Created by lisongting on 2017/8/11.
 */

public interface CompaniesContract {
    interface View extends BaseView<Presenter> {
        void showGetCompaniesError();

        void showCompanies(List<Company> list);
    }

    interface Presenter extends BasePresenter {

    }
}
