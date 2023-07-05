package com.kproject.quotes.presentation.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent

object Utils {

    fun copyToClipBoard(context: Context, text: String) {
        val clipBoard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("label", text)
        clipBoard.setPrimaryClip(clipData)
    }

    fun shareText(context: Context, text: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(
            Intent.EXTRA_TEXT,
            text
        )
        intent.type = "text/plain"
        context.startActivity(intent)
    }
}