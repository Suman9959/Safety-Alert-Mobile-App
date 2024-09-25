package com.kroger.classapp.data

import androidx.room.TypeConverter
import java.time.LocalDateTime

// Used for saving a date time object
class Converters {
    @TypeConverter
    fun toDateTime(dateTimeStr: String): LocalDateTime {
        return dateTimeStr.let { x -> LocalDateTime.parse(x) }
    }

    @TypeConverter
    fun fromDateTime(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }
}