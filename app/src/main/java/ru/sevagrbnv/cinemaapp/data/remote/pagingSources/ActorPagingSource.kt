package ru.sevagrbnv.cinemaapp.data.remote.pagingSources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.sevagrbnv.cinemaapp.data.remote.RetrofitService
import ru.sevagrbnv.cinemaapp.data.remote.toActor
import ru.sevagrbnv.cinemaapp.domain.models.Actor

class ActorPagingSource(
    private val service: RetrofitService,
    private val cinemaId: Int
) : PagingSource<Int, Actor>() {

    override fun getRefreshKey(state: PagingState<Int, Actor>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Actor> {

        val page = params.key ?: 1
        val pageSize = params.loadSize

        val response = service.getActorsByCinemaId(
            page = page,
            limit = pageSize,
            movieId = cinemaId
        )

        return if (response.isSuccessful) {
            val articles = checkNotNull(response.body()).docs.map { it.toActor() }
            val nextKey = if (articles.size < pageSize) null else page + 1
            val prevKey = if (page == 1) null else page - 1
            LoadResult.Page(articles, prevKey, nextKey)
        } else {
            LoadResult.Error(HttpException(response))
        }
    }
}