package com.example.backgroundprocess_runner

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast

class BackgroundService3:Service() {
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private fun performtask()
    {

        val interval: Long = 10000
        handler = Handler(Looper.getMainLooper())
        runnable= object : Runnable {
            override fun run() {
                showMessage() // Your function to pop up a message
                handler.postDelayed(this, interval) // Schedule the next run in 10 seconds
            }
        }

        handler.post(runnable)


    }
    private fun showMessage() {
        Toast.makeText(applicationContext, "Time to Drink Water!", Toast.LENGTH_SHORT).show()
    }
    override fun onBind(intent: Intent?): IBinder?{
        return null
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()
        performtask()
        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        Toast.makeText(applicationContext, "Service Stopped", Toast.LENGTH_SHORT).show()
    }
}