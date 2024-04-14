package ru.sevagrbnv.cinemaapp.domain.useCases

import ru.sevagrbnv.cinemaapp.domain.CinemaRepository
import javax.inject.Inject

class GetPagingPostersUseCase @Inject constructor(
    private val repository: CinemaRepository
) {

    operator fun invoke(cinemaId: Int) = repository.getPagingPosters(cinemaId)
}