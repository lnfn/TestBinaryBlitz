package com.eugenetereshkov.testbinaryblitz.model.data.server

import com.eugenetereshkov.testbinaryblitz.entity.User
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
interface BinaryBlitzApi {
    @GET("users.json")
    fun getUsers(): Single<List<User>>

    @POST("users.json")
    fun createUser(@Body user: User): Single<ResponseBody>

    @PATCH("users/{id}.json")
    fun editUser(@Path("id") userId: Long): Single<ResponseBody>
}