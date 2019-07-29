package com.openclassrooms.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.Status

class StatusConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromStatus(status: Status): Int {
            return status.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toStatus(int: Int): Status {
            return Status.values()[int]
        }
    }
}