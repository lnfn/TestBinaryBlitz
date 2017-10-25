package com.eugenetereshkov.testbinaryblitz.presentation.edituser

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.eugenetereshkov.testbinaryblitz.R
import com.eugenetereshkov.testbinaryblitz.di.qualifiers.UserDetail
import com.eugenetereshkov.testbinaryblitz.entity.EmailError
import com.eugenetereshkov.testbinaryblitz.entity.FirstNameError
import com.eugenetereshkov.testbinaryblitz.entity.LastNameError
import com.eugenetereshkov.testbinaryblitz.entity.User
import com.eugenetereshkov.testbinaryblitz.extentions.bindTo
import com.eugenetereshkov.testbinaryblitz.extentions.userMessage
import com.eugenetereshkov.testbinaryblitz.extentions.validate
import com.eugenetereshkov.testbinaryblitz.model.data.ResourceManager
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
        private val resourceManager: ResourceManager,
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

    fun saveUser(user: User) {
        val errors = user.validate(resourceManager)

        if (errors.isNotEmpty()) {
            errors.forEach {
                when (it) {
                    is FirstNameError -> viewState.showErrorFirstName(it.msg)
                    is LastNameError -> viewState.showErrorLastName(it.msg)
                    is EmailError -> viewState.showErrorEmail(it.msg)
                }
            }
            return
        }

        when (action) {
            ACTION.CREATE -> createUser(user)
            ACTION.EDIT -> editUser(user)
        }
    }

    private fun createUser(newUser: User) {
        newUser.avatarURL = user.avatarURL
        userRepository.createUser(newUser)
                .doOnSuccess { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe(
                        { response ->
                            Timber.d(response.string())
                            router.showSystemMessage(resourceManager.getString(R.string.success))
                            router.exitWithResult(EDIT_USER_RESULT, true)
                        },
                        {
                            router.showSystemMessage(it.userMessage(resourceManager))
                            Timber.d(it.message)
                        }
                )
                .bindTo(disposable)
    }

    private fun editUser(editUser: User) {
        editUser.apply {
            id = user.id
            avatarURL = user.avatarURL
        }
        userRepository.editUser(editUser)
                .doOnSuccess { viewState.showProgress(true) }
                .doAfterTerminate { viewState.showProgress(false) }
                .subscribe(
                        { response ->
                            Timber.d(response.string())
                            router.showSystemMessage(resourceManager.getString(R.string.success))
                            router.exitWithResult(EDIT_USER_RESULT, true)
                        },
                        {
                            Timber.d(it.message)
                            router.showSystemMessage(it.userMessage(resourceManager))
                        }
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

    companion object {
        const val EDIT_USER_RESULT = 101
    }

    enum class ACTION {
        CREATE, EDIT

    }
}