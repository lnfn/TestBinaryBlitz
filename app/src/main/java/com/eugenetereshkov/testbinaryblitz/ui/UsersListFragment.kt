package com.eugenetereshkov.testbinaryblitz.ui


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.eugenetereshkov.testbinaryblitz.R
import com.eugenetereshkov.testbinaryblitz.di.Injectable
import com.eugenetereshkov.testbinaryblitz.entity.User
import com.eugenetereshkov.testbinaryblitz.presentation.userslist.UsersListAdapter
import com.eugenetereshkov.testbinaryblitz.presentation.userslist.UsersListPresenter
import com.eugenetereshkov.testbinaryblitz.presentation.userslist.UsersListView
import kotlinx.android.synthetic.main.fragment_users_list.*
import javax.inject.Inject


class UsersListFragment : BaseFragment(), UsersListView, Injectable {
    @Inject
    @InjectPresenter
    lateinit var usersListPresenter: UsersListPresenter
    override val idResLayout: Int = R.layout.fragment_users_list
    private val adapter = UsersListAdapter()

    @ProvidePresenter
    fun provideUsersListPresenter(): UsersListPresenter = usersListPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.apply {
            title = getString(R.string.users_list)
            inflateMenu(R.menu.main_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.addUserAction -> usersListPresenter.addUser()
                }
                true
            }
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@UsersListFragment.adapter
        }

        usersListPresenter.getUsers()
    }

    override fun setItems(users: List<User>) {
        adapter.updateItems(users)
    }
}