package com.kroger.classapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class EmergencyEvent(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "type") val type: String, // Use EventTypes class, else defaults medical image
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "contacts") val contacts: String,
    @ColumnInfo(name = "date") val date: LocalDateTime
)
