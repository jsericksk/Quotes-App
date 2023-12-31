package com.kproject.quotes.data.remote.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kproject.quotes.commom.exception.QuoteException
import com.kproject.quotes.data.remote.model.quotes.toQuoteModel
import com.kproject.quotes.data.remote.model.toErrorResponse
import com.kproject.quotes.data.remote.service.QuotesApiService
import com.kproject.quotes.domain.model.quotes.QuoteModel

private const val QuoteNotFoundCode = "search_without_results"
private const val UserWithoutPostsCode = "user_without_posts"

class QuotesApiPagingSource(
    private val quotesApiService: QuotesApiService,
    private val filter: String?,
    private val userId: Int?
) : PagingSource<Int, QuoteModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteModel> {
        return try {
            val page = params.key ?: 1
            val response = quotesApiService.getQuotes(
                page = page,
                filter = filter,
                userId = userId
            )
            if (response.isSuccessful) {
                response.body()?.let { infoResponse ->
                    val quotesModel = infoResponse.results.map { quoteResponse ->
                        quoteResponse.toQuoteModel()
                    }
                    val nextPage = infoResponse.info.next?.let {
                        val uri = Uri.parse(it)
                        uri.getQueryParameter("page")?.toInt()
                    }
                    return LoadResult.Page(
                        data = quotesModel,
                        prevKey = if (page == 1) null else page.minus(1),
                        nextKey = nextPage
                    )
                }
            } else {
                if (response.code() == 404) {
                    val errorResponse = response.errorBody().toErrorResponse()
                    errorResponse?.let { error ->
                        when (error.errorCode) {
                            QuoteNotFoundCode -> {
                                return LoadResult.Error(QuoteException.NoQuoteFound)
                            }
                            UserWithoutPostsCode -> {
                                return LoadResult.Error(QuoteException.UserWithoutPosts)
                            }
                            else -> {}
                        }
                    }
                    return LoadResult.Error(QuoteException.UnknownError)
                }
            }
            LoadResult.Error(QuoteException.UnknownError)
        } catch (e: Exception) {
            LoadResult.Error(QuoteException.UnknownError)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, QuoteModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}