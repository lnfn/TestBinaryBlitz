package com.eugenetereshkov.testbinaryblitz.di

import com.eugenetereshkov.testbinaryblitz.di.scopes.FragmentScope
import com.eugenetereshkov.testbinaryblitz.ui.EditUserFragment
import com.eugenetereshkov.testbinaryblitz.ui.UsersListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@Module
interface MainActivityModule {
    @FragmentScope
    @ContributesAndroidInjector
    fun usersListFragmentInjector(): UsersListFragment

    @FragmentScope
    @ContributesAndroidInjector
    fun editUserFragmentInjector(): EditUserFragment
}