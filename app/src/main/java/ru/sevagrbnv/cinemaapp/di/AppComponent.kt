package ru.sevagrbnv.cinemaapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.sevagrbnv.cinemaapp.presentation.MainActivity
import ru.sevagrbnv.cinemaapp.presentation.cinemaDetail.DetailFragment
import ru.sevagrbnv.cinemaapp.presentation.cinemaList.CinemaListFragment
import javax.inject.Scope

//@Scope
//@Retention(AnnotationRetention.RUNTIME)
//annotation class AppScope()
//
//@AppScope
//@Component(
//    modules = [
//        DataModule::class,
//        ViewModelModule::class
//    ]
//)
//interface AppComponent {
//
//    fun inject(activity: MainActivity)
//
//    fun inject(fragment: CinemaListFragment)
//
//    fun inject(fragment: DetailFragment)
//
//    @Component.Factory
//    interface Factory {
//
//        fun create(
//            @BindsInstance application: Application
//        ): AppComponent
//    }
//}