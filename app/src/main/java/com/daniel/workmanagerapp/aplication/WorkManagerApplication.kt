package com.daniel.workmanagerapp.aplication

import android.app.Application
import androidx.lifecycle.Observer
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.daniel.workmanagerapp.aplication.di.KoinDependencyGraph
import com.daniel.workmanagerapp.data.worker.HeadLineNotificationWorker
import com.daniel.workmanagerapp.data.worker.NOTIFICATION_WORKER
import com.daniel.workmanagerapp.presentation.ui.HeadlineNotification
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named

class WorkManagerApplication : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WorkManagerApplication)
            modules(KoinDependencyGraph().getNewsAppKoinDependencyModules())
            workManagerFactory()
        }
        val worker = get<OneTimeWorkRequest>(named(NOTIFICATION_WORKER))
        scheduleNotification(worker)
        trackNotification(worker)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()


    private fun scheduleNotification(worker: OneTimeWorkRequest) {
        WorkManager.getInstance(this).enqueueUniqueWork(
            HeadLineNotificationWorker.TAG,
            ExistingWorkPolicy.REPLACE,
            worker
        )
    }

    private fun trackNotification(worker: OneTimeWorkRequest) {
        val workerInfo = WorkManager.getInstance(this).getWorkInfoByIdLiveData(worker.id)
        var workObserver: Observer<WorkInfo>? = null
        workerInfo.observeForever(Observer<WorkInfo> { workInfo ->
            when (workInfo.state) {
                WorkInfo.State.ENQUEUED -> {
                }

                WorkInfo.State.RUNNING -> {
                }

                WorkInfo.State.SUCCEEDED -> {
                    val headline =
                        workInfo.outputData.getString(HeadLineNotificationWorker.HEADLINE_TITLE)
                    val date =
                        workInfo.outputData.getString(HeadLineNotificationWorker.HEADLINE_DATE)
                    if (!headline.isNullOrEmpty() && !date.isNullOrEmpty())
                        HeadlineNotification(this, headline, date).sendNotification(0)
                    workObserver?.let {
                        workerInfo.removeObserver(it)
                    }

                }

                WorkInfo.State.FAILED -> {
                    workObserver?.let { workerInfo.removeObserver(it) }

                }

                WorkInfo.State.BLOCKED -> {
                    workObserver?.let { workerInfo.removeObserver(it) }

                }

                WorkInfo.State.CANCELLED -> {
                    workObserver?.let { workerInfo.removeObserver(it) }
                }
            }
        }.also { workObserver = it })
    }

}