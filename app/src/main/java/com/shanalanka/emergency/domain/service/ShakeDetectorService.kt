package com.shanalanka.emergency.domain.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.shanalanka.emergency.R
import com.shanalanka.emergency.data.PreferenceKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sqrt

/**
 * Foreground service that detects phone shaking to trigger emergency alerts.
 * Uses improved shake detection algorithm based on acceleration patterns.
 */
@AndroidEntryPoint
class ShakeDetectorService : Service(), SensorEventListener {
    
    @Inject
    lateinit var emergencyTriggerManager: EmergencyTriggerManager
    
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var vibrator: Vibrator
    
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    // Shake detection state
    private var shakeTimestamps = mutableListOf<Long>()
    private var lastUpdateTime = 0L
    
    // Thresholds based on sensitivity
    private var accelerationThreshold = 15f // m/s^2
    private var shakeWindowMs = 1500L // Time window to detect shake pattern
    private var requiredShakeEvents = 3 // Minimum shake movements to trigger
    
    companion object {
        private const val TAG = "ShakeDetectorService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "shake_detection_channel"
        
        // Sensitivity thresholds (m/s^2)
        private const val ACCELERATION_THRESHOLD_LOW = 20f // Strong shaking required
        private const val ACCELERATION_THRESHOLD_MEDIUM = 15f // Moderate shaking
        private const val ACCELERATION_THRESHOLD_HIGH = 12f // Gentle shaking
        
        private const val GRAVITY = 9.81f // Gravity constant
        private const val UPDATE_INTERVAL_MS = 100L
        
        fun start(context: Context) {
            val intent = Intent(context, ShakeDetectorService::class.java)
            context.startForegroundService(intent)
        }
        
        fun stop(context: Context) {
            val intent = Intent(context, ShakeDetectorService::class.java)
            context.stopService(intent)
        }
    }
    
    override fun onCreate() {
        super.onCreate()
        
        // Load sensitivity setting
        val prefs = getSharedPreferences(PreferenceKeys.PREFS_NAME, Context.MODE_PRIVATE)
        val sensitivityName = prefs.getString(PreferenceKeys.KEY_SHAKE_SENSITIVITY, "MEDIUM") ?: "MEDIUM"
        
        accelerationThreshold = when (sensitivityName) {
            "LOW" -> ACCELERATION_THRESHOLD_LOW
            "HIGH" -> ACCELERATION_THRESHOLD_HIGH
            else -> ACCELERATION_THRESHOLD_MEDIUM
        }
        
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        
        // Check if accelerometer is available
        if (accelerometer == null) {
            android.util.Log.e(TAG, "Accelerometer not available on this device")
            stopSelf()
            return
        }
        
        createNotificationChannel()
        
        // Try to start as foreground service
        try {
            startForeground(NOTIFICATION_ID, createNotification())
        } catch (e: SecurityException) {
            android.util.Log.w(TAG, "Could not start as foreground service due to SecurityException", e)
        } catch (e: Exception) {
            android.util.Log.w(TAG, "Could not start as foreground service", e)
        }
        
        // Register sensor listener with faster update rate for better detection
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }
    
    override fun onSensorChanged(event: SensorEvent?) {
        event ?: return
        
        val currentTime = System.currentTimeMillis()
        
        // Throttle updates
        if ((currentTime - lastUpdateTime) < UPDATE_INTERVAL_MS) {
            return
        }
        
        lastUpdateTime = currentTime
        
        // Get acceleration values
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        
        // Calculate acceleration magnitude (remove gravity)
        // Note: This is a simplified approach. For production, consider using a low-pass filter
        val accelerationMagnitude = sqrt(x * x + y * y + z * z) - GRAVITY
        
        // Detect significant acceleration (shake movement)
        if (accelerationMagnitude > accelerationThreshold) {
            synchronized(shakeTimestamps) {
                shakeTimestamps.add(currentTime)
                
                // Provide subtle haptic feedback for each detected shake movement
                if (vibrator.hasVibrator()) {
                    vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
                }
                
                // Clean up old timestamps outside the detection window
                shakeTimestamps.removeAll { it < currentTime - shakeWindowMs }
                
                // Check if we have enough shake events in the time window
                if (shakeTimestamps.size >= requiredShakeEvents) {
                    onShakeDetected()
                    shakeTimestamps.clear()
                }
            }
        } else {
            // Clean up old timestamps
            synchronized(shakeTimestamps) {
                shakeTimestamps.removeAll { it < currentTime - shakeWindowMs }
            }
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed
    }
    
    private fun onShakeDetected() {
        android.util.Log.d(TAG, "Shake pattern detected! Triggering emergency alert.")
        
        // Strong vibration pattern for confirmation
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(
                VibrationEffect.createWaveform(
                    longArrayOf(0, 100, 50, 100, 50, 100), // On-off pattern
                    -1 // Don't repeat
                )
            )
        }
        
        // Trigger emergency alert
        scope.launch {
            emergencyTriggerManager.triggerEmergencyAlert(EmergencyTriggerType.SHAKE)
        }
    }
    
    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Shake Detection Active")
        .setContentText("Shake phone vigorously to send emergency alert")
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .setOngoing(true)
        .build()
    
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Shake Detection",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "Shows when shake detection is active"
        }
        
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}
