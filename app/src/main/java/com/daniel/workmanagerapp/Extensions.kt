package com.daniel.core.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun String.Companion.convertArticleDate(value: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val formatter = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.getDefault())
    val date = dateFormat.parse(value)
    return formatter.format(date)
}