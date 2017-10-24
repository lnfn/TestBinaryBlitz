package com.eugenetereshkov.testbinaryblitz.di

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * Created by eugenetereshkov on 24.10.2017.
 */
@Module
class NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    fun provideRouter(): Router = cicerone.router

    @Singleton
    @Provides
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder
}