package com.eugenetereshkov.testbinaryblitz.di

import com.eugenetereshkov.testbinaryblitz.di.qualifiers.UserDetail
import com.eugenetereshkov.testbinaryblitz.di.scopes.FragmentScope
import com.eugenetereshkov.testbinaryblitz.entity.User
import com.eugenetereshkov.testbinaryblitz.ui.EditUserFragment
import dagger.Module
import dagger.Provides

/**
 * Created by eugenetereshkov on 25.10.2017.
 */
@Module
class EditUserFragmentModule {
    @FragmentScope
    @Provides
    @UserDetail
    fun provideUser(editUserFragment: EditUserFragment): User =
            editUserFragment.arguments.getParcelable(EditUserFragment.USER_DATA)
}