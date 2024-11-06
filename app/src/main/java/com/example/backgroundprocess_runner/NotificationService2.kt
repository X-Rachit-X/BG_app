package com.example.backgroundprocess_runner

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import java.util.Timer
import java.util.TimerTask


class NotificationService2:Service(){
    var kill=0
    private var timer: Timer? = null
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val notification = createNotification("Your Process 2 is running")
        startForeground(1, notification)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startRepeatingTask()
        return START_STICKY
    }
    private fun startRepeatingTask() {
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                showNotification()
            }
        }, 0, 10000)
        if(kill==1)
            stopSelf()

    }
    private fun showNotification() {
        val notification = createNotification("Reminder: Every 10 seconds")
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(2, notification)  // Show the notification
    }

    private fun createNotification(contentText: String): Notification {
        return NotificationCompat.Builder(this, "NOTIFICATION_CHANNEL_ID")
            .setContentTitle("Process 2")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .build()
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "NOTIFICATION_CHANNEL_ID",
                "Process 2",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
      kill=1
        timer?.cancel()
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}