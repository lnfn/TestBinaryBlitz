package com.eugenetereshkov.testbinaryblitz.extentions

import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.eugenetereshkov.testbinaryblitz.R
import com.eugenetereshkov.testbinaryblitz.entity.*
import com.eugenetereshkov.testbinaryblitz.model.data.ResourceManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
fun ViewGroup.inflate(idResLayout: Int): View =
        LayoutInflater.from(context).inflate(idResLayout, this, false)

fun Disposable.bindTo(disposable: CompositeDisposable) {
    disposable.add(this)
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

fun Throwable.userMessage(resourceManager: ResourceManager) = when (this) {
    is HttpException -> when (this.code()) {
        422 -> resourceManager.getString(R.string.unprocessable_error)
        else -> resourceManager.getString(R.string.unknown_error)
    }
    is IOException -> resourceManager.getString(R.string.network_error)
    else -> resourceManager.getString(R.string.unknown_error)
}

fun User.validate(resourceManager: ResourceManager): List<ValidateError> {
    val errors = mutableListOf<ValidateError>()

    if (this.firstName.isBlank()) errors.add(FirstNameError(resourceManager.getString(R.string.empty_field)))
    if (this.lastName.isBlank()) errors.add(LastNameError(resourceManager.getString(R.string.empty_field)))
    if (!Patterns.EMAIL_ADDRESS.matcher(this.email).matches())
        errors.add(EmailError(resourceManager.getString(R.string.empty_field)))

    return errors
}