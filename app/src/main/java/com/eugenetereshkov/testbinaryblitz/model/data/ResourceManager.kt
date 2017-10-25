package com.eugenetereshkov.testbinaryblitz.model.data

import android.content.Context
import javax.inject.Inject

/**
 * Created by eugenetereshkov on 25.10.2017.
 */
class ResourceManager @Inject constructor(private val context: Context) {

    fun getString(id: Int): String = context.getString(id)
}