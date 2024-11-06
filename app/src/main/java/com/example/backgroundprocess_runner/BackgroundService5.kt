package com.example.backgroundprocess_runner

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.app.Service.START_STICKY
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat.startForeground
import androidx.core.content.ContextCompat.getSystemService


class BackgroundService5:Service() {

    // Define your screen time limit in seconds (e.g., 1800 seconds = 30 minutes)
    private val screenTimeLimit = 30 // Example: 30 minutes
    private val handler = Handler(Looper.getMainLooper())
    private var screenTimeSeconds = 0
    public val screenTimeRunnable = object : Runnable {
        override fun run() {
            screenTimeSeconds++

            // Check if the screen time limit has been reached
            if (screenTimeSeconds >= screenTimeLimit) {
                sendAlert()
            }

            // Schedule the next check after 1 second
            handler.postDelayed(this, 1000)
        }
    }

override fun onCreate() {
    super.onCreate()

    // Create a notification channel for Android O and higher
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "screen_time_channel",
            "Screen Time",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
    }

    // Start foreground service with a notification
    val notification: Notification = NotificationCompat.Builder(this, "screen_time_channel")
        .setContentTitle("Screen Time Monitoring")
        .setContentText("Tracking your screen time...")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()

    startForeground(1, notification)
}

override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    // Start the handler to track screen time
    handler.post(screenTimeRunnable)

    return START_STICKY
}

override fun onDestroy() {
    super.onDestroy()
    // Remove the handler when the service is destroyed
    handler.removeCallbacks(screenTimeRunnable)
}

override fun onBind(intent: Intent?): IBinder? {
    return null
}

// Function to send an alert when the screen time limit is reached
private fun sendAlert() {
    handler.removeCallbacks(screenTimeRunnable) // Stop counting when the limit is reached

    // Notify the user
    Toast.makeText(this, "You've reached your screen time limit!", Toast.LENGTH_LONG).show()

    // You can also use a notification here instead of (or in addition to) a Toast
    val notification: Notification = NotificationCompat.Builder(this, "screen_time_channel")
        .setContentTitle("Screen Time Alert")
        .setContentText("You have reached your screen time limit!")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .build()

    val manager = getSystemService(NotificationManager::class.java)
    manager.notify(2, notification)

    // Stop the service when the limit is reached
    stopSelf()
}
}