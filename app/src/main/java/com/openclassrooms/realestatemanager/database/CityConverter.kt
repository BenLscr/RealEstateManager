package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.City

class CityConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromCity(city: City): Int {
            return city.ordinal
        }

        fun toCity(int: Int): City {
            return City.values()[int]
        }
    }
}