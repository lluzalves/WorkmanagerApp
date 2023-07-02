package com.daniel.workmanagerapp.aplication.di

import com.daniel.workmanagerapp.data.di.dataModule
import com.daniel.workmanagerapp.data.di.networkModule
import com.daniel.workmanagerapp.aplication.di.ApplicationModules.domainModule
import com.daniel.workmanagerapp.aplication.di.ApplicationModules.presentationModule
import com.daniel.workmanagerapp.data.worker.workerModule
import org.koin.core.module.Module

class KoinDependencyGraph {

    fun getNewsAppKoinDependencyModules(): List<Module> {
        return listOf(
            networkModule,
            concurrencyModule,
            dataModule,
            domainModule,
            presentationModule,
            workerModule
        )
    }
}