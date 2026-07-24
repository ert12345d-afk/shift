package com.bra.autosamurai

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Intent
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.text.SimpleDateFormat
import java.util.*

class AutoClickService : AccessibilityService() {
    private var keyword: String = ""
    private var targetTime: String = ""
    private var speed: Long = 500L
    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false

    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        keyword = intent?.getStringExtra("keyword") ?: ""
        targetTime = intent?.getStringExtra("time") ?: ""
        speed = intent?.getLongExtra("speed", 500L) ?: 500L
        isRunning = true
        startAutoClick()
        return START_STICKY
    }

    private fun startAutoClick() {
        handler.post(object : Runnable {
            override fun run() {
                if (!isRunning) return
                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
                if (currentTime == targetTime) {
                    performClick()
                }
                handler.postDelayed(this, speed)
            }
        })
    }

    private fun performClick() {
        val rootNode = rootInActiveWindow ?: return
        val nodes = rootNode.findAccessibilityNodeInfosByText(keyword)
        for (node in nodes) {
            if (node.isClickable) {
                node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                return
            }
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}
    override fun onInterrupt() { isRunning = false }
}
