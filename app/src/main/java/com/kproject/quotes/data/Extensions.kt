package com.kproject.quotes.data

import android.net.Uri
import com.google.gson.Gson
import com.kproject.quotes.data.remote.model.ErrorResponse
import okhttp3.ResponseBody

fun <T> String.fromJson(type: Class<T>): T {
    return Gson().fromJson(Uri.decode(this), type)
}

fun <T> T.toJson(): String {
    return Uri.encode(Gson().toJson(this))
}

fun ResponseBody?.toErrorResponse(): ErrorResponse? {
    val errorBodyString = this?.string()
    val gson = Gson()
    return gson.fromJson(errorBodyString, ErrorResponse::class.java)
}