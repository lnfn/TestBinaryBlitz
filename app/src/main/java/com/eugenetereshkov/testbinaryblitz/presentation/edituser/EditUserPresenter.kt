package com.eugenetereshkov.testbinaryblitz.presentation.edituser

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.eugenetereshkov.testbinaryblitz.di.qualifiers.UserDetail
import com.eugenetereshkov.testbinaryblitz.entity.User
import com.eugenetereshkov.testbinaryblitz.extentions.bindTo
import com.eugenetereshkov.testbinaryblitz.model.repository.UserRepository
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@InjectViewState
class EditUserPresenter @Inject constructor(
        private val userRepository: UserRepository,
        private val router: Router,
        @UserDetail val user: User
) : MvpPresenter<EditUserView>() {

    private val disposable = CompositeDisposable()
    val action: ACTION

    init {
        action = if (user.id == 0L) ACTION.CREATE else ACTION.EDIT
    }

    override fun onFirstViewAttach() {
        if (action == ACTION.EDIT) viewState.showUserData(user)
    }

    fun saveUser() {
        when (action) {
            ACTION.CREATE -> createUser()
            ACTION.EDIT -> editUser()
        }
    }

    private fun createUser() {
        userRepository.createUser(user)
                .subscribe(
                        { response -> Timber.d(response.string()) },
                        { Timber.d(it.message) }
                )
                .bindTo(disposable)
    }

    private fun editUser() {
        userRepository.editUser(user.id)
                .subscribe(
                        { response -> Timber.d(response.string()) },
                        { Timber.d(it.message) }
                )
                .bindTo(disposable)
    }

    fun getImage() {

    }

    fun onBackPressed() {
        router.exit()
    }

    override fun onDestroy() {
        disposable.clear()
        super.onDestroy()
    }

    enum class ACTION {
        CREATE, EDIT

    }
}