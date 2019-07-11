package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.Status

class StatusConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromStatus(status: Status): Int {
            return status.ordinal
        }

        fun toStatus(int: Int): Status {
            return Status.values()[int]
        }
    }
}