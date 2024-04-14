package ru.sevagrbnv.cinemaapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sevagrbnv.cinemaapp.data.CinemaRepositoryImpl
import ru.sevagrbnv.cinemaapp.data.remote.HeaderInterceptor
import ru.sevagrbnv.cinemaapp.data.remote.RemoteDataSource
import ru.sevagrbnv.cinemaapp.data.remote.RetrofitService
import ru.sevagrbnv.cinemaapp.domain.CinemaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(HeaderInterceptor())
            .build()

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient) = Retrofit.Builder()
            .baseUrl("https://api.kinopoisk.dev/v1.4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun providesRetrofitService(retrofit: Retrofit): RetrofitService =
        retrofit.create(RetrofitService::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        service: RetrofitService,
    ): RemoteDataSource {
        return RemoteDataSource(service)
    }


    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RemoteDataSource,
    ): CinemaRepository {
        return CinemaRepositoryImpl(
            remoteDataSource = remoteDataSource
        )
    }

}