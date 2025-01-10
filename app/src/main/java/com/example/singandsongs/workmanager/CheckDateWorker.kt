package com.example.singandsongs.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.singandsongs.MainActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class CheckDateWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
  override fun doWork(): Result {
    if (isDayBeforeSundayOrHoliday()) {
      sendNotification("Przypomnienie", "Jutro jest niedziela lub święto!")
      println("Jutro jest niedziela")
    }
    return Result.success()
  }

  private fun isDayBeforeSundayOrHoliday(): Boolean {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, 1)

    val isSunday = calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY

    val isHoliday = isHoliday(calendar.time)

    return isSunday || isHoliday
  }

  private fun isHoliday(date: Date): Boolean {

    val holidays = listOf(
      "01-01",
      "25-12",
      "26-12"
    )

    val dateFormat = SimpleDateFormat("dd-MM", Locale.getDefault())
    val formattedDate = dateFormat.format(date)

    return holidays.contains(formattedDate)
  }

  private fun sendNotification(title: String, message: String) {
    val notificationManager =
      applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      val channel = NotificationChannel(
        "reminder_channel",
        "Przypomnienia",
        NotificationManager.IMPORTANCE_HIGH
      )
      notificationManager.createNotificationChannel(channel)
    }

    val intent = Intent(applicationContext, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
      putExtra("TARGET_FRAGMENT", "NotificationsFragment")
    }

    val pendingIntent = PendingIntent.getActivity(
      applicationContext,
      0,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(applicationContext, "reminder_channel")
      .setContentTitle(title)
      .setContentText(message)
      .setSmallIcon(android.R.drawable.ic_dialog_info)
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setContentIntent(pendingIntent)
      .setAutoCancel(true)
      .build()

    notificationManager.notify(1, notification)
  }
}
