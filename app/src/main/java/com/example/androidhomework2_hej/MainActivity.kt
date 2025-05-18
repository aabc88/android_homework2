package com.example.androidhomework2_hej

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidhomework2_hej.databinding.ActivityMainBinding
import com.example.androidhomework2_hej.service.MyJobService
import com.example.androidhomework2_hej.service.MyWifiJobService

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initPermission() //알림권한
        checkWifi() //wifi연결 확인

    }//onCreate

    private fun updateJob() {
        val componentName = ComponentName(this, MyJobService::class.java)

        val builder = JobInfo.Builder(1, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .build()
        val scheduler = getSystemService(JobScheduler::class.java)
        scheduler.schedule(builder)
    }

    private fun checkWifi() {
        val componentName = ComponentName(this, MyWifiJobService::class.java)

        val builder = JobInfo.Builder(1, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        val scheduler = getSystemService(JobScheduler::class.java)
        val result = scheduler.schedule(builder.build())

        if (result == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(this, "wifi연결됨", Toast.LENGTH_SHORT).show()
            updateJob()
        } else {
            Toast.makeText(this, "wifi연결안됨", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initPermission() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (it) Toast.makeText(this, "권한 허용됨", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "권한 거부됨", Toast.LENGTH_SHORT).show()
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            permissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
    }
}//MainActivity