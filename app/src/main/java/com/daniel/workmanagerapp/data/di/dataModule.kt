package com.daniel.workmanagerapp.data.di

import com.daniel.workmanagerapp.BuildConfig
import com.daniel.workmanagerapp.data.datasource.TopHeadlinesRemoteDataSource
import com.daniel.workmanagerapp.data.datasource.TopHeadlinesRemoteDataSourceImpl
import com.daniel.workmanagerapp.data.services.NewsApiService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

const val SERVICE_API = "service_api"

val dataModule = module {
    factory<TopHeadlinesRemoteDataSource> {
        TopHeadlinesRemoteDataSourceImpl(
            client = get(
                named(
                    RETROFIT
                )
            )
        )
    }
    factory<NewsApiService>(named(SERVICE_API)) {
        get<Retrofit>(named(RETROFIT)).create(NewsApiService::class.java)
    }
}