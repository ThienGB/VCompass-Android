package com.vcompass.data.model.response.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vcompass.data.util.getDetailError
import com.vcompass.domain.model.response.PagingResponseModel
import com.vcompass.domain.model.response.checkLastPage
import kotlin.coroutines.cancellation.CancellationException

class GenericPagingSource<Domain : Any>(
    private val initialPage: Int = 0,
    private val onTotalItems: ((Int) -> Unit)? = null,
    private val onCalledAfterApi: () -> Unit = {},
    private val apiCaller: suspend (Int) -> Result<PagingResponseModel<Domain>>
) : PagingSource<Int, Domain>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Domain> {
        val page = params.key ?: initialPage

        return try {
            val result = apiCaller(page)
            onCalledAfterApi.invoke()

            result.fold(
                onSuccess = { pagingResult ->
                    val data = pagingResult.content ?: listOf()
                    val localNextPage = page + 1
                    val isLastPage = pagingResult.checkLastPage(localNextPage)
                    val nextPage = if (isLastPage) null else localNextPage

                    val prevKey = if (page > initialPage) page - 1 else null

                    pagingResult.totalElements?.toInt()?.let {
                        onTotalItems?.invoke(it)
                    }

                    LoadResult.Page(
                        data = data,
                        prevKey = prevKey,
                        nextKey = nextPage
                    )
                },
                onFailure = { throwable ->
                    LoadResult.Error(throwable.getDetailError())
                }
            )
        } catch (e: Throwable) {
            if (e is CancellationException) throw e
            onCalledAfterApi()
            LoadResult.Error(e.getDetailError())
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Domain>): Int? {
        val anchor = state.anchorPosition ?: return null
        val closest = state.closestPageToPosition(anchor) ?: return null
        return closest.prevKey?.plus(1) ?: closest.nextKey?.minus(1)
    }
}