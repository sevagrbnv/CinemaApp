package ru.sevagrbnv.cinemaapp.domain.useCases

import ru.sevagrbnv.cinemaapp.domain.CinemaRepository
import javax.inject.Inject

class GetPagingReviewsUseCase @Inject constructor(
    private val repository: CinemaRepository
) {

    operator fun invoke(cinemaId: Int) = repository.getPagingReviews(cinemaId)
}