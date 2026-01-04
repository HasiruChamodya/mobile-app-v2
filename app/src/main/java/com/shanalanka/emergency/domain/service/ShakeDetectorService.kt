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
 * Uses accelerometer sensor to detect shake pattern.
 */
@AndroidEntryPoint
class ShakeDetectorService : Service(), SensorEventListener {
    
    @Inject
    lateinit var emergencyTriggerManager: EmergencyTriggerManager
    
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var vibrator: Vibrator
    
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    private var shakeCount = 0
    private var lastShakeTime = 0L
    private var lastUpdateTime = 0L
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var shakeThreshold = 2.7f // Dynamic threshold based on sensitivity
    
    companion object {
        private const val TAG = "ShakeDetectorService"
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "shake_detection_channel"
        private const val SHAKE_THRESHOLD_LOW = 3.5f // Less sensitive
        private const val SHAKE_THRESHOLD_MEDIUM = 2.7f // Balanced
        private const val SHAKE_THRESHOLD_HIGH = 2.0f // More sensitive
        private const val SHAKE_SLOP_TIME_MS = 500 // Time between shakes
        private const val SHAKE_COUNT_RESET_TIME_MS = 2000 // Reset after 2 seconds
        private const val REQUIRED_SHAKE_COUNT = 3
        
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
        shakeThreshold = when (sensitivityName) {
            "LOW" -> SHAKE_THRESHOLD_LOW
            "HIGH" -> SHAKE_THRESHOLD_HIGH
            else -> SHAKE_THRESHOLD_MEDIUM
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
        
        // Try to start as foreground service (may fail on some Android versions)
        try {
            startForeground(NOTIFICATION_ID, createNotification())
        } catch (e: SecurityException) {
            // If foreground service fails due to missing permissions, log but continue
            android.util.Log.w(TAG, "Could not start as foreground service due to SecurityException", e)
        } catch (e: Exception) {
            // Catch other potential exceptions (e.g., ForegroundServiceStartNotAllowedException on Android 12+)
            android.util.Log.w(TAG, "Could not start as foreground service", e)
        }
        
        // Register sensor listener
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
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
        
        if ((currentTime - lastUpdateTime) > 100) {
            val diffTime = currentTime - lastUpdateTime
            lastUpdateTime = currentTime
            
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            
            val gX = (x - lastX) / diffTime * 10000
            val gY = (y - lastY) / diffTime * 10000
            val gZ = (z - lastZ) / diffTime * 10000
            
            val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)
            
            if (gForce > shakeThreshold) {
                if (currentTime - lastShakeTime > SHAKE_SLOP_TIME_MS) {
                    shakeCount++
                    lastShakeTime = currentTime
                    
                    // Provide haptic feedback
                    if (vibrator.hasVibrator()) {
                        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
                    }
                    
                    if (shakeCount >= REQUIRED_SHAKE_COUNT) {
                        onShakeDetected()
                        shakeCount = 0
                    }
                }
            }
            
            // Reset shake count if too much time has passed
            if (currentTime - lastShakeTime > SHAKE_COUNT_RESET_TIME_MS) {
                shakeCount = 0
            }
            
            lastX = x
            lastY = y
            lastZ = z
        }
    }
    
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not needed
    }
    
    private fun onShakeDetected() {
        // Stronger vibration for confirmation
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100, 100, 100), -1))
        }
        
        // Trigger emergency alert
        scope.launch {
            emergencyTriggerManager.triggerEmergencyAlert(EmergencyTriggerType.SHAKE)
        }
    }
    
    private fun createNotification() = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Shake Detection Active")
        .setContentText("Shake your phone 3 times to send emergency alert")
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
