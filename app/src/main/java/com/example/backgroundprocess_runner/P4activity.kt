package com.example.backgroundprocess_runner

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class P4activity:AppCompatActivity() {
    private lateinit var stepCountTextView: TextView
    private lateinit var stepReceiver: BroadcastReceiver
    private lateinit var serviceintent4: Intent
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.p4activity)

        stepCountTextView = findViewById(R.id.setextView)
        val butt: Button = findViewById(R.id.button7)
        serviceintent4 = Intent(this, locationservice4::class.java)
        stepReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val stepCount = intent?.getIntExtra("step_count", 0) ?: 0
                stepCountTextView.text = "Steps: $stepCount"
                Log.d("StepReceiver", "Received step count: $stepCount")
            }
        }

        if(DataHolder.l==0)
            butt.setText("Start")
        else
            butt.setText("Stop")

        butt.setOnClickListener {
            if (DataHolder.l == 0) {

                registerStepReceiver()
                startService(serviceintent4)
                stepCountTextView.text="Steps: ${DataHolder.step}"
                butt.setText("Stop")
                DataHolder.l = 1 
            } else {
                stopService(serviceintent4)
                butt.setText("Start")
                DataHolder.l = 0
                DataHolder.step=0
            }

        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerStepReceiver() {
        stepReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val stepCount = intent?.getIntExtra("step_count", 0)?:0
                stepCountTextView.text = "Steps: $stepCount"
                Log.d("StepReceiver", "Received step count: $stepCount")
            }
        }

// Register the receiver using LocalBroadcastManager
        LocalBroadcastManager.getInstance(this).registerReceiver(stepReceiver, IntentFilter("com.example.STEP_COUNT_UPDATE"))

    }


    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver when the activity is destroyed
        LocalBroadcastManager.getInstance(this).unregisterReceiver(stepReceiver)

    }
    }
