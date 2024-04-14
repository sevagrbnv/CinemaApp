package ru.sevagrbnv.cinemaapp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import ru.sevagrbnv.cinemaapp.data.remote.pagingSources.ActorPagingSource
import ru.sevagrbnv.cinemaapp.data.remote.pagingSources.CinemaListPagingSource
import ru.sevagrbnv.cinemaapp.data.remote.pagingSources.PosterPagingSource
import ru.sevagrbnv.cinemaapp.data.remote.pagingSources.ReviewPagingSource
import ru.sevagrbnv.cinemaapp.domain.models.CinemaDetail
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val service: RetrofitService,
) {

    fun getCinemas(
        year: String? = null,
        rating: String? = null,
        country: String? = null,
        name: String? = null
    ) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = {
            CinemaListPagingSource(
                service = service,
                year = year,
                rating = rating,
                country = country,
                name = name
            )
        }
    ).flow

    suspend fun getCinemaInfo(id: Int): CinemaDetail? {
        val response = service.getCinemaById(id = id)
        return if (response.isSuccessful) {
            checkNotNull(response.body()).toCinemaDetail()
        } else null
    }

    fun getReviews(
        cinemaId: Int
    ) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { ReviewPagingSource(service, cinemaId) }
    ).flow

    fun getActors(
        cinemaId: Int
    ) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { ActorPagingSource(service, cinemaId) }
    ).flow

    fun getPosters(
        cinemaId: Int
    ) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { PosterPagingSource(service, cinemaId) }
    ).flow
}