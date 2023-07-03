package com.kproject.quotes.commom

import com.kproject.quotes.commom.exception.BaseException

sealed class ResultState<out T>(val result: T? = null) {
    object Loading : ResultState<Nothing>()
    data class Success<T>(val data: T? = null) : ResultState<T>(result = data)
    data class Error<T>(val exception: BaseException? = null) : ResultState<T>()
}