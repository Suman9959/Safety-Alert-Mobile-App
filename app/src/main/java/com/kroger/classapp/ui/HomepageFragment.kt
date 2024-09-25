package com.kroger.classapp.ui

import com.kroger.classapp.helpers.EmergencyHandler
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.kroger.classapp.R
import com.kroger.classapp.constants.EventTypes
import com.kroger.classapp.viewmodel.ContactViewModel
import com.kroger.classapp.viewmodel.EmergencyHistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomepageFragment : Fragment() {
    private val historyViewModel: EmergencyHistoryViewModel by activityViewModels()
    private val contactViewModel: ContactViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_homepage, container, false)

        view.findViewById<View>(R.id.emergency_button).setOnClickListener {
            handleEmergency(EventTypes.MAIN)
        }
        // Set click listeners for each emergency type
        view.findViewById<View>(R.id.fire_card).setOnClickListener {
            handleEmergency(EventTypes.FIRE)
        }

        view.findViewById<View>(R.id.medical_card).setOnClickListener {
            handleEmergency(EventTypes.MEDICAL)
        }

        view.findViewById<View>(R.id.road_card).setOnClickListener {
            handleEmergency(EventTypes.ACCIDENT)
        }

        view.findViewById<View>(R.id.violence_card).setOnClickListener {
            handleEmergency(EventTypes.VIOLENCE)
        }

        return view
    }

    private fun handleEmergency(eventType: String) {
        val emergencyHandler = EmergencyHandler(
            requireContext(), historyViewModel, contactViewModel.getEmergencyContacts()
        )
        emergencyHandler.handleEmergency(eventType)
        Toast.makeText(requireContext(), "Emergency triggered: $eventType", Toast.LENGTH_SHORT)
            .show()
    }
}