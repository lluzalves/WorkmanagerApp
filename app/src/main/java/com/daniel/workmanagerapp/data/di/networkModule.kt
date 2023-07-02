package com.daniel.workmanagerapp.data.di

import com.daniel.workmanagerapp.data.network.NetworkClient
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val RETROFIT = "retrofit"
const val OK_HTTP_CLIENT = "ok_http_client"

val networkModule = module {

    factory(named(OK_HTTP_CLIENT)) { NetworkClient(androidContext()).getNetworkClient() }

    factory(named(RETROFIT)) {
        Retrofit.Builder()
            .client(get(named(OK_HTTP_CLIENT)))
            .baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}