package com.eugenetereshkov.testbinaryblitz.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
abstract class BaseFragment : Fragment() {
    protected abstract val idResLayout: Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(idResLayout, container, false)
    }
}