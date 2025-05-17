package com.example.androidhomework2_hej

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobService
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
        checkWifi()

    }//onCreate

    private fun checkWifi() {
        val componentName = ComponentName(this, MyJobService::class.java)

        val builder = JobInfo.Builder(1, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        val scheduler = getSystemService(JobScheduler::class.java)
        val result = scheduler.schedule(builder.build())

        if (result == JobScheduler.RESULT_SUCCESS) {
            Toast.makeText(this, "wifi연결됨", Toast.LENGTH_SHORT).show()
            //연결 된 상태일 경우 할 일
        } else {
            Toast.makeText(this, "wifi연결안됨", Toast.LENGTH_SHORT).show()
            //연결 안 된 상태
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