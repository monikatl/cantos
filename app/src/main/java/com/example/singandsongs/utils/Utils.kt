package com.example.singandsongs.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import java.net.URLEncoder

enum class SortCondition {
    DATE_ASC,
    DATE_DESC,
    FREQ_ASC,
    FREQ_DESC,
    AZ,
    ZA,
    BY_ID
}

enum class FilterCondition {
    CANTOS,
    DRAFTS,
    FAVOURITE
}

fun searchOnGoogle(context: Context, query: String) {
    val searchUri = Uri.parse("https://www.google.com/search?q=${URLEncoder.encode(query, "UTF-8")}")
    val searchIntent = Intent(Intent.ACTION_VIEW, searchUri)
    searchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try {
        context.startActivity(searchIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}