package ru.sevagrbnv.cinemaapp.presentation.cinemaList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.sevagrbnv.cinemaapp.domain.models.Cinema
import ru.sevagrbnv.cinemaapp.domain.useCases.GetPagingCinemasUseCase
import javax.inject.Inject


@HiltViewModel
class CinemaListViewModel @Inject constructor(
    private val getPagingCinemasUseCase: GetPagingCinemasUseCase
) : ViewModel() {

    val searchQueryPublisher = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        getData()
        listenToSearchQuery()
    }

    private var searchName: String? = null
    private var yearFilter: String? = null
    private var ratingFilter: String? = null
    private var countryFilter: String? = null

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun listenToSearchQuery() {
        searchQueryPublisher
            .filter { it.isNotEmpty() }
            .distinctUntilChanged()
            .debounce(1000)
            .onEach { resetFilters() }
            .mapLatest { searchByTitle(it) }
            .flowOn(Dispatchers.Default)
            .onEach { getData() }
            .launchIn(viewModelScope)
    }

    private fun searchByTitle(title: String) {
        searchName = if (title.length > 1) title else null
    }

    private val _data = MutableStateFlow<PagingData<Cinema>>(PagingData.empty())
    val data: StateFlow<PagingData<Cinema>>
        get() = _data

    private fun getData() {
        viewModelScope.launch {
            getPagingCinemasUseCase.invoke(
                year = yearFilter,
                rating = ratingFilter,
                country = countryFilter,
                name = searchName
            )
                .cachedIn(viewModelScope)
                .collect { pagingData ->
                    _data.value = pagingData
                }
        }
    }

    fun setFilters(year: String, country: String, rating: String) {
        yearFilter = format(year)
        countryFilter = format(country)
        ratingFilter = format(rating)
        getData()
    }

    private fun resetFilters() {
        searchName = null
        yearFilter = null
        countryFilter = null
        ratingFilter = null
    }


    private fun format(string: String): String? = if (string.isBlank()) null else string.trim()
}