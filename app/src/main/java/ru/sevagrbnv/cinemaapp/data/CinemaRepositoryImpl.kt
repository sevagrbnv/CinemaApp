package ru.sevagrbnv.cinemaapp.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.sevagrbnv.cinemaapp.data.remote.RemoteDataSource
import ru.sevagrbnv.cinemaapp.domain.CinemaRepository
import javax.inject.Inject

class CinemaRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : CinemaRepository {

    override fun getPagingCinemas(
        year: String?,
        rating: String?,
        country: String?,
        name: String?
    ) = remoteDataSource.getCinemas(year, rating, country, name)

    override suspend fun getCinemaDetails(id: Int) = remoteDataSource.getCinemaInfo(id)

    override fun getPagingActors(
        cinemaId: Int
    ) = remoteDataSource.getActors(cinemaId)

    override fun getPagingPosters(
        cinemaId: Int
    ) = remoteDataSource.getPosters(cinemaId)

    override fun getPagingReviews(
        cinemaId: Int
    ) = remoteDataSource.getReviews(cinemaId)
}

//object RetrofitClientInstance {
//    private var retrofit: Retrofit? = null
//    private const val BASE_URL = "https://api.kinopoisk.dev/v1.4/"
//    val retrofitInstance: Retrofit?
//        get() {
//            if (retrofit == null) {
//                val httpClient = OkHttpClient.Builder()
//                val logging = HttpLoggingInterceptor()
//                logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
//                httpClient.addInterceptor(logging)
//                retrofit = Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClient.build())
//                    .build()
//            }
//            return retrofit
//        }
//}