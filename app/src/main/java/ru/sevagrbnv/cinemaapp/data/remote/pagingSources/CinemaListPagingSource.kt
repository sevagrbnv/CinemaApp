package ru.sevagrbnv.cinemaapp.data.remote.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.sevagrbnv.cinemaapp.data.remote.RetrofitService
import ru.sevagrbnv.cinemaapp.data.remote.toCinema
import ru.sevagrbnv.cinemaapp.domain.models.Cinema

class CinemaListPagingSource(
    private val service: RetrofitService,
    private val year: String?,
    private val rating: String?,
    private val country: String?,
    private val name: String?
) : PagingSource<Int, Cinema>() {

    override fun getRefreshKey(state: PagingState<Int, Cinema>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cinema> {

        val page = params.key ?: 1
        val pageSize = params.loadSize

        val response = if (name == null) {
            service.getPagingArticle(
                page = page,
                limit = params.loadSize,
                year = year,
                country = country,
                rating = rating
            )
        } else {
            service.getPagingArticleByName(
                page = page,
                limit = params.loadSize,
                name = name
            )
        }

        return if (response.isSuccessful) {
            val articles = checkNotNull(response.body()).docs.map { it.toCinema() }
            val nextKey = if (articles.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(articles, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(response))
        }
    }
}