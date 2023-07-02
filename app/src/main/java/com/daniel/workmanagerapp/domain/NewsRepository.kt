package com.daniel.workmanagerapp.domain

import com.daniel.workmanagerapp.domain.entity.ArticleItems


interface NewsRepository {
    suspend fun getNewsItems(): Result<ArticleItems>
}