package com.eugenetereshkov.testbinaryblitz.presentation.edituser

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.eugenetereshkov.testbinaryblitz.model.data.server.BinaryBlitzApi
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@InjectViewState
class EditUserPresenter @Inject constructor(
        private val binaryBlitzApi: BinaryBlitzApi,
        private val router: Router
) : MvpPresenter<EditUserView>() {

    fun onBackPressed() {
        router.exit()
    }
}