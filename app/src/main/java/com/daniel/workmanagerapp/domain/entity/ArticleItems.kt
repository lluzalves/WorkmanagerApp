package com.daniel.workmanagerapp.domain.entity

data class ArticleItems(
    val status: String,
    var totalResults: Int,
    var headlines: List<Article>
)