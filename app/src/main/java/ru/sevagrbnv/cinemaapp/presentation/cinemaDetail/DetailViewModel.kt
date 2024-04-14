package ru.sevagrbnv.cinemaapp.presentation.cinemaDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.sevagrbnv.cinemaapp.domain.models.Actor
import ru.sevagrbnv.cinemaapp.domain.models.CinemaDetail
import ru.sevagrbnv.cinemaapp.domain.models.Poster
import ru.sevagrbnv.cinemaapp.domain.models.Review
import ru.sevagrbnv.cinemaapp.domain.useCases.GetCinemaDetailsUseCase
import ru.sevagrbnv.cinemaapp.domain.useCases.GetPagingActorsUseCase
import ru.sevagrbnv.cinemaapp.domain.useCases.GetPagingPostersUseCase
import ru.sevagrbnv.cinemaapp.domain.useCases.GetPagingReviewsUseCase
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCinemaDetailsUseCase: GetCinemaDetailsUseCase,
    private val getPagingActorsUseCase: GetPagingActorsUseCase,
    private val getPagingPostersUseCase: GetPagingPostersUseCase,
    private val getPagingReviewsUseCase: GetPagingReviewsUseCase
) : ViewModel() {

    var id: Int? = null
        set(value) {
            field = value
            getDetails()
            getReviews()
            getActors()
            getPosters()
        }

    private val _data = MutableStateFlow<DetailScreenState>(DetailScreenState.Loading)
    val data: StateFlow<DetailScreenState>
        get() = _data

    private val _reviews = MutableStateFlow<PagingData<Review>>(PagingData.empty())
    val reviews: StateFlow<PagingData<Review>>
        get() = _reviews

    private fun getReviews() {
        viewModelScope.launch {
            getPagingReviewsUseCase
                .invoke(checkNotNull(id))
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _reviews.value = pagingData
                }
        }
    }

    private val _posters = MutableStateFlow<PagingData<Poster>>(PagingData.empty())
    val posters: StateFlow<PagingData<Poster>>
        get() = _posters

    private fun getPosters() {
        viewModelScope.launch {
            getPagingPostersUseCase
                .invoke(checkNotNull(id))
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _posters.value = pagingData
                }
        }
    }

    private val _actors = MutableStateFlow<PagingData<Actor>>(PagingData.empty())
    val actors: StateFlow<PagingData<Actor>>
        get() = _actors

    private fun getActors() {
        viewModelScope.launch {
            getPagingActorsUseCase.invoke(checkNotNull(id))
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _actors.value = pagingData
                }
        }
    }

    private fun getDetails() {
        viewModelScope.launch {
            var response: CinemaDetail? = null

            viewModelScope.launch(Dispatchers.IO) {
                response =
                    getCinemaDetailsUseCase.invoke(checkNotNull(id))
            }.join()

            if (response != null) {
                _data.tryEmit(
                    DetailScreenState.Success(
                        response ?: throw IllegalStateException("Response is null")
                    )
                )
            } else _data.tryEmit(DetailScreenState.Error)

        }
    }
}