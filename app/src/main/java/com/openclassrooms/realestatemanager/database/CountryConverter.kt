package com.openclassrooms.realestatemanager.database

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.Country

class CountryConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromCountry(country: Country): Int {
            return country.ordinal
        }

        fun toCountry(int: Int): Country {
            return Country.values()[int]
        }
    }
}