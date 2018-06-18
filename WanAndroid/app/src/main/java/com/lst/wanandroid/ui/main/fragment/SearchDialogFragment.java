package com.lst.wanandroid.ui.main.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jakewharton.rxbinding2.view.RxView;
import com.lst.wanandroid.R;
import com.lst.wanandroid.app.Constants;
import com.lst.wanandroid.base.fragment.BaseDialogFragment;
import com.lst.wanandroid.contract.main.SearchContract;
import com.lst.wanandroid.core.bean.main.search.TopSearchData;
import com.lst.wanandroid.core.dao.HistoryData;
import com.lst.wanandroid.presenter.main.SearchPresenter;
import com.lst.wanandroid.ui.main.HistorySearchAdapter;
import com.lst.wanandroid.utils.CommonUtil;
import com.lst.wanandroid.utils.JudgeUtils;
import com.lst.wanandroid.utils.KeyBoardUtil;
import com.lst.wanandroid.widget.CircularRevealAnim;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

public class SearchDialogFragment extends BaseDialogFragment<SearchPresenter>
    implements SearchContract.View,
        CircularRevealAnim.AnimListener,
        ViewTreeObserver.OnPreDrawListener{

    @BindView(R.id.search_back_ib)
    ImageButton mBackIb;
    @BindView(R.id.search_tint_tv)
    TextView mTintTv;
    @BindView(R.id.search_edit)
    EditText mSearchEdit;
    @BindView(R.id.search_tv)
    TextView mSearchTv;
    @BindView(R.id.search_history_clear_all_tv)
    TextView mClearAllHistoryTv;
    @BindView(R.id.search_scroll_view)
    NestedScrollView mSearchScrollView;
    @BindView(R.id.search_history_null_tint_tv)
    TextView mHistoryNullTintTv;
    @BindView(R.id.search_history_rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.top_search_flow_layout)
    TagFlowLayout mTopSearchFlowLayout;
    @BindView(R.id.search_floating_action_btn)
    FloatingActionButton mFloatingActionButton;

    private List<TopSearchData> mTopSearchDataList;
    private CircularRevealAnim circularRevealAnim;
    private HistorySearchAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.DialogStyle);
    }

    @Override
    public void onStart() {
        super.onStart();
        initDialog();
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

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initEventAndData() {
        initCircleAnimation();
        mTopSearchDataList = new ArrayList<>();
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(mSearchEdit.getText().toString())) {
                    mTintTv.setText(R.string.search_tint);
                } else {
                    mTintTv.setText("");
                }
            }
        });
        mPresenter.addRxBindingSubscribe(RxView.clicks(mSearchTv)
            .throttleFirst(Constants.CLICK_TIME_AREA, TimeUnit.MILLISECONDS)
            .filter(new Predicate<Object>() {
                @Override
                public boolean test(Object o) throws Exception {
                    return !TextUtils.isEmpty(mSearchEdit.getText().toString().trim());
                }
            })
            .subscribe(new Consumer<Object>() {
                @Override
                public void accept(Object o) throws Exception {
                    mPresenter.addHistoryData(mSearchEdit.getText().toString().trim());
                    setHistoryTvStatus(false);
                }
            }));
        showHistoryData(mPresenter.loadAllHistoryData());
        mPresenter.getTopSearchData();
    }

    @Override
    public void showHistoryData(List<HistoryData> historyDataList) {
        if (historyDataList == null || historyDataList.size() == 0) {
            setHistoryTvStatus(true);
            return;
        }
        setHistoryTvStatus(false);
        Collections.reverse(historyDataList);
        adapter = new HistorySearchAdapter(R.layout.item_search_history, historyDataList);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                HistoryData historyData = (HistoryData) adapter.getData().get(position);
                mPresenter.addHistoryData(historyData.getData());
                mSearchEdit.setText(historyData.getData());
                mSearchEdit.setSelection(mSearchEdit.getText().length());
                setHistoryTvStatus(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(adapter);
    }

    private void setHistoryTvStatus(boolean isClearAll) {
        Drawable drawable;
        mClearAllHistoryTv.setEnabled(!isClearAll);
        if (isClearAll) {
            mHistoryNullTintTv.setVisibility(View.VISIBLE);
            mClearAllHistoryTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.search_grey_gone));
            drawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_clear_all_gone);
        } else {
            mHistoryNullTintTv.setVisibility(View.GONE);
            mClearAllHistoryTv.setTextColor(ContextCompat.getColor(getActivity(), R.color.search_grey_gone));
            drawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_clear_all);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mClearAllHistoryTv.setCompoundDrawables(drawable, null, null, null);
        mClearAllHistoryTv.setCompoundDrawablePadding(CommonUtil.dp2px(6));
    }

    @Override
    public void showTopSearchData(List<TopSearchData> topSearchDataList) {
        mTopSearchDataList = topSearchDataList;
        mTopSearchFlowLayout.setAdapter(new TagAdapter<TopSearchData>(mTopSearchDataList) {
            @Override
            public View getView(FlowLayout parent, int position, TopSearchData topSearchData) {
                assert getActivity() != null;
                TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.flow_layout_tv, parent, false);
                String name = topSearchData.getName();
                tv.setText(name);
                setItemBackground(tv);
                mTopSearchFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position1, FlowLayout parent) {
                        mPresenter.addHistoryData(mTopSearchDataList.get(position1).getName().trim());
                        setHistoryTvStatus(false);
                        mSearchEdit.setText(mTopSearchDataList.get(position1).getName().trim());
                        mSearchEdit.setSelection(mSearchEdit.getText().length());
                        return true;
                    }
                });
                return tv;
            }
        });
    }

    @Override
    public void judgeToTheSearchListActivity() {
        backEvent();
        JudgeUtils.startSearchListActivity(getActivity(),mSearchEdit.getText().toString().trim());
    }

    @Override
    public void onHideAnimationEnd() {
        mSearchEdit.setText("");
        dismissAllowingStateLoss();
    }

    @Override
    public void onShowAnimationEnd() {
        KeyBoardUtil.openKeyboard(getActivity(),mSearchEdit);
    }

    //todo:
    @Override
    public boolean onPreDraw() {
        mSearchEdit.getViewTreeObserver().removeOnPreDrawListener(this);
        circularRevealAnim.show(mSearchEdit,mRootView);
        return true;
    }

    @OnClick({R.id.search_back_ib, R.id.search_floating_action_btn, R.id.search_history_clear_all_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_back_ib:
                backEvent();
                break;
            case R.id.search_floating_action_btn:
                mSearchScrollView.smoothScrollTo(0,0);
                break;
            case R.id.search_history_clear_all_tv:
                mPresenter.clearHistoryData();
                adapter.replaceData(new ArrayList<>());
                setHistoryTvStatus(true);
                break;
            default:break;
        }
    }

    private void setItemBackground(TextView tv) {
        tv.setBackgroundColor(CommonUtil.randomColor());
        tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
    }

    private void initCircleAnimation(){
        circularRevealAnim = new CircularRevealAnim();
        circularRevealAnim.setAnimListener(this);
        mSearchEdit.getViewTreeObserver().addOnPreDrawListener(this);
    }

    public void backEvent(){
        KeyBoardUtil.closeKeyboard(getActivity(),mSearchEdit);
        circularRevealAnim.hide(mSearchEdit,mRootView);
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




}
