package com.example.tubechallenge.presentation.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.SystemClock
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.example.tubechallenge.R
import com.example.tubechallenge.domain.utils.Utils
import com.example.tubechallenge.presentation.ui.MainActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class StopwatchService : Service() {

    companion object {
        private val _elapsedTime = MutableStateFlow(0L) // Live stopwatch time
        val elapsedTime = _elapsedTime.asStateFlow()
        var isRunning = false
    }

    private var startTime: Long = 0L
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var notificationManager: NotificationManager

    private val CHANNEL_ID = "StopwatchChannel"
    private val NOTIFICATION_ID = 1

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(NotificationManager::class.java)
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "START_STOPWATCH") {
            startTime = SystemClock.elapsedRealtime()
            startForeground(NOTIFICATION_ID, buildNotification("00:00:00"), ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
            startTimer()
        } else if (intent?.action == "STOP_STOPWATCH") {
            stopSelf()
            isRunning = false
        }
        return START_STICKY
    }

    private fun startTimer() {
        handler.post(object : Runnable {
            override fun run() {
                val elapsedMillis = SystemClock.elapsedRealtime() - startTime
                _elapsedTime.value = elapsedMillis // Update UI observers
                notificationManager.notify(NOTIFICATION_ID, buildNotification(Utils.formatElapsedTime(elapsedMillis, false)))
                handler.postDelayed(this, 1) // Update every second
                isRunning = true
            }
        })
    }

    private fun buildNotification(time: String): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Good Luck!")
            .setContentText("Elapsed Time: $time")
            .setSmallIcon(R.drawable.baseline_train_24)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Stopwatch", NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
