package com.eugenetereshkov.testbinaryblitz.ui


import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.eugenetereshkov.testbinaryblitz.R
import com.eugenetereshkov.testbinaryblitz.di.Injectable
import com.eugenetereshkov.testbinaryblitz.entity.User
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
            title = when (editUserPresenter.action) {
                EditUserPresenter.ACTION.CREATE -> getString(R.string.create_user)
                EditUserPresenter.ACTION.EDIT -> getString(R.string.edit_user)
            }
            setNavigationOnClickListener {
                hideKeyboard()
                editUserPresenter.onBackPressed()
            }
            inflateMenu(R.menu.edit_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.saveUserAction -> editUserPresenter.saveUser()
                }
                true
            }
        }

        if (editUserPresenter.action == EditUserPresenter.ACTION.CREATE)
            imageView.setOnClickListener { editUserPresenter.getImage() }

        showKeyboard(firstNameEditText)
    }

    private fun showKeyboard(view: View) {
        view.post {
            view.requestFocus()
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, 0)
        }
    }

    override fun showUserData(user: User) {
        firstNameEditText.setText(user.firstName)
        lastNameEditText.setText(user.lastName)
        user.email?.let { emailEditText.setText(it) }

        Glide.with(context)
                .load(user.avatarURL)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .placeholder(R.drawable.circle)
                .error(R.drawable.circle)
                .into(object : BitmapImageViewTarget(imageView) {
                    override fun setResource(resource: Bitmap?) {
                        resource?.let {
                            RoundedBitmapDrawableFactory.create(view.resources, it)?.run {
                                this.isCircular = true
                                imageView?.setImageDrawable(this)
                            }
                        }
                    }
                })
    }

    companion object {
        const val USER_DATA = "user_data"
        fun newInstants(user: User) = EditUserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_DATA, user)
            }
        }
    }
}