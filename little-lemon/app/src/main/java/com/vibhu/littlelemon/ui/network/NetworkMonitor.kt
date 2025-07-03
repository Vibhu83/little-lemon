package com.vibhu.littlelemon.ui.network

import android.content.Context
import kotlinx.coroutines.flow.Flow

object NetworkMonitor {
    private lateinit var observer: ConnectivityObserver

    fun initialize(context: Context) {
        observer = NetworkConnectivityObserver(context.applicationContext)
    }

    fun observe(): Flow<ConnectivityObserver.Status> {
        check(::observer.isInitialized) { "NetworkMonitor not initialized" }
        return observer.observe()
    }
}
