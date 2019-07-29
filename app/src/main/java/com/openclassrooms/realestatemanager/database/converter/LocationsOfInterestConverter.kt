package com.openclassrooms.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.LocationOfInterest

private const val SEPARATOR = ","
class LocationsOfInterestConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromLocationsOfInterest(locationsOfInterest: List<LocationOfInterest>): String {
            return locationsOfInterest.map { it.ordinal }.joinToString(separator = SEPARATOR)
        }

        @TypeConverter
        @JvmStatic
        fun toLocationsOfInterest(string: String): List<LocationOfInterest> {
            return string.split(SEPARATOR).map { it.toInt()}.toList()
                    .map { LocationOfInterest.values()[it] }
        }
    }
}