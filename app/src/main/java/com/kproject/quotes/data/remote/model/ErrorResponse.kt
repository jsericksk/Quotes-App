package com.kproject.quotes.data.remote.model

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody

data class ErrorResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("error_code")
    val errorCode: String
)

fun ResponseBody?.toErrorResponse(): ErrorResponse? {
    val errorBodyString = this?.string()
    val gson = Gson()
    return gson.fromJson(errorBodyString, ErrorResponse::class.java)
}