package com.eugenetereshkov.testbinaryblitz.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.eugenetereshkov.testbinaryblitz.App
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import timber.log.Timber

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
class AppInjector private constructor() {
    companion object {
        fun init(app: App) {
            DaggerAppComponent.builder().context(app).build().inject(app)
            app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityPaused(p0: Activity?) {

                }

                override fun onActivityResumed(p0: Activity?) {

                }

                override fun onActivityStarted(p0: Activity?) {

                }

                override fun onActivityDestroyed(p0: Activity?) {

                }

                override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {

                }

                override fun onActivityStopped(p0: Activity?) {

                }

                override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                    handleActivity(p0)
                }
            })
        }

        private fun handleActivity(activity: Activity) {
            if (activity is HasSupportFragmentInjector) {
                Timber.d("Inject activity")
                AndroidInjection.inject(activity)
            }

            if (activity is FragmentActivity) {
                activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                        object : FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentAttached(fm: FragmentManager?, f: Fragment?, context: Context?) {
                                Timber.d("Inject fragment")

                                if (f is Injectable) {
                                    AndroidSupportInjection.inject(f)
                                }
                            }
                        }, true)
            }
        }
    }
}