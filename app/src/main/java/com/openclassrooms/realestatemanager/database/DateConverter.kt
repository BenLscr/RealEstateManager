package com.openclassrooms.realestatemanager.database

import java.util.*
import androidx.room.TypeConverter

class DateConverter {
    companion object{
        @TypeConverter
        @JvmStatic
        fun fromDate(date: Date): Long {
            return date.time
        }

        @TypeConverter
        @JvmStatic
        fun toDate(long: Long): Date {
            return Date(long)
        }
    }
}