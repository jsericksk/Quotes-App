package com.kproject.quotes.data.remote

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kproject.quotes.data.remote.service.QuotesApiService
import com.kproject.quotes.presentation.model.Quote

class QuotesApiPagingSource(
    private val quotesApiService: QuotesApiService,
    private val filter: String?,
    private val userId: Int?
) : PagingSource<Int, Quote>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Quote> {
        return try {
            val page = params.key ?: 1
            val response = quotesApiService.getQuotes(
                page = page,
                filter = filter,
                userId = userId
            )
            if (response.isSuccessful) {
                response.body()?.let { infoResponse ->
                    val uri = Uri.parse(infoResponse.info.next)
                    val nextPageQuery = uri.getQueryParameter("page")
                    val nextPage = nextPageQuery?.toInt()
                    LoadResult.Page(
                        data = infoResponse.results,
                        prevKey = if (page == 1) null else page.minus(1),
                        nextKey = nextPage
                    )
                }
            }
            return LoadResult.Error(Exception())
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Quote>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}