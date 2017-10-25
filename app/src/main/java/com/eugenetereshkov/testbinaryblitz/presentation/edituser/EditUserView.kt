package com.eugenetereshkov.testbinaryblitz.presentation.edituser

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.eugenetereshkov.testbinaryblitz.entity.User

/**
 * Created by eugenetereshkov on 24.10.2017.
 */

@StateStrategyType(AddToEndSingleStrategy::class)
interface EditUserView : MvpView {
    fun showUserData(user: User)
    fun showProgress(show: Boolean)
    fun showErrorFirstName(msg: String)
    fun showErrorLastName(msg: String)
    fun showErrorEmail(msg: String)
    fun setUploadImageProgress(percents: Int)
}
