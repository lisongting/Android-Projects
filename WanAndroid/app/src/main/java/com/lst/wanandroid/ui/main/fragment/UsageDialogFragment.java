package com.lst.wanandroid.ui.main.fragment;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lst.wanandroid.R;
import com.lst.wanandroid.base.fragment.BaseDialogFragment;
import com.lst.wanandroid.contract.main.UsageDialogContract;
import com.lst.wanandroid.core.bean.main.search.UsefulSiteData;
import com.lst.wanandroid.presenter.main.UsageDialogPresenter;
import com.lst.wanandroid.utils.CommonUtil;
import com.lst.wanandroid.utils.JudgeUtils;
import com.lst.wanandroid.widget.CircularRevealAnim;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UsageDialogFragment extends BaseDialogFragment<UsageDialogPresenter>
    implements UsageDialogContract.View,
        CircularRevealAnim.AnimListener,
        ViewTreeObserver.OnPreDrawListener{

    @BindView(R.id.common_toolbar)
    Toolbar toolbar;
    @BindView(R.id.common_toolbar_title_tv)
    TextView mTitleView;
    @BindView(R.id.useful_sites_flow_layout)
    TagFlowLayout mUsefulSitesFlowLayout;

    private List<UsefulSiteData> mUsefulSiteDataList;
    private CircularRevealAnim mCircularRevealAnim;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart(){
        super.onStart();
        initDialog();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_usage;
    }

    @Override
    protected void initEventAndData() {
        initCircleAnimation();
        initToolbar();
        mUsefulSiteDataList = new ArrayList<>();
        mPresenter.getUsefulSites();
    }

    @Override
    public void showUsefulSites(List<UsefulSiteData> usefulSiteDataList) {
        mUsefulSiteDataList = usefulSiteDataList;
        mUsefulSitesFlowLayout.setAdapter(new TagAdapter<UsefulSiteData>(mUsefulSiteDataList) {
            @Override
            public View getView(FlowLayout parent, int position, UsefulSiteData usefulSiteData) {
                TextView textView = (TextView) LayoutInflater.from(getContext())
                        .inflate(R.layout.flow_layout_tv, parent, false);
                String name = usefulSiteData.getName();
                textView.setText(name);
                setItemBackGround(textView);
                mUsefulSitesFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int p, FlowLayout parent) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                                view, getString(R.string.share_view));
                        JudgeUtils.startArticleDetailActivity(getActivity(),
                                options,
                                mUsefulSiteDataList.get(p).getId(),
                                mUsefulSiteDataList.get(p).getName().trim(),
                                mUsefulSiteDataList.get(p).getLink().trim(),
                                false,false,true);
                        return true;
                    }
                });
                return textView;
            }
        });
    }

    @Override
    public void onHideAnimationEnd(){
        dismissAllowingStateLoss();
    }

    @Override
    public boolean onPreDraw() {
        mTitleView.getViewTreeObserver().removeOnPreDrawListener(this);
        mCircularRevealAnim.show(mTitleView, mRootView);
        return true;
    }
    
    private void setItemBackGround(TextView textView){
        textView.setBackgroundColor(CommonUtil.randomTagColor());
        textView.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
    }

    private void initCircleAnimation(){
        mCircularRevealAnim = new CircularRevealAnim();
        mCircularRevealAnim.setAnimListener(this);
        mTitleView.getViewTreeObserver().addOnPreDrawListener(this);
    }

    private void initDialog(){
        Window window = getDialog().getWindow();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        //DialogSearch的宽
        int width = (int) (displayMetrics.widthPixels * 0.98);
        window.setLayout(width, WindowManager.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.TOP);
        //取消过度动画，使DialogSearch的出现更加平滑
        window.setWindowAnimations(R.style.DialogEmptyAnimation);
    }

    private void initToolbar(){
        mTitleView.setText(R.string.useful_sites);
        if (mPresenter.getNightModeState()) {
            mTitleView.setTextColor(ContextCompat.getColor(getActivity(), R.color.comment_text));
            toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorCard));
            toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_back_white_24dp));
        } else {
            mTitleView.setTextColor(ContextCompat.getColor(getActivity(), R.color.title_black));
            toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
            toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_back_grey_24dp));
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCircularRevealAnim.hide(mTitleView,mRootView);
            }
        });
    }
    @Override
    public void useNightMode(boolean isNightMode) {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showLoginView() {

    }

    @Override
    public void showLogoutView() {

    }

    @Override
    public void showCollectSuccess() {

    }

    @Override
    public void showCancelCollectSuccess() {

    }


    @Override
    public void onShowAnimationEnd() {

    }
}
