package com.eugenetereshkov.testbinaryblitz.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
data class User(
        @SerializedName("id") val id: Long,
        @SerializedName("first_name") val firstName: String,
        @SerializedName("last_name") val lastName: String,
        @SerializedName("email") val email: String?,
        @SerializedName("avatar_url") val avatarURL: String?,
        @SerializedName("url") val url: String
)