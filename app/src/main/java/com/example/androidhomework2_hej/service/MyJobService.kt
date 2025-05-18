package com.example.androidhomework2_hej.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.androidhomework2_hej.R

class MyJobService : JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d("EJ", "onStartJob()")
        sendNoti()
        return false
    }

    private fun sendNoti() {
        val channelId = "update_channel"
        val nm = getSystemService(NotificationManager::class.java)

        val updateIntent = Intent(this, UpdateReceiver::class.java).apply {
            action = "com.example.androidhomework2_hej.ACTION_UPDATE"
        }

        val updatePendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            updateIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "업데이트 채널",
                NotificationManager.IMPORTANCE_HIGH
            )
            nm.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("업데이트")
            .setContentText("WIFI에 연결되었습니다. 지금 업데이트 하시겠습니까?")
            .setSmallIcon(R.drawable.baseline_autorenew_24)
            .addAction(R.drawable.baseline_autorenew_24, "업데이트", updatePendingIntent)
            .build()
        nm.notify(Channel.notificationId.channelCode, notification)
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.d("EJ", "onStopJob()")
        return false
    }

}