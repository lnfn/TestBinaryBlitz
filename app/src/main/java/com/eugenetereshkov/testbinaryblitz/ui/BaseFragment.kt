package com.eugenetereshkov.testbinaryblitz.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
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

    protected fun showProgressDialog(progress: Boolean) {
        val fragment = childFragmentManager?.findFragmentByTag(PROGRESS_TAG)
        if (fragment != null && !progress) {
            (fragment as ProgressDialog).dismissAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        } else if (fragment == null && progress) {
            ProgressDialog().show(childFragmentManager, PROGRESS_TAG)
            childFragmentManager.executePendingTransactions()
        }
    }

    protected fun showKeyboard() {
        activity?.run {
            val view = window?.currentFocus
            view?.post {
                val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                inputMethodManager?.showSoftInput(view, 0)
            }
        }
    }

    protected fun hideKeyboard() {
        activity?.run {
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            inputMethodManager?.hideSoftInputFromWindow(window?.currentFocus?.windowToken, 0)
        }
    }

    companion object {
        private val PROGRESS_TAG = "bf_progress"
    }
}