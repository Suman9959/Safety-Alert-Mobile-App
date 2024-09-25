package com.kroger.classapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kroger.classapp.model.Contact
import com.kroger.classapp.model.EmergencyEvent

@Database(entities = [EmergencyEvent::class, Contact::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emergencyEventDao(): EmergencyEventDao

    abstract fun contactDao(): ContactDao

}