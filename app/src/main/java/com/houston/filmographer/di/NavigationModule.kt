package com.houston.filmographer.di

import com.houston.filmographer.navigation.NavigatorHolder
import com.houston.filmographer.navigation.Router
import com.houston.filmographer.navigation.RouterImpl
import org.koin.dsl.module

val navigationModule = module {
    val router = RouterImpl()
    single <Router> { router }
    single <NavigatorHolder> { router.navigatorHolder }
}