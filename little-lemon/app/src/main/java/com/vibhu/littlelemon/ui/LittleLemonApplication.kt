package com.vibhu.littlelemon.ui

import android.app.Application
import com.vibhu.littlelemon.ui.network.NetworkMonitor

class LittleLemonApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkMonitor.initialize(this)
    }
}