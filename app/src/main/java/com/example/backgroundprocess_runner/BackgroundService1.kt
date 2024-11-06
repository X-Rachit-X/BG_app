package com.example.backgroundprocess_runner

import android.app.Service.START_STICKY
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.ServiceCompat.stopForeground
import java.security.Provider.Service


class BackgroundService1:android.app.Service() {

    override fun onBind(intent: Intent?):IBinder?{
        return null
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()

        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show()
    }
    }

private fun Any.onDestroy() {
    TODO("Not yet implemented")
}
