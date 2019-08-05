package com.openclassrooms.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.LocationOfInterest

class LocationOfInterestConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromLocationOfInterest(locationsOfInterest: LocationOfInterest): Int {
            return locationsOfInterest.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toLocationOfInterest(int: Int): LocationOfInterest {
            return LocationOfInterest.values()[int]
        }
    }
}