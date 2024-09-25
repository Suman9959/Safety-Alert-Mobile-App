package com.kroger.classapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kroger.classapp.model.Contact
import com.kroger.classapp.model.EmergencyEvent

@Dao
interface EmergencyEventDao {

    @Query("SELECT * FROM emergencyevent ORDER BY uid desc")
    fun getAll(): List<EmergencyEvent>

    @Insert
    fun insertAll(vararg users: EmergencyEvent)
}