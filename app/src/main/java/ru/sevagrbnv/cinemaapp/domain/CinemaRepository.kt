package ru.sevagrbnv.cinemaapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.sevagrbnv.cinemaapp.domain.models.Actor
import ru.sevagrbnv.cinemaapp.domain.models.Cinema
import ru.sevagrbnv.cinemaapp.domain.models.CinemaDetail
import ru.sevagrbnv.cinemaapp.domain.models.Poster
import ru.sevagrbnv.cinemaapp.domain.models.Review

interface CinemaRepository {

    fun getPagingCinemas(
        year: String?,
        rating: String?,
        country: String?,
        name: String?
    ): Flow<PagingData<Cinema>>

    suspend fun getCinemaDetails(id: Int): CinemaDetail?

    fun getPagingActors(
        cinemaId: Int
    ): Flow<PagingData<Actor>>

    fun getPagingPosters(
        cinemaId: Int
    ): Flow<PagingData<Poster>>

    fun getPagingReviews(
        cinemaId: Int
    ): Flow<PagingData<Review>>
}