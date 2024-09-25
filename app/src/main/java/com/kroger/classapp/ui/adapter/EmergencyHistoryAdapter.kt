package com.kroger.classapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColor
import androidx.recyclerview.widget.RecyclerView
import com.kroger.classapp.model.EmergencyEvent
import com.kroger.classapp.R
import com.kroger.classapp.constants.EventTypes
import java.time.format.DateTimeFormatter

class EmergencyHistoryAdapter(private val emergencyEvents: List<EmergencyEvent>) :
    RecyclerView.Adapter<EmergencyHistoryAdapter.EmergencyHistoryHolder>() {

    class EmergencyHistoryHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        val emergencyEventType: TextView = itemView.findViewById(R.id.history_type)
        val emergencyEventDate: TextView = itemView.findViewById(R.id.history_date)
        val emergencyEventContacts: TextView = itemView.findViewById(R.id.history_contacts)
        val emergencyEventMessage: TextView = itemView.findViewById(R.id.history_message)
        val emergencyEventIcon: ImageView = itemView.findViewById(R.id.history_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyHistoryHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.emergency_event, parent, false)
        return EmergencyHistoryHolder(view)
    }

    override fun getItemCount(): Int {
        return emergencyEvents.size
    }

    override fun onBindViewHolder(holder: EmergencyHistoryHolder, position: Int) {
        val emergencyEvent = emergencyEvents[position]
        holder.emergencyEventType.text =
            holder.itemView.context.getString(R.string.history_type, emergencyEvent.type)
        holder.emergencyEventDate.text =
            holder.itemView.context.getString(
                R.string.history_date,
                emergencyEvent.date.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss"))
                    .toString()
            )
        holder.emergencyEventContacts.text =
            holder.itemView.context.getString(R.string.history_contacts, emergencyEvent.contacts)
        holder.emergencyEventMessage.text =
            holder.itemView.context.getString(R.string.history_message, emergencyEvent.message)
        holder.emergencyEventIcon.setImageResource(getEventType(emergencyEvent.type))
    }

    private fun getEventType(type: String): Int {
        return when (type) {
            EventTypes.ACCIDENT -> R.drawable.baseline_car_crash_24
            EventTypes.FIRE -> R.drawable.baseline_local_fire_department_24
            EventTypes.MEDICAL -> R.drawable.baseline_medical_services_24
            EventTypes.VIOLENCE -> R.drawable.baseline_settings_accessibility_24
            else -> R.drawable.baseline_add_alert_24 // just the default if no match
        }
    }
}