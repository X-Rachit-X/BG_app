package com.example.backgroundprocess_runner

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlin.math.sqrt

@Suppress("DEPRECATION")
class locationservice4 : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var gyroscope: Sensor
    private var stepCount = 0
    private var accelerationThreshold = 2.0f // Adjust this threshold for sensitivity
    private var previousMagnitude = 0f
    private var lastStepTime: Long = 0
    private val stepDelay = 530
    private var lastStepDirection = FloatArray(3)
    private var stepCountDebounce = false
    private val alpha = 0.8f // Smoothing factor for low-pass filter
    private var filteredGyroX = 0f
    private var filteredGyroY = 0f
    private var filteredGyroZ = 0f
    private var rotationThreshold = 0.2f
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        // Initialize the accelerometer sensor
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)!!
        // Register the sensor listener for accelerometer
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL)
        // Start the service in foreground
        startForegroundServiceWithNotification()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        sensorManager.unregisterListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startForegroundServiceWithNotification() {
        val channelId = "step_tracking_channel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Step Tracking",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Build the notification
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Step Tracking Service")
            .setContentText("Tracking your steps using accelerometer and gyroscope")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app's icon
            .setPriority(NotificationCompat.PRIORITY_LOW) // Set low priority for a background task
            .build()

        // Start the service in the foreground
        startForeground(1, notification)
    }
    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                // Handle accelerometer data
                handleAccelerometerData(event.values)
            }
            Sensor.TYPE_GYROSCOPE -> {
                // Handle gyroscope data
                handleGyroscopeData(event.values)
            }
        }
    }

    fun handleAccelerometerData(values: FloatArray) {
        val x = values[0]
        val y = values[1]
        val z = values[2]

        // Calculate the magnitude of acceleration
        val magnitude = sqrt((x * x + y * y + z * z).toDouble()).toFloat()

        // Check for step-like movement by comparing acceleration magnitude changes
        val deltaMagnitude = magnitude - previousMagnitude

        // Get current time
        val currentTime = System.currentTimeMillis()

        // Detect step
        if (deltaMagnitude > accelerationThreshold && (currentTime - lastStepTime) > stepDelay && !stepCountDebounce) {
            if (isRotationMinimal()) {
                stepCount++
                stepCountDebounce = true


            val intent = Intent("com.example.STEP_COUNT_UPDATE")
            intent.putExtra("step_count", stepCount)
            LocalBroadcastManager
                .getInstance(this@locationservice4)
                .sendBroadcast(intent)
            Log.d("StepTrackingService", "Step Detected! Total Steps: $stepCount")

            lastStepTime = currentTime

            lastStepDirection = floatArrayOf(x, y, z)}
        } else if (deltaMagnitude < accelerationThreshold) {
            stepCountDebounce = false
        }


        previousMagnitude = magnitude
    }
    private fun handleGyroscopeData(values: FloatArray) {

        filteredGyroX = alpha * filteredGyroX + (1 - alpha) * values[0]
        filteredGyroY = alpha * filteredGyroY + (1 - alpha) * values[1]
        filteredGyroZ = alpha * filteredGyroZ + (1 - alpha) * values[2]


        Log.d("StepTrackingService", "Filtered Gyroscope Data - X: $filteredGyroX, Y: $filteredGyroY, Z: $filteredGyroZ")


    }
    private fun isRotationMinimal(): Boolean {
        // Calculate the magnitude of the rotation vector
        val rotationMagnitude = sqrt((filteredGyroX * filteredGyroX + filteredGyroY * filteredGyroY + filteredGyroZ * filteredGyroZ).toDouble()).toFloat()

        // Return true if the rotation is within the threshold (indicating minimal movement)
        return rotationMagnitude < rotationThreshold
    }
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle accuracy changes if necessary
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
