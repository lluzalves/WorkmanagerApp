package com.daniel.workmanagerapp.domain.usecases

import com.daniel.workmanagerapp.domain.entity.ArticleItems

interface GetArticlesUseCase {
    suspend fun getArticlesResult() : Result<ArticleItems>
}