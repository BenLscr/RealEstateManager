package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.District

class DistrictConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromDistrict(district: District): Int {
            return district.ordinal
        }

        fun toDistrict(int: Int): District {
            return District.values()[int]
        }
    }
}