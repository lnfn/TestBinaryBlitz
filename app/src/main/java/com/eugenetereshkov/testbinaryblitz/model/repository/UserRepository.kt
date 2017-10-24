package com.eugenetereshkov.testbinaryblitz.model.repository

import com.eugenetereshkov.testbinaryblitz.model.data.server.BinaryBlitzApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
class UserRepository @Inject constructor(
        private val binaryBlitzApi: BinaryBlitzApi
) {
    fun getUsers() =
            binaryBlitzApi.getUsers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}