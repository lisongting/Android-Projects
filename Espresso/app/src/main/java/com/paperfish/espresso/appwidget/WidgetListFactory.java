package com.paperfish.espresso.appwidget;

import android.content.Context;
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
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.item_package_for_widget);

        Realm rlm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DATABASE_NAME)
                .build());

        List<Package> result = rlm.copyFromRealm()
        return null;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
