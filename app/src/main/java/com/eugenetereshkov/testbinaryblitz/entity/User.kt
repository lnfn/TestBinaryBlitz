package com.eugenetereshkov.testbinaryblitz.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@Parcelize
data class User(
        @SerializedName("id") val id: Long = 0,
        @SerializedName("first_name") val firstName: String = "",
        @SerializedName("last_name") val lastName: String = "",
        @SerializedName("email") val email: String? = null,
        @SerializedName("avatar_url") val avatarURL: String? = null,
        @SerializedName("url") val url: String = ""
) : Parcelable