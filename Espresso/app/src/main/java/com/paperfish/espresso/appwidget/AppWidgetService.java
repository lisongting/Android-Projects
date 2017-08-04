package com.paperfish.espresso.appwidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by lisongting on 2017/8/4.
 */

public class AppWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new WidgetListFactory(getApplicationContext());
    }
}
