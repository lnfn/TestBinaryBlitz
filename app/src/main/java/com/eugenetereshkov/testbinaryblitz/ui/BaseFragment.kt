package com.eugenetereshkov.testbinaryblitz.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
abstract class BaseFragment : MvpAppCompatFragment() {
    protected abstract val idResLayout: Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(idResLayout, container, false)
    }
}