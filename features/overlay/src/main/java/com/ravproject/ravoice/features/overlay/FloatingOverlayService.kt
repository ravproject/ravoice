package com.ravproject.ravoice.features.overlay

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import com.ravproject.ravoice.core.designsystem.OverlayContent

class FloatingOverlayService : Service() {
    private lateinit var windowManager: WindowManager
    private var composeView: ComposeView? = null

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        showOverlay()
    }

    private fun showOverlay() {
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT
        )

        composeView = ComposeView(this).apply {
            setContent {
                OverlayContent(
                    onTranslateToggle = { /* Logic */ },
                    onVoiceChange = { /* Logic */ }
                )
            }
        }
        windowManager.addView(composeView, params)
    }

    override fun onDestroy() {
        super.onDestroy()
        composeView?.let { windowManager.removeView(it) }
    }

    override fun onBind(intent: Intent?) = null
}
