package com.kproject.quotes.data.remote.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("error_code")
    val errorCode: String
)