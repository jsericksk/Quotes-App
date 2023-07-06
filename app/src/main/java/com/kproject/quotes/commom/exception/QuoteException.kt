package com.kproject.quotes.commom.exception

sealed class QuoteException : BaseException() {
    object NoQuoteFound : QuoteException()
    object UnknownError : QuoteException()
}