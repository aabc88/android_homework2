package com.example.androidhomework2_hej.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast

class UpdateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("EJ", "onReceive: 업데이트리시버")

        if (intent?.action == "com.example.androidhomework2_hej.ACTION_UPDATE") {
            Toast.makeText(context, "업데이트 시작", Toast.LENGTH_SHORT).show()

            val serviceIntent = Intent(context, UpdateService::class.java)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
        }
    }
}