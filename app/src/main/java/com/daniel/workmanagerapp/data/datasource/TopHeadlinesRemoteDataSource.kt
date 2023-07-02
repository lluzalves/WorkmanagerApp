package com.daniel.workmanagerapp.data.datasource

import com.daniel.workmanagerapp.data.model.NewsHeadlinesResponse
import retrofit2.Response

interface TopHeadlinesRemoteDataSource {
    suspend fun getTopHeadlines(): Response<NewsHeadlinesResponse>
}