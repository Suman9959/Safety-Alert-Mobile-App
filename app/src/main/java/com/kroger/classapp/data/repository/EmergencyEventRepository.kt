package com.kroger.classapp.data.repository

import com.kroger.classapp.data.EmergencyEventDao
import com.kroger.classapp.model.Contact
import com.kroger.classapp.model.EmergencyEvent
import javax.inject.Inject

class EmergencyEventRepository @Inject constructor(
    private val eventDao: EmergencyEventDao
) {
    fun getHistory() = eventDao.getAll()

    fun saveEmergencyEvent(event: EmergencyEvent) = eventDao.insertAll(event)
}