package com.eugenetereshkov.testbinaryblitz.extentions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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