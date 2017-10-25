package com.eugenetereshkov.testbinaryblitz.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@Parcelize
data class User(
        @SerializedName("id") var id: Long = 0,
        @SerializedName("first_name") var firstName: String = "",
        @SerializedName("last_name") var lastName: String = "",
        @SerializedName("email") var email: String = "",
        @SerializedName("avatar_url") var avatarURL: String? = null,
        @SerializedName("url") var url: String = ""
) : Parcelable