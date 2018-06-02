package com.lst.wanandroid.di.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module(subcomponents = BaseDialogFragmentComponent.class)
public abstract class AbstractAllDialogFragmentModule {

    @ContributesAndroidInjector(modules = SearchDialogFragmentModule.class)
    abstract SearchDialogFragment contributesSearchDialogFragmentInject();

    @ContributesAndroidInjector(modules = UsageDialogFragmentModule.class)
    abstract UsageDialogFragment contributesUsageDialogFragmentInject();

}
