package ru.sevagrbnv.cinemaapp.data.remote.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.sevagrbnv.cinemaapp.data.remote.RetrofitService
import ru.sevagrbnv.cinemaapp.data.remote.toReview
import ru.sevagrbnv.cinemaapp.domain.models.Review

class ReviewPagingSource(
    private val service: RetrofitService,
    private val cinemaId: Int
) : PagingSource<Int, Review>() {

    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, Review> {

        val page = params.key ?: 1
        val pageSize = params.loadSize

        val response = service.getReviewByCinemaId(
            page = page,
            limit = pageSize,
            movieId = cinemaId
        )

        return if (response.isSuccessful) {
            val articles = checkNotNull(response.body()).docs.map { it.toReview() }
            val nextKey = if (articles.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            PagingSource.LoadResult.Page(articles, prevKey, nextKey)
        } else {
            PagingSource.LoadResult.Error(HttpException(response))
        }
    }
}