package com.example.androidhomework2_hej.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.androidhomework2_hej.R

class UpdateService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startUpdateNotification()
        return START_NOT_STICKY
    }

    private fun startUpdateNotification() {
        val channelId = "update_channel"
        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "업데이트 채널",
                NotificationManager.IMPORTANCE_HIGH
            )
            nm.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("업데이트 중...")
            .setContentText("진행중")
            .setSmallIcon(R.drawable.baseline_autorenew_24)
            .setOnlyAlertOnce(true)
            .setProgress(10, 0, false)

        startForeground(1, builder.build())

        Thread {
            for (i in 1..10) {
                Thread.sleep(100)
                builder.setProgress(10, i, false)
                builder.setContentText("진행중: ${i*10}%")
                nm.notify(Channel.notificationId.channelCode, builder.build())
            }

            // 완료되면 알림 수정
            builder.setContentTitle("업데이트 완료")
                .setContentText("업데이트가 완료되었습니다.")
                .setProgress(0, 0, false)
            nm.notify(Channel.notificationId.channelCode, builder.build())

            stopForeground(true)
            stopSelf()
        }.start()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}