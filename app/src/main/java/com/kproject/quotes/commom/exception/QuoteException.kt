package com.kproject.quotes.commom.exception

sealed class QuoteException : BaseException() {
    object NoQuoteFound : QuoteException()
    object UserWithoutPosts : QuoteException()
    object QuoteDoesNotExist : QuoteException()
    object UnknownError : QuoteException()
}