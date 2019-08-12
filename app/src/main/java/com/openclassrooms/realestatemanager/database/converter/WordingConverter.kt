package com.openclassrooms.realestatemanager.database.converter

import androidx.room.TypeConverter
import com.openclassrooms.realestatemanager.models.Wording

class WordingConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromWording(wording: Wording): Int {
            return wording.ordinal
        }

        @TypeConverter
        @JvmStatic
        fun toWording(int: Int): Wording {
            return Wording.values()[int]
        }
    }
}