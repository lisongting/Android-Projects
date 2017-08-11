package com.paperfish.espresso.appwidget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.paperfish.espresso.R;
import com.paperfish.espresso.data.Package;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.Sort;

import static com.paperfish.espresso.realm.RealmHelper.DATABASE_NAME;
/**
 * Created by lisongting on 2017/8/4.
 */

public class WidgetListFactory  implements RemoteViewsService.RemoteViewsFactory{

    private final Context context;
    private String statusError;
    private String[] packageStatus;


    public WidgetListFactory(Context applicationContext) {
        this.context = applicationContext;
        statusError = context.getString(R.string.get_status_error);
        packageStatus = context.getResources().getStringArray(R.array.package_status);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DATABASE_NAME)
                .build())
                .where(Package.class)
                .notEqualTo("state", String.valueOf(Package.STATUS_DELIVERED))
                .findAllSorted("timeStamp", Sort.DESCENDING)
                .size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.item_package_for_widget);

        Realm rlm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DATABASE_NAME)
                .build());

        //找到已经派送的包裹
        List<Package> result = rlm.copyFromRealm(rlm.where(Package.class)
                .notEqualTo("state", String.valueOf(Package.STATUS_DELIVERED))
                .findAllSorted("timestamp", Sort.DESCENDING));

        Package p = result.get(position);
        if (p.getData() != null && p.getData().size() > 0) {
            int state = Integer.parseInt(p.getState());
            //设置RemoteView的内容
            remoteViews.setTextViewText(R.id.textViewStatus, String.valueOf(packageStatus[state])
                    + " - " + p.getData().get(0).getContext());
            remoteViews.setTextViewText(R.id.textViewTime, p.getData().get(0).getTime());
        } else {
            remoteViews.setTextViewText(R.id.textViewStatus, statusError);
            remoteViews.setTextViewText(R.id.textViewTime, "");
        }

        remoteViews.setTextViewText(R.id.textViewPackageName, p.getName());
        remoteViews.setTextViewText(R.id.textViewAvatar,p.getName().substring(0,1));

        Intent intent = new Intent();
        intent.putExtra(PackageDetailsActivity.PACKAGE_ID, p.getNumber());
        remoteViews.setOnClickFillInIntent(R.id.layoutPackageItemMain, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
