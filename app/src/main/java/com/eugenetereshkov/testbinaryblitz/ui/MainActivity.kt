package com.eugenetereshkov.testbinaryblitz.ui

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.eugenetereshkov.testbinaryblitz.R
import com.eugenetereshkov.testbinaryblitz.Screens
import com.eugenetereshkov.testbinaryblitz.entity.User
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), HasSupportFragmentInjector {
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var router: Router
    @Inject lateinit var navigationHolder: NavigatorHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            router.replaceScreen(Screens.USERS_LIST_SCREEN)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    private val navigator = object : SupportAppNavigator(this, R.id.container) {
        override fun createActivityIntent(screenKey: String?, data: Any?): Intent? = null

        override fun createFragment(screenKey: String?, data: Any?): Fragment? = when (screenKey) {
            Screens.USERS_LIST_SCREEN -> UsersListFragment()
            Screens.EDIT_USER_SCREEN -> EditUserFragment.newInstants(data as? User ?: User())
            else -> null
        }

        override fun applyCommand(command: Command) {
            when (command) {
                is Replace -> {
                    supportFragmentManager.beginTransaction()
                            .add(R.id.container, UsersListFragment(), Screens.USERS_LIST_SCREEN)
                            .commit()
                }
                else -> super.applyCommand(command)
            }
        }
    }
}