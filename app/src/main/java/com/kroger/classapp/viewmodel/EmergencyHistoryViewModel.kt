package com.kroger.classapp.viewmodel

import androidx.lifecycle.ViewModel
import com.kroger.classapp.data.repository.EmergencyEventRepository
import com.kroger.classapp.model.Contact
import com.kroger.classapp.model.EmergencyEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmergencyHistoryViewModel @Inject constructor(
    private val emergencyEventRepository: EmergencyEventRepository
) : ViewModel() {
    fun getAllHistory(): List<EmergencyEvent> = emergencyEventRepository.getHistory()
    fun saveEvent(event: EmergencyEvent) = emergencyEventRepository.saveEmergencyEvent(event)
}