package com.paperfish.espresso.mvp.packages;

import android.appwidget.AppWidgetProvider;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.paperfish.espresso.R;
import com.paperfish.espresso.data.Package;
import com.paperfish.espresso.interfaces.OnRecyclerViewItemClickListener;
import com.paperfish.espresso.mvp.addpackage.AddPackageActivity;
import com.paperfish.espresso.mvp.packagedetails.PackageDetailsActivity;

import java.util.List;

/**
 * Created by lisongting on 2017/7/31.
 */

public class PackagesFragment extends Fragment implements PackagesContract.View{

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private SwipeRefreshLayout refreshLayout;
    private PackagesAdapter adapter;
    private PackagesContract.Presenter presenter;
    private String selectedPackageNumber;

    public static PackagesFragment newInstance(){
        return new PackagesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View v = layoutInflater.inflate(R.layout.fragment_packages, container, false);
        initViews(v);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddPackageActivity.class));
            }
        });

        //底部的tab按钮
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_all:
                        presenter.setFiltering(PackageFilterType.ALL_PACKAGES);
                        break;

                    case R.id.nav_on_the_way:
                        presenter.setFiltering(PackageFilterType.ON_THE_WAY_PACKAGES);
                        break;

                    case R.id.nav_delivered:
                        presenter.setFiltering(PackageFilterType.DELIVERED_PACKAGES);
                        break;
                }
                //设置了过滤规则后，重新加载包数据
                presenter.loadPackages();
                return true;
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshPackages();
            }
        });

        setHasOptionsMenu(true);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //这个subscribe()实际上直接调用了loadPackage()方法，加载所有的包裹数据
        presenter.subscribe();

    }

    @Override
    public void onPause() {
        super.onPause();
        //这里unsubscribe()实际上是将CompositeDisposable中的所有Disposable对象丢弃
        presenter.unsubscribe();
        setLoadingIndicator(false);
        getActivity().sendBroadcast(AppWidgetProvider.getRefreshBroadcastIntent(getContext()));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.packages_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            startActivity(new Intent(getContext(), SearchActivity.class));
        } else if (id == R.id.action_mark_all_read) {
            presenter.markAllPacksRead();
        }
        return true;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item == null || selectedPackageNumber == null) {
            return false;
        }
        switch (item.getItemId()) {
            case R.id.action_set_readable:
                presenter.setPackageReadable(getSelectedPackageNumber()
                        ,!item.getTitle().equals(R.string.set_read));
                break;
            case R.id.action_copy_code:
                copyPackageNumber();
                break;
            case R.id.action_search:
                presenter.setShareData(getSelectedPackageNumber());
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    public void initViews(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottomNavigationView);
        emptyView = (LinearLayout) view.findViewById(R.id.emptyView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));


        //ItemTouchHelper用来处理滑动删除和拖动效果
        //TODO：这里后续可以加入拖动重排序和滑动删除功能
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                log("ItemTouchHelper -- onMove");
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                log("ItemTouchHelper -- onMove");


            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                log("ItemTouchHelper -- onMove");

                super.clearView(recyclerView, viewHolder);
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                log("ItemTouchHelper -- onMove");

                return 0;
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                super.onSelectedChanged(viewHolder, actionState);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void setPresenter(PackagesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        //这里直接用一个View调用post()方法，实际上也是加入到MessageQueue中的
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(active);
            }
        });
    }


    @Override
    public void showEmptyView(boolean toShow) {
        if (toShow) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showPackages(@NonNull final List<Package> list) {
        if (adapter == null) {
            adapter = new PackagesAdapter(getContext(), list);
            adapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void OnItemClick(View v, int pos) {
                    Intent intent = new Intent(getContext(), PackageDetailsActivity.class);
                    intent.putExtra(PackageDetailsActivity.PACKAGE_ID, list.get(pos).getNumber());
                    startActivity(intent);
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateData(list);
        }
        //如果list数据为空，则展示空列表
        showEmptyView(list.isEmpty());
    }

    @Override
    public void shareTo(@NonNull Package pack) {
        StringBuilder shareData = new StringBuilder(pack.getName())
                .append("\n( ")
                .append(pack.getNumber())
                .append(" ")
                .append(pack.getCompanyChineseName())
                .append(" )\n")
                .append(getString(R.string.latest_status));
        if (pack.getData() != null && !pack.getData().isEmpty()) {
            shareData = shareData.append(pack.getData().get(0).getContext())
                    .append(pack.getData().get(0).getFtime());
        } else {
            shareData = shareData.append(getString(R.string.get_status_error));
        }
        try {
            Intent intent = new Intent().setAction(Intent.ACTION_SEND).setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, shareData.toString());
            startActivity(Intent.createChooser(intent, getString(R.string.share)));

        } catch (ActivityNotFoundException e) {
            Snackbar.make(fab, R.string.something_wrong, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showPackageRemovedMsg(String packageName) {
        String msg = packageName
                + " "
                + getString(R.string.package_removed_msg);
        Snackbar.make(fab, msg, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.recoverPackage();
                    }
                })
                .show();
    }

    @Override
    public void copyPackageNumber() {
        ClipboardManager manager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", getSelectedPackageNumber());
        manager.setPrimaryClip(clipData);
        Snackbar.make(fab, R.string.package_number_copied, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showNetworkError() {
        Snackbar.make(fab, R.string.network_error, Snackbar.LENGTH_SHORT)
                .setAction(R.string.go_to_settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent().setAction(Settings.ACTION_SETTINGS));
                    }
                })
                .show();
    }

    private String getSelectedPackageNumber() {
        return selectedPackageNumber;
    }

    public void setSelectedPackageNumber(@NonNull String s) {
        this.selectedPackageNumber = s;
    }
    public void log(String str) {
        Log.i("tag", str);
    }
}
