package com.daniel.workmanagerapp.data.network

import android.content.Context
import com.daniel.workmanagerapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class NetworkClient(private val context: Context) {

    private lateinit var client: OkHttpClient


    fun getNetworkClient(): OkHttpClient {
        return if (::client.isInitialized) {
            client
        } else {
            buildClient()
        }
    }

    private fun makeRemoteRequest(request: Request): Request {
        val period = 10  // seconds
        return request.newBuilder()
            .header(name = "Cache-Control", value = "public, max-age=$period").build()
    }

    private fun buildClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.BASIC
                }
            }).addInterceptor { chain ->
                var request = chain.request()
                NetworkChecker(context).hasConnectivity().apply {
                    request = try {
                        makeRemoteRequest(request)
                    } catch (exception: Exception) {
                        throw exception
                    }
                }
                chain.proceed(request)
            }
            .connectTimeout(timeout = 2, unit = TimeUnit.MINUTES)
            .writeTimeout(timeout = 2, TimeUnit.MINUTES)
            .readTimeout(timeout = 2, TimeUnit.MINUTES)
            .retryOnConnectionFailure(retryOnConnectionFailure = false)
            .build()
    }
}

