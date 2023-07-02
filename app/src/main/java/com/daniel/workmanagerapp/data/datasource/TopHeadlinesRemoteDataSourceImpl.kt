package com.daniel.workmanagerapp.data.datasource

import com.daniel.workmanagerapp.BuildConfig
import com.daniel.workmanagerapp.data.model.NewsHeadlinesResponse
import com.daniel.workmanagerapp.data.services.NewsApiService
import retrofit2.Response
import retrofit2.Retrofit


class TopHeadlinesRemoteDataSourceImpl(private val client: Retrofit) :
    TopHeadlinesRemoteDataSource {
    override suspend fun getTopHeadlines(): Response<NewsHeadlinesResponse> =
        client.create(NewsApiService::class.java)
            .getHeadlines(
                apiKey = BuildConfig.NEWS_API_KEY,
                countryCode = "us",
                category = "general"
            )


}