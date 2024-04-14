package ru.sevagrbnv.cinemaapp.domain.useCases

import ru.sevagrbnv.cinemaapp.domain.CinemaRepository
import javax.inject.Inject

class GetPagingCinemasUseCase @Inject constructor(
    private val repository: CinemaRepository
) {

    operator fun invoke(
        year: String?,
        country: String?,
        rating: String?,
        name: String?
    ) = repository.getPagingCinemas(
        year = year,
        country = country,
        rating = rating,
        name = name
    )
}