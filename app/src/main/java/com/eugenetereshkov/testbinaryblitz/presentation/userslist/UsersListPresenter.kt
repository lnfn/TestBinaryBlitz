package com.eugenetereshkov.testbinaryblitz.presentation.userslist

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.eugenetereshkov.testbinaryblitz.Screens
import com.eugenetereshkov.testbinaryblitz.entity.User
import com.eugenetereshkov.testbinaryblitz.extentions.bindTo
import com.eugenetereshkov.testbinaryblitz.model.repository.UserRepository
import com.eugenetereshkov.testbinaryblitz.presentation.edituser.EditUserPresenter
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@InjectViewState
class UsersListPresenter @Inject constructor(
        private val router: Router,
        private val userRepository: UserRepository
) : MvpPresenter<UsersListView>() {

    private var data = listOf<User>()
    private val disposabe = CompositeDisposable()

    override fun onFirstViewAttach() {
        if (data.isEmpty()) loadFromNetwork()
        viewState.setItems(data)

        router.setResultListener(EditUserPresenter.EDIT_USER_RESULT, {
            if (it as Boolean) loadFromNetwork()
        })
    }

    private fun loadFromNetwork() {
        userRepository.getUsers()
                .doOnSubscribe { if (data.isEmpty()) viewState.showEmptyLoading(true) }
                .doAfterTerminate { viewState.showEmptyLoading(false) }
                .subscribe(
                        { users ->
                            data = users
                            Timber.d(users.toString())
                            viewState.setItems(users)

                        },
                        { Timber.d(it.message) }
                )
                .bindTo(disposabe)
    }

    override fun onDestroy() {
        router.removeResultListener(EditUserPresenter.EDIT_USER_RESULT)
        disposabe.clear()
        super.onDestroy()
    }

    fun addUser() {
        router.navigateTo(Screens.EDIT_USER_SCREEN)
    }

    fun onUserClicked(user: User) {
        router.navigateTo(Screens.EDIT_USER_SCREEN, user)
    }
}