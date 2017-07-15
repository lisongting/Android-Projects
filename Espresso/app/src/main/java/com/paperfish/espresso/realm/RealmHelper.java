package com.paperfish.espresso.realm;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by lisongting on 2017/7/15.
 */

public class RealmHelper {
    public static final String DATABASE_NAME = "Espresso.realm";

    public static Realm newRealmInstance() {
        return Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DATABASE_NAME)
                .build());
    }
}
