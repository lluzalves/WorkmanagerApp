package com.daniel.workmanagerapp.data.worker

import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.daniel.core.extensions.convertArticleDate
import com.daniel.workmanagerapp.BuildConfig
import com.daniel.workmanagerapp.data.model.NewsHeadlinesResponse
import com.daniel.workmanagerapp.data.network.NetworkClient
import com.daniel.workmanagerapp.data.services.NewsApiService
import com.daniel.workmanagerapp.data.services.api.Source
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class HeadLineNotificationWorker constructor(
    private val appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    override suspend fun doWork(): Result {

        val countryCode = Source.getSources().asSequence().shuffled().take(1).first()
        val category = Source.getCategory().asSequence().shuffled().take(1).first()
        return try {
            withContext(Dispatchers.IO) {
                val result = Retrofit.Builder()
                    .client(NetworkClient(appContext).getNetworkClient())
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .build()
                    .create(NewsApiService::class.java).getHeadlines(
                        apiKey = BuildConfig.NEWS_API_KEY,
                        countryCode = countryCode,
                        category = category
                    )
                val headlines = result.body()
                if (headlines != null) {
                    Result.success(createOutputData(headlines))
                } else {
                    Result.failure(Data.EMPTY)
                }
            }
        } catch (exception: Exception) {
            Timber.d(exception.localizedMessage)
            return Result.failure(Data.EMPTY)
        }
    }

    private fun createOutputData(result: NewsHeadlinesResponse): Data {
        val topHeadline = result.headlines.first()
        return Data.Builder()
            .putString(HEADLINE_TITLE, topHeadline.title)
            .putString(HEADLINE_DATE, String.convertArticleDate(topHeadline.publishedAt))
            .build()
    }


    companion object {
        const val TAG = "notification_work"
        const val HEADLINE_TITLE = "headline_title"
        const val HEADLINE_DATE = "headline_date"
    }
}