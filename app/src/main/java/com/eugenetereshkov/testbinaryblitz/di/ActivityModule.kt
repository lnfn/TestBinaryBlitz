package com.eugenetereshkov.testbinaryblitz.di

import com.eugenetereshkov.testbinaryblitz.di.scopes.ActivityScope
import com.eugenetereshkov.testbinaryblitz.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@Module
interface ActivityModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = arrayOf(MainActivityModule::class))
    fun mainActivityInjector(): MainActivity
}