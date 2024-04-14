package ru.sevagrbnv.cinemaapp.presentation.cinemaDetail

import ru.sevagrbnv.cinemaapp.domain.models.CinemaDetail

sealed class DetailScreenState {

    data object Loading : DetailScreenState()

    data object Error : DetailScreenState()

    data class Success(val data: CinemaDetail) : DetailScreenState()
}