package com.eugenetereshkov.testbinaryblitz.ui


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
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
                    R.id.saveUserAction -> editUserPresenter.saveUser(
                            User(
                                    firstName = firstNameEditText.text.toString(),
                                    lastName = lastNameEditText.text.toString(),
                                    email = emailEditText.text.toString()
                            )
                    )
                }
                true
            }
        }

        if (editUserPresenter.action == EditUserPresenter.ACTION.CREATE)
            imageView.setOnClickListener {
                if (ContextCompat.checkSelfPermission(context,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_READ_EXTERNAL_STORAGE)
                } else {
                    makePickRequest()
                }
            }

        firstNameEditText.requestFocus()
        showKeyboard()
    }

    override fun setUploadImageProgress(percents: Int) {
        percentsTextView.text = "$percents %"
    }

    private fun makePickRequest() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GELLARY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GELLARY && data != null) {
            val selectImageURI = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            activity.contentResolver.query(selectImageURI, filePathColumn, null, null, null).use { cursor ->
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val imageDecode = cursor.getString(columnIndex)
                setUserImage(imageDecode)
                editUserPresenter.uploadImageToServer(imageDecode)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE && grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makePickRequest()
        }
    }

    override fun showUserData(user: User) {
        firstNameEditText.setText(user.firstName)
        lastNameEditText.setText(user.lastName)
        emailEditText.setText(user.email)
        setUserImage(user.avatarURL)
    }

    private fun setUserImage(url: String?) {
        Glide.with(context)
                .load(url)
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

    override fun showProgress(show: Boolean) {
        hideKeyboard()
        showProgressDialog(show)
    }

    override fun showErrorFirstName(msg: String) {
        firstNameTextInput.error = msg
    }

    override fun showErrorLastName(msg: String) {
        lastNameTextInput.error = msg
    }

    override fun showErrorEmail(msg: String) {
        emailTextInput.error = msg
    }

    companion object {
        const val REQUEST_GELLARY = 2
        const val REQUEST_READ_EXTERNAL_STORAGE = 1
        const val USER_DATA = "user_data"
        fun newInstants(user: User) = EditUserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(USER_DATA, user)
            }
        }
    }
}