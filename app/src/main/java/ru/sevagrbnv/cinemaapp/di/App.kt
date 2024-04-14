package ru.sevagrbnv.cinemaapp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() //{
//    val component: AppComponent by lazy {
//        DaggerAppComponent.factory().create(this)
//    }
//}