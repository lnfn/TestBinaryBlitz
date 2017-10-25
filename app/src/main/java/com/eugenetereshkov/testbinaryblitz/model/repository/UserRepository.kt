package com.eugenetereshkov.testbinaryblitz.model.repository

import com.eugenetereshkov.testbinaryblitz.entity.User
import com.eugenetereshkov.testbinaryblitz.model.data.server.BinaryBlitzApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
class UserRepository @Inject constructor(
        private val binaryBlitzApi: BinaryBlitzApi
) {
    fun getUsers(): Single<List<User>> =
            binaryBlitzApi.getUsers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun createUser(user: User): Single<ResponseBody> =
            binaryBlitzApi.createUser(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    fun editUser(idUser: Long): Single<ResponseBody> =
            binaryBlitzApi.editUser(idUser)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
}