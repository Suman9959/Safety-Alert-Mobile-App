package com.kroger.classapp.helpers

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import android.telephony.SmsManager
import android.util.Log
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.kroger.classapp.constants.EventTypes
import com.kroger.classapp.model.Contact
import com.kroger.classapp.model.EmergencyEvent
import com.kroger.classapp.viewmodel.EmergencyHistoryViewModel
import java.time.LocalDateTime


// Class to handle emergency situations by sending alerts and logging events.
class EmergencyHandler(
    private val context: Context,
    private val historyViewModel: EmergencyHistoryViewModel,
    private val emergencyContactList: List<Contact>
) {

    // Location manager to access the device's GPS service.
    private var locationManager: LocationManager? = null

    init {
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    }

    // Main function to handle different types of emergencies.
    fun handleEmergency(eventType: String) {
        // Check if the required permissions are granted.
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.SEND_SMS
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Handle main event type with custom message or other types with a predefined message.
            if (eventType == EventTypes.MAIN) {
                requestCustomMessage { customMessage ->
                    requestLocationAndUpdate(eventType, customMessage)
                }
            } else {
                requestLocationAndUpdate(eventType, null)
            }
        } else {
            Log.e("Permission Error", "Required permissions not granted")
            showPermissionErrorDialog()
        }
    }

    // Request current location and update emergency contacts.
    private fun requestLocationAndUpdate(eventType: String, customMessage: String?) {
        try {
            locationManager?.requestSingleUpdate(LocationManager.GPS_PROVIDER, { location ->
                sendMessagesWithLocation(eventType, location, customMessage)
            }, null)
        } catch (ex: SecurityException) {
            Log.e("Location Error", "Failed to request location update", ex)
            showPermissionErrorDialog()
        }
    }


    // Prompt user to enter a custom emergency message.
    private fun requestCustomMessage(callback: (String) -> Unit) {
        val inputField = EditText(context)
        Handler(Looper.getMainLooper()).post {
            AlertDialog.Builder(context)
                .setTitle("Custom Message")
                .setMessage("Enter your emergency message:")
                .setView(inputField)
                .setPositiveButton("OK") { dialog, _ ->
                    callback(inputField.text.toString())
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
                .show()
        }
    }


    // Send emergency messages to all contacts with the current location.
    private fun sendMessagesWithLocation(
        eventType: String,
        location: Location,
        customMessage: String?
    ) {
        val googleMapsUrl = "https://maps.google.com/?q=${location.latitude},${location.longitude}"
        val message = if (customMessage.isNullOrEmpty()) createMessage(
            eventType,
            googleMapsUrl
        ) else "$customMessage\nLocation: $googleMapsUrl"

        emergencyContactList.forEach { contact ->
            SmsManager.getDefault().sendTextMessage(contact.number, null, message, null, null)
            Log.d("Message Sent", "Emergency message sent successfully to ${contact.name}")
        }

        saveEmergencyEvent(eventType, message, emergencyContactList)
    }


    // Display a dialog if necessary permissions are not granted.
    private fun showPermissionErrorDialog() {
        Handler(Looper.getMainLooper()).post {
            AlertDialog.Builder(context)
                .setTitle("Permission Required")
                .setMessage("This app requires SMS and location permissions to function properly for emergencies. Please enable them in the settings.")
                .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }


    // Create a formatted emergency message based on the event type.
    private fun createMessage(eventType: String, googleMapsUrl: String): String {
        return when (eventType) {
            EventTypes.FIRE -> "Fire Emergency! Location: $googleMapsUrl"
            EventTypes.MEDICAL -> "Medical Emergency! Location: $googleMapsUrl"
            EventTypes.ACCIDENT -> "Accident Emergency! Location: $googleMapsUrl"
            EventTypes.VIOLENCE -> "Violence Emergency! Location: $googleMapsUrl"
            else -> "Emergency! ,  Location: $googleMapsUrl"
        }
    }

    // Save the details of an emergency event to the history view model.
    private fun saveEmergencyEvent(type: String, message: String, contacts: List<Contact>) {
        val contactStr = mutableListOf<String>()
        for (contact in contacts) {
            contactStr.add(contact.name)
        }

        val emergencyEvent = EmergencyEvent(
            type = type,
            contacts = contactStr.joinToString(", "),
            message = message,
            date = LocalDateTime.now()
        )

        historyViewModel.saveEvent(emergencyEvent)
    }
}