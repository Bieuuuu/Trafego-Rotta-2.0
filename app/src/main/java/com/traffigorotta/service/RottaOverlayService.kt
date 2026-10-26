package com.traffigorotta.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.MotionEvent
import android.view.WindowManager
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.NotificationCompat
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.traffigorotta.MainActivity
import com.traffigorotta.R
import com.traffigorotta.core.design.theme.RottaTheme
import com.traffigorotta.ui.overlay.RottaOverlayFloatingContent
import kotlin.math.abs

/**
 * Foreground Service que hospeda o Overlay Flutuante do Rotta.
 *
 * Limites Inegociáveis (AGENTS.md):
 * 1. Posição arrastável, lembrada, fora da área inferior da tela por padrão.
 * 2. No máximo 30% da altura quando expandido.
 * 3. Notificação persistente de primeiro plano declarada no Play Console.
 */
class RottaOverlayService : Service() {

    private var windowManager: WindowManager? = null
    private var composeView: ComposeView? = null
    private val serviceLifecycle = ServiceComposeLifecycle()

    private var layoutParams: WindowManager.LayoutParams? = null

    override fun onCreate() {
        super.onCreate()
        serviceLifecycle.onCreate()
        startAsForeground()
        initOverlayWindow()
    }

    private fun startAsForeground() {
        val channelId = "rotta_overlay_channel"
        val channelName = "Rotta Copiloto Flutuante"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.overlay_fgs_description)
                setShowBadge(false)
            }
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }

        val openAppIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            openAppIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.overlay_notification_title))
            .setContentText(getString(R.string.overlay_notification_text))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            )
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun initOverlayWindow() {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        val type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            type,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 24
            y = 200 // Posição padrão fora da área inferior
        }
        layoutParams = params

        val view = ComposeView(this).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
            setViewTreeLifecycleOwner(serviceLifecycle)
            setViewTreeSavedStateRegistryOwner(serviceLifecycle)
            setViewTreeViewModelStoreOwner(serviceLifecycle)

            setContent {
                val isExpanded by OfferCoordinator.isOverlayExpanded.collectAsState()
                val journeyEarnings by OfferCoordinator.journeyEarnings.collectAsState()
                val latestEvaluation by OfferCoordinator.latestEvaluation.collectAsState()
                val activeStatus by OfferCoordinator.activeRideStatus.collectAsState()
                val autoReportNotice by OfferCoordinator.autoReportNotice.collectAsState()

                RottaTheme(darkTheme = true) {
                    RottaOverlayFloatingContent(
                        isExpanded = isExpanded,
                        journeyEarnings = journeyEarnings,
                        evaluation = latestEvaluation,
                        activeStatus = activeStatus,
                        autoReportNotice = autoReportNotice,
                        onToggleExpand = {
                            OfferCoordinator.toggleOverlayExpanded()
                        }
                    )
                }
            }
        }

        setupDragListener(view, params)
        composeView = view

        windowManager?.addView(view, params)
    }

    private fun setupDragListener(view: ComposeView, params: WindowManager.LayoutParams) {
        var initialX = 0
        var initialY = 0
        var initialTouchX = 0f
        var initialTouchY = 0f

        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                    false
                }
                MotionEvent.ACTION_MOVE -> {
                    val deltaX = (event.rawX - initialTouchX).toInt()
                    val deltaY = (event.rawY - initialTouchY).toInt()

                    if (abs(deltaX) > 10 || abs(deltaY) > 10) {
                        params.x = initialX + deltaX
                        params.y = initialY + deltaY
                        windowManager?.updateViewLayout(view, params)
                        true
                    } else {
                        false
                    }
                }
                else -> false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        composeView?.let {
            windowManager?.removeView(it)
            composeView = null
        }
        serviceLifecycle.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object {
        private const val NOTIFICATION_ID = 4040

        fun start(context: Context) {
            val intent = Intent(context, RottaOverlayService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }

        fun stop(context: Context) {
            val intent = Intent(context, RottaOverlayService::class.java)
            context.stopService(intent)
        }
    }
}
