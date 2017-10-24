package com.eugenetereshkov.testbinaryblitz.model.data.server

import com.eugenetereshkov.testbinaryblitz.entity.User
import io.reactivex.Single
import retrofit2.http.GET

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
interface BinaryBlitzApi {
    @GET("users.json")
    fun getUsers(): Single<List<User>>
}