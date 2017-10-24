package com.eugenetereshkov.testbinaryblitz.ui


import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.eugenetereshkov.testbinaryblitz.R
import com.eugenetereshkov.testbinaryblitz.di.Injectable
import com.eugenetereshkov.testbinaryblitz.presentation.edituser.EditUserPresenter
import com.eugenetereshkov.testbinaryblitz.presentation.edituser.EditUserView
import kotlinx.android.synthetic.main.fragment_edit_user.*
import javax.inject.Inject


class EditUserFragment : BaseFragment(), Injectable, EditUserView {
    @Inject
    @InjectPresenter
    lateinit var editUserPresenter: EditUserPresenter
    override val idResLayout: Int = R.layout.fragment_edit_user

    @ProvidePresenter
    fun provideEditUserPresenter() = editUserPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.apply {
            title = getString(R.string.edit_user)
            setNavigationOnClickListener { editUserPresenter.onBackPressed() }
        }
    }
}