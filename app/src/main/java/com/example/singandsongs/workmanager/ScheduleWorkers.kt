package com.example.singandsongs.workmanager

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleDailyWork(context: Context) {
  val workRequest = PeriodicWorkRequestBuilder<CheckDateWorker>(1, TimeUnit.DAYS)
    .build()

  WorkManager.getInstance(context).enqueueUniquePeriodicWork(
    "CheckDateWork",
    androidx.work.ExistingPeriodicWorkPolicy.REPLACE,
    workRequest
  )
}
