package com.example.androidhomework2_hej.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class UpdateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent?.action == "com.example.androidhomework2_hej.ACTION_UPDATE") {
            Toast.makeText(context, "onReceive", Toast.LENGTH_SHORT).show()
        }
    }
}