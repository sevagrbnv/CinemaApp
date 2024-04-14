package ru.sevagrbnv.cinemaapp.di
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import dagger.Binds
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import dagger.internal.Provider
//import dagger.multibindings.IntoMap
//import ru.sevagrbnv.cinemaapp.presentation.ViewModelFactory
//import ru.sevagrbnv.cinemaapp.presentation.cinemaDetail.DetailViewModel
//import ru.sevagrbnv.cinemaapp.presentation.cinemaList.CinemaListViewModel
//
//@Module
//@InstallIn(SingletonComponent::class)
//interface ViewModelModule {
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(DetailViewModel::class)
//    fun bindDetailViewModel(viewModel: DetailViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(CinemaListViewModel::class)
//    fun bindCinemaListViewModel(viewModel: CinemaListViewModel): ViewModel
//
//    companion object {
//
//        @Provides
//        fun provideViewModelFactory(
//            map: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
//        ): ViewModelProvider.Factory {
//            return ViewModelFactory(map)
//        }
//    }
//}