package com.daniel.workmanagerapp.data.repository

import com.daniel.workmanagerapp.data.datasource.TopHeadlinesRemoteDataSource
import com.daniel.workmanagerapp.data.mapper.toArticleItems
import com.daniel.workmanagerapp.domain.NewsRepository
import com.daniel.workmanagerapp.domain.entity.ArticleItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext
import java.lang.Exception

class NewsRepositoryImp(val dataSource: TopHeadlinesRemoteDataSource, val scope: CoroutineScope) :
    NewsRepository {

    override suspend fun getNewsItems(): Result<ArticleItems> {
        return withContext(scope.coroutineContext) {
            try {
                val response = dataSource.getTopHeadlines().body()
                when {
                    response != null -> {
                        Result.success(toArticleItems(response))
                    }
                    else -> {
                        Result.failure(Throwable("Error, invalid request or response"))
                    }
                }
            } catch (exception: Exception) {
                Result.failure(exception)
            }
        }
    }

}