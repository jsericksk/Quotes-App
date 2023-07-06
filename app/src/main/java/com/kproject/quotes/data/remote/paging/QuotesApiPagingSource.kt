package com.kproject.quotes.data.remote.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kproject.quotes.data.remote.model.quotes.toQuoteModel
import com.kproject.quotes.data.remote.service.QuotesApiService
import com.kproject.quotes.domain.model.quotes.QuoteModel

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
            }

            // TODO: Adicionar verificação de frases não encontradas para certos usuários

            return LoadResult.Error(Exception("Unknown error loading quotes"))
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, QuoteModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}