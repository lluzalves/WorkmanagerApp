package com.daniel.workmanagerapp.domain.usecases

import com.daniel.workmanagerapp.domain.NewsRepository
import com.daniel.workmanagerapp.domain.entity.ArticleItems

class GetArticlesUseCaseImpl(private val repository: NewsRepository) : GetArticlesUseCase {
    override suspend fun getArticlesResult(): Result<ArticleItems> {
       return repository.getNewsItems()
    }
}