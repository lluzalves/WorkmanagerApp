package com.daniel.workmanagerapp.aplication.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module

val concurrencyModule = module {
    factory {
        CoroutineScope(Dispatchers.IO + Job())
    }
}