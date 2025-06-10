package com.kirabium.relayance

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Custom Application class annotated with [HiltAndroidApp] to enable
 * dependency injection using Hilt throughout the app.
 *
 * Extend this class to perform app-wide initialization logic if needed.
 */
@HiltAndroidApp
class MyApplication : Application() {
    // You can add initialization logic here if necessary
}