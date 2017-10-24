package com.eugenetereshkov.testbinaryblitz.presentation.userslist

import com.arellomobile.mvp.MvpView
import com.eugenetereshkov.testbinaryblitz.entity.User

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
interface UsersListView : MvpView {
    fun setItems(users: List<User>)
}