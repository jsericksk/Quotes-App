package com.kproject.quotes.commom

import android.net.Uri
import com.google.gson.Gson

fun <T> String.fromJson(type: Class<T>): T {
    return Gson().fromJson(Uri.decode(this), type)
}

fun <T> T.toJson(): String {
    return Uri.encode(Gson().toJson(this))
}