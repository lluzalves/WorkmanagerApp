package com.daniel.workmanagerapp.data.worker

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.UUID
import java.util.concurrent.TimeUnit

const val NOTIFICATION_WORKER = "fetch_worker_notification"
val workerModule = module {
    factory<OneTimeWorkRequest>(named(NOTIFICATION_WORKER)) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()


        return@factory OneTimeWorkRequest.Builder(HeadLineNotificationWorker::class.java)
            .setId(UUID.randomUUID())
            .setConstraints(constraints)
            .addTag(HeadLineNotificationWorker.TAG)
            .build()
    }
}