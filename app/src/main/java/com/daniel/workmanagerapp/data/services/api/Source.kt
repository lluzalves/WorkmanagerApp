package com.daniel.workmanagerapp.data.services.api

object Source {
    private val sources = listOf<String>(
        "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de",
        "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr", "lt",
        "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru",
        "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za"
    )

    private val categories = listOf<String>(
        "business",
        "entertainment",
        "general",
        "health",
        "science",
        "sports",
        "technology"
    )

    fun getSources() = sources
    fun getCategory() = categories
}