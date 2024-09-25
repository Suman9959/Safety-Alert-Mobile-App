package com.kroger.classapp.helpers

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AppPermissions {
    // List of permissions
    private val permissionsList = listOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.SEND_SMS,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.ACCESS_NOTIFICATION_POLICY

    )


    // Function to request permissions
    fun requestPermissions(activity: Activity, requestCode: Int) {
        val permissionsToRequest = mutableListOf<String>()

        // Check which permissions are not granted and add them to the request list
        for (permission in permissionsList) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionsToRequest.add(permission)
            }
        }

        // Request permissions
        ActivityCompat.requestPermissions(
            activity,
            permissionsToRequest.toTypedArray(),
            requestCode
        )
    }
}