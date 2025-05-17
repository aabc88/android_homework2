package com.example.androidhomework2_hej.service

import android.app.job.JobParameters
import android.app.job.JobService
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log

class MyWifiJobService : JobService() {
    override fun onStartJob(params: JobParameters?): Boolean {
        if (isWifiConnected())
            Log.d("EJ", "onStartJob: wifi연결 됨")
        else
            Log.d("EJ", "onStartJob: wifi연결 안됨")
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        TODO("Not yet implemented")
    }

    private fun isWifiConnected(): Boolean {
        val cm = getSystemService(ConnectivityManager::class.java)
        val networkInfo = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(networkInfo) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
}