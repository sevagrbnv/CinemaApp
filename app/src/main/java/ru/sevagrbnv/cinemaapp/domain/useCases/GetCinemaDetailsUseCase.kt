package ru.sevagrbnv.cinemaapp.domain.useCases

import ru.sevagrbnv.cinemaapp.domain.CinemaRepository
import javax.inject.Inject

class GetCinemaDetailsUseCase @Inject constructor (
    private val repository: CinemaRepository
) {

    suspend operator fun invoke(cinemaId: Int) = repository.getCinemaDetails(cinemaId)
}