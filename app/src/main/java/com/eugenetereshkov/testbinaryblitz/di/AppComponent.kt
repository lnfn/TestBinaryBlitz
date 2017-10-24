package com.eugenetereshkov.testbinaryblitz.di

import android.content.Context
import com.eugenetereshkov.testbinaryblitz.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@Singleton
@Component(
        modules = arrayOf(
                AndroidSupportInjectionModule::class,
                ActivityModule::class,
                NetworkModule::class,
                NavigationModule::class
        )
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}