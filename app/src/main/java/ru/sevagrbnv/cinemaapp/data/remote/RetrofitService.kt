package ru.sevagrbnv.cinemaapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import ru.sevagrbnv.cinemaapp.data.remote.models.actors.ActorList
import ru.sevagrbnv.cinemaapp.data.remote.models.detailMain.DetailInfo
import ru.sevagrbnv.cinemaapp.data.remote.models.listFragment.CinemaList
import ru.sevagrbnv.cinemaapp.data.remote.models.posters.PosterList
import ru.sevagrbnv.cinemaapp.data.remote.models.reviews.ReviewList

interface RetrofitService {

    @GET("movie")
    suspend fun getPagingArticle(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("year") year: String?,
        @Query("countries.name") country: String?,
        @Query("rating.kp") rating: String?,
    ) : Response<CinemaList>

    @GET("movie/search")
    suspend fun getPagingArticleByName(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("query") name: String,
    ) : Response<CinemaList>

    @GET("movie/{id}")
    suspend fun getCinemaById(
        @Path("id") id: Int
    ) : Response<DetailInfo>

    @GET("review")
    suspend fun getReviewByCinemaId(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("movieId") movieId: Int,
    ) : Response<ReviewList>

    @GET("person")
    suspend fun getActorsByCinemaId(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("movies.id") movieId: Int,
    ) : Response<ActorList>

    @GET("image")
    suspend fun getPostersByCinemaId(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("movieId") movieId: Int,
    ) : Response<PosterList>
}